#!/bin/bash
#Original version of this script from http://azureus.sf.net

# get the app dir
PROGRAM_DIR=`dirname "$0"`
PROGRAM_DIR=`cd "$PROGRAM_DIR"; pwd`

cd ${PROGRAM_DIR}

java -jar @@HOUSEKEEPER_JAR@@
