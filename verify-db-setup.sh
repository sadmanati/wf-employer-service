#!/bin/bash

# ═══════════════════════════════════════════════════════════════════════════
# WF Employer Service - Database Migration Verification Script
# ═══════════════════════════════════════════════════════════════════════════

set -e

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "  🚀 WF Employer Service - Database Migration Verification"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# Color codes
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Database connection details
DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-wf_employer_db}
DB_USER=${DB_USER:-postgres}
DB_PASSWORD=${DB_PASSWORD:-postgres}

echo ""
echo "${BLUE}📋 Configuration:${NC}"
echo "   Database Host: ${DB_HOST}"
echo "   Database Port: ${DB_PORT}"
echo "   Database Name: ${DB_NAME}"
echo "   Database User: ${DB_USER}"
echo ""

# Function to execute SQL
execute_sql() {
    PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -p ${DB_PORT} -U ${DB_USER} -d ${DB_NAME} -t -c "$1"
}

# Step 1: Check if database exists
echo "${YELLOW}[1/8] Checking database connection...${NC}"
if PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -p ${DB_PORT} -U ${DB_USER} -lqt | cut -d \| -f 1 | grep -qw ${DB_NAME}; then
    echo "   ${GREEN}✓ Database '${DB_NAME}' exists${NC}"
else
    echo "   ${RED}✗ Database '${DB_NAME}' does not exist${NC}"
    echo "   ${YELLOW}Creating database...${NC}"
    PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -p ${DB_PORT} -U ${DB_USER} -c "CREATE DATABASE ${DB_NAME};"
    echo "   ${GREEN}✓ Database created${NC}"
fi

# Step 2: Check Liquibase changelog table
echo ""
echo "${YELLOW}[2/8] Checking Liquibase setup...${NC}"
CHANGELOG_EXISTS=$(execute_sql "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'databasechangelog');" | xargs)

if [ "$CHANGELOG_EXISTS" = "t" ]; then
    echo "   ${GREEN}✓ Liquibase is initialized${NC}"
    MIGRATIONS_COUNT=$(execute_sql "SELECT COUNT(*) FROM databasechangelog;" | xargs)
    echo "   ${BLUE}   Applied migrations: ${MIGRATIONS_COUNT}${NC}"
else
    echo "   ${YELLOW}! Liquibase not yet initialized (will be done on first run)${NC}"
fi

# Step 3: Check if tables exist
echo ""
echo "${YELLOW}[3/8] Verifying database tables...${NC}"
TABLES=("employers" "employer_quotas" "demand_letters" "job_orders" "job_requirements" "job_order_history" "reason_codes" "audit_logs")

for table in "${TABLES[@]}"; do
    TABLE_EXISTS=$(execute_sql "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = '${table}');" | xargs)
    if [ "$TABLE_EXISTS" = "t" ]; then
        ROW_COUNT=$(execute_sql "SELECT COUNT(*) FROM ${table};" | xargs)
        echo "   ${GREEN}✓ ${table}${NC} (${ROW_COUNT} rows)"
    else
        echo "   ${YELLOW}○ ${table} (not created yet)${NC}"
    fi
done

# Step 4: Check constraints
echo ""
echo "${YELLOW}[4/8] Verifying database constraints...${NC}"
CONSTRAINTS_COUNT=$(execute_sql "SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_type IN ('PRIMARY KEY', 'FOREIGN KEY', 'UNIQUE', 'CHECK') AND table_schema = 'public';" | xargs)

if [ ! -z "$CONSTRAINTS_COUNT" ]; then
    echo "   ${GREEN}✓ Found ${CONSTRAINTS_COUNT} constraints${NC}"

    PK_COUNT=$(execute_sql "SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_type = 'PRIMARY KEY' AND table_schema = 'public';" | xargs)
    FK_COUNT=$(execute_sql "SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_type = 'FOREIGN KEY' AND table_schema = 'public';" | xargs)
    UK_COUNT=$(execute_sql "SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_type = 'UNIQUE' AND table_schema = 'public';" | xargs)
    CK_COUNT=$(execute_sql "SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_type = 'CHECK' AND table_schema = 'public';" | xargs)

    echo "   ${BLUE}   - Primary Keys: ${PK_COUNT}${NC}"
    echo "   ${BLUE}   - Foreign Keys: ${FK_COUNT}${NC}"
    echo "   ${BLUE}   - Unique Constraints: ${UK_COUNT}${NC}"
    echo "   ${BLUE}   - Check Constraints: ${CK_COUNT}${NC}"
else
    echo "   ${YELLOW}○ No constraints found (tables not created yet)${NC}"
fi

# Step 5: Check indexes
echo ""
echo "${YELLOW}[5/8] Verifying database indexes...${NC}"
INDEXES_COUNT=$(execute_sql "SELECT COUNT(*) FROM pg_indexes WHERE schemaname = 'public';" | xargs)

if [ ! -z "$INDEXES_COUNT" ]; then
    echo "   ${GREEN}✓ Found ${INDEXES_COUNT} indexes${NC}"
else
    echo "   ${YELLOW}○ No indexes found (tables not created yet)${NC}"
fi

# Step 6: Check seed data
echo ""
echo "${YELLOW}[6/8] Verifying seed data...${NC}"
REASON_CODES_EXISTS=$(execute_sql "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'reason_codes');" | xargs)

if [ "$REASON_CODES_EXISTS" = "t" ]; then
    REASON_CODES_COUNT=$(execute_sql "SELECT COUNT(*) FROM reason_codes;" | xargs)
    if [ "$REASON_CODES_COUNT" -gt 0 ]; then
        echo "   ${GREEN}✓ Reason codes seeded: ${REASON_CODES_COUNT} records${NC}"

        WF_CODES=$(execute_sql "SELECT COUNT(*) FROM reason_codes WHERE applicable_to = 'workforce_uae';" | xargs)
        EMP_CODES=$(execute_sql "SELECT COUNT(*) FROM reason_codes WHERE applicable_to = 'employer';" | xargs)

        echo "   ${BLUE}   - Workforce UAE codes: ${WF_CODES}${NC}"
        echo "   ${BLUE}   - Employer codes: ${EMP_CODES}${NC}"
    else
        echo "   ${YELLOW}○ Reason codes table is empty${NC}"
    fi
else
    echo "   ${YELLOW}○ Reason codes table not created yet${NC}"
fi

# Step 7: Validate migration files
echo ""
echo "${YELLOW}[7/8] Validating migration files...${NC}"
MIGRATION_DIR="src/main/resources/db/changelog/migrations"

if [ -d "$MIGRATION_DIR" ]; then
    MIGRATION_FILES=$(ls -1 $MIGRATION_DIR/*.yaml 2>/dev/null | wc -l)
    echo "   ${GREEN}✓ Found ${MIGRATION_FILES} migration files${NC}"

    # List migration files
    for file in $MIGRATION_DIR/*.yaml; do
        filename=$(basename "$file")
        filesize=$(wc -c < "$file" | xargs)

        if [ "$filesize" -gt 100 ]; then
            echo "   ${BLUE}   - ${filename}${NC} (${filesize} bytes)"
        else
            echo "   ${RED}   - ${filename}${NC} (${filesize} bytes) ${RED}[EMPTY/TOO SMALL]${NC}"
        fi
    done
else
    echo "   ${RED}✗ Migration directory not found${NC}"
fi

# Step 8: Check application configuration
echo ""
echo "${YELLOW}[8/8] Checking application configuration...${NC}"

if [ -f "src/main/resources/application.yml" ]; then
    echo "   ${GREEN}✓ application.yml exists${NC}"

    if grep -q "liquibase:" "src/main/resources/application.yml"; then
        echo "   ${GREEN}✓ Liquibase configuration found${NC}"
    else
        echo "   ${RED}✗ Liquibase configuration missing${NC}"
    fi
else
    echo "   ${RED}✗ application.yml not found${NC}"
fi

if [ -f "liquibase.properties" ]; then
    echo "   ${GREEN}✓ liquibase.properties exists${NC}"
else
    echo "   ${YELLOW}○ liquibase.properties not found (optional)${NC}"
fi

# Summary
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "  ${GREEN}✓ Verification Complete${NC}"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "${BLUE}Next Steps:${NC}"
echo "   1. Run: ${YELLOW}./mvnw clean install${NC}"
echo "   2. Run: ${YELLOW}./mvnw liquibase:update${NC} (optional - will run automatically on startup)"
echo "   3. Run: ${YELLOW}./mvnw spring-boot:run${NC}"
echo ""
echo "${BLUE}To view applied migrations:${NC}"
echo "   ${YELLOW}PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -p ${DB_PORT} -U ${DB_USER} -d ${DB_NAME} -c 'SELECT * FROM databasechangelog ORDER BY dateexecuted;'${NC}"
echo ""

