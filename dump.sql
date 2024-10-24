-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `value` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Jan Kowalski',1500.00),(2,'Anna Nowak',2300.50),(3,'Piotr Winglewski',1750.75),(4,'Katarzyna Prodkiewicz',3200.40),(5,'Tomasz Komin',4500.80),(6,'Agnieszka Lewandowska',2800.90),(7,'Piotr Mitkiewicz',1900.20),(8,'Magdalena Szym',3100.70),(9,'Michael Jones',4200.00),(10,'Zofia Darling',2700.60),(11,'Krzysztof Koza',3800.15),(12,'Barbara Jankowska',2950.30),(13,'Marcin Mazur',1400.50),(14,'Alicja Krawczyk',3500.75),(15,'Grzegorz Kaczmarek',2250.45),(16,'Joanna Piotrowska',2900.35),(17,'Rafa┼é Pawlak',1300.90),(18,'Ewa Grabowska',4100.40),(19,'Mariusz Michalski',3050.20),(20,'Monika Zetor',1600.60),(21,'Adam Krel',2000.00),(22,'Justyna Wieczorek',2150.85),(23,'Dariusz Baran',1100.50),(24,'Natalia Groska',1750.75),(25,'Mateusz Stopa',3250.10),(26,'Ewa Rutkowska',2400.95),(27,'Wojciech Dudek',3500.50),(28,'Patrycja Kubiak',2750.00),(29,'Artur Sadowski',1850.40),(30,'Olga Czapska',2200.65);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_search_info`
--

DROP TABLE IF EXISTS `person_search_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_search_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime(6) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `response_code` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_search_info`
--

LOCK TABLES `person_search_info` WRITE;
/*!40000 ALTER TABLE `person_search_info` DISABLE KEYS */;
INSERT INTO `person_search_info` VALUES (1,'2024-10-23 19:23:04.740609','Jan Kowalski',200),(2,'2024-10-23 19:26:03.500611','Jan Kowalski',200),(3,'2024-10-23 19:26:36.563621','Jan Kowalski',200),(4,'2024-10-23 19:29:11.717609','Jan Kowalski',200),(5,'2024-10-24 10:34:24.143474','Jan Kowalski',200),(6,'2024-10-24 10:34:58.037124','Malwina Kubiak',404),(7,'2024-10-24 10:35:02.945559','Patrycja Kubiak',200);
/*!40000 ALTER TABLE `person_search_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-24 13:48:52
