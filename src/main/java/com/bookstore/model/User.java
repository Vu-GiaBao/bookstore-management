package com.bookstore.model;

public class User {

    private int id;
    private String username;
    private String role;   // demo only

    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getter cho ID (quan trọng vì invoice cần user_id)
    public int getId() {
        return id;
    }

    // Getter & Setter cơ bản
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return username;   // để ComboBox hiển thị username thay vì đối tượng
    }
}
