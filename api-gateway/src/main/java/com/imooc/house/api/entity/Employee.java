package com.imooc.house.entity;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Employee  {

    public Employee() {
    }

    public Employee(Integer id, Integer age, String gender, String fName, String lName){
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.firstName = fName;
        this.lastName = lName;
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
    }

    private Integer id;
    private Integer age;
    private String gender;
    private String firstName;
    private String lastName;
    private List<Integer> list = new ArrayList<Integer>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //Please generate Getter and Setters

    @Override
    public String toString() {
        return this.id.toString()+" - "+this.age.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    public static Predicate<Employee> isAdultMale() {
        return p -> p.getAge() > 21 && p.getGender().equalsIgnoreCase("M");
    }
    public static Predicate<Employee> isAdultFemale() {
        return p -> p.getAge() > 18 && p.getGender().equalsIgnoreCase("F");
    }
    public static Predicate<Employee> isAgeMoreThan(Integer age) {
        return p -> p.getAge() > age;
    }

    public static Employee getEmployee() {
        Employee a = new Employee();
        a.setId(100);
        a.setAge(18);
        return a;
    }
}
