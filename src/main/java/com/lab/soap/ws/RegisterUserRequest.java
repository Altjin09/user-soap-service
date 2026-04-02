package com.lab.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "registerUserRequest", namespace = "http://lab.com/soap")
public class RegisterUserRequest {

    @XmlElement(name = "username", namespace = "http://lab.com/soap", required = true)
    private String username;

    @XmlElement(name = "password", namespace = "http://lab.com/soap", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}