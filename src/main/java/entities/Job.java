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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 */@Entity
@Table(name = "jobs")
@NamedQueries({
    @NamedQuery(name = "Job.deleteAllRows", query = "DELETE from Job"),
    @NamedQuery(name = "Job.getAllRows", query = "SELECT j from Job j"),
    @NamedQuery(name = "Job.getJob", query = "SELECT j from Job j WHERE j = :x")
})
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title", length = 175, nullable = false, unique = true)
    private String title;

    //***************One to Many****************
    //mappedBy fortæller vilken fremmednøgle/variablenavn den skal mappes til i Fee objektet (private Person person)
    //Det er Person der ejer relationen, da det er ONE der ejer en ONE-TO-MANY relation
    //Husk at lave en this.fees = new ArrayList<Fee>(); i konstruktøren
    @OneToMany(mappedBy = "job", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Der findes også CascadeType.REMOVE hvis den må slette uden der sker noget
    private List<Person> persons;

    public void addPerson(Person person) {
        if (person != null) {
            person.setJob(this);
            this.persons.add(person);
        }
    }
    
//    public void removePerson(Person person) {
//        if (person != null) {
//            this.persons.remove(person);
//        }
//    }

    public List<Person> getPersons() {
        return persons;
    }
    //*****************************************

    public Job() {
    }

    public Job(String title) {
        this.title = title;
        this.persons = new ArrayList<Person>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.title);
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
        final Job other = (Job) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Job{" + "id=" + id + ", title=" + title + '}';
    }
   
}
