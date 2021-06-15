/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Dog;
import entities.Hobby;
import entities.Walker;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;

/**
 *
 * @author Tweny
 */
public class WalkerDTO {
    
    private int id;
    private String name;
    private String address;
    private String phone;
    private ArrayList<DogDTO> dogs;

    public WalkerDTO() {
    }

    public WalkerDTO(Walker walker) {
        this.id = walker.getId();
        this.name = walker.getName();
        this.address = walker.getAddress();
        this.phone = walker.getPhone();
        this.dogs = walker.getDogs() != null ? makeDTOlist(walker.getDogs()) : new ArrayList<>();
    }
    
    public WalkerDTO(int id, String name, String address, String phone, ArrayList<DogDTO> dogs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = dogs;
    }
    
    public WalkerDTO(String name, String address, String phone, ArrayList<DogDTO> dogs) {
        this.id = -1;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = dogs;
    }
    
    public WalkerDTO(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    
     public WalkerDTO(String name, String address, String phone) {
        this.id = -1;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    
    private ArrayList<DogDTO> makeDTOlist(List<Dog> dogs) {
        ArrayList<DogDTO> dogDTOlist = new ArrayList<>();
        for (Dog dog : dogs) {
            dogDTOlist.add(new DogDTO(dog));
        }
        return dogDTOlist;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<DogDTO> getDogs() {
        return dogs;
    }

    public void setDogs(ArrayList<DogDTO> dogs) {
        this.dogs = dogs;
    }

    @Override
    public String toString() {
        return "WalkerDTO{" + "id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", dogs=" + dogs + '}';
    }
    
    
    
}
