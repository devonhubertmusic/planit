package planit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
 
//Run using mvn clean test
public class AppTest 
{
    private static Activity activity;
 
    @BeforeClass
    public static void initActivity() {
        activity = new Activity();
    }
 
    @Before
    public void beforeEachTest() {
        System.out.println("This is executed before each Test");
    }
 
    @After
    public void afterEachTest() {
        System.out.println("This is exceuted after each Test");
    }
 
    @Test
    public void testName() {
        activity.setName("test");
 
        assertEquals(activity.getName(), "test");
    }

    @Test
    public void testType() {
        activity.setActivityType("testtype");
 
        assertEquals(activity.getActivityType(), "testtype");
    }

    @Test
    public void testMaxTime() {
        activity.setMaxTime(50);
 
        assertEquals(activity.getMaxTime(), 50, .1);
    }

    @Test
    public void testIdealTime() {
        activity.setIdealTime(30);
 
        assertEquals(activity.getIdealTime(), 30, .1);
    }

    @Test
    public void testActualTime() {
        activity.setActualTime(40);
 
        assertEquals(activity.getActualTime(), 40, .1);
    }

    @Test
    public void testMaxCost() {
        activity.setMaxCost(100);
 
        assertEquals(activity.getMaxCost(), 100, .1);
    }

    @Test
    public void testCostPerHour() {
        double cph = (activity.getMaxCost()/activity.getIdealTime())/60.0;
 
        assertEquals(activity.getCostPerHour(), cph, .1);
    }

    @Test
    public void testCompare() {
    	Activity copy = activity.copy();
    	copy.setIdealTime(40);
 
        assertEquals(activity.compareTo(copy), 1, .1);
    }
}
