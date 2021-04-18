/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Tweny
 */
public class FetchPersonDTOtoPost {
    
    private String name;
    private String street;
    private int zip;
    private String city;

    public FetchPersonDTOtoPost() {
    }
    
    public FetchPersonDTOtoPost(FetchPersonDTO fetchPersonDTO) {
        this.name = fetchPersonDTO.getName();
        this.street = fetchPersonDTO.getStreet();
        this.zip = fetchPersonDTO.getZip();
        this.city = fetchPersonDTO.getCity();
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
        return "FetchPersonDTOtoPost{" + "name=" + name + ", street=" + street + ", zip=" + zip + ", city=" + city + '}';
    }
    
    
    
}
