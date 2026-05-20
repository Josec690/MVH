CREATE DATABASE IF NOT EXISTS museu_virtual_hardware
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'mvh_user'@'localhost' IDENTIFIED BY 'mvh_password';
GRANT ALL PRIVILEGES ON museu_virtual_hardware.* TO 'mvh_user'@'localhost';
FLUSH PRIVILEGES;
