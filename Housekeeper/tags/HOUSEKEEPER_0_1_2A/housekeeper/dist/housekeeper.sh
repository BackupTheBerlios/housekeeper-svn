#!/bin/bash
#Original version of this script from http://azureus.sf.net

######## CONFIGURE ########
JAVA_PROGRAM_DIR=""				# use full path to java bin dir, ex. "/usr/java/j2sdk1.4.2/bin/"
###########################

MSG0="OOPS, unable to locate java exec in "
MSG1=" hierarchy"
MSG2="Java exec found in "
MSG3="Java exec not found in PATH, starting auto-search..."
MSG4="Java exec found in PATH. Verifying..."

look_for_java()
{
  JAVADIR=/usr/java
  IFS=$'\n'
  potential_java_dirs=(`ls -1 "$JAVADIR" | sort | tac`)
  IFS=
  for D in "${potential_java_dirs[@]}"; do
    if [[ -d "$JAVADIR/$D" && -x "$JAVADIR/$D/bin/java" ]]; then
      JAVA_PROGRAM_DIR="$JAVADIR/$D/bin/"
      echo $MSG2 $JAVA_PROGRAM_DIR
      return 0
    fi
  done
  echo $MSG0 "${JAVADIR}/" $MSG1
  return 1
}


# locate and test the java executable
if [ "$JAVA_PROGRAM_DIR" == "" ]; then
  if ! command -v java &>/dev/null; then
    echo $MSG3
    if ! look_for_java ; then
      exit 1
    fi
  else
    echo $MSG4
      if ! look_for_java ; then
        exit 1
      fi
  fi
fi

# get the app dir
PROGRAM_DIR=`dirname "$0"`
PROGRAM_DIR=`cd "$PROGRAM_DIR"; pwd`

cd ${PROGRAM_DIR}

# build the classpath
for FILE in *.jar; do CLASSPATH="${CLASSPATH}:${FILE}"; done
for FILE in lib/*.jar; do CLASSPATH="${CLASSPATH}:${FILE}"; done


echo "${JAVA_PROGRAM_DIR}java -cp ${CLASSPATH} net.sf.housekeeper.Housekeeper '$@'"
${JAVA_PROGRAM_DIR}java -cp ${CLASSPATH} net.sf.housekeeper.Housekeeper "$@"
#${JAVA_PROGRAM_DIR}java -ea -Djava.util.logging.config.file=etc/logging.properties -cp ${CLASSPATH} net.sf.housekeeper.Housekeeper "$@"
