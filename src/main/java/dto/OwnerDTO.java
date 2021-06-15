/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Dog;
import entities.Owner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tweny
 */
public class OwnerDTO {

    private int id;
    private String name;
    private String address;
    private String phone;
    private List<DogSmallDTO> dogs;

    public OwnerDTO() {
    }

    public OwnerDTO(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
        this.dogs = owner.getDogs() != null ? makeDTOlist(owner.getDogs()) : new ArrayList<>();
    }

    public OwnerDTO(int id, String name, String address, String phone, List<DogSmallDTO> dogs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = dogs;
    }

    public OwnerDTO(String name, String address, String phone, List<DogSmallDTO> dogs) {
        this.id = -1;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = dogs;
    }

    public OwnerDTO(String name, String address, String phone) {
        this.id = -1;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = new ArrayList<DogSmallDTO>();
    }

    private ArrayList<DogSmallDTO> makeDTOlist(List<Dog> dogs) {
        ArrayList<DogSmallDTO> dogDTOlist = new ArrayList<>();
        for (Dog dog : dogs) {
            dogDTOlist.add(new DogSmallDTO(dog));
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

    public List<DogSmallDTO> getDogs() {
        return dogs;
    }

    public void setDogs(List<DogSmallDTO> dogs) {
        this.dogs = dogs;
    }

    public void addDogs(DogSmallDTO dog) {
        this.dogs.add(dog);
    }

    @Override
    public String toString() {
        return "OwnerDTO{" + "id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", dogs=" + dogs + '}';
    }

}
