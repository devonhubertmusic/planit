# Plan-it 1.0

Welcome to Plan-it, activity planning on a budget! In the parent folder (planit) you will find a pre-compiled executable .jar file named "plan-it.jar." Ensure you have a current version of Java running on your system, navigate to the parent folder from the command line, then run:

java plan-it.jar

Alternately, you can just double click on the .jar file if you are the pointy-clicky type of person.

Plan-it uses Maven to handle user dependencies such as JUnit testing, and connecting to MySQL. To compile and run Plan-it from scratch, you must have Maven installed. Once installed, first run:

mvn clean compile assembly:single

and then:

java -jar target/planit-1.0-SNAPSHOT-jar-with-dependencies.jar

to run the jar file.
