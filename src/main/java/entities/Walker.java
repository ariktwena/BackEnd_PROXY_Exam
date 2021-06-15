/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Tweny
 */
@Entity
@Table(name = "walker")
@NamedQueries({
    @NamedQuery(name = "Walker.deleteAllRows", query = "DELETE from Walker"),
    @NamedQuery(name = "Walker.getAllRows", query = "SELECT w from Walker w"),
    @NamedQuery(name = "Walker.getWalker", query = "SELECT w from Walker w WHERE w = :w")
})
public class Walker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name", length = 255, nullable = false, unique = false)
    private String name;
    
    @Column(name = "address", length = 255, nullable = false, unique = false)
    private String address;
    
    @Column(name = "phone", length = 8, nullable = false, unique = true)
    private String phone;
    
            //***************One to Many****************
    //mappedBy fortæller vilken fremmednøgle/variablenavn den skal mappes til i Fee objektet (private Person person)
    //Det er Person der ejer relationen, da det er ONE der ejer en ONE-TO-MANY relation
    //Husk at lave en this.fees = new ArrayList<Fee>(); i konstruktøren
    @OneToMany(mappedBy = "walker", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Der findes også CascadeType.REMOVE hvis den må slette uden der sker noget
    private List<Dog> dogs;

    public void addDog(Dog dog) {
        if (dog != null) {
            dog.setWalker(this);
            this.dogs.add(dog);
        }
    }
    
//    public void removePerson(Person person) {
//        if (person != null) {
//            this.persons.remove(person);
//        }
//    }

    public List<Dog> getDogs() {
        return dogs;
    }
    //*****************************************

    public Walker() {
    }

    public Walker(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = new ArrayList<Dog>();
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

 
    
}
