-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for coding_hive_ticket_monster_2018_db
CREATE DATABASE IF NOT EXISTS `coding_hive_ticket_monster_2018_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `coding_hive_ticket_monster_2018_db`;

-- Dumping structure for table coding_hive_ticket_monster_2018_db.events
CREATE TABLE IF NOT EXISTS `events` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `location` varchar(100) NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `sold_out` tinyint(1) unsigned NOT NULL DEFAULT 0,
  `event_duration` datetime NOT NULL,
  `event_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `tickets_left` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table coding_hive_ticket_monster_2018_db.events: ~0 rows (approximately)
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
/*!40000 ALTER TABLE `events` ENABLE KEYS */;

-- Dumping structure for table coding_hive_ticket_monster_2018_db.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` tinyint(2) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roles_title` (`title`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table coding_hive_ticket_monster_2018_db.roles: ~2 rows (approximately)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
REPLACE INTO `roles` (`id`, `title`) VALUES
	(2, 'Admin'),
	(1, 'User');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table coding_hive_ticket_monster_2018_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` tinyint(2) unsigned NOT NULL COMMENT 'user role',
  `email` varchar(30) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `create_time` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `persons_role_id_roles` (`role_id`) USING BTREE,
  CONSTRAINT `persons_role_id_roles_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table coding_hive_ticket_monster_2018_db.users: ~0 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table coding_hive_ticket_monster_2018_db.user_events
CREATE TABLE IF NOT EXISTS `user_events` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `event_id` smallint(6) unsigned NOT NULL,
  `person_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `personid_person_fk_idx` (`person_id`),
  KEY `eventid_events_fk_idx` (`event_id`),
  CONSTRAINT `eventid_events_fk` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `personid_persons_fk` FOREIGN KEY (`person_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table coding_hive_ticket_monster_2018_db.user_events: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_events` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
