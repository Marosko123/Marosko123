CREATE DATABASE  IF NOT EXISTS `myauction` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `myauction`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: myauction
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `actual_unit`
--

DROP TABLE IF EXISTS `actual_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actual_unit` (
  `unitType` varchar(45) NOT NULL,
  `unitPrice` int NOT NULL,
  `lastBid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`unitType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actual_unit`
--

LOCK TABLES `actual_unit` WRITE;
/*!40000 ALTER TABLE `actual_unit` DISABLE KEYS */;
INSERT INTO `actual_unit` VALUES ('storageUnits.CommonUnit',0,NULL);
/*!40000 ALTER TABLE `actual_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sold_units`
--

DROP TABLE IF EXISTS `sold_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sold_units` (
  `unitId` int NOT NULL AUTO_INCREMENT,
  `unitType` varchar(45) NOT NULL,
  `soldTo` varchar(45) NOT NULL,
  `unitPrice` int NOT NULL,
  `unitValue` int NOT NULL,
  `items` varchar(1000) NOT NULL,
  PRIMARY KEY (`unitId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sold_units`
--

LOCK TABLES `sold_units` WRITE;
/*!40000 ALTER TABLE `sold_units` DISABLE KEYS */;
INSERT INTO `sold_units` VALUES (8,'storageUnits.CommonUnit','Bot-1510715629',100,144,'\nCommon items in unit: 16\nShoes, Bone, Bucket, Shoes, Tin cans, Chair, Bucket, Bucket, Iron, Calculator, Shoes, Mask, Iron, Iron hoe, Bone, Iron, \nCommon items in unit: 16\nShoes, Bone, Bucket, Shoes, Tin cans, Chair, Bucket, Bucket, Iron, Calculator, Shoes, Mask, Iron, Iron hoe, Bone, Iron, '),(9,'storageUnits.CommonUnit','Bot1612651486',150,181,'\nCommon items in unit: 22\nDoll, Doll, Calculator, Calculator, Cards, Chair, Doll, Tin cans, Bucket, Chair, Cards, Cards, Iron, Bucket, Bottles, Mask, Doll, Calculator, Bike, Bucket, Chair, Tin cans, \nCommon items in unit: 22\nDoll, Doll, Calculator, Calculator, Cards, Chair, Doll, Tin cans, Bucket, Chair, Cards, Cards, Iron, Bucket, Bottles, Mask, Doll, Calculator, Bike, Bucket, Chair, Tin cans, '),(10,'storageUnits.EpicUnit','Bot-1930934841',1400,2870,'\nCommon items in unit: 23\nDoll, Calculator, Calculator, Bottles, Shoes, Chair, Bike, Chair, Bone, Cards, Shoes, Iron hoe, Iron, Cards, Chair, Iron, Bone, Bucket, Iron hoe, Doll, Calculator, Iron, Chair, \n\nRare items in unit: 4\nWeights, Washingmashine, Tires, Washingmashine, \n\nEpic items in unit:       1 \nDead heaven star, \nCommon items in unit: 23\nDoll, Calculator, Calculator, Bottles, Shoes, Chair, Bike, Chair, Bone, Cards, Shoes, Iron hoe, Iron, Cards, Chair, Iron, Bone, Bucket, Iron hoe, Doll, Calculator, Iron, Chair, \n\nRare items in unit: 4\nTires, Phone, Windows Licence, Phone, \n\nEpic items in unit:       3 \nJedays lighting sword, Gombikovas wheelie, 1 000 000 Shiba tokens, '),(11,'storageUnits.EpicUnit','Bot-1251619273',2800,5256,'\nCommon items in unit: 11\nDoll, Chair, Iron hoe, Chair, Dry rolls, Mask, Bone, Bike, Cards, Doll, Mask, \n\nRare items in unit: 4\nWashingmashine, Xbox360, PlayStation4, Snowboard, \n\nEpic items in unit:       3 \nDead heaven star, Jetpack, Thanos glove, \nCommon items in unit: 11\nDoll, Chair, Iron hoe, Chair, Dry rolls, Mask, Bone, Bike, Cards, Doll, Mask, \n\nRare items in unit: 6\nPhone, Phone, Weights, PlayStation4, Weights, PlayStation4, \n\nEpic items in unit:       1 \n1 Ethereum, '),(12,'storageUnits.CommonUnit','Bot-1371758961',150,84,'\nCommon items in unit: 6\nIron hoe, Shoes, Calculator, Bike, Mask, Cards, \nCommon items in unit: 6\nIron hoe, Shoes, Calculator, Bike, Mask, Cards, '),(13,'storageUnits.CommonUnit','Bot-586158900',300,121,'\nCommon items in unit: 16\nBike, Shoes, Dry rolls, Shoes, Mask, Calculator, Doll, Shoes, Chair, Bucket, Cards, Chair, Tin cans, Bike, Dry rolls, Calculator, \nCommon items in unit: 16\nBike, Shoes, Dry rolls, Shoes, Mask, Calculator, Doll, Shoes, Chair, Bucket, Cards, Chair, Tin cans, Bike, Dry rolls, Calculator, '),(14,'storageUnits.CommonUnit','Bot1042539710',350,117,'\nCommon items in unit: 10\nChair, Shoes, Shoes, Bone, Doll, Doll, Shoes, Chair, Doll, Mask, \nCommon items in unit: 10\nChair, Shoes, Shoes, Bone, Doll, Doll, Shoes, Chair, Doll, Mask, '),(15,'storageUnits.RareUnit','Bot1492111167',500,376,'\nCommon items in unit: 12\nMask, Chair, Cards, Bottles, Chair, Bike, Bike, Chair, Chair, Doll, Bone, Iron, \n\nRare items in unit: 4\nWindows Licence, Windows Licence, Phone, MP4-player, \nCommon items in unit: 12\nMask, Chair, Cards, Bottles, Chair, Bike, Bike, Chair, Chair, Doll, Bone, Iron, \n\nRare items in unit: 7\nTires, Weights, Tires, Windows Licence, Windows Licence, Laptop, Snowboard, ');
/*!40000 ALTER TABLE `sold_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `moneyBalance` int NOT NULL,
  `unitsBought` int NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin',99996243,32),(6,'Maros','123Maros!',5000,0),(7,'User1','123User!',3750,0),(8,'user2','User2!',7916,0),(9,'user3','123User!',6250,0),(10,'IgorBajza','8954Misa!',1000,0),(11,'majko','mojk5112!C',10000,0),(12,'igor','123Igor!',5416,0),(13,'Maros123','123Maros!',8333,0),(14,'fiitkar','123456aA@',2500,0),(15,'Bea','Piatok@123',107,0),(16,'samuel','samuelS2!',2218,0),(17,'marosko','Maros123!',3750,0),(18,'123Ferko','Ferko!123',4166,0),(19,'jurko','123Jurkoi!',9333,0),(20,'Johny','123Johny123!',6357,0),(21,'marko','123Marko!',9607,1),(22,'marko2','123Marko!',8514,6),(23,'adminko','Adminko123!',0,0),(24,'marosko147','123Maros!',1000,0),(25,'uaosnfpa','sdga16SA!D',4583,0),(26,'DsaSucks','DsaSucks123!',7083,0),(27,'muhamad','Muhamada123!',1250,0),(28,'ookii','123Oki!',850,1),(29,'OOPTest','OOPTest123!',9968,1),(30,'jurko123','Jurko123!',3750,0),(31,'jurkko','jurko',1000,0),(32,'oop','1223P',1000,0),(33,'user48','user48!A',1000,0),(34,'adminko123','adminko123!A',10000,0),(35,'random','123Random!',1000,0);
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

-- Dump completed on 2022-05-14 17:54:38
