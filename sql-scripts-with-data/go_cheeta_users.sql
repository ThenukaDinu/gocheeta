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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(10000) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `first_name` varchar(1000) NOT NULL,
  `last_name` varchar(1000) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  `address` varchar(300) DEFAULT NULL,
  `avatar` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'testadmin1@admin.edu',NULL,'1993-04-15','Admin up','Test01 up','0777123124','testadmin1@admin.edu','No 100, Nugegoda, colombo','FB_IMG_1630175981935.jpg'),(2,'testuser@gmail.com','$2a$10$ZL4.dlV2f.qugiE/h1dg2OvMdOa7wqmBM28hLB2FP/8MvppXDdC9a','1996-07-11','Jhon','Doe','0777123123','testuser@gmail.com','No 5, Nugegoda, colombo',NULL),(9,'driver.test@yopmail.com','$2a$10$EnZP0KfDXDauO.aD4euoc..q1N1zVp25S/EAgNOWCFFiMfM1bijtO','1993-03-15','Admin updated','Test01 updated a','0777123129','driver.test@yopmail.com','No 5, Nugegoda, colombo updated',NULL),(17,'driver77.test@yopmail.com','$2a$10$Uj8vuGBhv/Mj2UJNvpg9rODMcPbxSz7hSLfZtugvtAh6uN6F0EQqO','1998-03-15','Admin','Test77','0777123177','driver77.test@yopmail.com','No 33, Nugegoda, colombo',NULL),(19,'driver66.test@yopmail.com','$2a$10$DhdIAPz3GK88ONDT.qFAiuTBzT5jxd9nUAOy94.d6mwixTZiyRnWG','1998-03-15','Admin','Test66','0777123166','driver66.test@yopmail.com','No 66, Nugegoda, colombo',NULL),(24,'gocheetatest01@ypmail.com','$2a$10$1dZ/v45D0hGfUizsTXfVCuQORNn1a2A8rUB7chx.kAp0aeahmn1BG','1998-08-01','','','0777123123','gocheetatest01@ypmail.com','Colombo 01',NULL),(25,'gocheetatest02@yopmail.com','$2a$10$ozT6DXab5uvFxEXCTcI0ne/2OX8Oe7ahNNm64HtdK.EjaVmpot1Au','1998-08-01','Test User 02','Test User 02','0777123123','gocheetatest02@yopmail.com','Colombo 01',NULL),(34,'testadmin2@admin.edu','$2a$10$opGDtY1aTGvjAdhu4g5kW.aPSiyFOucZ13MwHEjVUjQsHGFMEv8Ji','1993-04-15','Admin up','Test01 up','0777123124','testadmin2@admin.edu','No 100, Nugegoda, colombo','FB_IMG_1630175981935.jpg'),(35,'testadmin3@admin.edu','$2a$10$H0wbRrNpjj/SEg2o6xz7f.xMDq.Ad0oLJAX9zaLhIsN9PilZMlJRO','1993-03-15','Admin','Test01','0777123123','testadmin3@admin.edu','No 5, Nugegoda, colombo',NULL),(36,'testuser10gocheeta@yopmail.com','$2a$10$R4HPwHsUUKRIjGcKit2Yle42YOpCKLfkujTR9SmEV6vfjNSeg/kbG',NULL,'test user 10','test user 10',NULL,'testuser10gocheeta@yopmail.com',NULL,NULL),(37,'newdriver1@yopmail.com','$2a$10$IhsEdbgc0.MjPyyN9GDwneZ4XMEyfomZXo9adeBLBaxUOVxtRk5tK','1994-08-01','new Driver 01','ln new driver 01','0771234346','newdriver1@yopmail.com','no 11, Nugegoda, Colombo',NULL),(38,'newdriver2@yopmail.com','$2a$10$qjablBnEcgOfwUYYzpAisOeWBiKPayAp2Ta.Z5bdXPUusUvAKp2gu','1995-08-01','new Driver 02','ln new driver 02','0776724776','newdriver2@yopmail.com','no 12, Nugegoda, Colombo',NULL),(54,'testdriver123@yopmail.com','$2a$10$uZKek7hGvx582b7oz2baYOk4CwLUxogyi0ay5.UfoZsMiF2kSIoJ.','2022-09-01','Test Driver delete test','Test Driver delete test','0777237465','testdriver123@yopmail.com','test 123',NULL),(56,'testdriver567@yopmail.com','$2a$10$PDyFmjV3mCB7IuNWLF1wlu78DjmDA3mhbntT7kmJodSnU/eAwWXm2','1986-05-01','Test Driver delete test 567','Test Driver delete test 567','0777237888','testdriver567@yopmail.com','test 5678',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
