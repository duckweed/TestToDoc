
call mvn install -Dmaven.test.skip

call mvn  jar:test-jar

dir target\TestToDoc-Main-1.0-SNAPSHOT-tests.jar

cd TestToDoc-Main

call mvn install

call runDemo.bat

cd ..