-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 18, 2016 at 07:55 PM
-- Server version: 5.5.47-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `warehouse_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `log_tb`
--

CREATE TABLE IF NOT EXISTS `log_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation_name` varchar(30) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `operation_tb`
--

CREATE TABLE IF NOT EXISTS `operation_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `operation_tb`
--

INSERT INTO `operation_tb` (`id`, `name`) VALUES
(1, 'create_order'),
(2, 'view_order'),
(3, 'approve_order'),
(4, 'complete_order');

-- --------------------------------------------------------

--
-- Table structure for table `order_tb`
--

CREATE TABLE IF NOT EXISTS `order_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `items` varchar(50) DEFAULT NULL,
  `is_approved` tinyint(1) DEFAULT NULL,
  `is_completed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `permission_tb`
--

CREATE TABLE IF NOT EXISTS `permission_tb` (
  `role_id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  KEY `role_id` (`role_id`),
  KEY `operation_id` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permission_tb`
--

INSERT INTO `permission_tb` (`role_id`, `operation_id`) VALUES
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `role_tb`
--

CREATE TABLE IF NOT EXISTS `role_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `role_tb`
--

INSERT INTO `role_tb` (`id`, `name`) VALUES
(1, 'Manager'),
(2, 'Employee');

-- --------------------------------------------------------

--
-- Table structure for table `user_role_tb`
--

CREATE TABLE IF NOT EXISTS `user_role_tb` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_tb`
--

CREATE TABLE IF NOT EXISTS `user_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user_tb`
--

INSERT INTO `user_tb` (`id`, `name`, `user_name`, `pwd`) VALUES
(2, 'Mai Nguyen', 'mainguyen', '280189');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `permission_tb`
--
ALTER TABLE `permission_tb`
  ADD CONSTRAINT `permission_tb_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role_tb` (`id`),
  ADD CONSTRAINT `permission_tb_ibfk_2` FOREIGN KEY (`operation_id`) REFERENCES `operation_tb` (`id`);

--
-- Constraints for table `user_role_tb`
--
ALTER TABLE `user_role_tb`
  ADD CONSTRAINT `user_role_tb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`id`),
  ADD CONSTRAINT `user_role_tb_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role_tb` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
