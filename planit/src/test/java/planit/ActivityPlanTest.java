package planit;

import static org.junit.Assert.*;
import java.util.*;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
 
//Run using mvn clean test
public class ActivityPlanTest 
{
    private static ActivityPlan activityPlan;
    
    @BeforeClass
    public static void initActivityPlan() {
        activityPlan = new ActivityPlan();
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

    public void testGetPlanList() {
        ArrayList<Activity> planListTest = activityPlan.getPlanList(0.0);
        assertNotNull(planListTest);
    }

}
