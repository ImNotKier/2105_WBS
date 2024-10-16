-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 16, 2024 at 02:46 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `waterbillingsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE `billing` (
  `BillingNumber` int(11) NOT NULL,
  `BillingDate` date NOT NULL,
  `PreviousBillingDate` date NOT NULL,
  `SerialID` int(11) NOT NULL,
  `ConsessionaireID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`BillingNumber`, `BillingDate`, `PreviousBillingDate`, `SerialID`, `ConsessionaireID`) VALUES
(1, '2024-09-01', '2024-08-01', 1, 1),
(2, '2024-09-01', '2024-08-01', 2, 2),
(3, '2024-09-01', '2024-08-01', 3, 3),
(4, '2024-09-01', '2024-08-01', 4, 4),
(5, '2024-09-01', '2024-08-01', 5, 1),
(6, '2024-09-01', '2024-08-01', 6, 2),
(7, '2024-09-01', '2024-08-01', 7, 3),
(8, '2024-09-01', '2024-08-01', 8, 4),
(9, '2024-09-01', '2024-08-01', 9, 1),
(10, '2024-09-01', '2024-08-01', 10, 2),
(11, '2024-09-01', '2024-08-01', 11, 3),
(12, '2024-09-01', '2024-08-01', 12, 4),
(13, '2024-09-01', '2024-08-01', 13, 1),
(14, '2024-09-01', '2024-08-01', 14, 2),
(15, '2024-09-01', '2024-08-01', 15, 3),
(16, '2024-09-01', '2024-08-01', 16, 4),
(17, '2024-09-01', '2024-08-01', 17, 1),
(18, '2024-09-01', '2024-08-01', 18, 2),
(19, '2024-09-01', '2024-08-01', 19, 3),
(20, '2024-09-01', '2024-08-01', 20, 4),
(21, '2024-09-01', '2024-08-01', 21, 1),
(22, '2024-09-01', '2024-08-01', 22, 2),
(23, '2024-09-01', '2024-08-01', 23, 3),
(24, '2024-09-01', '2024-08-01', 24, 4),
(25, '2024-09-01', '2024-08-01', 25, 1),
(26, '2024-09-01', '2024-08-01', 26, 2),
(27, '2024-09-01', '2024-08-01', 27, 3),
(28, '2024-09-01', '2024-08-01', 28, 4),
(29, '2024-09-01', '2024-08-01', 29, 1),
(30, '2024-09-01', '2024-08-01', 30, 2),
(31, '2024-09-01', '2024-08-01', 31, 3),
(32, '2024-09-01', '2024-08-01', 32, 4),
(33, '2024-09-01', '2024-08-01', 33, 1),
(34, '2024-09-01', '2024-08-01', 34, 2),
(35, '2024-09-01', '2024-08-01', 35, 3),
(36, '2024-09-01', '2024-08-01', 36, 4),
(37, '2024-09-01', '2024-08-01', 37, 1),
(38, '2024-09-01', '2024-08-01', 38, 2),
(39, '2024-09-01', '2024-08-01', 39, 3),
(40, '2024-09-01', '2024-08-01', 40, 4),
(41, '2024-09-01', '2024-08-01', 41, 1),
(42, '2024-09-01', '2024-08-01', 42, 2),
(43, '2024-09-01', '2024-08-01', 43, 3),
(44, '2024-09-01', '2024-08-01', 44, 4),
(45, '2024-09-01', '2024-08-01', 45, 1),
(46, '2024-09-01', '2024-08-01', 46, 2),
(47, '2024-09-01', '2024-08-01', 47, 3),
(48, '2024-09-01', '2024-08-01', 48, 4),
(49, '2024-09-01', '2024-08-01', 49, 1),
(50, '2024-09-01', '2024-08-01', 50, 2);

-- --------------------------------------------------------

--
-- Table structure for table `charge`
--

CREATE TABLE `charge` (
  `ChargeID` int(11) NOT NULL,
  `SerialID` int(11) NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `DateIncurred` date NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `IsDebt` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `charge`
--

INSERT INTO `charge` (`ChargeID`, `SerialID`, `Amount`, `DateIncurred`, `Description`, `IsDebt`) VALUES
(1, 1, 50.00, '2024-09-01', 'Late payment fee', 1),
(2, 2, 25.00, '2024-09-05', 'Water leak repair', 0),
(3, 3, 10.00, '2024-09-10', 'Additional meter reading', 0),
(4, 4, 75.00, '2024-09-15', 'Unauthorized use', 1),
(5, 5, 15.00, '2024-09-20', 'Meter replacement', 0),
(6, 6, 20.00, '2024-09-25', 'Overconsumption charge', 1),
(7, 7, 30.00, '2024-10-01', 'Late payment fee', 1),
(8, 8, 12.00, '2024-10-05', 'Meter reading error', 0),
(9, 9, 5.00, '2024-10-10', 'Service call', 0),
(10, 10, 80.00, '2024-10-15', 'Unauthorized connection', 1),
(11, 11, 18.00, '2024-10-20', 'Meter relocation', 0),
(12, 12, 22.00, '2024-10-25', 'Overconsumption charge', 1),
(13, 13, 35.00, '2024-11-01', 'Late payment fee', 1),
(14, 14, 14.00, '2024-11-05', 'Meter reading error', 0),
(15, 15, 8.00, '2024-11-10', 'Service call', 0),
(16, 16, 90.00, '2024-11-15', 'Unauthorized connection', 1),
(17, 17, 21.00, '2024-11-20', 'Meter relocation', 0),
(18, 18, 24.00, '2024-11-25', 'Overconsumption charge', 1),
(19, 19, 40.00, '2024-12-01', 'Late payment fee', 1),
(20, 20, 16.00, '2024-12-05', 'Meter reading error', 0),
(21, 21, 10.00, '2024-12-10', 'Service call', 0),
(22, 22, 100.00, '2024-12-15', 'Unauthorized connection', 1),
(23, 23, 23.00, '2024-12-20', 'Meter relocation', 0),
(24, 24, 26.00, '2024-12-25', 'Overconsumption charge', 1),
(25, 25, 45.00, '2025-01-01', 'Late payment fee', 1),
(26, 26, 18.00, '2025-01-05', 'Meter reading error', 0),
(27, 27, 12.00, '2025-01-10', 'Service call', 0),
(28, 28, 110.00, '2025-01-15', 'Unauthorized connection', 1),
(29, 29, 25.00, '2025-01-20', 'Meter relocation', 0),
(30, 30, 28.00, '2025-01-25', 'Overconsumption charge', 1),
(31, 31, 50.00, '2025-02-01', 'Late payment fee', 1),
(32, 32, 20.00, '2025-02-05', 'Meter reading error', 0),
(33, 33, 14.00, '2025-02-10', 'Service call', 0),
(34, 34, 120.00, '2025-02-15', 'Unauthorized connection', 1),
(35, 35, 27.00, '2025-02-20', 'Meter relocation', 0),
(36, 36, 30.00, '2025-02-25', 'Overconsumption charge', 1),
(37, 37, 55.00, '2025-03-01', 'Late payment fee', 1),
(38, 38, 22.00, '2025-03-05', 'Meter reading error', 0),
(39, 39, 16.00, '2025-03-10', 'Service call', 0),
(40, 40, 130.00, '2025-03-15', 'Unauthorized connection', 1),
(41, 41, 29.00, '2025-03-20', 'Meter relocation', 0),
(42, 42, 32.00, '2025-03-25', 'Overconsumption charge', 1),
(43, 43, 60.00, '2025-04-01', 'Late payment fee', 1),
(44, 44, 24.00, '2025-04-05', 'Meter reading error', 0),
(45, 45, 18.00, '2025-04-10', 'Service call', 0),
(46, 46, 140.00, '2025-04-15', 'Unauthorized connection', 1),
(47, 47, 31.00, '2025-04-20', 'Meter relocation', 0),
(48, 48, 34.00, '2025-04-25', 'Overconsumption charge', 1),
(49, 49, 65.00, '2025-05-01', 'Late payment fee', 1),
(50, 50, 26.00, '2025-05-05', 'Meter reading error', 0);

-- --------------------------------------------------------

--
-- Table structure for table `concessionaire`
--

CREATE TABLE `concessionaire` (
  `ConsessionaireID` int(11) NOT NULL,
  `ConsessionaireName` varchar(100) NOT NULL,
  `PricePerCubicMeter` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `concessionaire`
--

INSERT INTO `concessionaire` (`ConsessionaireID`, `ConsessionaireName`, `PricePerCubicMeter`) VALUES
(1, 'Water Company A', 10.00),
(2, 'Water Company B', 12.50),
(3, 'Water Company C', 15.00),
(4, 'Water Company D', 8.75);

-- --------------------------------------------------------

--
-- Table structure for table `consumerinfo`
--

CREATE TABLE `consumerinfo` (
  `SerialID` int(11) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `ContactNumber` varchar(20) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `MeterID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `consumerinfo`
--

INSERT INTO `consumerinfo` (`SerialID`, `FirstName`, `LastName`, `Address`, `ContactNumber`, `Email`, `MeterID`) VALUES
(1, 'John', 'Doe', '123 Main St', '555-1234', 'johndoe@example.com', 1),
(2, 'Jane', 'Smith', '456 Oak Ave', '555-5678', 'janesmith@example.com', 2),
(3, 'Michael', 'Johnson', '789 Pine Blvd', '555-9012', 'michaeljohnson@example.com', 3),
(4, 'Emily', 'Williams', '101 Elm St', '555-3456', 'emilywilliams@example.com', 4),
(5, 'David', 'Wilson', '202 Maple St', '555-7890', 'davidwilson@example.com', 5),
(6, 'Sarah', 'Lee', '303 Pine St', '555-3210', 'sarahlee@example.com', 6),
(7, 'Christopher', 'Brown', '404 Oak St', '555-6540', 'christopherbrown@example.com', 7),
(8, 'Olivia', 'Taylor', '505 Elm St', '555-2340', 'oliviataylor@example.com', 8),
(9, 'Ethan', 'Carter', '606 Maple St', '555-4560', 'ethancarter@example.com', 9),
(10, 'Sophia', 'Miller', '707 Pine St', '555-7650', 'sophiamiller@example.com', 10),
(11, 'Ava', 'Jones', '808 Oak St', '555-1234', 'avajones@example.com', 11),
(12, 'Liam', 'Garcia', '909 Elm St', '555-5678', 'liamgarcia@example.com', 12),
(13, 'Noah', 'Martinez', '1010 Maple St', '555-9012', 'noahmartinez@example.com', 13),
(14, 'Emma', 'Davis', '1111 Pine St', '555-3456', 'emmadavis@example.com', 14),
(15, 'Oliver', 'Rodriguez', '1212 Oak St', '555-7890', 'oliverrodriguez@example.com', 15),
(16, 'Sofia', 'Wilson', '1313 Elm St', '555-3210', 'sofiawilson@example.com', 16),
(17, 'Elijah', 'Lee', '1414 Maple St', '555-6540', 'elijahlee@example.com', 17),
(18, 'Isabella', 'Brown', '1515 Pine St', '555-2340', 'isabellabrown@example.com', 18),
(19, 'Lucas', 'Taylor', '1616 Oak St', '555-4560', 'lucastaylor@example.com', 19),
(20, 'Mia', 'Carter', '1717 Elm St', '555-7650', 'miacarter@example.com', 20),
(21, 'Charlotte', 'Johnson', '1818 Maple St', '555-1234', 'charlottejohnson@example.com', 21),
(22, 'Ethan', 'Miller', '1919 Pine St', '555-5678', 'ethanmiller@example.com', 22),
(23, 'Amelia', 'Davis', '2020 Oak St', '555-9012', 'ameliamiller@example.com', 23),
(24, 'Benjamin', 'Rodriguez', '2121 Elm St', '555-3456', 'benjaminrodriguez@example.com', 24),
(25, 'Evelyn', 'Wilson', '2222 Maple St', '555-7890', 'evelynwilson@example.com', 25),
(26, 'Jacob', 'Lee', '2323 Pine St', '555-3210', 'jacoblee@example.com', 26),
(27, 'Avery', 'Brown', '2424 Oak St', '555-6540', 'averybrown@example.com', 27),
(28, 'Eleanor', 'Taylor', '2525 Elm St', '555-2340', 'eleanortaylor@example.com', 28),
(29, 'Caleb', 'Carter', '2626 Maple St', '555-4560', 'calebcarter@example.com', 29),
(30, 'Sofia', 'Johnson', '2727 Pine St', '555-7650', 'sofiajohnson@example.com', 30),
(31, 'Lucas', 'Martinez', '2828 Oak St', '555-1234', 'lucasmartinez@example.com', 31),
(32, 'Mia', 'Davis', '2929 Elm St', '555-5678', 'miadavis@example.com', 32),
(33, 'Charlotte', 'Rodriguez', '3030 Maple St', '555-9012', 'charlotteRodriguez@example.com', 33),
(34, 'Ethan', 'Wilson', '3131 Pine St', '555-3456', 'ethanwilson@example.com', 34),
(35, 'Amelia', 'Lee', '3232 Oak St', '555-7890', 'amelialee@example.com', 35),
(36, 'Benjamin', 'Brown', '3333 Elm St', '555-3210', 'benjaminbrown@example.com', 36),
(37, 'Evelyn', 'Taylor', '3434 Maple St', '555-6540', 'evelynTaylor@example.com', 37),
(38, 'Jacob', 'Carter', '3535 Pine St', '555-2340', 'jacobcarter@example.com', 38),
(39, 'Avery', 'Johnson', '3636 Oak St', '555-4560', 'averyjohnson@example.com', 39),
(40, 'Eleanor', 'Martinez', '3737 Elm St', '555-7650', 'eleanormartinez@example.com', 40),
(41, 'Caleb', 'Davis', '3838 Maple St', '555-1234', 'calebDavis@example.com', 41),
(42, 'Sofia', 'Rodriguez', '3939 Pine St', '555-5678', 'sofiaRodriguez@example.com', 42),
(43, 'Lucas', 'Wilson', '4040 Oak St', '555-9012', 'lucaswilson@example.com', 43),
(44, 'Mia', 'Lee', '4141 Elm St', '555-3456', 'miaLee@example.com', 44),
(45, 'Charlotte', 'Brown', '4242 Maple St', '555-7890', 'charlotteBrown@example.com', 45),
(46, 'Ethan', 'Taylor', '4343 Pine St', '555-3210', 'ethanTaylor@example.com', 46),
(47, 'Amelia', 'Carter', '4444 Oak St', '555-6540', 'ameliaCarter@example.com', 47),
(48, 'Benjamin', 'Johnson', '4545 Elm St', '555-2340', 'benjaminJohnson@example.com', 48),
(49, 'Evelyn', 'Martinez', '4646 Maple St', '555-4560', 'evelynMartinez@example.com', 49),
(50, 'Jacob', 'Davis', '4747 Pine St', '555-7650', 'jacobDavis@example.com', 50);

-- --------------------------------------------------------

--
-- Table structure for table `debt`
--

CREATE TABLE `debt` (
  `DebtID` int(11) NOT NULL,
  `SerialID` int(11) NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `DateIncurred` date NOT NULL,
  `DueDate` date NOT NULL,
  `PaymentStatus` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `debt`
--

INSERT INTO `debt` (`DebtID`, `SerialID`, `Amount`, `DateIncurred`, `DueDate`, `PaymentStatus`) VALUES
(1, 1, 100.00, '2024-09-01', '2024-10-01', 'Unpaid'),
(2, 2, 50.00, '2024-09-15', '2024-10-15', 'Unpaid'),
(3, 3, 25.00, '2024-10-01', '2024-11-01', 'Unpaid'),
(4, 4, 75.00, '2024-10-15', '2024-11-15', 'Unpaid'),
(5, 5, 150.00, '2024-11-01', '2024-12-01', 'Unpaid'),
(6, 6, 100.00, '2024-11-15', '2024-12-15', 'Unpaid'),
(7, 7, 50.00, '2024-12-01', '2025-01-01', 'Unpaid'),
(8, 8, 75.00, '2024-12-15', '2025-01-15', 'Unpaid'),
(9, 9, 150.00, '2025-01-01', '2025-02-01', 'Unpaid'),
(10, 10, 100.00, '2025-01-15', '2025-02-15', 'Unpaid'),
(11, 11, 50.00, '2025-02-01', '2025-03-01', 'Unpaid'),
(12, 12, 75.00, '2025-02-15', '2025-03-15', 'Unpaid'),
(13, 13, 150.00, '2025-03-01', '2025-04-01', 'Unpaid'),
(14, 14, 100.00, '2025-03-15', '2025-04-15', 'Unpaid'),
(15, 15, 50.00, '2025-04-01', '2025-05-01', 'Unpaid'),
(16, 16, 75.00, '2025-04-15', '2025-05-15', 'Unpaid'),
(17, 17, 150.00, '2025-05-01', '2025-06-01', 'Unpaid'),
(18, 18, 100.00, '2025-05-15', '2025-06-15', 'Unpaid'),
(19, 19, 50.00, '2025-06-01', '2025-07-01', 'Unpaid'),
(20, 20, 75.00, '2025-06-15', '2025-07-15', 'Unpaid'),
(21, 21, 150.00, '2025-07-01', '2025-08-01', 'Unpaid'),
(22, 22, 100.00, '2025-07-15', '2025-08-15', 'Unpaid'),
(23, 23, 50.00, '2025-08-01', '2025-09-01', 'Unpaid'),
(24, 24, 75.00, '2025-08-15', '2025-09-15', 'Unpaid'),
(25, 25, 150.00, '2025-09-01', '2025-10-01', 'Unpaid'),
(26, 26, 100.00, '2025-09-15', '2025-10-15', 'Unpaid'),
(27, 27, 50.00, '2025-10-01', '2025-11-01', 'Unpaid'),
(28, 28, 75.00, '2025-10-15', '2025-11-15', 'Unpaid'),
(29, 29, 150.00, '2025-11-01', '2025-12-01', 'Unpaid'),
(30, 30, 100.00, '2025-11-15', '2025-12-15', 'Unpaid'),
(31, 31, 50.00, '2025-12-01', '2026-01-01', 'Unpaid'),
(32, 32, 75.00, '2025-12-15', '2026-01-15', 'Unpaid'),
(33, 33, 150.00, '2026-01-01', '2026-02-01', 'Unpaid'),
(34, 34, 100.00, '2026-01-15', '2026-02-15', 'Unpaid'),
(35, 35, 50.00, '2026-02-01', '2026-03-01', 'Unpaid'),
(36, 36, 75.00, '2026-02-15', '2026-03-15', 'Unpaid'),
(37, 37, 150.00, '2026-03-01', '2026-04-01', 'Unpaid'),
(38, 38, 100.00, '2026-03-15', '2026-04-15', 'Unpaid'),
(39, 39, 50.00, '2026-04-01', '2026-05-01', 'Unpaid'),
(40, 40, 75.00, '2026-04-15', '2026-05-15', 'Unpaid'),
(41, 41, 150.00, '2026-05-01', '2026-06-01', 'Unpaid'),
(42, 42, 100.00, '2026-05-15', '2026-06-15', 'Unpaid'),
(43, 43, 50.00, '2026-06-01', '2026-07-01', 'Unpaid'),
(44, 44, 75.00, '2026-06-15', '2026-07-15', 'Unpaid'),
(45, 45, 150.00, '2026-07-01', '2026-08-01', 'Unpaid'),
(46, 46, 100.00, '2026-07-15', '2026-08-15', 'Unpaid'),
(47, 47, 50.00, '2026-08-01', '2026-09-01', 'Unpaid'),
(48, 48, 75.00, '2026-08-15', '2026-09-15', 'Unpaid'),
(49, 49, 150.00, '2026-08-01', '2026-09-01', 'Unpaid'),
(50, 50, 100.00, '2026-08-15', '2026-09-15', 'Unpaid');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `InvoiceNumber` int(11) NOT NULL,
  `BillingNumber` int(11) NOT NULL,
  `TotalAmount` decimal(10,2) NOT NULL,
  `DueDate` date NOT NULL,
  `PaymentDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`InvoiceNumber`, `BillingNumber`, `TotalAmount`, `DueDate`, `PaymentDate`) VALUES
(1, 1, 500.00, '2024-09-15', '2024-09-30'),
(2, 2, 600.00, '2024-09-15', '2024-09-30'),
(3, 3, 700.00, '2024-09-15', '2024-09-30'),
(4, 4, 800.00, '2024-09-15', '2024-09-30'),
(5, 5, 900.00, '2024-09-15', '2024-09-30'),
(6, 6, 1000.00, '2024-09-15', '2024-09-30'),
(7, 7, 1100.00, '2024-09-15', '2024-09-30'),
(8, 8, 1200.00, '2024-09-15', '2024-09-30'),
(9, 9, 1300.00, '2024-09-15', '2024-09-30'),
(10, 10, 1400.00, '2024-09-15', '2024-09-30'),
(11, 11, 1500.00, '2024-09-15', '2024-09-30'),
(12, 12, 1600.00, '2024-09-15', '2024-09-30'),
(13, 13, 1700.00, '2024-09-15', '2024-09-30'),
(14, 14, 1800.00, '2024-09-15', '2024-09-30'),
(15, 15, 1900.00, '2024-09-15', '2024-09-30'),
(16, 16, 2000.00, '2024-09-15', '2024-09-30'),
(17, 17, 2100.00, '2024-09-15', '2024-09-30'),
(18, 18, 2200.00, '2024-09-15', '2024-09-30'),
(19, 19, 2300.00, '2024-09-15', '2024-09-30'),
(20, 20, 2400.00, '2024-09-15', '2024-09-30'),
(21, 21, 2500.00, '2024-09-15', '2024-09-30'),
(22, 22, 2600.00, '2024-09-15', '2024-09-30'),
(23, 23, 2700.00, '2024-09-15', '2024-09-30'),
(24, 24, 2800.00, '2024-09-15', '2024-09-30'),
(25, 25, 2900.00, '2024-09-15', '2024-09-30'),
(26, 26, 3000.00, '2024-09-15', '2024-09-30'),
(27, 27, 3100.00, '2024-09-15', '2024-09-30'),
(28, 28, 3200.00, '2024-09-15', '2024-09-30'),
(29, 29, 3300.00, '2024-09-15', '2024-09-30'),
(30, 30, 3400.00, '2024-09-15', '2024-09-30'),
(31, 31, 3500.00, '2024-09-15', '2024-09-30'),
(32, 32, 3600.00, '2024-09-15', '2024-09-30'),
(33, 33, 3700.00, '2024-09-15', '2024-09-30'),
(34, 34, 3800.00, '2024-09-15', '2024-09-30'),
(35, 35, 3900.00, '2024-09-15', '2024-09-30'),
(36, 36, 4000.00, '2024-09-15', '2024-09-30'),
(37, 37, 4100.00, '2024-09-15', '2024-09-30'),
(38, 38, 4200.00, '2024-09-15', '2024-09-30'),
(39, 39, 4300.00, '2024-09-15', '2024-09-30'),
(40, 40, 4400.00, '2024-09-15', '2024-09-30'),
(41, 41, 4500.00, '2024-09-15', '2024-09-30'),
(42, 42, 4600.00, '2024-09-15', '2024-09-30'),
(43, 43, 4700.00, '2024-09-15', '2024-09-30'),
(44, 44, 4800.00, '2024-09-15', '2024-09-30'),
(45, 45, 4900.00, '2024-09-15', '2024-09-30'),
(46, 46, 5000.00, '2024-09-15', '2024-09-30'),
(47, 47, 5100.00, '2024-09-15', '2024-09-30'),
(48, 48, 5200.00, '2024-09-15', '2024-09-30'),
(49, 49, 5300.00, '2024-09-15', '2024-09-30'),
(50, 50, 5400.00, '2024-09-15', '2024-09-30');

-- --------------------------------------------------------

--
-- Table structure for table `watermeter`
--

CREATE TABLE `watermeter` (
  `MeterID` int(11) NOT NULL,
  `PresentReading` decimal(10,2) NOT NULL,
  `PreviousReading` decimal(10,2) NOT NULL,
  `Consumption` decimal(10,2) GENERATED ALWAYS AS (`PresentReading` - `PreviousReading`) STORED
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `watermeter`
--

INSERT INTO `watermeter` (`MeterID`, `PresentReading`, `PreviousReading`) VALUES
(1, 123.45, 100.12),
(2, 256.78, 220.34),
(3, 389.01, 350.56),
(4, 521.23, 482.78),
(5, 653.45, 614.90),
(6, 785.67, 747.12),
(7, 917.89, 879.34),
(8, 1050.11, 1011.56),
(9, 1182.33, 1143.78),
(10, 1314.55, 1276.00),
(11, 1446.77, 1408.22),
(12, 1578.99, 1540.44),
(13, 1711.21, 1672.66),
(14, 1843.43, 1804.88),
(15, 1975.65, 1937.10),
(16, 2107.87, 2069.32),
(17, 2239.09, 2200.54),
(18, 2370.31, 2331.76),
(19, 2502.53, 2464.00),
(20, 2634.75, 2596.20),
(21, 2766.97, 2728.42),
(22, 2899.19, 2860.64),
(23, 3031.41, 2992.86),
(24, 3163.63, 3125.08),
(25, 3295.85, 3257.30),
(26, 3428.07, 3389.52),
(27, 3560.29, 3521.74),
(28, 3692.51, 3653.96),
(29, 3824.73, 3786.18),
(30, 3956.95, 3918.40),
(31, 4089.17, 4050.62),
(32, 4221.39, 4182.84),
(33, 4353.61, 4315.06),
(34, 4485.83, 4447.28),
(35, 4618.05, 4579.50),
(36, 4750.27, 4711.72),
(37, 4882.49, 4843.94),
(38, 5014.71, 4976.16),
(39, 5146.93, 5108.38),
(40, 5279.15, 5240.60),
(41, 5411.37, 5372.82),
(42, 5543.59, 5505.04),
(43, 5675.81, 5637.26),
(44, 5808.03, 5769.48),
(45, 5940.25, 5930.90),
(46, 6072.47, 6062.12),
(47, 6204.69, 6193.34),
(48, 6336.91, 6324.56),
(49, 6469.13, 6455.78),
(50, 6601.35, 6587.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`BillingNumber`),
  ADD KEY `SerialID` (`SerialID`),
  ADD KEY `ConsessionaireID` (`ConsessionaireID`);

--
-- Indexes for table `charge`
--
ALTER TABLE `charge`
  ADD PRIMARY KEY (`ChargeID`),
  ADD KEY `SerialID` (`SerialID`);

--
-- Indexes for table `concessionaire`
--
ALTER TABLE `concessionaire`
  ADD PRIMARY KEY (`ConsessionaireID`);

--
-- Indexes for table `consumerinfo`
--
ALTER TABLE `consumerinfo`
  ADD PRIMARY KEY (`SerialID`);

--
-- Indexes for table `debt`
--
ALTER TABLE `debt`
  ADD PRIMARY KEY (`DebtID`),
  ADD KEY `SerialID` (`SerialID`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`InvoiceNumber`),
  ADD KEY `BillingNumber` (`BillingNumber`);

--
-- Indexes for table `watermeter`
--
ALTER TABLE `watermeter`
  ADD PRIMARY KEY (`MeterID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `billing`
--
ALTER TABLE `billing`
  MODIFY `BillingNumber` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `charge`
--
ALTER TABLE `charge`
  MODIFY `ChargeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `concessionaire`
--
ALTER TABLE `concessionaire`
  MODIFY `ConsessionaireID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `consumerinfo`
--
ALTER TABLE `consumerinfo`
  MODIFY `SerialID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `debt`
--
ALTER TABLE `debt`
  MODIFY `DebtID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `InvoiceNumber` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `watermeter`
--
ALTER TABLE `watermeter`
  MODIFY `MeterID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `billing`
--
ALTER TABLE `billing`
  ADD CONSTRAINT `billing_ibfk_1` FOREIGN KEY (`SerialID`) REFERENCES `consumerinfo` (`SerialID`),
  ADD CONSTRAINT `billing_ibfk_2` FOREIGN KEY (`ConsessionaireID`) REFERENCES `concessionaire` (`ConsessionaireID`);

--
-- Constraints for table `charge`
--
ALTER TABLE `charge`
  ADD CONSTRAINT `charge_ibfk_1` FOREIGN KEY (`SerialID`) REFERENCES `consumerinfo` (`SerialID`);

--
-- Constraints for table `debt`
--
ALTER TABLE `debt`
  ADD CONSTRAINT `debt_ibfk_1` FOREIGN KEY (`SerialID`) REFERENCES `consumerinfo` (`SerialID`);

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`BillingNumber`) REFERENCES `billing` (`BillingNumber`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
