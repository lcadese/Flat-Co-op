@echo off

:: location of H2 JAR
set jar=h2-2.2.220-info202.jar

:: ports
set web_port=8082
set db_port=9092

:: location of the H2 database files
if exist "h:\" (
   set dbhome=h:\h2-dbs
) else (
	if exist "%USERPROFILE%\Documents" (
		::set dbhome=%USERPROFILE%\Documents\h2-dbs
		set dbhome=%~dp0.
	) else (
		echo Could not locate Documents folder!
		pause
		exit /b 1
	)
)

echo Storing/using databases in %dbhome%

:: create dbhome if it doesn't already exist
if not exist "%dbhome%" mkdir "%dbhome%"

:: start H2
javaw.exe -cp %jar% -Duser.home="%dbhome%" -Dh2.baseDir="%dbhome%" -Dh2.bindAddress=localhost -Dh2.consoleTimeout=5400000 org.h2.tools.Console -tcp -tcpPort %db_port% -web -webPort %web_port% -tool

if errorlevel 1 pause
