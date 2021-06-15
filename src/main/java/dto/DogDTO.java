/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Dog;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String birthdate;
    private WalkerSmallDTO walker;

    public DogDTO() {
    }

    public DogDTO(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
        this.breed = dog.getBreed();
        this.image = dog.getImage();
        this.gender = dog.getGender();
        this.birthdate = dog.getBirthdate().format(DateTimeFormatter.ISO_DATE);
        this.walker = dog.getWalker() != null ? new WalkerSmallDTO(dog.getWalker()) : null;
    }
    
    public DogDTO(int id, String name, String breed, String image, Dog.Gender gender, String birthdate, WalkerSmallDTO walker) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender;
        this.birthdate = birthdate;
        this.walker = walker;
    }
    
    public DogDTO(String name, String breed, String image, Dog.Gender gender, String birthdate, WalkerSmallDTO walker) {
        this.id = -1;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender;
        this.birthdate = birthdate;
        this.walker = walker;
    }
    
    public DogDTO(int id, String name, String breed, String image, Dog.Gender gender, String birthdate) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender;
        this.birthdate = birthdate;
    }
    
    public DogDTO(String name, String breed, String image, Dog.Gender gender, String birthdate) {
        this.id = -1;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender;
        this.birthdate = birthdate;
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

    public void setGender(Dog.Gender gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public WalkerSmallDTO getWalker() {
        return walker;
    }

    public void setWalker(WalkerSmallDTO walker) {
        this.walker = walker;
    }

    @Override
    public String toString() {
        return "DogDTO{" + "id=" + id + ", name=" + name + ", breed=" + breed + ", image=" + image + ", gender=" + gender + ", birthdate=" + birthdate + ", walker=" + walker + '}';
    }
      
}
