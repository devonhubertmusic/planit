public class User {

    private int id;
    private String firstName;
    private int lastName;
    private int age;

    public User(int ID, String FirstName, int LastName, int Age)
    {
        this.id = ID;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.age = Age;
    }

    public int getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public int getLastNAme()
    {
        return lastName;
    }

    public int getAge()
    {
        return age;
    }
}
