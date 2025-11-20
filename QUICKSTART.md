# 🚀 WF Employer Service - Quick Start Guide

## Prerequisites

- Java 17+ installed
- Maven 3.8+ installed
- PostgreSQL 14+ installed and running
- Git (for version control)

---

## 📦 Step 1: Database Setup

### Option A: Using psql (Recommended)

```bash
# Create database and user
psql -U postgres << EOF
CREATE DATABASE wf_employer_db;
CREATE USER wf_user WITH PASSWORD 'wf_password';
GRANT ALL PRIVILEGES ON DATABASE wf_employer_db TO wf_user;
\c wf_employer_db
GRANT ALL ON SCHEMA public TO wf_user;
EOF
```

### Option B: Using init script

```bash
psql -U postgres -f src/main/resources/db/init-database.sql
```

---

## ⚙️ Step 2: Configure Application

### Development Profile (application-dev.yml)

Already configured with:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/wf_employer_db
    username: postgres
    password: postgres
```

If your setup is different, update the credentials.

---

## 🏗️ Step 3: Build the Project

```bash
# Clean and build
./mvnw clean install

# Skip tests for faster build
./mvnw clean install -DskipTests
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: 15.234 s
```

---

## 🚀 Step 4: Run the Application

### Method 1: Using Maven (Development)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Method 2: Using JAR (Production-like)

```bash
java -jar target/wf-employer-service-0.0.1-SNAPSHOT.jar
```

### Expected Startup Logs

```
  ____  _____  ____  _____  _   _  ____  
 / ___||_   _|/ _  ||  _  || | | |/ _  | 
 \___ \  | | / /_\ || |_| || | | || |_| |
  ___) | | | |  _  ||  _  || |_| ||  _  |
 |____/  |_| |_| |_||_| |_|\___/|_| |_|

:: Spring Boot ::                (v3.2.0)

2025-11-19 INFO  Starting WfEmployerServiceApplication...
2025-11-19 INFO  Liquibase: Running Changeset: db/changelog/migrations/001-create-employers-table.yaml
2025-11-19 INFO  Liquibase: Running Changeset: db/changelog/migrations/002-create-employer-quotas-table.yaml
...
2025-11-19 INFO  Liquibase: Successfully ran 10 changesets
2025-11-19 INFO  Started WfEmployerServiceApplication in 5.234 seconds
```

---

## ✅ Step 5: Verify Installation

### Check Application Health

```bash
curl http://localhost:8081/api/v1/actuator/health
```

Expected response:
```json
{"status":"UP"}
```

### Verify Database Tables

```bash
psql -U postgres -d wf_employer_db -c "\dt"
```

Expected output:
```
                   List of relations
 Schema |          Name          | Type  |  Owner   
--------+------------------------+-------+----------
 public | audit_logs             | table | postgres
 public | demand_letters         | table | postgres
 public | employer_quotas        | table | postgres
 public | employers              | table | postgres
 public | job_order_history      | table | postgres
 public | job_orders             | table | postgres
 public | job_requirements       | table | postgres
 public | reason_codes           | table | postgres
 public | databasechangelog      | table | postgres
 public | databasechangeloglock  | table | postgres
```

### Check Seed Data

```bash
psql -U postgres -d wf_employer_db -c "SELECT COUNT(*) FROM reason_codes;"
```

Expected: 13 rows (or more)

---

## 🔍 Troubleshooting

### Issue 1: Database Connection Failed

**Error:**
```
org.postgresql.util.PSQLException: Connection refused
```

**Solution:**
```bash
# Check if PostgreSQL is running
pg_isready

# Start PostgreSQL (macOS)
brew services start postgresql@14

# Start PostgreSQL (Linux)
sudo systemctl start postgresql
```

---

### Issue 2: Liquibase Checksum Failed

**Error:**
```
Validation Failed: Checksum mismatch
```

**Solution:**
```bash
# Clear checksums
./mvnw liquibase:clearCheckSums

# Or drop and recreate database
psql -U postgres -c "DROP DATABASE IF EXISTS wf_employer_db;"
psql -U postgres -c "CREATE DATABASE wf_employer_db;"
```

---

### Issue 3: Port Already in Use

**Error:**
```
Port 8081 is already in use
```

**Solution:**
```bash
# Find process using port 8081
lsof -i :8081

# Kill the process
kill -9 <PID>

# Or change port in application.yml
server:
  port: 8082
```

---

## 🧪 Testing the API

### Sample API Calls

#### Create Employer
```bash
curl -X POST http://localhost:8081/api/v1/employers \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "companyName": "Al Noor Construction LLC",
    "tradeLicenseNumber": "TL-983742",
    "tradeLicenseExpiry": "2026-05-12",
    "mohreEstablishmentId": "MEI-44877",
    "address": "Dubai Industrial City",
    "city": "Dubai",
    "emirate": "Dubai",
    "contactDetails": {
      "phone": "+971500000000",
      "email": "hr@alnoor.com"
    }
  }'
```

#### Get All Reason Codes
```bash
curl http://localhost:8081/api/v1/reason-codes
```

---

## 📊 Database Commands Cheat Sheet

### View Migration History
```sql
SELECT id, author, filename, dateexecuted, orderexecuted 
FROM databasechangelog 
ORDER BY orderexecuted;
```

### Check Table Sizes
```sql
SELECT 
    schemaname,
    tablename,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
FROM pg_tables 
WHERE schemaname = 'public'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;
```

### View All Indexes
```sql
SELECT 
    tablename,
    indexname,
    indexdef
FROM pg_indexes 
WHERE schemaname = 'public'
ORDER BY tablename, indexname;
```

### Count Records in All Tables
```sql
SELECT 
    'employers' AS table_name, COUNT(*) FROM employers
UNION ALL
SELECT 'employer_quotas', COUNT(*) FROM employer_quotas
UNION ALL
SELECT 'demand_letters', COUNT(*) FROM demand_letters
UNION ALL
SELECT 'job_orders', COUNT(*) FROM job_orders
UNION ALL
SELECT 'job_requirements', COUNT(*) FROM job_requirements
UNION ALL
SELECT 'job_order_history', COUNT(*) FROM job_order_history
UNION ALL
SELECT 'reason_codes', COUNT(*) FROM reason_codes
UNION ALL
SELECT 'audit_logs', COUNT(*) FROM audit_logs;
```

---

## 🔄 Liquibase Commands

### View Status
```bash
./mvnw liquibase:status
```

### Generate SQL Preview (without applying)
```bash
./mvnw liquibase:updateSQL > migration-preview.sql
```

### Rollback Last Changeset
```bash
./mvnw liquibase:rollback -Dliquibase.rollbackCount=1
```

### Rollback to Date
```bash
./mvnw liquibase:rollback -Dliquibase.rollbackDate=2025-11-19
```

### Validate Changelog
```bash
./mvnw liquibase:validate
```

---

## 🎯 Next Steps

1. **Create Entity Classes** - Map database tables to Java entities
2. **Build Repositories** - Create Spring Data JPA repositories
3. **Implement Services** - Business logic layer
4. **Add REST Controllers** - API endpoints
5. **Write Tests** - Unit and integration tests
6. **Add Security** - JWT authentication/authorization
7. **Configure Logging** - Structured logging with ELK
8. **Set Up CI/CD** - Automated deployment pipeline

---

## 📚 Useful Links

- **Liquibase Docs**: https://docs.liquibase.com/
- **Spring Boot Reference**: https://docs.spring.io/spring-boot/docs/current/reference/html/
- **PostgreSQL Docs**: https://www.postgresql.org/docs/
- **Spring Data JPA**: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

---

## 💡 Pro Tips

### 1. Use Profiles
```bash
# Development
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Production
java -jar app.jar --spring.profiles.active=prod
```

### 2. Monitor Liquibase Changes
```bash
# Watch for new migrations
watch -n 2 "psql -U postgres -d wf_employer_db -c 'SELECT id, filename FROM databasechangelog ORDER BY dateexecuted DESC LIMIT 5;'"
```

### 3. Database Backup
```bash
# Backup
pg_dump -U postgres wf_employer_db > backup_$(date +%Y%m%d).sql

# Restore
psql -U postgres wf_employer_db < backup_20251119.sql
```

### 4. Performance Monitoring
```sql
-- Enable query statistics
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;

-- View slow queries
SELECT 
    query,
    calls,
    total_time,
    mean_time
FROM pg_stat_statements 
ORDER BY mean_time DESC 
LIMIT 10;
```

---

## ✅ Checklist

- [ ] PostgreSQL installed and running
- [ ] Database created (`wf_employer_db`)
- [ ] Application built successfully
- [ ] All 10 migrations applied
- [ ] 8 tables created
- [ ] 13+ reason codes inserted
- [ ] Application starts without errors
- [ ] Health endpoint responds
- [ ] Sample API call works

---

## 🆘 Need Help?

1. Check logs: `./mvnw spring-boot:run` (verbose output)
2. Review `DATABASE_IMPLEMENTATION.md` for schema details
3. Check `IMPLEMENTATION_SUMMARY.md` for overview
4. Verify PostgreSQL logs: `/usr/local/var/log/postgresql@14/`

---

**Status**: ✅ Ready to Go!  
**Version**: 1.0.0  
**Last Updated**: November 19, 2025

