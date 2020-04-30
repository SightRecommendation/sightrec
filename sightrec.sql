CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL unique,
  `password` varchar(64) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `head_url` varchar(256) NOT NULL,
  `email` TEXT,
  `phone` TEXT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `user_id` INT NOT NULL,
  `sight_id` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `sight_index` (`sight_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL unique,
  `password` varchar(64) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `phone` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `sight` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `subject` TEXT,
  `name` TEXT NOT NULL,
  `province` TEXT NOT NULL,
  `city` TEXT NOT NULL,
  `location` TEXT NOT NULL,
  `image_url` JSON NOT NULL,
  `point` INT NOT NULL,
  `level` INT,
  `description` TEXT NOT NULL,
  `introduction` TEXT NOT NULL,
  `coordinate` TEXT NOT NULL,
  `heat` FLOAT NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `point_index` (`point` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `subject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `subject_name` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `subject_name_UNIQUE` (`subject_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `sight_subject_relation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sight_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `favorite` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sight_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `rating` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sight_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `rating` INT NOT NULL DEFAULT 0,
  `created_date` DATETIME NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;