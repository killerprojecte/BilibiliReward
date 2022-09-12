package flyproject.bili;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static flyproject.bili.Color.color;

public class BiliCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1){
            if (sender.isOp()){
                if (args[0].equals("force")){

                } else if (args[0].equals("reload")){

                } else {
                    sendHelp(sender);
                }
            } else {
                sendHelp(sender);
            }
        } else if (args.length==2){

        } else {
            sendHelp(sender);
        }
        return false;
    }

    private static void sendHelp(CommandSender sender){
        sender.sendMessage(color("&6&lBilibili Reward"));
        sender.sendMessage(color("&e/breward bind <b站id> ———— 绑定您的b站用户id"));
        if (sender.isOp()){
            sender.sendMessage(color("&e/breward force ———— 强制刷新"));
            sender.sendMessage(color("&e/breward reload ———— 重载配置"));
        }
    }
}
