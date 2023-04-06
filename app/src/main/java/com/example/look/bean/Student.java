package com.example.look.bean;

import java.io.Serializable;

public class Student implements Serializable {

    // 该版本号的目的在于验证序列化的对象和对应类是否版本匹配。
    private static final long serialVersionUID = 12 ;
    private int age ;
    private String name ;
    // transient修饰字段不会被添加到序列化中
    private transient String sex ;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
