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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Tweny
 */
@Entity
@Table(name = "persons")
@NamedQueries({
    @NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person"),
    @NamedQuery(name = "Person.getAllRows", query = "SELECT p from Person p"),
    @NamedQuery(name = "Person.getPerson", query = "SELECT p from Person p WHERE p = :p")
})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", length = 175, nullable = false, unique = false)
    private String name;

    //***************One to One hvor vi peger tilbage****************
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "nickname_id", referencedColumnName = "id")
    private NickName nickName;

    public void setNickName(NickName nickName) {
        this.nickName = nickName;
        if (nickName != null) {
            nickName.setPerson(this);
        }
    }

    public NickName getNickName() {
        return nickName;
    }
    //***************************************************************

    //***************Many to One hvor vi peger tilbage****************
    @ManyToOne
    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }
    //***************************************************************

    //***************Many to Many****************
    @ManyToMany(mappedBy = "persons", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // , fetch = FetchType.EAGER
    private List<Hobby> hobbies;

    public void addHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.add(hobby);
            hobby.addPerson(this);
        }
    }

    public void removeHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.remove(hobby);
            hobby.getPersons().remove(this);
        }
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }
    
    //*******************************************

    public Person() {
    }

    public Person(String name) {
        this.name = name;
        this.job = null;
        this.nickName = null;
        this.hobbies = new ArrayList<>();
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

    @Override
    public String toString() {
        String jobTitle = job == null ? "Job not jet set" : job.getTitle();
        String theNickName = nickName == null ? "Nickname not jet set" : nickName.getName();
        return "Person{" + "id=" + id + ", name=" + name + ", nickName=" + theNickName + ", job=" + jobTitle + ", hobbies=" + hobbies.toString() + '}';
    }

}
