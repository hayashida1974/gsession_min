#!/bin/bash
#
# Start GroupSession h2 Database user&password edit
#
LOGDIR=../log
GSROOT=../../../

if [ -z "$JAVA_HOME" ]
then
    echo "Please set the JAVA_HOME"
    exit 0
fi

#dirCheck
if [ -f "user_pass.sh" ]
then
    echo "Directory OK"
else
    echo "Please change directory with the user_pass.sh"
    exit 0
fi

echo "CLASSPATH=" $CLASSPATH
CLASSPATH="$CLASSPATH"
export CLASSPATH="$CLASSPATH":../classes
export CLASSPATH="$CLASSPATH":../conf
export CLASSPATH="$CLASSPATH":../../classes
export CLASSPATH="$CLASSPATH":../lib/servlet-api.jar
export CLASSPATH="$CLASSPATH":../../lib/commons-logging-1.3.0.jar
export CLASSPATH="$CLASSPATH":../../lib/commons-digester-1.8.jar
export CLASSPATH="$CLASSPATH":../../lib/commons-collections-3.2.1.jar
export CLASSPATH="$CLASSPATH":../../lib/commons-beanutils-1.8.3.jar
export CLASSPATH="$CLASSPATH":../../lib/oro-2.0.8.jar
export CLASSPATH="$CLASSPATH":../../lib/h2_1.3.jar
export CLASSPATH="$CLASSPATH":../../lib/velocity-engine-core-2.3.jar
export CLASSPATH="$CLASSPATH":../../lib/log4j-api-2.22.0.jar
export CLASSPATH="$CLASSPATH":../../lib/log4j-core-2.22.0.jar
export CLASSPATH="$CLASSPATH":../../lib/log4j-jcl-2.22.0.jar
export CLASSPATH="$CLASSPATH":../../lib/log4j-slf4j-impl-2.22.0.jar
export CLASSPATH="$CLASSPATH":../../lib/log4j-web-2.22.0.jar

echo "CLASSPATH=" $CLASSPATH
echo "LOGDIR=" $LOGDIR
set __LOGDIR=$LOGDIR
if [ -d $LOGDIR ]
then
  echo "LOG Directory is Exist"
else
  mkdir $LOGDIR
fi

$JAVA_HOME/bin/java -classpath $CLASSPATH jp.co.sjts.gsession.dsedit.DataSourceEdit $GSROOT