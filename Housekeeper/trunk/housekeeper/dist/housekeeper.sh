#!/bin/sh
java -cp housekeeper-0.1.2.jar:lib/binding-1.0b3.jar:lib/commons-lang-2.0.jar:lib/commons-logging-1.0.4.jar:lib/forms-1.0.5.jar:lib/jdom-1.0.jar:lib/looks-1.2.2.jar net.sf.housekeeper.Housekeeper

#Use this line for debugging:
#java -ea -Djava.util.logging.config.file=etc/logging.properties -cp housekeeper-0.1.2.jar:lib/binding-1.0b3.jar:lib/commons-lang-2.0.jar:lib/commons-logging-1.0.4.jar:lib/forms-1.0.5.jar:lib/jdom-1.0.jar:lib/looks-1.2.2.jar net.sf.housekeeper.Housekeeper
