import java.util.*;

public class PlanitRunner
{
//Fields:


    public static void main(String[] args) {
        
        System.out.println("It Works!"); //Test
        
        Activity goShopping = new Activity();
        goShopping.setName("Go Shopping");
        goShopping.setMaxTime(120.00);
        goShopping.setIdealTime(90.00);
        
        Plan testPlan = new Plan();
        
        testPlan.generatePlan();
        
        ArrayList<Activity> activityList = testPlan.activityList;
        
        activityList.add(goShopping);
        
        System.out.println(activityList);

    }

//Methods and stuff:


}
