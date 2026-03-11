-- ============================================================
-- Event Booking App - Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS event_booking_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE event_booking_db;

-- ============================================================
-- Table: users
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT          AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    NOT NULL UNIQUE,
    password    VARCHAR(255)    NOT NULL,
    role        VARCHAR(20)     NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Index tăng tốc lookup theo email (dùng cho login)
CREATE INDEX idx_users_email ON users(email);

-- ============================================================
-- Sample data (test login)
-- Password plain: password123  → BCrypt hash bên dưới
-- ============================================================
INSERT IGNORE INTO users (full_name, email, password, role)
VALUES (
    'Admin User',
    'admin@example.com',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoOe1nE2V4pGk2m8TiULpXERl3AqkGfL6.',
    'ADMIN'
);
