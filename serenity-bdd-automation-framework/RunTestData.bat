@echo off
cd /d "%~dp0"
REM Example: run only automated Lead scenarios (exclude pending tags).
call mvn clean verify -Dcucumber.filter.tags="@automated"
