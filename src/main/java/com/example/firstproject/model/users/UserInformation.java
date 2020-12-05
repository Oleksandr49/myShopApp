package com.example.firstproject.model.users;

import javax.persistence.*;

@Entity
public class UserInformation {
    @Id
    private Integer userInfoId;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;

    @Embedded
    private UserAddress userAddress;

    @OneToOne(mappedBy = "userInformation")
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Override
    public String toString(){
        return this.getName() + " " + this.getSurname() + " " + this.getEmail();
    }

    public void setUser(User user) {
        this.user = user;
    }
}

