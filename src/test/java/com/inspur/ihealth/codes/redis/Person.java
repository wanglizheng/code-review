package com.inspur.ihealth.codes.redis;

import lombok.Data;

@Data
public class Person {

    private String userName;
    private String phone;

    public Person(String userName,
                  String phone) {
        this.userName = userName;
        this.phone = phone;
    }
}
