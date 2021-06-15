/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Dog;
import entities.Walker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tweny
 */
public class DogSmallDTO {
    
    private int id;
    private String name;
    private String breed;
    private String image;
    private Dog.Gender gender;
    private LocalDate birthdate;
    
    public DogSmallDTO() {
    }

    public DogSmallDTO(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
        this.breed = dog.getBreed();
        this.image = dog.getImage();
        this.gender = dog.getGender();
        this.birthdate = dog.getBirthdate();
    }

    public DogSmallDTO(int id, String name, String breed, String image, String gender, String birthdate) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender.equals("male") ? Dog.Gender.M : Dog.Gender.F;
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public DogSmallDTO(String name, String breed, String image, String gender, String birthdate) {
        this.id = -1;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender.equals("male") ? Dog.Gender.M : Dog.Gender.F;
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    @Override
    public String toString() {
        return "DogSmallDTO{" + "id=" + id + ", name=" + name + ", breed=" + breed + ", image=" + image + ", gender=" + gender + ", birthdate=" + birthdate + '}';
    }


    
}
