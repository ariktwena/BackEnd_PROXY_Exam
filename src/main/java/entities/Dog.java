/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.DogDTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Tweny
 */
@Entity
@Table(name = "dog")
@NamedQueries({
    @NamedQuery(name = "Dog.deleteAllRows", query = "DELETE from Dog"),
    @NamedQuery(name = "Dog.getAllRows", query = "SELECT d from Dog d"),
    @NamedQuery(name = "Dog.getDog", query = "SELECT d from Dog d WHERE d.name = :d")
})
public class Dog implements Serializable {

    public enum Gender {
        M,
        F
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "breed", length = 255, nullable = false, unique = false)
    private String breed;

    @Column(name = "image", length = 255, nullable = false, unique = false)
    private String image;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;
    
     //***************Many to One hvor vi peger tilbage****************
    @ManyToOne //(fetch = FetchType.EAGER)
    private Walker walker;

    public void setWalker(Walker walker) {
        this.walker = walker;
    }

    public Walker getWalker() {
        return walker;
    }
    //***************************************************************

    
    public Dog() {
    }

    public Dog(String name, String breed, String image, Gender gender, String birthdate) {
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender;
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.walker = null;
    }
    
    public Dog(DogDTO dogDTO) {
        this.id = -1;
        this.name = dogDTO.getName();
        this.breed = dogDTO.getBreed();
        this.image = dogDTO.getImage();
        this.gender = dogDTO.getGender();
        this.birthdate = LocalDate.parse(dogDTO.getBirthdate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.walker = null;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

  


}
