package com.driver.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Customer
 */
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int customerId;
    String mobile;
    String password;
    @OneToMany(mappedBy = "customer",cascade =  CascadeType.ALL)
    List<TripBooking> tripBookingList=new ArrayList<>();
    public Customer(String mobile, String password, List<TripBooking> tripBookingList) {
        
        this.mobile = mobile;
        this.password = password;
        this.tripBookingList = tripBookingList;
    }
    public Customer() {
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }
    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }

}