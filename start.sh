#!/bin/sh

#Build and run backend
./gradlew build
./gradlew run &

#Navigate and install and run node
cd Frontend/flat-management-frontend
npm install
npm start 