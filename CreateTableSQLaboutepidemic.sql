CREATE DATABASE `epidemic` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `area` (
  `area_id` int NOT NULL,
  `area_name` varchar(45) NOT NULL,
  `confirm_add` int NOT NULL DEFAULT '0',
  `confirm` int NOT NULL DEFAULT '0',
  `now_confirm` int NOT NULL DEFAULT '0',
  `dead` int NOT NULL DEFAULT '0',
  `heal` int NOT NULL DEFAULT '0',
  `update_time` varchar(45) NOT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE `city` (
  `city_id` int NOT NULL,
  `city_name` varchar(30) NOT NULL,
  `confirm_add` int NOT NULL,
  `confirm` int NOT NULL,
  `now_confirm` int NOT NULL,
  `dead` int NOT NULL,
  `heal` int NOT NULL,
  `province_name` varchar(24) NOT NULL,
  `update_time` varchar(45) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
