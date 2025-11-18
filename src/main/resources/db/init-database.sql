-- Employer Service Database Initialization Script
-- Run this script as PostgreSQL superuser to set up the database

-- Create database
CREATE DATABASE wf_employer_db
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- Connect to the new database
\c wf_employer_db;

-- Create extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create user (optional - for production)
-- CREATE USER wf_employer_user WITH PASSWORD 'change_this_password';
-- GRANT ALL PRIVILEGES ON DATABASE wf_employer_db TO wf_employer_user;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO wf_employer_user;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO wf_employer_user;
-- ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO wf_employer_user;
-- ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO wf_employer_user;

-- Verify setup
SELECT version();
SELECT * FROM pg_extension WHERE extname IN ('uuid-ossp', 'pgcrypto');

-- Success message
\echo 'Database wf_employer_db created successfully!'
\echo 'Extensions uuid-ossp and pgcrypto are installed.'
\echo 'You can now run the Spring Boot application to execute Liquibase migrations.'

