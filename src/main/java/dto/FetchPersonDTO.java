/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tweny
 */
public class FetchPersonDTO {
       
    private int id;
    private String name;
    private String street;
    private int zip;
    private String city;
    
     public FetchPersonDTO(String name, String street, int zip, String city) {
        this.name = name;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }
    
    public FetchPersonDTO(int id, String name, String street, int zip, String city) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "FetchPersonDTO{" + "id=" + id + ", name=" + name + ", street=" + street + ", zip=" + zip + ", city=" + city + '}';
    }
    
    

}
