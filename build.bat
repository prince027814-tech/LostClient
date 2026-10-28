@echo off
REM Needs Java 21 and Gradle 8.8+ on PATH (https://gradle.org/install/)
gradle build
echo.
echo Jar is in build\libs\  (use the one WITHOUT -sources)
pause
