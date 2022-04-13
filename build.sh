#!/bin/sh

set -xe

if [ ! -e /usr/bin/java ]
then
	echo "Java is not installed"
else
	ver=$(( `java --version |  cut -d ' ' -f 2 | head -n 1 | cut -d '.' -f 1` ))
	if [ $ver -lt 11 ]
	then
		echo 'Java version 11 is required'
	fi
fi

javac `find src -name *.java -type f` -d build -cp build -Xdiags:verbose -Xlint:all

cd build

echo 'Manifest-Version: 1.0
Created-By: NimComPoo-04
Main-Class: chungus.Main
Class-Path: chungus.jar
' > manifest.txt

jar cvfm chungus.jar manifest.txt `find chungus -name *.class -type f`
