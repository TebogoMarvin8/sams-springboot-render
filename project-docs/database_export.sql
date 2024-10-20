-- --------------------------------------------------------
-- Host:                         thh2lzgakldp794r.cbetxkdyhwsb.us-east-1.rds.amazonaws.com
-- Server version:               8.0.35 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for wp5vf4k31d28kt0g
CREATE DATABASE IF NOT EXISTS `wp5vf4k31d28kt0g` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `wp5vf4k31d28kt0g`;

-- Dumping structure for table wp5vf4k31d28kt0g.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_hawikyhwwfvbnog5byokutpff` (`user_id`) USING BTREE,
  CONSTRAINT `FK8ahhk8vqegfrt6pd1p9i03aej` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.admin: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.attendance
CREATE TABLE IF NOT EXISTS `attendance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) DEFAULT NULL,
  `class_id` bigint DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKmma65jopifnpb1yjpd97ss9ml` (`class_id`) USING BTREE,
  KEY `FKnq6vm31it076obtjf2qp5coim` (`student_id`) USING BTREE,
  CONSTRAINT `FKmma65jopifnpb1yjpd97ss9ml` FOREIGN KEY (`class_id`) REFERENCES `clazz` (`id`),
  CONSTRAINT `FKnq6vm31it076obtjf2qp5coim` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.attendance: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.class_student
CREATE TABLE IF NOT EXISTS `class_student` (
  `class_id` bigint NOT NULL,
  `student_id` bigint NOT NULL,
  KEY `FKehoe5qmc3ro5nxlcwwa7kf2et` (`student_id`) USING BTREE,
  KEY `FKivbopkojry3giel93498wuvg9` (`class_id`) USING BTREE,
  CONSTRAINT `FKehoe5qmc3ro5nxlcwwa7kf2et` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKivbopkojry3giel93498wuvg9` FOREIGN KEY (`class_id`) REFERENCES `clazz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.class_student: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.clazz
CREATE TABLE IF NOT EXISTS `clazz` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  `instructor_id` bigint DEFAULT NULL,
  `day_of_week` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `end_time` time(6) DEFAULT NULL,
  `start_time` time(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKl717r2ytsugssmoolftdhi3ae` (`course_id`) USING BTREE,
  KEY `FKa1wwpw8bo2pxg238b9c67w46o` (`instructor_id`) USING BTREE,
  CONSTRAINT `FKa1wwpw8bo2pxg238b9c67w46o` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  CONSTRAINT `FKl717r2ytsugssmoolftdhi3ae` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.clazz: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.course
CREATE TABLE IF NOT EXISTS `course` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.course: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.instructor
CREATE TABLE IF NOT EXISTS `instructor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_cr0g7gh88hv7sfdx9kqbrbiyw` (`user_id`) USING BTREE,
  CONSTRAINT `FKpyhf3fgtvlqq630u3697wsmre` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.instructor: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.rfidtag
CREATE TABLE IF NOT EXISTS `rfidtag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.rfidtag: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.student
CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `face_descriptors` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `rfid_tag_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_btsuhi5y70tsh6y6r85dryiw` (`rfid_tag_id`) USING BTREE,
  UNIQUE KEY `UK_bkix9btnoi1n917ll7bplkvg5` (`user_id`) USING BTREE,
  CONSTRAINT `FK7btlsj442nr15ujh3l6ag9hb5` FOREIGN KEY (`rfid_tag_id`) REFERENCES `rfidtag` (`id`),
  CONSTRAINT `FKk5m148xqefonqw7bgnpm0snwj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.student: ~0 rows (approximately)

-- Dumping structure for table wp5vf4k31d28kt0g.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `reset_token_expiry` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table wp5vf4k31d28kt0g.user: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
