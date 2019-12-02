package planit;

import static org.junit.Assert.*;
import java.util.*;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
 
//Run using mvn clean test
public class ActivityPlanTest 
{
    private static ArrayList<Activity> myList;
    private static ArrayList<Activity> myActivityList;
    private static Plan myPlan;
    private static int testCounter;
    private static double availableTime;
    private static double availableMoney;
    
    @BeforeClass
    public static void initActivityPlan() {
        System.out.println("Test Class - ActivityPlanTest.java\nThis class contains 2 tests");
        System.out.println("Initializing activity plan to test plan generator. . .\n");
        myPlan = new Plan();
        myList = new ArrayList<Activity>();
        myActivityList = new ArrayList<Activity>();
        Activity test1 = new Activity("Juggle", "Misc", 60, 30, 5);
        Activity test2 = new Activity("Ride a bike", "Misc", 30, 15, 35);
        Activity test3 = new Activity("Go shopping", "Misc", 90, 45, 0);
        myList.add(test1);
        myList.add(test2);
        myList.add(test3);
        availableTime = 60.0;
        availableMoney = 50.0;
        testCounter = 0;
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("Done with ActivityTest.java\n");
    }

    @Before
    public void beforeEachTest() {
        testCounter++;
        System.out.println("Executing test " + testCounter + ":");
    }
 
    @After
    public void afterEachTest() {
        System.out.println("Test " +testCounter+ " finished\n");
    }
 
    @Test
    public void testGetPlanList() {
        System.out.println("Testing generatePlan function");
        boolean generated = myPlan.generatePlan(myList, availableTime, availableMoney, 0.75);
        if(generated){
            myActivityList = myPlan.getActivityList();
        }
        assertTrue(generated);
    }

    @Test
    public void showPlanList(){
        double totalCost = 0.0;
        double totalTime = 0.0;
        System.out.println("Testing plan generator\n");
        System.out.println("Available money: $" + availableMoney);
        System.out.println("Available time: " + availableTime + " minutes\n");
        Activity temp = new Activity();
        int listSize = myActivityList.size();
        System.out.println("Activity list generated has " + listSize + " activies");
        for (int counter = 0; counter < listSize; counter++){
            temp = myActivityList.get(counter);
            totalCost += temp.getMaxCost();
            totalTime += temp.getActualTime();
            System.out.println("Activity " + (counter + 1) + ": " + temp.getName());
        }
        System.out.println("\nTotal cost of plan: $" + totalCost);
        System.out.println("Total time of plan: " + totalTime + " minutes");
        assertTrue(totalCost <= availableMoney && totalTime <= availableTime);
    }



}
