package com.example.firstproject.model.users;
import com.example.firstproject.model.orders.CustomerOrder;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "is_Active")
    private Boolean isActive;
    @Column(name = "Roles")
    private String roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userInformation_fk")
    private UserInformation userInformation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerOrderHistory_fk")
    private List<CustomerOrder> customerOrderHistory;


    public User(){
        this.setActive(true);
        this.setRoles("ROLE_USER");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public List<CustomerOrder> getCustomerOrderHistory() {
        return customerOrderHistory;
    }

    public void setCustomerOrderHistory(List<CustomerOrder> customerOrderHistory) {
        this.customerOrderHistory = customerOrderHistory;
    }
    @Override
    public String toString(){
        return this.getUserInformation().toString();
    }
}


