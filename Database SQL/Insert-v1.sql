USE HOTEL_DB;

-- Εισαγωγή Πελατών
INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH, PHONE) VALUES
('George', 'Papadopoulos', 'george.papadopoulos@example.com', SHA2('geo12345', 256), '6901234567'),
('Maria', 'Ioannou', 'maria.ioannou@example.com', SHA2('maria_pass', 256), '6956789012'),
('Nikos', 'Vasilis', 'nikos.vasilis@example.com', SHA2('nikos2024', 256), '6945678923'),
('Elena', 'Karali', 'elena.karali@example.com', SHA2('elena@secure', 256), '6934567890'),
('Dimitris', 'Kostis', 'dimitris.kostis@example.com', SHA2('dimitris!pass', 256), '6987654321');

-- Εισαγωγή Διαχειριστών
INSERT INTO ADMIN (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH) VALUES
('Christos', 'Nikolaou', 'christos.nikolaou@example.com', SHA2('admin_chris', 256)),
('Eleni', 'Pappas', 'eleni.pappas@example.com', SHA2('secure_admin', 256));

-- Εισαγωγή Υπαλλήλων
INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH, SALARY, DAYOFF) VALUES
('Petros', 'Markou', 'petros.markou@example.com', SHA2('peter_work', 256), 4800.00, 0),
('Katerina', 'Lambrou', 'katerina.lambrou@example.com', SHA2('kat1234', 256), 7200.00, 1),
('Alexandros', 'Dimitriou', 'alex.dimitriou@example.com', SHA2('alex$pass', 256), 6000.00, 0),
('Sofia', 'Zacharaki', 'sofia.zacharaki@example.com', SHA2('sofia2024', 256), 8200.00, 1),
('Panagiotis', 'Vlachos', 'panos.vlachos@example.com', SHA2('vlachos99', 256), 5500.00, 0);

-- Εισαγωγή Δωματίων
INSERT INTO ROOM (ROOM_NUMBER, ROOM_TYPE, PRICE_PER_NIGHT, STATUS) VALUES
('201', 'Single', 75.00, 'Available'),
('202', 'Double', 115.00, 'Available'),
('203', 'Suite', 250.00, 'Booked'),
('204', 'Single', 85.00, 'Under Maintenance'),
('205', 'Double', 140.00, 'Available');

-- Εισαγωγή Κρατήσεων
INSERT INTO RESERVATION (CUSTOMER_ID, ROOM_ID, CHECK_IN, CHECK_OUT, STATUS) VALUES
(1, 1, '2025-04-05', '2025-04-10', 'Confirmed'),
(2, 3, '2025-05-15', '2025-05-20', 'Confirmed'),
(3, 2, '2025-06-07', '2025-06-12', 'Completed'),
(4, 5, '2025-07-18', '2025-07-25', 'Cancelled'),
(5, 4, '2025-08-10', '2025-08-15', 'Confirmed');


