#!/bin/bash

# Define variables
TABLE_NAME="OTPs"
REGION="ap-southeast-2"
PARTITION_KEY="email"
SORT_KEY="otp"
TTL_ATTRIBUTE="TTL"

# Create the DynamoDB table
aws dynamodb create-table \
    --table-name $TABLE_NAME \
    --attribute-definitions \
        AttributeName=$PARTITION_KEY,AttributeType=S \
        AttributeName=$SORT_KEY,AttributeType=S \
    --key-schema \
        AttributeName=$PARTITION_KEY,KeyType=HASH \
        AttributeName=$SORT_KEY,KeyType=RANGE \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --region $REGION

# Enable TTL on the table
aws dynamodb update-time-to-live \
    --table-name $TABLE_NAME \
    --time-to-live-specification \
        "Enabled=true, AttributeName=$TTL_ATTRIBUTE" \
    --region $REGION

echo "DynamoDB table $TABLE_NAME created and TTL enabled."