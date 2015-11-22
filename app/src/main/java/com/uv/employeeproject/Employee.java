package com.uv.employeeproject;

/**
 * Created by venkatsr on 22/11/15.
 */
public class Employee {

    private String name;
    private int age;
    byte[] image;

    public Employee(String name, int age, byte[] image) {
        this.name = name;
        this.age = age;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
