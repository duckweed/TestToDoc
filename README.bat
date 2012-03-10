
@echo off

call mvn clean install -Dmaven.test.skip

cd TestToDoc-TestJar

call mvn jar:test-jar

::cd ..\TestToDoc-Main

::call mvn install

::call runDemo.bat

cd ..