package com.kt.demo.model;

public class Person {

    private String number;  //主键
    private String id;  //学号
    private String name;  //姓名
    private String mobile;  //手机号码

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() { return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

    @Override
    public String toString() {
        return "Person{" +
                "number=" + number +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
