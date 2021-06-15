/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Walker;

/**
 *
 * @author Tweny
 */
public class WalkerSmallDTO {
    
    private int id;
    private String name;
    private String address;
    private String phone;

    public WalkerSmallDTO() {
    }

    public WalkerSmallDTO(Walker walker) {
        this.id = walker.getId();
        this.name = walker.getName();
        this.address = walker.getAddress();
        this.phone = walker.getPhone();
    }
    
    public WalkerSmallDTO(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    
    public WalkerSmallDTO(String name, String address, String phone) {
        this.id = -1;
        this.name = name;
        this.address = address;
        this.phone = phone;
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

    @Override
    public String toString() {
        return "WalkerSmallDTO{" + "id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + '}';
    }
    
}
