# planit

To build maven project, navigate to the planit folder with pom.xml file and on your console/terminal and type:
 
	mvn clean compile assembly:single

and then:

	java -jar target/planit-1.0-SNAPSHOT-jar-with-dependencies.jar 

to run the jar file.


Build HTML JavaDoc using:
 
	mvn clean site

**************************************************************************************

At its core, the program will attempt to create the best activity plan possible for a 
user, given the specified resources: time and money. The end result would be a 
printable/saveable list of activities, along with the estimated time and cost for each 
activity, and the overall time and cost. There are three main steps needed for this to 
happen.


1) The program needs to aquire data from the user. 

For this initial version, the code could simply prompt the user to enter in several 
activities they enjoy, along with the cost range and time range. From this, it could 
create a database, which would serve as the library from which potential activities could 
be drawn. However, even with a good user interface, this task could be tedious for the 
user, so future versions might look into smarter methods of gathering this data, such as 
location/preference based activity suggestions. In addition, the database could be later 
expanded to include travel time/distance from the user's location, type of activity, etc. 
to further refine the quality of the final list. 


2) The algorithm needs to choose good activities.

The details of how this will be done will need to be refined as we go along. In its most 
basic form, the algorithm could be a loop, adding activities from the database until the 
upper cost/time limits are reached. However, the code would ideally be able to adjust the 
time and cost for each activity within their ranges, in order to maximize the time spent 
having fun, and minimize the cost. If other variables are added to the database, the 
algorithm would need to consider these as well. How exactly it will do this will be one 
of the harder questions we will face, as there isn't one "right" way to approach it. One 
approach would be to analyze how we (as humans) make decisions about what we want to do 
with our free time. What variables do we consider, and what are the deciding factors?


3) The program needs to learn from user feedback.

Possible features would include the ability for a user to delete an activity from the 
list, with the algorithm immediately replacing it with another suggestion. This would 
allow the user to modify the plan to their liking. Perhaps the user could even stretch 
out one preferred activity, with the others shifting to fit around it. In addition, after 
a user completes an activity plan, perhaps they could rate their experience, and their 
enjoyment of each of the activities. These ideas may be best suited for a later version 
of the project, but they are something to consider when creating the core architecture 
of the initial code.

