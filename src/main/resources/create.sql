CREATE TABLE IF NOT EXISTS `%s`
(
    `uuid`   VARCHAR(50)  NOT NULL ,
    `data`   TEXT  NOT NULL ,
    PRIMARY KEY (`uuid`),
    UNIQUE INDEX `uuid` (`uuid`)
    )
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;