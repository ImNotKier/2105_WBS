-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 24, 2024 at 05:50 PM
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
-- Database: `wbs`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `BillingID` int(11) NOT NULL,
  `SerialID` int(11) NOT NULL,
  `DebtID` int(11) NOT NULL,
  `ChargeID` int(11) NOT NULL,
  `BillingAmount` decimal(10,2) NOT NULL,
  `DueDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`BillingID`, `SerialID`, `DebtID`, `ChargeID`, `BillingAmount`, `DueDate`) VALUES
(1, 1, 1, 0, 110.25, '2024-10-24'),
(2, 2, 2, 0, 102.38, '2024-12-24'),
(3, 3, 3, 0, 107.63, '2024-12-24'),
(4, 4, 4, 0, 107.63, '2024-12-24'),
(5, 5, 5, 0, 107.63, '2024-12-24'),
(6, 6, 6, 0, 107.63, '2024-12-24'),
(7, 7, 7, 0, 107.63, '2024-12-24'),
(8, 8, 8, 0, 107.63, '2024-12-24'),
(9, 9, 9, 0, 107.63, '2024-12-24'),
(10, 10, 10, 0, 107.63, '2024-12-24'),
(11, 11, 11, 0, 107.63, '2024-12-24'),
(12, 12, 12, 0, 107.63, '2024-12-24'),
(13, 13, 13, 0, 107.63, '2024-12-24'),
(14, 14, 14, 0, 169.00, '2024-12-24'),
(15, 15, 15, 0, 169.00, '2024-12-24'),
(16, 16, 16, 0, 169.00, '2024-12-24'),
(17, 17, 17, 0, 169.00, '2024-12-24'),
(18, 18, 18, 0, 169.00, '2024-12-24'),
(19, 19, 19, 0, 169.00, '2024-12-24'),
(20, 20, 20, 0, 169.00, '2024-12-24'),
(21, 21, 21, 0, 169.00, '2024-12-24'),
(22, 22, 22, 0, 169.00, '2024-12-24'),
(23, 23, 23, 0, 169.00, '2024-12-24'),
(24, 24, 24, 0, 169.00, '2024-12-24'),
(25, 25, 25, 0, 169.00, '2024-12-24'),
(26, 26, 26, 0, 169.00, '2024-12-24'),
(27, 27, 27, 0, 225.00, '2024-12-24'),
(28, 28, 28, 0, 225.00, '2024-12-24'),
(29, 29, 29, 0, 225.00, '2024-12-24'),
(30, 30, 30, 0, 225.00, '2024-12-24'),
(31, 31, 31, 0, 225.00, '2024-12-24'),
(32, 32, 32, 0, 225.00, '2024-12-24'),
(33, 33, 33, 0, 225.00, '2024-12-24'),
(34, 34, 34, 0, 225.00, '2024-12-24'),
(35, 35, 35, 0, 225.00, '2024-12-24'),
(36, 36, 36, 0, 225.00, '2024-12-24'),
(37, 37, 37, 0, 225.00, '2024-12-24'),
(38, 38, 38, 0, 225.00, '2024-12-24'),
(39, 39, 39, 0, 95.06, '2024-12-24'),
(40, 40, 40, 0, 104.81, '2024-12-24'),
(41, 41, 41, 0, 99.94, '2024-12-24'),
(42, 42, 42, 0, 99.94, '2024-12-24'),
(43, 43, 43, 0, 99.94, '2024-12-24'),
(44, 44, 44, 0, 99.94, '2024-12-24'),
(45, 45, 45, 0, 99.94, '2024-12-24'),
(46, 46, 46, 0, 99.94, '2024-12-24'),
(47, 47, 47, 0, 99.94, '2024-12-24'),
(48, 48, 48, 0, 99.94, '2024-12-24'),
(49, 49, 49, 0, 99.94, '2024-12-24'),
(50, 50, 50, 0, 99.94, '2024-12-24');

-- --------------------------------------------------------

--
-- Table structure for table `charge`
--

CREATE TABLE `charge` (
  `ChargeID` int(11) NOT NULL,
  `SerialID` int(11) NOT NULL,
  `ChargeAmount` decimal(10,2) NOT NULL,
  `DateIncurred` date NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `IsDebt` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `concessionaire`
--

CREATE TABLE `concessionaire` (
  `ConcessionaireID` int(1) NOT NULL,
  `ConcessionaireName` varchar(30) NOT NULL,
  `PricePerCubicMeter` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `concessionaire`
--

INSERT INTO `concessionaire` (`ConcessionaireID`, `ConcessionaireName`, `PricePerCubicMeter`) VALUES
(1, 'NasugbuWaters', 10.50),
(2, 'BalayanWaterSystem', 13.00),
(3, 'LemeryWaterDistrict', 15.00),
(4, 'CalataganWaterElement', 9.75);

-- --------------------------------------------------------

--
-- Table structure for table `consumerinfo`
--

CREATE TABLE `consumerinfo` (
  `SerialID` int(11) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `FirstName` varchar(30) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `ContactNumber` varchar(20) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `MeterID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `consumerinfo`
--

INSERT INTO `consumerinfo` (`SerialID`, `Password`, `FirstName`, `LastName`, `Address`, `ContactNumber`, `Email`, `MeterID`) VALUES
(1, 'Password1', 'Jared Jeffrey', 'Abellera', '123 Main St', '555-1234', 'jared.abellera@example.com', 1),
(2, 'Secure123', 'Clarence', 'Adrias', '456 Oak Ave', '555-5678', 'clarence.adrias@example.com', 2),
(3, 'MyPass45', 'Naicel', 'Apolona', '789 Pine Blvd', '555-9012', 'naicel.apolona@example.com', 3),
(4, 'Alpha@123', 'Lheoricke Miguel', 'Atienza', '101 Elm St', '555-3456', 'lheoricke.atienza@example.com', 4),
(5, 'Beta0987', 'Saipoden', 'Banto', '202 Maple St', '555-7890', 'saipoden.banto@example.com', 5),
(6, 'Gamma432', 'John Aldrie', 'Baquiran', '303 Pine St', '555-3210', 'john.baquiran@example.com', 6),
(7, 'Delta567', 'Jhon', 'Boiser', '404 Oak St', '555-6540', 'jhon.boiser@example.com', 7),
(8, 'Omega123', 'Clyde Allen', 'Brucal', '505 Elm St', '555-2340', 'clyde.brucal@example.com', 8),
(9, 'Zeta456', 'Jhayvic', 'Bugtong', '606 Maple St', '555-4560', 'jhayvic.bugtong@example.com', 9),
(10, 'Sigma789', 'Ivan', 'Cabatian', '707 Pine St', '555-7650', 'ivan.cabatian@example.com', 10),
(11, 'Epsilon9', 'Kaye Cee', 'Cagula', '808 Oak St', '555-1234', 'kaye.cagula@example.com', 11),
(12, 'Theta567', 'Kier Andrei', 'Catibog', '909 Elm St', '555-5678', 'kier.catibog@example.com', 12),
(13, 'Lambda42', 'Aj', 'Catli', '1010 Maple St', '555-9012', 'aj.catli@example.com', 13),
(14, 'Kappa987', 'Jomhar', 'Condicion', '1111 Pine St', '555-3456', 'jomhar.condicion@example.com', 14),
(15, 'PhiAlpha', 'Joyce Anne', 'Corpuz', '1212 Oak St', '555-7890', 'joyce.corpuz@example.com', 15),
(16, 'Omicron1', 'Eloisa Joyce', 'Creencia', '1313 Elm St', '555-3210', 'eloisa.creencia@example.com', 16),
(17, 'NuBeta56', 'Irish Kaye', 'Cuenca', '1414 Maple St', '555-6540', 'irish.cuenca@example.com', 17),
(18, 'XiDelta89', 'Jerome', 'Daluz', '1515 Pine St', '555-2340', 'jerome.daluz@example.com', 18),
(19, 'RhoGamma', 'King Exiquiel', 'Dando', '1616 Oak St', '555-4560', 'king.dando@example.com', 19),
(20, 'TauOmega1', 'Ian Jhon', 'Desuyo', '1717 Elm St', '555-7650', 'ian.desuyo@example.com', 20),
(21, 'Alpha77', 'Elon', 'Musk', '1 Innovation Ave', '555-1010', 'elon.musk@example.com', 21),
(22, 'Secure99', 'Jeff', 'Bezos', '2 Space Blvd', '555-2020', 'jeff.bezos@example.com', 22),
(23, 'MyCode88', 'Bill', 'Gates', '3 Tech Rd', '555-3030', 'bill.gates@example.com', 23),
(24, 'Techie21', 'Steve', 'Jobs', '4 Apple Ln', '555-4040', 'steve.jobs@example.com', 24),
(25, 'Quantum77', 'Marie', 'Curie', '5 Science St', '555-5050', 'marie.curie@example.com', 25),
(26, 'Relativity88', 'Albert', 'Einstein', '6 Physics Ave', '555-6060', 'albert.einstein@example.com', 26),
(27, 'Explorer33', 'Neil', 'Armstrong', '7 Moon Blvd', '555-7070', 'neil.armstrong@example.com', 27),
(28, 'Visionary44', 'Mark', 'Zuckerberg', '8 Meta Pl', '555-8080', 'mark.zuckerberg@example.com', 28),
(29, 'Author10', 'J.K.', 'Rowling', '9 Potter Ln', '555-9090', 'jk.rowling@example.com', 29),
(30, 'Imagine12', 'John', 'Lennon', '10 Beatle St', '555-1111', 'john.lennon@example.com', 30),
(31, 'Dynamic99', 'Taylor', 'Swift', '11 Music Blvd', '555-2222', 'taylor.swift@example.com', 31),
(32, 'Freedom22', 'Nelson', 'Mandela', '12 Peace St', '555-3333', 'nelson.mandela@example.com', 32),
(33, 'Artistic76', 'Leonardo', 'da Vinci', '13 Renaissance Pl', '555-4444', 'leonardo.davinci@example.com', 33),
(34, 'Playmaker5', 'Michael', 'Jordan', '14 Basketball Rd', '555-5555', 'michael.jordan@example.com', 34),
(35, 'Astrophile9', 'Carl', 'Sagan', '15 Cosmos Ave', '555-6666', 'carl.sagan@example.com', 35),
(36, 'Inspire66', 'Oprah', 'Winfrey', '16 Talkshow Ln', '555-7777', 'oprah.winfrey@example.com', 36),
(37, 'Classic77', 'Ludwig', 'Beethoven', '17 Symphony Blvd', '555-8888', 'ludwig.beethoven@example.com', 37),
(38, 'Legend10', 'Kobe', 'Bryant', '18 Mamba Dr', '555-9999', 'kobe.bryant@example.com', 38),
(39, 'King33', 'Martin', 'Luther King', '19 Dream St', '555-0000', 'martin.king@example.com', 39),
(40, 'Eloquent88', 'Maya', 'Angelou', '20 Poetic Pl', '555-1212', 'maya.angelou@example.com', 40),
(41, 'Pioneer77', 'Isaac', 'Newton', '21 Gravity Blvd', '555-1313', 'isaac.newton@example.com', 41),
(42, 'Bright123', 'Ada', 'Lovelace', '22 Programming Ln', '555-1414', 'ada.lovelace@example.com', 42),
(43, 'Explorer44', 'Christopher', 'Columbus', '23 Discovery St', '555-1515', 'christopher.columbus@example.com', 43),
(44, 'Resilient89', 'Malala', 'Yousafzai', '24 Education Ave', '555-1616', 'malala.yousafzai@example.com', 44),
(45, 'Nobel22', 'Alexander', 'Fleming', '25 Penicillin Blvd', '555-1717', 'alexander.fleming@example.com', 45),
(46, 'Classic91', 'Wolfgang', 'Mozart', '26 Melody Rd', '555-1818', 'wolfgang.mozart@example.com', 46),
(47, 'Inspire11', 'Amelia', 'Earhart', '27 Aviation Ln', '555-1919', 'amelia.earhart@example.com', 47),
(48, 'Artistic33', 'Vincent', 'van Gogh', '28 Starry Night Blvd', '555-2020', 'vincent.vangogh@example.com', 48),
(49, 'Innovate44', 'Thomas', 'Edison', '29 Lightbulb St', '555-2121', 'thomas.edison@example.com', 49),
(50, 'Champion66', 'Usain', 'Bolt', '30 Speed Blvd', '555-2222', 'usain.bolt@example.com', 50);

-- --------------------------------------------------------

--
-- Table structure for table `debt`
--

CREATE TABLE `debt` (
  `DebtID` int(11) NOT NULL,
  `MeterID` int(11) NOT NULL,
  `AmountDue` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `debt`
--

INSERT INTO `debt` (`DebtID`, `MeterID`, `AmountDue`) VALUES
(1, 1, 110.25),
(2, 2, 102.38),
(3, 3, 107.63),
(4, 4, 107.63),
(5, 5, 107.63),
(6, 6, 107.63),
(7, 7, 107.63),
(8, 8, 107.63),
(9, 9, 107.63),
(10, 10, 107.63),
(11, 11, 107.63),
(12, 12, 107.63),
(13, 13, 107.63),
(14, 14, 169.00),
(15, 15, 169.00),
(16, 16, 169.00),
(17, 17, 169.00),
(18, 18, 169.00),
(19, 19, 169.00),
(20, 20, 169.00),
(21, 21, 169.00),
(22, 22, 169.00),
(23, 23, 169.00),
(24, 24, 169.00),
(25, 25, 169.00),
(26, 26, 169.00),
(27, 27, 225.00),
(28, 28, 225.00),
(29, 29, 225.00),
(30, 30, 225.00),
(31, 31, 225.00),
(32, 32, 225.00),
(33, 33, 225.00),
(34, 34, 225.00),
(35, 35, 225.00),
(36, 36, 225.00),
(37, 37, 225.00),
(38, 38, 225.00),
(39, 39, 95.06),
(40, 40, 104.81),
(41, 41, 99.94),
(42, 42, 99.94),
(43, 43, 99.94),
(44, 44, 99.94),
(45, 45, 99.94),
(46, 46, 99.94),
(47, 47, 99.94),
(48, 48, 99.94),
(49, 49, 99.94),
(50, 50, 99.94);

-- --------------------------------------------------------

--
-- Table structure for table `ledger`
--

CREATE TABLE `ledger` (
  `LedgerID` int(11) NOT NULL,
  `BillingID` int(11) NOT NULL,
  `SerialID` int(11) NOT NULL,
  `AmountPaid` decimal(10,2) NOT NULL,
  `PaymentDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `watermeter`
--

CREATE TABLE `watermeter` (
  `MeterID` int(11) NOT NULL,
  `PresentReading` decimal(10,2) NOT NULL,
  `PreviousReading` decimal(10,2) NOT NULL,
  `Consumption` decimal(10,2) GENERATED ALWAYS AS (`PresentReading` - `PreviousReading`) STORED,
  `ConcessionaireID` int(11) NOT NULL,
  `isPaid` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `watermeter`
--

INSERT INTO `watermeter` (`MeterID`, `PresentReading`, `PreviousReading`, `ConcessionaireID`, `isPaid`) VALUES
(1, 100.50, 90.00, 1, 0),
(2, 150.25, 137.25, 2, 0),
(3, 200.75, 185.75, 3, 0),
(4, 120.50, 110.75, 4, 0),
(5, 110.25, 100.50, 1, 0),
(6, 160.50, 147.50, 2, 0),
(7, 210.25, 195.25, 3, 0),
(8, 130.75, 120.00, 4, 0),
(9, 120.50, 110.25, 1, 0),
(10, 170.75, 157.75, 2, 0),
(11, 220.50, 205.50, 3, 0),
(12, 140.25, 130.00, 4, 0),
(13, 130.75, 120.50, 1, 0),
(14, 180.25, 167.25, 2, 0),
(15, 230.75, 215.75, 3, 0),
(16, 150.50, 140.25, 4, 0),
(17, 140.25, 130.00, 1, 0),
(18, 190.50, 177.50, 2, 0),
(19, 240.25, 225.25, 3, 0),
(20, 160.75, 150.50, 4, 0),
(21, 150.50, 140.25, 1, 0),
(22, 200.75, 187.75, 2, 0),
(23, 250.25, 235.25, 3, 0),
(24, 170.25, 160.00, 4, 0),
(25, 160.75, 150.50, 1, 0),
(26, 210.25, 197.25, 2, 0),
(27, 260.50, 245.50, 3, 0),
(28, 180.50, 170.25, 4, 0),
(29, 170.25, 160.00, 1, 0),
(30, 220.50, 207.50, 2, 0),
(31, 270.75, 255.75, 3, 0),
(32, 190.25, 180.00, 4, 0),
(33, 180.50, 170.25, 1, 0),
(34, 230.75, 217.75, 2, 0),
(35, 280.25, 265.25, 3, 0),
(36, 200.50, 190.25, 4, 0),
(37, 190.25, 180.00, 1, 0),
(38, 240.75, 227.75, 2, 0),
(39, 290.25, 275.25, 3, 0),
(40, 210.50, 200.25, 4, 0),
(41, 200.25, 190.00, 1, 0),
(42, 250.50, 237.50, 2, 0),
(43, 300.75, 285.75, 3, 0),
(44, 220.25, 210.00, 4, 0),
(45, 210.50, 200.25, 1, 0),
(46, 260.75, 247.75, 2, 0),
(47, 310.25, 295.25, 3, 0),
(48, 230.50, 220.25, 4, 0),
(49, 220.25, 210.00, 1, 0),
(50, 270.50, 257.50, 2, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`BillingID`),
  ADD KEY `Bill.SerialID_FK` (`SerialID`),
  ADD KEY `Bill.DebtID_FK` (`DebtID`),
  ADD KEY `Bill.ChargeID_FK` (`ChargeID`);

--
-- Indexes for table `charge`
--
ALTER TABLE `charge`
  ADD PRIMARY KEY (`ChargeID`),
  ADD KEY `Charge.SerialID_FK` (`SerialID`);

--
-- Indexes for table `concessionaire`
--
ALTER TABLE `concessionaire`
  ADD PRIMARY KEY (`ConcessionaireID`);

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
  ADD KEY `MeterID_FK` (`MeterID`);

--
-- Indexes for table `ledger`
--
ALTER TABLE `ledger`
  ADD PRIMARY KEY (`LedgerID`),
  ADD KEY `Ledger.SerialID_FK` (`SerialID`),
  ADD KEY `Ledger.BillID_FK` (`BillingID`);

--
-- Indexes for table `watermeter`
--
ALTER TABLE `watermeter`
  ADD PRIMARY KEY (`MeterID`),
  ADD KEY `WaterMeter.ConcessionaireID_FK` (`ConcessionaireID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `BillingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `charge`
--
ALTER TABLE `charge`
  MODIFY `ChargeID` int(11) NOT NULL AUTO_INCREMENT;

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
-- AUTO_INCREMENT for table `ledger`
--
ALTER TABLE `ledger`
  MODIFY `LedgerID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `watermeter`
--
ALTER TABLE `watermeter`
  MODIFY `MeterID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `Bill.DebtID_FK` FOREIGN KEY (`DebtID`) REFERENCES `debt` (`DebtID`),
  ADD CONSTRAINT `Bill.SerialID_FK` FOREIGN KEY (`SerialID`) REFERENCES `consumerinfo` (`SerialID`);

--
-- Constraints for table `charge`
--
ALTER TABLE `charge`
  ADD CONSTRAINT `Charge.SerialID_FK` FOREIGN KEY (`SerialID`) REFERENCES `consumerinfo` (`SerialID`);

--
-- Constraints for table `debt`
--
ALTER TABLE `debt`
  ADD CONSTRAINT `MeterID_FK` FOREIGN KEY (`MeterID`) REFERENCES `watermeter` (`MeterID`);

--
-- Constraints for table `ledger`
--
ALTER TABLE `ledger`
  ADD CONSTRAINT `Ledger.BillID_FK` FOREIGN KEY (`BillingID`) REFERENCES `bill` (`BillingID`),
  ADD CONSTRAINT `Ledger.SerialID_FK` FOREIGN KEY (`SerialID`) REFERENCES `consumerinfo` (`SerialID`);

--
-- Constraints for table `watermeter`
--
ALTER TABLE `watermeter`
  ADD CONSTRAINT `WaterMeter.ConcessionaireID_FK` FOREIGN KEY (`ConcessionaireID`) REFERENCES `concessionaire` (`ConcessionaireID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
