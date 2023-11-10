@echo off
setlocal

if not exist mvnw (
    call mvn -N io.takari:maven:wrapper
)

call .\mvnw %*
