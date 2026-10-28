#!/bin/sh
# Needs Java 21 and Gradle 8.8+ on PATH
gradle build && echo "Jar is in build/libs/ (use the one without -sources)"
