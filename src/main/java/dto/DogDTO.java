/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Dog;
import entities.Walker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Tweny
 */
public class DogDTO {

    private int id;
    private String name;
    private String breed;
    private String image;
    private Dog.Gender gender;
    private LocalDate birthdate;
    private OwnerSmallDTO owner;
    private List<WalkerSmallDTO> walkers;

    public DogDTO() {
    }

    public DogDTO(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
        this.breed = dog.getBreed();
        this.image = dog.getImage();
        this.gender = dog.getGender();
        this.birthdate = dog.getBirthdate();
        this.owner = dog.getOwner() != null ? new OwnerSmallDTO(dog.getOwner()) : null;
        this.walkers = dog.getWalkers() != null ? createWalkerSmallDTOList(dog.getWalkers()) : new ArrayList<>();
    }

    public DogDTO(int id, String name, String breed, String image, String gender, String birthdate, OwnerSmallDTO owner, List<WalkerSmallDTO> walkers) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender.equals("male") ? Dog.Gender.M : Dog.Gender.F;
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.owner = owner;
        this.walkers = walkers;
    }

    public DogDTO(String name, String breed, String image, String gender, String birthdate, OwnerSmallDTO owner, List<WalkerSmallDTO> walkers) {
        this.id = -1;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender.equals("male") ? Dog.Gender.M : Dog.Gender.F;
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
        this.owner = owner;
        this.walkers = walkers;
    }

    public DogDTO(int id, String name, String breed, String image, String gender, String birthdate) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender.equals("male") ? Dog.Gender.M : Dog.Gender.F;
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
        this.owner = null;
        this.walkers = new ArrayList<WalkerSmallDTO>();
    }

    public DogDTO(String name, String breed, String image, String gender, String birthdate) {
        this.id = -1;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender.equals("male") ? Dog.Gender.M : Dog.Gender.F;
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
        this.owner = null;
        this.walkers = new ArrayList<WalkerSmallDTO>();
    }

    private List<WalkerSmallDTO> createWalkerSmallDTOList(List<Walker> walkers) {
        List<WalkerSmallDTO> walkerSmallDTOs = new ArrayList<>();
        for (Walker w : walkers) {
            walkerSmallDTOs.add(new WalkerSmallDTO(w));
        }
        return walkerSmallDTOs;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Dog.Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender.equals("male") ? Dog.Gender.M : Dog.Gender.F;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
    }

    public OwnerSmallDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerSmallDTO owner) {
        this.owner = owner;
    }

    public List<WalkerSmallDTO> getWalkers() {
        return walkers;
    }

    public void setWalkers(List<WalkerSmallDTO> walkers) {
        this.walkers = walkers;
    }
    
      public void addWalkerSmallDTO(WalkerSmallDTO walkerSmallDTO) {
        walkers.add(walkerSmallDTO);
    }
    
    

    @Override
    public String toString() {
        return "DogDTO{" + "id=" + id + ", name=" + name + ", breed=" + breed + ", image=" + image + ", gender=" + gender + ", birthdate=" + birthdate + ", walkers=" + walkers + '}';
    }

}
