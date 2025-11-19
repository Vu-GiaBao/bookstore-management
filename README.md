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
}

public class User 
{
    private String username;
    private String password; 
    private String role;     // "admin", "employee"

    // Constructor
    public User(String username, String password, String role) 
    {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Key Function:  (using by UserManager) 
    public boolean login(String inputPassword) {
        // Check password
        return this.password.equals(inputPassword);
    }

    // Key Function: Log out(announcement)
    public void logout() 
    {
        System.out.println(username + " has logged out.");
    }
}

