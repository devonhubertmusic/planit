package planit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
 
//Run using mvn clean test
public class ActivityTest 
{
    private static Activity activity;
    private static int testCounter;
 
    @BeforeClass
    public static void initActivity() {
        activity = new Activity();
        testCounter = 0;

        System.out.println("Test Class - ActivityTest.java\nThis class contains 8 tests\n");
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
    public void testName() {
        System.out.println("Testing getName function");
        activity.setName("test");
 
        assertEquals(activity.getName(), "test");
    }

    @Test
    public void testType() {
        System.out.println("Testing getActivityType function");
        activity.setActivityType("testtype");
 
        assertEquals(activity.getActivityType(), "testtype");
    }

    @Test
    public void testMaxTime() {
        System.out.println("Testing getMaxTime function");
        activity.setMaxTime(50);
        
        assertEquals(activity.getMaxTime(), 50, .1);
    }

    @Test
    public void testIdealTime() {
        System.out.println("Testing getIdealTime function");
        activity.setIdealTime(30);
 
        assertEquals(activity.getIdealTime(), 30, .1);
    }

    @Test
    public void testActualTime() {
        System.out.println("Testing getActualTime function");
        activity.setActualTime(40);
 
        assertEquals(activity.getActualTime(), 40, .1);
    }

    @Test
    public void testMaxCost() {
        System.out.println("Testing getMaxCost function");
        activity.setMaxCost(100);
 
        assertEquals(activity.getMaxCost(), 100, .1);
    }

    @Test
    public void testCostPerHour() {
        System.out.println("Testing getCostPerHour function");
        double cph = (activity.getMaxCost()/activity.getIdealTime())/60.0;
 
        assertEquals(activity.getCostPerHour(), cph, .1);
    }

    @Test
    public void testCompare() {
        System.out.println("Testing compareTo function");
    	Activity copy = activity.copy();
    	copy.setIdealTime(40);
 
        assertEquals(activity.compareTo(copy), 1, .1);
    }
}
