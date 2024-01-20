package com.driver.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cab{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int Id;
    int perKmRate;
    boolean available;
    @OneToOne(mappedBy = "cab",cascade = CascadeType.ALL)
    Driver driver;
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public int getPerKmRate() {
        return perKmRate;
    }
    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    public Cab(int id, int perKmRate, boolean available, Driver driver) {
        Id = id;
        this.perKmRate = perKmRate;
        this.available = available;
        this.driver = driver;
    }
    public Cab() {
    }

}