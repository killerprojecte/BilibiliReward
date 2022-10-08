package flyproject.bili;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MySQL {
    public static boolean ENABLED = false;
    public static boolean isclose = false;

    public static HikariDataSource dataSource;
    private static String DATABASE;
    private static String TABLE = "bilibilireward";

    public static void setUP() {
        ENABLED = true;
        isclose = false;
        FileConfiguration cfg = BilibiliReward.instance.getConfig();
        HikariConfig config = new HikariConfig();
        config.setPoolName(BilibiliReward.instance.getName());
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver);
        } catch (Exception ignored) {
            driver = "com.mysql.jdbc.Driver";
            System.err.println("Driver class '" + driver + "' not found! Falling back to legacy MySQL driver (com.mysql.jdbc.Driver)");
        }
        config.setDriverClassName(driver);
        config.setUsername(cfg.getString("storage.username"));
        config.setPassword(cfg.getString("storage.password"));
        Properties properties = new Properties();
        String jdbcUrl = "jdbc:mysql://" + cfg.getString("storage.host") + ':' +
                cfg.getString("storage.port") + '/' + cfg.getString("storage.database") + "?autoReconnect=true";
        DATABASE = cfg.getString("storage.database");
        TABLE = cfg.getString("storage.table");
        properties.setProperty("useSSL", cfg.getString("storage.useSSL"));
        config.setConnectionTestQuery("SELECT 1");
        config.setMaximumPoolSize(1);
        properties.setProperty("date_string_format", "yyyy-MM-dd HH:mm:ss");
        config.setJdbcUrl(jdbcUrl);
        config.setDataSourceProperties(properties);
        dataSource = new HikariDataSource(config);
        try {
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTables() throws SQLException {
        String file = "/create.sql";
        try (InputStream in = BilibiliReward.instance.getClass().getResourceAsStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement()) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) continue;
                builder.append(line);
                if (line.endsWith(";")) {
                    String sql = builder.toString();
                    stmt.addBatch(String.format(sql, TABLE));
                    builder = new StringBuilder();
                }
            }
            stmt.executeBatch();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static UUID getPlayer(String data) {
        String sql = "SELECT `uuid`, `data` FROM `%s`.`%s` WHERE  `data`=?;";
        sql = String.format(sql, DATABASE, TABLE);
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, data);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    return UUID.fromString(uuid);
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return null;
    }

    public static void savePlayer(String data, String uuid) {
        String sql = "INSERT IGNORE INTO `%s`.`%s` (`uuid`, `data`) VALUES(?,?) ON DUPLICATE KEY UPDATE `data`=?;";
        sql = String.format(sql, DATABASE, TABLE);
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            stmt.setString(1, uuid);
            stmt.setString(2, data);
            stmt.setString(3, data);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static String getData(String UUID) {
        String sql = "SELECT * FROM `%s`.`%s` WHERE `uuid`=" + "'" + UUID + "'" + ";";
        sql = String.format(sql, DATABASE, TABLE);
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<UUID, String> getAll() {
        String sql = "SELECT * FROM `%s`.`%s`;";
        sql = String.format(sql, DATABASE, TABLE);
        Map<UUID, String> map = new HashMap<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    map.put(UUID.fromString(resultSet.getString("uuid")), resultSet.getString("data"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void close() {
        if (isclose) return;
        if (dataSource != null) {
            dataSource.close();
            isclose = true;
        }
    }
}
