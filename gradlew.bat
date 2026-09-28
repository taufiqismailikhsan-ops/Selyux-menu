@echo off
setlocal
set "APP_HOME=%~dp0"
set "WRAPPER_JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar"
set "WRAPPER_URL=https://raw.githubusercontent.com/gradle/gradle/v8.14.3/gradle/wrapper/gradle-wrapper.jar"
set "EXPECTED_SHA256=7d3a4ac4de1c32b59bc6a4eb8ecb8e612ccd0cf1ae1e99f66902da64df296172"

if not exist "%WRAPPER_JAR%" (
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -UseBasicParsing -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'"
  if errorlevel 1 exit /b 1
)

for /f "tokens=1" %%H in ('powershell -NoProfile -Command "(Get-FileHash -Algorithm SHA256 '%WRAPPER_JAR%').Hash.ToLower()"') do set "ACTUAL_SHA256=%%H"
if /I not "%ACTUAL_SHA256%"=="%EXPECTED_SHA256%" (
  echo ERROR: Gradle Wrapper JAR checksum mismatch.
  del /q "%WRAPPER_JAR%" 2>nul
  exit /b 1
)

"%JAVA_HOME%\bin\java.exe" -jar "%WRAPPER_JAR%" %*
if errorlevel 1 exit /b %errorlevel%
endlocal
