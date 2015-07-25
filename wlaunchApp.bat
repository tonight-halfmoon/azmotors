@REM **************
@REM 	AZMotors
@REM **************

@REM Startup script for AZMotors on Windows OS
@ECHO OFF

@REM --

set CLASSPATH=lib\logback-classic-1.1.2.jar
set CLASSPATH=%CLASSPATH%;lib\logback-core-1.1.2.jar
set CLASSPATH=%CLASSPATH%;lib\slf4j-api-1.7.7.jar
set CLASSPATH=%CLASSPATH%;lib\commons-io-2.4.jar
set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\tools.jar
set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\jre\lib\jfxrt.jar
set CLASSPATH=%CLASSPATH%;application\azmotors-1.0.0-SNAPSHOT.jar

@echo 'AZMotors is launching...'
"%JAVA_HOME%\bin\java" -classpath "%CLASSPATH%" -Xms1024m -Xmx1024m com.azmotors.store.App

@REM --END