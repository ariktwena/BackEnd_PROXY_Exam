/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Tweny
 */
@Entity
@Table(name = "hobbies")
@NamedQueries({
    @NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE from Hobby"),
    @NamedQuery(name = "Hobby.getAllRows", query = "SELECT h from Hobby h"),
    @NamedQuery(name = "Hobby.getHobby", query = "SELECT h from Hobby h WHERE h.id = :id")
})
public class Hobby implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 175, nullable = false, unique = false)
    private String name;

    @ManyToMany
    private List<Person> persons;

    public Hobby() {
    }

    public Hobby(String name) {
        this.name = name;
        this.persons = new ArrayList<>();
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

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    //Methods
    public void addPerson(Person person) {
        if (person != null) {
            this.persons.add(person);
//            person.getHobbies().add(this);
        }
    }

    public void removePerson(Person person) {
        if (person != null) {
            this.persons.remove(person);
            person.getHobbies().remove(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hobby other = (Hobby) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Hobby{" + "id=" + id + ", name=" + name + '}';
    }

}
