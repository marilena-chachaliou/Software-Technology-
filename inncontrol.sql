-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 30 Μάη 2025 στις 19:27:00
-- Έκδοση διακομιστή: 10.4.32-MariaDB
-- Έκδοση PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `inncontrol`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `bookings`
--

CREATE TABLE `bookings` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `check_in` date DEFAULT NULL,
  `check_out` date DEFAULT NULL,
  `guests` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `bookings`
--

INSERT INTO `bookings` (`id`, `user_id`, `room_id`, `check_in`, `check_out`, `guests`) VALUES
(11, 2, 2, '2025-06-10', '2025-06-14', 2),
(13, 4, 4, '2025-06-11', '2025-06-13', 3),
(14, 5, 5, '2025-06-14', '2025-06-18', 2),
(15, 6, 2, '2025-06-19', '2025-06-21', 1),
(16, 9, 2, '2025-06-05', '2025-06-19', 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `event_date` date DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `events`
--

INSERT INTO `events` (`id`, `title`, `description`, `event_date`, `location`) VALUES
(1, 'Wedding Reception', 'A beautiful wedding reception event.', '2025-07-15', 'Main Hall'),
(2, 'Corporate Meeting', 'Annual corporate strategy meeting.', '2025-08-10', 'Conference Room A'),
(3, 'Product Launch', 'Launch of the new product line.', '2025-09-05', 'Auditorium'),
(4, 'Charity Gala', 'Fundraising gala for charity.', '2025-10-12', 'Banquet Hall');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `maintenance`
--

CREATE TABLE `maintenance` (
  `id` int(11) NOT NULL,
  `room_number` varchar(10) DEFAULT NULL,
  `issue` text DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `date_reported` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `maintenance`
--

INSERT INTO `maintenance` (`id`, `room_number`, `issue`, `status`, `date_reported`) VALUES
(1, '102', 'Leaking sink', 'open', '2025-05-20 14:30:00'),
(2, '105', 'Air conditioning broken', 'in_progress', '2025-05-22 09:00:00'),
(3, '107', 'Light bulb replacement', 'closed', '2025-05-18 10:15:00'),
(4, '110', 'Heater not working', 'open', '2025-05-25 08:45:00');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `maintenance_requests`
--

CREATE TABLE `maintenance_requests` (
  `id` int(11) NOT NULL,
  `room_id` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `status` enum('open','in_progress','closed') DEFAULT 'open',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `maintenance_requests`
--

INSERT INTO `maintenance_requests` (`id`, `room_id`, `description`, `status`, `created_at`) VALUES
(14, 2, 'Checked leaking sink, needs pipe replacement', 'in_progress', '2025-05-21 07:00:00'),
(15, 3, 'Air conditioning repair scheduled', 'closed', '2025-05-22 09:00:00'),
(17, 5, 'Heater parts ordered', 'in_progress', '2025-05-26 06:15:00');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `sender_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `sent_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `messages`
--

INSERT INTO `messages` (`id`, `sender_id`, `receiver_id`, `content`, `sent_at`) VALUES
(1, 1, 2, 'Room 203 needs cleaning', '2025-05-22 07:00:00'),
(3, 1, 3, 'Guest requested extra towels', '2025-05-23 06:30:00'),
(4, 3, 1, 'Towels delivered to room 101', '2025-05-23 07:00:00');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `minibar`
--

CREATE TABLE `minibar` (
  `id` int(11) NOT NULL,
  `room_number` varchar(10) DEFAULT NULL,
  `item` varchar(100) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `minibar_items`
--

CREATE TABLE `minibar_items` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `minibar_items`
--

INSERT INTO `minibar_items` (`id`, `name`, `price`) VALUES
(1, 'Water Bottle', 2.50),
(2, 'Soda Can', 3.00),
(3, 'Chocolate Bar', 2.80),
(4, 'Mini Wine', 5.50),
(5, 'Juice Box', 2.20),
(6, 'Energy Drink', 4.00),
(7, 'Nuts Pack', 3.20),
(8, 'Cookies', 2.70),
(9, 'Mini Champagne', 9.90);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `minibar_orders`
--

CREATE TABLE `minibar_orders` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `charged` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `minibar_orders`
--

INSERT INTO `minibar_orders` (`id`, `booking_id`, `item_id`, `quantity`, `charged`) VALUES
(2, 14, 6, 2, 0),
(3, 13, 7, 7, 0);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `rooms`
--

CREATE TABLE `rooms` (
  `id` int(11) NOT NULL,
  `room_number` varchar(10) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `rooms`
--

INSERT INTO `rooms` (`id`, `room_number`, `type_id`, `is_available`) VALUES
(2, '105', 2, 1),
(3, '104', 1, 1),
(4, '106', 3, 1),
(5, '107', 2, 0);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `room_service`
--

CREATE TABLE `room_service` (
  `id` int(11) NOT NULL,
  `room_number` varchar(10) DEFAULT NULL,
  `request` text DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `room_service`
--

INSERT INTO `room_service` (`id`, `room_number`, `request`, `status`, `timestamp`) VALUES
(1, '105', 'Towels x2', 'Pending', '2025-05-30 14:59:00'),
(3, '104', 'Sheets', 'Delivered', '2025-05-30 18:24:15'),
(4, '106', 'Towels', 'Pending', '2025-05-30 18:24:33');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `room_types`
--

CREATE TABLE `room_types` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `room_types`
--

INSERT INTO `room_types` (`id`, `name`, `description`) VALUES
(1, 'Single', 'One bed room'),
(2, 'Double', 'Two beds room'),
(3, 'Suite', 'Luxury suite with extra space');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `services`
--

CREATE TABLE `services` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `service_type` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `charge` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `special_requests`
--

CREATE TABLE `special_requests` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `request` text DEFAULT NULL,
  `status` enum('pending','handled') DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `staff_leaves`
--

CREATE TABLE `staff_leaves` (
  `id` int(11) NOT NULL,
  `staff_id` int(11) DEFAULT NULL,
  `leave_start` date DEFAULT NULL,
  `leave_end` date DEFAULT NULL,
  `reason` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `staff_leaves`
--

INSERT INTO `staff_leaves` (`id`, `staff_id`, `leave_start`, `leave_end`, `reason`) VALUES
(1, 1, '2025-06-01', '2025-06-05', 'Vacation'),
(2, 2, '2025-07-10', '2025-07-12', 'Sick Leave'),
(3, 3, '2025-08-15', '2025-08-20', 'Personal Leave'),
(4, 1, '2025-09-05', '2025-09-07', 'Conference Attendance');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `transfers`
--

CREATE TABLE `transfers` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `pickup_location` varchar(100) DEFAULT NULL,
  `dropoff_location` varchar(100) DEFAULT NULL,
  `transfer_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `role` enum('admin','staff','guest') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `full_name`, `role`) VALUES
(1, 'admin', 'admin123', 'System Admin', 'admin'),
(2, 'maria', 'default123', 'maria', 'guest'),
(3, 'george.k', 'george2025', 'George Konstantinou', 'guest'),
(4, 'sofia.p', 'sofiaroom12', 'Sofia Papadopoulou', 'guest'),
(5, 'eleni.manager', 'el3niMGR!', 'Eleni Karidi', ''),
(6, 'nikos.clean', 'cleanme123', 'Nikos Spanoulis', ''),
(7, 'vicky.reception', 'welcomeV1', 'Vicky Tsakiri', ''),
(8, 'admin1', 'secureAdmin!', 'Alexis Dimitriou', 'admin'),
(9, 'irenesigala', 'default123', 'Irene Sigala', 'guest');

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Ευρετήρια για πίνακα `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `maintenance`
--
ALTER TABLE `maintenance`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `maintenance_requests`
--
ALTER TABLE `maintenance_requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `room_id` (`room_id`);

--
-- Ευρετήρια για πίνακα `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender_id` (`sender_id`),
  ADD KEY `receiver_id` (`receiver_id`);

--
-- Ευρετήρια για πίνακα `minibar`
--
ALTER TABLE `minibar`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `minibar_items`
--
ALTER TABLE `minibar_items`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `minibar_orders`
--
ALTER TABLE `minibar_orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Ευρετήρια για πίνακα `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type_id` (`type_id`);

--
-- Ευρετήρια για πίνακα `room_service`
--
ALTER TABLE `room_service`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `room_types`
--
ALTER TABLE `room_types`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Ευρετήρια για πίνακα `special_requests`
--
ALTER TABLE `special_requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Ευρετήρια για πίνακα `staff_leaves`
--
ALTER TABLE `staff_leaves`
  ADD PRIMARY KEY (`id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Ευρετήρια για πίνακα `transfers`
--
ALTER TABLE `transfers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Ευρετήρια για πίνακα `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT για πίνακα `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `maintenance`
--
ALTER TABLE `maintenance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `maintenance_requests`
--
ALTER TABLE `maintenance_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT για πίνακα `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `minibar`
--
ALTER TABLE `minibar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `minibar_items`
--
ALTER TABLE `minibar_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT για πίνακα `minibar_orders`
--
ALTER TABLE `minibar_orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT για πίνακα `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT για πίνακα `room_service`
--
ALTER TABLE `room_service`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `room_types`
--
ALTER TABLE `room_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT για πίνακα `services`
--
ALTER TABLE `services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `special_requests`
--
ALTER TABLE `special_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `staff_leaves`
--
ALTER TABLE `staff_leaves`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `transfers`
--
ALTER TABLE `transfers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

--
-- Περιορισμοί για πίνακα `maintenance_requests`
--
ALTER TABLE `maintenance_requests`
  ADD CONSTRAINT `maintenance_requests_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

--
-- Περιορισμοί για πίνακα `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`);

--
-- Περιορισμοί για πίνακα `minibar_orders`
--
ALTER TABLE `minibar_orders`
  ADD CONSTRAINT `minibar_orders_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`),
  ADD CONSTRAINT `minibar_orders_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `minibar_items` (`id`);

--
-- Περιορισμοί για πίνακα `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `room_types` (`id`);

--
-- Περιορισμοί για πίνακα `services`
--
ALTER TABLE `services`
  ADD CONSTRAINT `services_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`);

--
-- Περιορισμοί για πίνακα `special_requests`
--
ALTER TABLE `special_requests`
  ADD CONSTRAINT `special_requests_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`);

--
-- Περιορισμοί για πίνακα `staff_leaves`
--
ALTER TABLE `staff_leaves`
  ADD CONSTRAINT `staff_leaves_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `users` (`id`);

--
-- Περιορισμοί για πίνακα `transfers`
--
ALTER TABLE `transfers`
  ADD CONSTRAINT `transfers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
