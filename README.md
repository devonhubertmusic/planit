# Welcome to Plan-it 1.0!

*******************************************************************************************************************************
# Getting Started

Welcome to Plan-it, activity planning on a budget!

In the parent folder (Planit) you will find a pre-compiled executable .jar file named "plan-it.jar." Ensure you have a current version of Java running on your system, navigate to the parent folder from the command line, then run:

    java -jar plan-it.jar

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
# Testing

We used Maven to handle JUnit test dependencies, so Maven must first be installed in order to run unit testing. Once installed, navigate to the inner project folder (Planit/planit/). Then run:

    mvn -Dtest=TestAll test

Testing documentation and JUnit test sample output can also be found in the /Planit/Testing/ folder.

*******************************************************************************************************************************
# Documentation

The team used Clubhouse.io as our virtual scrumboard/environment. Here is the link to our project on Clubhouse:

    https://app.clubhouse.io/teamrock/epic/37/plan-it

In addition, records of all documentation can be found in the /Planit/Documentation/ folder.

Current contents:

Plan-it Release Plan.pdf
Working Prototype Report.pdf
System and Unit Test Report.pdf
/Sprint\ Plans/
    Sprint 1 Plan.docx
    Sprint 1 Tasks - Sheet1.pdf (before we started using clubhouse)
    Sprint 2 Plan.docx
    Sprint 3 Plan.docx
    Sprint 4 Plan.docx
/Sprint\ Reports/
    Sprint 1 Report.docx
    Sprint 2 Report.docx
    Sprint 3 Report.docx
    Sprint 4 Report.docx
/Software\ Guides\ and\ Requirements/
    Definition of Done.docx
    Definition of Done.pdf
    Google Java Style Guide.pdf
/Presentations/
    Plan-it Presentation.pptx
    Plan-it Final Presentation.pptx

*******************************************************************************************************************************
# Source Files

Source files for the project are located in the /Planit/planit/src/main/java/planit/ folder.
