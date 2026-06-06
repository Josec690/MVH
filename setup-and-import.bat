@echo off
REM Setup and import script for Museu Virtual Hardware (Windows)
REM Usage: run in project root. MySQL must be in PATH

SET "MYSQL_USER=root"
SET "MYSQL_DB=museu_virtual_hardware"
SET "PROJECT_DIR=%~dp0"

necho Creating database and user (mysql-setup.sql)...
%ComSpec% /c "mysql -u %MYSQL_USER% -p < "%PROJECT_DIR%mysql-setup.sql""
necho Importing seed data (import.sql)...
%ComSpec% /c "mysql -u %MYSQL_USER% -p %MYSQL_DB% < "%PROJECT_DIR%src\main\resources\import.sql""
necho Done.
pause