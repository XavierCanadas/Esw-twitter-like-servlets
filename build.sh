#!/bin/bash

# Maven build script
# This script cleans and packages the project

echo "Running Maven clean..."
./mvnw clean

echo "Running Maven package..."
./mvnw package

echo "Build complete!"

