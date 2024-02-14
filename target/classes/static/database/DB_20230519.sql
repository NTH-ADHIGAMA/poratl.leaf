-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: senseacre
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `accessories`
--

DROP TABLE IF EXISTS `accessories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accessories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `make` varchar(250) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `category` varchar(250) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `Date_Created` datetime DEFAULT NULL,
  `Date_Modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accessories`
--

LOCK TABLES `accessories` WRITE;
/*!40000 ALTER TABLE `accessories` DISABLE KEYS */;
INSERT INTO `accessories` VALUES (1,'kjbkj','jjknkjn','kjkjn',878,'jbjb',1,'2023-05-25 11:18:22','2023-05-25 11:18:22');
/*!40000 ALTER TABLE `accessories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clusters`
--

DROP TABLE IF EXISTS `clusters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clusters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `latitude` varchar(250) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `kml_path` varchar(250) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `dateOfCreation` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clusters`
--

LOCK TABLES `clusters` WRITE;
/*!40000 ALTER TABLE `clusters` DISABLE KEYS */;
/*!40000 ALTER TABLE `clusters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumers`
--

DROP TABLE IF EXISTS `consumers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consumers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  `type` varchar(250) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `phone` varchar(55) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `requirement` varchar(2000) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `record_status` tinyint(1) DEFAULT '1',
  `dateOfCreation` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumers`
--

LOCK TABLES `consumers` WRITE;
/*!40000 ALTER TABLE `consumers` DISABLE KEYS */;
/*!40000 ALTER TABLE `consumers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drones`
--

DROP TABLE IF EXISTS `drones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drones` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Model` varchar(100) DEFAULT NULL,
  `Make` varchar(250) DEFAULT NULL,
  `Year` int DEFAULT NULL,
  `Camera` varchar(100) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT '1',
  `Date_Created` datetime DEFAULT NULL,
  `Date_Modified` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drones`
--

LOCK TABLES `drones` WRITE;
/*!40000 ALTER TABLE `drones` DISABLE KEYS */;
INSERT INTO `drones` VALUES (1,'Sony','ABC',2023,'XYZ',1,'2023-05-24 12:05:22','2023-05-24 12:05:22'),(2,'LG','aaa',2023,'bbb',1,'2023-05-24 12:08:35','2023-05-24 12:08:35');
/*!40000 ALTER TABLE `drones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_category` varchar(45) DEFAULT NULL,
  `role_name` varchar(45) DEFAULT NULL,
  `role_code` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'platform','SuperAdmin','SADMIN','Super Admin',1,'2023-03-11 13:08:46','2023-03-11 13:08:46'),(2,'company','CompanyAdmin','ADMIN','Company Admin',1,'2023-03-11 13:08:46','2023-03-11 13:08:46'),(3,'company','RegionalManager','RGM','Regional Manager',1,'2023-03-11 13:08:46','2023-03-11 13:08:46'),(4,'company','ReportMaker','RM','Report Maker',1,'2023-03-11 13:08:46','2023-03-11 13:08:46'),(5,'company','Approver','APR','Approver',1,'2023-03-11 13:08:46','2023-03-11 13:08:46'),(6,'company','FieldOfficer','FO','Field Officer',1,'2023-03-11 13:08:46','2023-03-11 13:08:46');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_category_master`
--

DROP TABLE IF EXISTS `service_category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_category_master` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `description` varchar(225) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_category_master`
--

LOCK TABLES `service_category_master` WRITE;
/*!40000 ALTER TABLE `service_category_master` DISABLE KEYS */;
INSERT INTO `service_category_master` VALUES (1,'Arial Survey','ASRV','Arial Survey',1,'2023-05-13 18:55:46','2023-05-13 18:55:46'),(2,'PEST DETECTION','PSD','Pest Detection',1,'2023-05-13 18:56:10','2023-05-13 18:56:10'),(3,'Drone Survey','DSRV','Drone Survey',1,'2023-05-13 18:56:26','2023-05-13 18:56:26'),(4,'Spraying','SPRAY','Spraying',1,'2023-05-13 19:30:51','2023-05-13 19:30:51');
/*!40000 ALTER TABLE `service_category_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_providers`
--

DROP TABLE IF EXISTS `service_providers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_providers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `contact_name` varchar(45) DEFAULT NULL,
  `phone` varchar(55) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `pin` varchar(45) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `billing_cycle` varchar(45) DEFAULT NULL,
  `licences` int DEFAULT '0',
  `logo_path` varchar(250) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `record_status` tinyint(1) DEFAULT '1',
  `created_by` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `last_modified_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_providers`
--

LOCK TABLES `service_providers` WRITE;
/*!40000 ALTER TABLE `service_providers` DISABLE KEYS */;
INSERT INTO `service_providers` VALUES (1,'SA','SenseAcre','Mahesh','9849396669','mahesh@nthphase.com','Hyderabad','Telangana','500081','Nizampet','ANNUALLY',100,'companyLogos/20221208130632.jpg','1',1,1,'2022-12-08 13:06:33','2022-12-08 13:06:33',1);
/*!40000 ALTER TABLE `service_providers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services_master`
--

DROP TABLE IF EXISTS `services_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services_master` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `description` varchar(225) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services_master`
--

LOCK TABLES `services_master` WRITE;
/*!40000 ALTER TABLE `services_master` DISABLE KEYS */;
INSERT INTO `services_master` VALUES (1,'Service1','SC1','New Service','PSD',1,'2023-05-16 17:36:54','2023-05-16 17:36:54'),(2,'Service2','SC2','New Service','PSD',1,'2023-05-16 17:36:54','2023-05-16 17:36:54'),(3,'PEST DETECTION','ARS','kjkjklknlk','ASRV',1,'2023-05-19 18:33:52','2023-05-19 18:33:52'),(4,'rcb','srh','hgc','ASRV',1,'2023-05-22 19:02:37','2023-05-22 19:02:37');
/*!40000 ALTER TABLE `services_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sites`
--

DROP TABLE IF EXISTS `sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `location_name` varchar(100) DEFAULT NULL,
  `customer` varchar(250) DEFAULT NULL,
  `latitude` varchar(250) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `cluster` varchar(100) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `landmark` varchar(100) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `dateOfCreation` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sites`
--

LOCK TABLES `sites` WRITE;
/*!40000 ALTER TABLE `sites` DISABLE KEYS */;
/*!40000 ALTER TABLE `sites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_access_settings`
--

DROP TABLE IF EXISTS `user_access_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_access_settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `user_type` varchar(250) DEFAULT NULL,
  `services` varchar(250) DEFAULT NULL,
  `apps` varchar(55) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_access_settings`
--

LOCK TABLES `user_access_settings` WRITE;
/*!40000 ALTER TABLE `user_access_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_access_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `user_type` varchar(45) DEFAULT NULL,
  `parent_id` int DEFAULT '0',
  `role` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `first_login` tinyint(1) DEFAULT '0',
  `first_login_date` datetime DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `record_status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'sadmin','admin123','superuser',0,'admin','admin@poratl.com','9849396669','mahesh','p','123456789',0,NULL,NULL,NULL,NULL,'1',1),(2,'cmpadmin','admin123','applicationuser',1,'admin','admin@company.com','9849396669','mahesh','p','123456789',0,NULL,NULL,NULL,NULL,'1',1);
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

-- Dump completed on 2023-06-05 13:07:27

--Projects table added by Ramakrishna

CREATE TABLE Projects (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    project_name VARCHAR(255),
    project_code VARCHAR(255),
    description TEXT,
    service VARCHAR(255),
    consumer VARCHAR(255),
    site_location VARCHAR(255),
    address TEXT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50),
    created_date datetime DEFAULT NULL,
    last_modified_date datetime DEFAULT NULL
);


CREATE TABLE `mediafiles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `media_category` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `filename` varchar(63) DEFAULT NULL,
  `filetype` varchar(45) DEFAULT NULL,
  `fileExt` varchar(45) DEFAULT NULL,
  `filepath` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `thumbnail` longblob,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `company` varchar(45) DEFAULT NULL,
  `created_by` int DEFAULT '0',
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3;

