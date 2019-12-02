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
    private static Plan myPlan;
    private static int testCounter;
    
    @BeforeClass
    public static void initActivityPlan() {
        System.out.println("Test Class - ActivityPlanTest.java\nThis class contains 1 test\n");
        System.out.println("Initializing activity plan to test plan generator. . .");
        myPlan = new Plan();
        myList = new ArrayList<Activity>();
        Activity test1 = new Activity("Test1 - Name", "Misc", 60, 30, 10);
        Activity test2 = new Activity("Test2 - Name", "Misc", 30, 15, 15);
        Activity test3 = new Activity("Test3 - Name", "Misc", 90, 45, 20);
        myList.add(test1);
        myList.add(test2);
        myList.add(test3);
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
        ArrayList<Activity> myActivityList = new ArrayList<Activity>();
        boolean generated = myPlan.generatePlan(myList, 60.0, 50.0, 0.75);
        if(generated){
            myActivityList = myPlan.getActivityList();
        }
        assertTrue(generated);
    }



}
