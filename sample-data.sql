-- ======================================================
-- Sample Data for Global School Booking System
-- Run this AFTER creating the database and starting the app
-- OR run it directly in MySQL before starting the app
-- ======================================================

USE global_school;

-- Clean existing data (optional – comment out if you want to keep)
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE booking;
-- TRUNCATE TABLE session;
-- TRUNCATE TABLE offering;
-- TRUNCATE TABLE parent;
-- TRUNCATE TABLE course;
-- TRUNCATE TABLE teacher;
-- SET FOREIGN_KEY_CHECKS = 1;

-- ======================================================
-- 1. Teachers
-- ======================================================
INSERT INTO teacher (name, email) VALUES 
('Ms. Sarah Smith', 'sarah.smith@globalschool.com'),
('Mr. John Davis', 'john.davis@globalschool.com');

-- ======================================================
-- 2. Courses
-- ======================================================
INSERT INTO course (name, description) VALUES 
('Python Coding', 'Learn Python programming from basics to advanced'),
('Art Drawing', 'Creative drawing techniques for beginners'),
('Public Speaking', 'Master the art of public communication');

-- ======================================================
-- 3. Parents (with different timezones)
-- ======================================================
INSERT INTO parent (name, email, timezone, version) VALUES 
('Alice Johnson', 'alice@example.com', 'Asia/Kolkata', 0),          -- India (UTC+5:30)
('Bob Williams', 'bob@example.com', 'Europe/London', 0),            -- UK (UTC+1 in summer)
('Carol Martinez', 'carol@example.com', 'America/Los_Angeles', 0);  -- US Pacific (UTC-7 in summer)

-- ======================================================
-- 4. Offerings (Sections)
-- ======================================================
INSERT INTO offering (name, course_id, teacher_id, teacher_timezone) VALUES 
('Saturday Batch', 1, 1, 'America/New_York'),    -- Python Coding, Ms. Smith in NY
('Weekday Summer Camp', 2, 2, 'Europe/London'),  -- Art Drawing, Mr. Davis in London
('Evening Batch', 3, 1, 'America/New_York');     -- Public Speaking, Ms. Smith in NY

-- ======================================================
-- 5. Sessions (all times in UTC)
-- 
-- Saturday Batch (NY time = UTC-4 in June):
--   6 PM NY = 10 PM UTC
-- ======================================================
INSERT INTO session (offering_id, start_time_utc, end_time_utc) VALUES 
-- Python Saturday Batch (3 sessions)
(1, '2025-06-07 22:00:00', '2025-06-07 23:00:00'),
(1, '2025-06-14 22:00:00', '2025-06-14 23:00:00'),
(1, '2025-06-21 22:00:00', '2025-06-21 23:00:00'),

-- Art Weekday Summer Camp (London time = UTC+1 in June):
--   5 PM London = 4 PM UTC
(2, '2025-06-09 16:00:00', '2025-06-09 17:00:00'),
(2, '2025-06-10 16:00:00', '2025-06-10 17:00:00'),
(2, '2025-06-11 16:00:00', '2025-06-11 17:00:00'),
(2, '2025-06-12 16:00:00', '2025-06-12 17:00:00'),
(2, '2025-06-13 16:00:00', '2025-06-13 17:00:00'),

-- Public Speaking Evening Batch (NY time UTC-4):
--   7 PM NY = 11 PM UTC (next day UTC)
(3, '2025-06-10 23:00:00', '2025-06-11 00:00:00'),
(3, '2025-06-17 23:00:00', '2025-06-18 00:00:00');

-- ======================================================
-- 6. Bookings (optional – shows pre-booked data)
-- ======================================================
INSERT INTO booking (parent_id, offering_id, booked_at) VALUES 
(1, 1, UTC_TIMESTAMP()),  -- Alice booked Python Saturday Batch
(2, 2, UTC_TIMESTAMP());  -- Bob booked Art Summer Camp

-- ======================================================
-- Verify inserted data
-- ======================================================
SELECT 'Teachers:' as '';
SELECT * FROM teacher;
SELECT 'Courses:' as '';
SELECT * FROM course;
SELECT 'Parents:' as '';
SELECT * FROM parent;
SELECT 'Offerings:' as '';
SELECT * FROM offering;
SELECT 'Sessions (UTC):' as '';
SELECT * FROM session;
SELECT 'Bookings:' as '';
SELECT * FROM booking;
