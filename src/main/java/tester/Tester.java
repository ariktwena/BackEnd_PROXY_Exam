/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import entities.Hobby;
import entities.Job;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * @author Tweny
 */
public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

//        Job job1 = new Job("Developer");
//        Person person1 = new Person("Sam");
//        job1.addPerson(person1);
//        em.persist(job1);
        Person person = em.find(Person.class, 1);
        System.out.println(person);
        
        //Get Hobbie list
        TypedQuery<Hobby> existingHobbies = em.createQuery("SELECT h FROM Hobby h JOIN h.persons p WHERE p.id = :id ", Hobby.class);
        existingHobbies.setParameter("id", person.getId());
        List<Hobby> hobbies = existingHobbies.getResultList();
        System.out.println(hobbies);
        System.out.println(person);
        
        //Get hobbie list for person, But needs ", fetch = FetchType.EAGER"
        Query query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE p.id = :id", Person.class);
        query.setParameter("id", person.getId());
        person = (Person) query.getSingleResult();
        System.out.println(person);
        
        //Remove
        person.removeHobby(hobbies.get(0));
        em.merge(person);

        em.getTransaction().commit();
    }

}
