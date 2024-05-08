#!/usr/bin/env bash

# location of the H2 JAR
jar=h2-2.2.220-info202.jar

# ports
web_port=8082
db_port=9092

# macOS does not execute scripts from the location they reside, so CD into the correct directory
cd "$(dirname "${0}")"
# echo ${PWD}

# location of H2 database files (set to the current directory)
dbhome="${PWD}"

# escape spaces in home folder name
dbhome=$(echo "${dbhome}" | tr " " "\\ ")


echo Storing/using databases in ${dbhome}
echo

# start H2
java -cp "${jar}" -Duser.home="${dbhome}" -Dh2.baseDir="${dbhome}" -Dh2.bindAddress=localhost -Dh2.consoleTimeout=5400000 org.h2.tools.Console -tcp -tcpPort ${db_port} -web -webPort ${web_port} -tool
