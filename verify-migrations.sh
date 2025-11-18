#!/bin/bash

# Liquibase Migration Files Verification Script
# This script verifies all migration files are present and properly formatted

echo "=========================================="
echo "Liquibase Migration Files Verification"
echo "=========================================="
echo ""

MIGRATIONS_DIR="src/main/resources/db/changelog/migrations"
MASTER_FILE="src/main/resources/db/changelog/db.changelog-master.yaml"

# Color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if master changelog exists
if [ -f "$MASTER_FILE" ]; then
    echo -e "${GREEN}✓${NC} Master changelog found: $MASTER_FILE"
else
    echo -e "${RED}✗${NC} Master changelog NOT found: $MASTER_FILE"
    exit 1
fi

echo ""

# Check migration files
EXPECTED_FILES=(
    "001-create-employers-table.yaml"
    "002-create-employer-quotas-table.yaml"
    "003-create-demand-letters-table.yaml"
    "004-create-job-orders-table.yaml"
    "005-create-job-requirements-table.yaml"
    "006-create-job-order-history-table.yaml"
    "007-create-reason-codes-table.yaml"
    "008-create-audit-logs-table.yaml"
    "009-create-indexes.yaml"
    "010-insert-seed-data.yaml"
)

echo "Checking migration files..."
echo ""

total_files=0
valid_files=0
empty_files=0
missing_files=0

for file in "${EXPECTED_FILES[@]}"; do
    filepath="$MIGRATIONS_DIR/$file"
    total_files=$((total_files + 1))

    if [ -f "$filepath" ]; then
        filesize=$(wc -c < "$filepath" | tr -d ' ')

        if [ "$filesize" -eq 0 ]; then
            echo -e "${YELLOW}⚠${NC} $file - EMPTY (0 bytes)"
            empty_files=$((empty_files + 1))
        else
            # Check if file has valid YAML structure
            if grep -q "databaseChangeLog:" "$filepath"; then
                echo -e "${GREEN}✓${NC} $file - OK ($filesize bytes)"
                valid_files=$((valid_files + 1))
            else
                echo -e "${RED}✗${NC} $file - INVALID YAML STRUCTURE ($filesize bytes)"
            fi
        fi
    else
        echo -e "${RED}✗${NC} $file - NOT FOUND"
        missing_files=$((missing_files + 1))
    fi
done

echo ""
echo "=========================================="
echo "Summary:"
echo "=========================================="
echo "Total expected files: $total_files"
echo -e "${GREEN}Valid files: $valid_files${NC}"
echo -e "${YELLOW}Empty files: $empty_files${NC}"
echo -e "${RED}Missing files: $missing_files${NC}"
echo ""

# Count actual changesets
echo "Checking changeset IDs..."
changeset_count=$(grep -r "id: 00" "$MIGRATIONS_DIR" 2>/dev/null | wc -l | tr -d ' ')
echo "Total changesets found: $changeset_count"
echo ""

# Verify master changelog includes all files
echo "Verifying master changelog includes all migrations..."
included_count=0
for file in "${EXPECTED_FILES[@]}"; do
    if grep -q "$file" "$MASTER_FILE"; then
        included_count=$((included_count + 1))
    else
        echo -e "${RED}✗${NC} $file NOT included in master changelog"
    fi
done

if [ "$included_count" -eq "$total_files" ]; then
    echo -e "${GREEN}✓${NC} All $included_count migrations included in master changelog"
else
    echo -e "${RED}✗${NC} Only $included_count of $total_files migrations included"
fi

echo ""

# Final status
if [ "$valid_files" -eq "$total_files" ] && [ "$empty_files" -eq 0 ] && [ "$missing_files" -eq 0 ]; then
    echo -e "${GREEN}=========================================="
    echo -e "✓ ALL CHECKS PASSED!"
    echo -e "==========================================${NC}"
    exit 0
else
    echo -e "${RED}=========================================="
    echo -e "✗ ISSUES DETECTED!"
    echo -e "==========================================${NC}"
    exit 1
fi

