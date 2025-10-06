@echo off

echo Running Maven clean...
mvnw.cmd clean

echo Running Maven package...
mvnw.cmd package

echo Build complete!