-- MariaDB dump 10.19  Distrib 10.4.22-MariaDB, for Win64 (AMD64)
--
-- Host: 195.235.211.197    Database: 
-- ------------------------------------------------------
-- Server version	10.3.34-MariaDB-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `pri4URestaurant`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pri4URestaurant` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `pri4URestaurant`;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factura` (
  `idInvoice` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `idPedido` int(11) NOT NULL,
  `iva` decimal(10,2) NOT NULL,
  `totalInvoice` decimal(10,2) NOT NULL,
  `date` date NOT NULL,
  `method` varchar(25) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`idInvoice`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `idPedido` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `status` varchar(25) COLLATE utf8_spanish2_ci NOT NULL,
  `date` datetime NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idPedido`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` (`idPedido`, `idUser`, `status`, `date`, `total`) VALUES (1,1,'Delivered','2022-05-26 20:10:12',44.71),(2,1,'Pending','2022-05-26 20:10:43',48.35);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidoproductos`
--

DROP TABLE IF EXISTS `pedidoproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidoproductos` (
  `idPedido` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidoproductos`
--

LOCK TABLES `pedidoproductos` WRITE;
/*!40000 ALTER TABLE `pedidoproductos` DISABLE KEYS */;
INSERT INTO `pedidoproductos` (`idPedido`, `idProducto`, `quantity`, `Timestamp`) VALUES (1,1,2,'2022-04-28 14:02:36'),(1,7,2,'2022-04-28 14:02:36'),(1,5,1,'2022-04-28 14:02:36'),(2,10,2,'2022-04-28 14:04:22'),(2,2,1,'2022-04-28 14:04:22'),(1,7,2,'2022-05-25 20:19:11'),(2,1,1,'2022-05-26 18:10:43'),(2,9,1,'2022-05-26 18:10:43'),(2,3,1,'2022-05-26 18:10:43'),(2,6,1,'2022-05-26 18:10:43');
/*!40000 ALTER TABLE `pedidoproductos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidosensor`
--

DROP TABLE IF EXISTS `pedidosensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedidosensor` (
  `idSensor` int(11) NOT NULL,
  `idPedido` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidosensor`
--

LOCK TABLES `pedidosensor` WRITE;
/*!40000 ALTER TABLE `pedidosensor` DISABLE KEYS */;
INSERT INTO `pedidosensor` (`idSensor`, `idPedido`) VALUES (1,2);
/*!40000 ALTER TABLE `pedidosensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_spanish2_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `iva` double NOT NULL,
  `typeProduct` varchar(150) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` (`idProducto`, `name`, `description`, `quantity`, `price`, `iva`, `typeProduct`) VALUES (1,'Producto 1','Desc de prod 1',0,5.99,21,'1 - Entrantes'),(2,'Producto 2','Desc de prod 2',0,2.99,21,'3 - Segundo Plato'),(3,'Producto 3','Desc de prod 3',0,7.99,21,'4 - Postre'),(4,'Producto 4','Desc de Prod 4',0,15.99,21,'4 - Postre'),(5,'Producto 5','Desc de Prod 5',0,4.99,21,'5 - Refrescos'),(6,'Producto 6','Desc de Prod 6',0,11.99,21,'5 - Refrescos'),(7,'Producto 7','Desc de Prod 7',0,9.99,21,'2 - Primer Plato'),(8,'Producto 8','Desc de Prod 8',0,7.99,21,'2 - Primer Plato'),(9,'Producto 9','Desc de Prod 9',0,13.99,21,'3 - Segundo Plato'),(10,'Producto 10','Desc de prod 10',0,4.99,21,'1 - Entrantes');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensor` (
  `idSensor` int(11) DEFAULT NULL,
  `tempC` float DEFAULT NULL,
  `humC` float DEFAULT NULL,
  `tempF` float DEFAULT NULL,
  `humF` float DEFAULT NULL,
  `longitud` decimal(20,6) DEFAULT NULL,
  `latitud` decimal(20,6) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` (`idSensor`, `tempC`, `humC`, `tempF`, `humF`, `longitud`, `latitud`, `timestamp`, `id`) VALUES (1,24,15,17,13,40.373028,-3.919244,'2022-05-24 18:50:29',1),(1,24.2,21,18,21,40.370000,-3.920000,'2022-05-25 19:52:02',2),(1,32,18,15.8,14,40.523251,-3.640580,'2022-05-25 20:08:06',3),(1,26,20,14,19,40.523251,-3.640580,'2022-05-25 20:28:52',4),(1,28.5,14,20.8,11,40.523251,-3.640580,'2022-05-25 20:42:53',5),(1,30.8,10,14.2,20,40.523251,-3.640580,'2022-05-25 21:30:54',6),(1,27.1,12,19.1,15,40.476513,-3.716109,'2022-05-26 20:30:13',7),(1,27.3,13,19.1,15,40.476513,-3.716109,'2022-05-26 20:30:14',8),(1,27.1,13,19.1,15,40.476513,-3.716109,'2022-05-26 20:30:15',9),(1,27.2,13,19,16,40.476513,-3.716109,'2022-05-26 20:30:21',10),(1,27.2,12,18.9,16,40.476513,-3.716109,'2022-05-26 20:30:21',11),(1,27,12,18.9,16,40.476513,-3.716109,'2022-05-26 20:30:22',12),(1,26.8,12,19,15,40.476513,-3.716109,'2022-05-26 20:30:23',13),(1,26.8,12,19,15,40.476513,-3.716109,'2022-05-26 20:30:24',14);
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(150) COLLATE utf8_spanish2_ci NOT NULL,
  `Username` varchar(150) COLLATE utf8_spanish2_ci NOT NULL,
  `Password` varchar(150) COLLATE utf8_spanish2_ci NOT NULL,
  `Rol` int(11) NOT NULL,
  `Email` varchar(150) COLLATE utf8_spanish2_ci NOT NULL,
  `Address` varchar(250) COLLATE utf8_spanish2_ci NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`idUser`, `Name`, `Username`, `Password`, `Rol`, `Email`, `Address`, `Date`) VALUES (1,'Juan Camilo Quiroz Muñoz','camilo','12345',2,'jcamiloquiroz@gmail.com','Valdemoro, Madrid','2022-04-08'),(2,'David','llugh','12345',0,'vicendmg@gmail.com','Meco','2022-05-26');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-26 20:45:22
