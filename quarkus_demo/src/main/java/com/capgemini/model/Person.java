package com.capgemini.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Person extends PanacheEntity {
    public String firstname;
    public String lastname;
    public String address;
}
