-- MySQL dump 10.13  Distrib 5.7.11, for osx10.9 (x86_64)
--
-- Host: localhost    Database: warehouse_db
-- ------------------------------------------------------
-- Server version	5.7.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `log_tb`
--

DROP TABLE IF EXISTS `log_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `operation_name` varchar(30) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `log_tb_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_tb` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_tb`
--

LOCK TABLES `log_tb` WRITE;
/*!40000 ALTER TABLE `log_tb` DISABLE KEYS */;
INSERT INTO `log_tb` VALUES (4,4,2,'modify_order','2016-02-21 22:58:20'),(8,7,2,'validate_order','2016-02-21 23:38:51'),(9,7,2,'validate_order','2016-02-21 23:39:11'),(10,7,2,'validate_order','2016-02-21 23:39:29'),(11,8,3,'create_order','2016-02-21 23:46:16'),(13,10,3,'create_order','2016-02-21 23:48:11'),(14,10,3,'validate_order','2016-02-21 23:48:18'),(15,1,3,'modify_order','2016-02-21 23:48:25'),(16,10,3,'complete_order','2016-02-21 23:48:34'),(22,11,2,'approve_order','2016-02-21 23:56:00'),(23,12,2,'modify_order','2016-02-21 23:56:03'),(24,1,2,'complete_order','2016-02-21 23:56:06'),(25,16,3,'create_order','2016-02-22 00:25:18'),(27,1,2,'approve_order','2016-02-22 23:17:03'),(28,4,2,'approve_order','2016-02-23 00:16:42'),(29,13,2,'modify_order','2016-02-23 00:16:59'),(30,15,2,'approve_order','2016-02-23 00:21:19'),(31,12,2,'approve_order','2016-02-23 00:24:17'),(32,8,2,'approve_order','2016-02-23 00:36:55');
/*!40000 ALTER TABLE `log_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_tb`
--

DROP TABLE IF EXISTS `operation_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_tb`
--

LOCK TABLES `operation_tb` WRITE;
/*!40000 ALTER TABLE `operation_tb` DISABLE KEYS */;
INSERT INTO `operation_tb` VALUES (1,'create_order'),(2,'view_order'),(3,'approve_order'),(4,'complete_order');
/*!40000 ALTER TABLE `operation_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_tb`
--

DROP TABLE IF EXISTS `order_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `items` varchar(50) DEFAULT NULL,
  `is_approved` tinyint(1) DEFAULT NULL,
  `is_completed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_tb`
--

LOCK TABLES `order_tb` WRITE;
/*!40000 ALTER TABLE `order_tb` DISABLE KEYS */;
INSERT INTO `order_tb` VALUES (1,'List item 1is modified',1,1),(2,'List item 1',0,0),(3,'List item -1292582788',0,0),(4,'New list items',1,0),(5,'List item 388190423',0,0),(6,'List item -2127688476',0,0),(7,'List item 25',1,0),(8,'List item 74',1,0),(9,'List item 71',0,0),(10,'List item 56',1,1),(11,'List item 19',1,0),(12,'List item 41is modified',1,0),(13,'List item 25is modified',0,0),(14,'List item 67',0,0),(15,'List item 42',1,0),(16,'List item 3',0,0);
/*!40000 ALTER TABLE `order_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_tb`
--

DROP TABLE IF EXISTS `permission_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_tb` (
  `role_id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  KEY `role_id` (`role_id`),
  KEY `operation_id` (`operation_id`),
  CONSTRAINT `permission_tb_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role_tb` (`id`),
  CONSTRAINT `permission_tb_ibfk_2` FOREIGN KEY (`operation_id`) REFERENCES `operation_tb` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_tb`
--

LOCK TABLES `permission_tb` WRITE;
/*!40000 ALTER TABLE `permission_tb` DISABLE KEYS */;
INSERT INTO `permission_tb` VALUES (1,2),(1,3),(2,1),(2,2),(2,4);
/*!40000 ALTER TABLE `permission_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_tb`
--

DROP TABLE IF EXISTS `role_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_tb`
--

LOCK TABLES `role_tb` WRITE;
/*!40000 ALTER TABLE `role_tb` DISABLE KEYS */;
INSERT INTO `role_tb` VALUES (1,'Manager'),(2,'Employee');
/*!40000 ALTER TABLE `role_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_tb`
--

DROP TABLE IF EXISTS `user_role_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_tb` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_tb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`id`),
  CONSTRAINT `user_role_tb_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role_tb` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_tb`
--

LOCK TABLES `user_role_tb` WRITE;
/*!40000 ALTER TABLE `user_role_tb` DISABLE KEYS */;
INSERT INTO `user_role_tb` VALUES (2,1),(3,2);
/*!40000 ALTER TABLE `user_role_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tb`
--

DROP TABLE IF EXISTS `user_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tb`
--

LOCK TABLES `user_tb` WRITE;
/*!40000 ALTER TABLE `user_tb` DISABLE KEYS */;
INSERT INTO `user_tb` VALUES (2,'Mai Nguyen','mainguyen','280189'),(3,'Minh Nguyen','minhxu','12345');
/*!40000 ALTER TABLE `user_tb` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-23  1:54:02
