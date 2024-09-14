package Model;

import java.sql.Date;

import javax.swing.ImageIcon;

public class Account {
    private int id;
    private int role;
    private String name;
    private Date dob;
    private String phoneNumber;
    private String email;
    private String school;
    private String Class;
    private ImageIcon image;

    public Account(int role, String name, Date dob, String phoneNumber, String email, String school, String Class,
            ImageIcon image) {
        this.role = role;
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.school = school;
        this.Class = Class;
        this.image = image;
    }

    public Account(int id, int role, String name, Date dob, String phoneNumber, String email, String school,
            String Class,
            ImageIcon image) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.school = school;
        this.Class = Class;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSchool() {
        return school;
    }

    public String getclass() {
        return Class;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setClass(String Class) {
        this.Class = Class;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}
