-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: go_cheeta
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) DEFAULT NULL,
  `trip_placed_time` datetime DEFAULT NULL,
  `trip_scheduled_time` datetime DEFAULT NULL,
  `trip_start_time` datetime DEFAULT NULL,
  `trip_finished_time` datetime DEFAULT NULL,
  `trip_canceled_time` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '0',
  `pick_up_location` varchar(100) NOT NULL,
  `drop_off_location` varchar(100) NOT NULL,
  `driver_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `vehicle_id` bigint DEFAULT NULL,
  `distance` decimal(10,2) DEFAULT '0.00',
  `duration` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_booked_user_id_idx` (`user_id`),
  KEY `fk_booked_driver_id_idx` (`driver_id`),
  KEY `fk_booked_vehicle_id_idx` (`vehicle_id`),
  CONSTRAINT `fk_booked_driver_id` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`),
  CONSTRAINT `fk_booked_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_booked_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,0.00,'2022-08-14 11:00:00','2022-08-15 11:00:00',NULL,NULL,NULL,0,'Nugegoda','Maharagama',5,1,1,0.00,0.00),(2,0.00,'2022-08-14 11:30:00','2022-08-16 14:30:00',NULL,NULL,NULL,0,'Delkada','Rajagiriya',11,2,2,0.00,0.00),(3,0.00,'2022-08-14 11:24:00','2022-08-17 16:15:00',NULL,NULL,NULL,0,'Gampaha','Kohuwala',12,2,12,0.00,0.00),(7,0.00,'2022-08-14 11:23:54','2022-08-16 18:00:00',NULL,NULL,NULL,0,'Kotte','Colombo 07',12,2,12,0.00,0.00),(8,0.00,'2022-09-18 10:58:00','2022-08-16 18:00:00',NULL,NULL,NULL,0,'Kotte','Colombo 07',12,2,12,0.00,0.00),(9,0.00,'2022-09-18 11:13:31','2022-08-16 18:00:00',NULL,NULL,NULL,0,'Nugegoda, Sri Lanka','Embuldeniya, Nugegoda, Sri Lanka',23,34,2,1.60,6.00),(10,0.00,'2022-09-18 11:36:23','2022-09-21 16:30:00',NULL,NULL,NULL,1,'Kelaniya, Peliyagoda, Sri Lanka','Nugegoda, Sri Lanka',23,34,8,14.10,34.00);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-20  1:23:08
