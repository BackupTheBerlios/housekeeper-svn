#!/bin/bash
#Original version of this script from http://azureus.sf.net

# get the app dir
PROGRAM_DIR=`dirname "$0"`
PROGRAM_DIR=`cd "$PROGRAM_DIR"; pwd`

cd ${PROGRAM_DIR}

# build the classpath
for FILE in *.jar; do CLASSPATH="${CLASSPATH}:${FILE}"; done
for FILE in lib/*.jar; do CLASSPATH="${CLASSPATH}:${FILE}"; done


echo "java -cp ${CLASSPATH} net.sf.housekeeper.ApplicationController '$@'"
java -cp ${CLASSPATH} net.sf.housekeeper.ApplicationController "$@"
#java -ea -Djava.util.logging.config.file=etc/logging.properties -cp ${CLASSPATH} net.sf.housekeeper.ApplicationController "$@"
