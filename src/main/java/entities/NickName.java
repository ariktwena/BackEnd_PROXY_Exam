/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Tweny
 */
@Entity
@Table(name = "nicknames")
@NamedQueries({
    @NamedQuery(name = "NickName.deleteAllRows", query = "DELETE from NickName"),
    @NamedQuery(name = "NickName.getAllRows", query = "SELECT n from NickName n"),
    @NamedQuery(name = "NickName.getNickName", query = "SELECT n from NickName n WHERE n = :x")
})
public class NickName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nickName", length = 175, nullable = false, unique = false)
    private String name;
    
        //***************One to One hvor vi peger tilbage****************
    @OneToOne(mappedBy = "nickName")
    private Person person;

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
    //***************************************************************

    public NickName() {
    }

    public NickName(String name) {
        this.name = name;
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
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final NickName other = (NickName) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        String personName = person == null ? "Person not jet set" : person.getName();
        return "NickName{" + "id=" + id + ", name=" + name + ", person=" + personName + '}';
    }
    
}
