#!/bin/bash

# Simple migration file checker
echo "Checking Liquibase migration files..."
echo ""

cd "src/main/resources/db/changelog/migrations" || exit 1

for i in {001..010}; do
    pattern="${i}-*.yaml"
    file=$(ls $pattern 2>/dev/null)

    if [ -n "$file" ]; then
        size=$(wc -c < "$file" 2>/dev/null | xargs)
        if [ "$size" -gt 0 ]; then
            echo "✓ $file ($size bytes)"
        else
            echo "✗ $file (EMPTY)"
        fi
    else
        echo "✗ ${i}-*.yaml (NOT FOUND)"
    fi
done

