# Welcome to Plan-it 1.0!

*******************************************************************************************************************************
# Getting Started

Welcome to Plan-it, activity planning on a budget!

In the parent folder (Planit) you will find a pre-compiled executable .jar file named "plan-it.jar." Ensure you have a current version of Java running on your system, navigate to the parent folder from the command line, then run:

    ./ plan-it.jar

Alternately, you can just double click on the .jar file if you are a pointy-clicky type of person.

Make sure you are connected to the internet before use. Plan-it makes use of a MySQL database located on an external server, so you won't get far without a connection.

Once the main window pops up, you will be able to enter in your current amount of free time, and current budget, and Plan-it will do the rest!

*******************************************************************************************************************************
# Running Plan-it the "Nerd Way"

Plan-it uses Maven to handle user dependencies such as JUnit testing, and connecting to MySQL. To compile and run Plan-it from scratch, you must have Maven installed. Once installed, navigate to the inner project folder (Planit/planit/). Then run:

    mvn clean compile assembly:single

followed by:

    java -jar target/planit-1.0-SNAPSHOT-jar-with-dependencies.jar

to run the .jar file.

All Maven dependencies are specified in the /Planit/planit/pom.xml file.

*******************************************************************************************************************************
# Documentation and Testing

Documentation and testing can be found in the /Planit/Documentation_and_Testing/ folder.

*******************************************************************************************************************************
# Source Files

Source files for the project are located in the /Planit/planit/src/main/java/planit/ folder.
