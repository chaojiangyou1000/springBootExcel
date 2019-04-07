/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.15 : Database - db_excel_demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_excel_demo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_excel_demo`;

/*Table structure for table `t_client` */

DROP TABLE IF EXISTS `t_client`;

CREATE TABLE `t_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bianhao` varchar(20) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_client` */

insert  into `t_client`(`id`,`bianhao`,`create_date_time`,`name`,`phone`,`remark`) values 
(1,'001','2019-03-06 01:29:33','李丽','13245563412','备注一');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
