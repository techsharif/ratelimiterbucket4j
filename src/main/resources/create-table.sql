CREATE TABLE `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL UNIQUE ,
    `api_key` VARCHAR(50) NOT NULL UNIQUE,
	`limits` TEXT DEFAULT NULL,
	PRIMARY KEY (`id`)
)
ENGINE=InnoDB;
