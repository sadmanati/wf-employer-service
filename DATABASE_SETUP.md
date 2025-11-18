# Employer Service - Database Setup

## Prerequisites
- PostgreSQL 14+ installed and running
- Maven 3.8+
- Java 21+

## Database Setup

### 1. Create Database

Run the following SQL commands to create the database:

```sql
-- Connect to PostgreSQL as superuser
psql -U postgres

-- Create database
CREATE DATABASE wf_employer_db;

-- Create user (if needed)
CREATE USER wf_employer_user WITH PASSWORD 'wf_employer_pass';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE wf_employer_db TO wf_employer_user;

-- Connect to the database
\c wf_employer_db

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
```

Or use the provided script:
```bash
psql -U postgres -f src/main/resources/db/init-database.sql
```

### 2. Update Configuration

Update `src/main/resources/application-dev.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/wf_employer_db
    username: wf_employer_user
    password: wf_employer_pass
```

### 3. Run Liquibase Migrations

The migrations will run automatically when you start the application:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Or run manually:
```bash
./mvnw liquibase:update
```

## Database Schema

### Tables Created

1. **employers** - Employer master profile
2. **employer_quotas** - Quota allocation per job category
3. **demand_letters** - Demand letters issued by employers
4. **job_orders** - Job orders created by employers
5. **job_requirements** - Skills/certifications/language requirements
6. **job_order_history** - Workflow history and audit trail
7. **reason_codes** - Standardized rejection/return codes
8. **audit_logs** - Comprehensive audit trail

### Key Features

- ✅ UUID primary keys for distributed systems
- ✅ Timestamp with timezone for proper time handling
- ✅ JSONB for flexible metadata storage
- ✅ Foreign key constraints with proper cascade rules
- ✅ Check constraints for data validation
- ✅ Comprehensive indexing for performance
- ✅ Seed data for reason codes
- ✅ Rollback support for all migrations

## Liquibase Commands

### View Status
```bash
./mvnw liquibase:status
```

### Rollback Last Changeset
```bash
./mvnw liquibase:rollback -Dliquibase.rollbackCount=1
```

### Generate SQL (without applying)
```bash
./mvnw liquibase:updateSQL
```

### Clear Checksums
```bash
./mvnw liquibase:clearCheckSums
```

## Verify Installation

After running migrations, verify the setup:

```sql
-- Check tables created
\dt

-- Check table structure
\d employers
\d job_orders

-- Verify reason codes seed data
SELECT code, description, applicable_to FROM reason_codes ORDER BY display_order;

-- Check indexes
\di
```

## Production Considerations

1. **Connection Pooling**: Adjust HikariCP settings based on load
2. **Indexes**: Monitor query performance and add indexes as needed
3. **Partitioning**: Consider partitioning audit_logs by date for large datasets
4. **Backup**: Set up regular PostgreSQL backups
5. **Replication**: Configure read replicas for read-heavy workloads

## Troubleshooting

### Issue: Liquibase checksum mismatch
```bash
./mvnw liquibase:clearCheckSums
```

### Issue: Database connection refused
- Check PostgreSQL is running: `pg_isready`
- Verify connection details in application.yml
- Check PostgreSQL logs: `tail -f /var/log/postgresql/postgresql-*.log`

### Issue: Permission denied
```sql
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO wf_employer_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO wf_employer_user;
```

## Migration History

| Version | Description | Date |
|---------|-------------|------|
| 001 | Create employers table | 2025-11-19 |
| 002 | Create employer_quotas table | 2025-11-19 |
| 003 | Create demand_letters table | 2025-11-19 |
| 004 | Create job_orders table | 2025-11-19 |
| 005 | Create job_requirements table | 2025-11-19 |
| 006 | Create job_order_history table | 2025-11-19 |
| 007 | Create reason_codes table | 2025-11-19 |
| 008 | Create audit_logs table | 2025-11-19 |
| 009 | Create indexes | 2025-11-19 |
| 010 | Insert seed data | 2025-11-19 |

