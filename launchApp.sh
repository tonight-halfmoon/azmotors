#!/bin/sh

unamestr=`uname`
if [ "$JAVA_HOME" = '' ]; then
  if [ "$unamestr" = 'Darwin' ]; then
    export JAVA_HOME = `/usr/libexec/java_home`
  else
    echo "JAVA_HOME has not been set."
    exit 0;
  fi
fi

# Requires the jar to be build using 
# mvn package 
#or 
# ant jar
#
#
# You may need to set -Xmx (max heap) and -XX:MaxPermSize

APP_HOME="`pwd -P`"

CLASSPATH=$CLASSPATH:lib/logback-classic-1.1.2.jar
CLASSPATH=$CLASSPATH:lib/logback-core-1.1.2.jar
CLASSPATH=$CLASSPATH:lib/slf4j-api-1.7.7.jar
CLASSPATH=$CLASSPATH:lib/commons-io-2.4.jar
CLASSPATH=$CLASSPATH:$JAVA_HOME/lib/tools.jar
CLASSPATH=$CLASSPATH:$JAVA_HOME/jre/lib/jfxrt.jar
CLASSPATH=$CLASSPATH:$APP_HOME/application/azmotors-1.0.0-SNAPSHOT.jar

echo 'AZMotors is launching...'

$JAVA_HOME/bin/java -cp $CLASSPATH -ea -Xms1024m -Xmx1024m com.azmotors.store.App

