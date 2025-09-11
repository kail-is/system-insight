#!/bin/bash

# API endpoint
API_URL="http://localhost:8080/api/v1/users"

# Create admin user
curl -X POST $API_URL \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@systeminsight.com",
    "password": "admin123",
    "role": "ADMIN"
  }'

# Create regular users
for i in {1..5}; do
  curl -X POST $API_URL \
    -H "Content-Type: application/json" \
    -d "{
      \"username\": \"user$i\",
      \"email\": \"user$i@systeminsight.com\",
      \"password\": \"user$i\",
      \"role\": \"USER\"
    }"
done

echo "Users created successfully!" 