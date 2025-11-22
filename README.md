public class Customer 
{
    private int id;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public Customer(int id, String name, String email, String phone) 
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Setters 
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    // Key Function: display infomation
    public void displayInfo() 
    {
        System.out.println("--- Customer Information ---");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
    @Override
    public String toString() {
        return "Customer{ID=" + id + ", Name=" + name + ", Email=" + email + ", Phone=" + phone + "}";
    }
}

public class User 
{
    private String userid;
    private String username;
    private String password;
    private String role;   // "admin", "staff"
    private int salary;

    public User(String userid, String username, String password, String role, int salary) 
    {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.role = role;
        this.salary = salary;
    }

    // Getters
    public String getUserid()   { return userid; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
    public int getSalary()      { return salary; }

    // Login check
    public boolean login(String passwordInput) 
    {
        return this.password.equals(passwordInput);
    }

    public void logout() 
    {
        System.out.println(username + " has been logged out.");
    }

    public void displayInfo() 
    {
        System.out.println("--- " + role + " Information ---");
        System.out.println("ID: " + userid);
        System.out.println("Name: " + username);
        System.out.println("Salary: " + salary);
    }

    @Override
    public String toString() {
        return "User{" + userid + ", Name=" + username + ", Role=" + role + ", Salary=" + salary + "}";
    }
}

public class UserService 
{
    private ArrayList<User> users = new ArrayList<>();
    public void addUser(User u) 
    {
        users.add(u);
    }

    // Login logic
    public User login(String username, String password) 
    {
        for (User u : users) 
        {
            if (u.getUsername().equals(username) && u.login(password)) 
            {
                return u;   // login success
            }
        }
        return null; // failed
    }

    public User findById(String userid) 
    {
        for (User u : users) 
        {
            if (u.getUserid().equals(userid)) return u;
        }
        return null;
    }
}
public class CustomerService 
{
    private ArrayList<Customer> customers = new ArrayList<>();
    public void addCustomer(Customer c) 
    {
        customers.add(c);
    }

    public Customer searchByName(String name) 
    {
        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(name)) 
            {
                return c;
            }
        }
        return null;
    }

    public Customer searchById(int id) 
    {
        for (Customer c : customers) 
        {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public void displayAll() 
    {
        for (Customer c : customers) 
        {
            System.out.println(c);
        }
    }
}
