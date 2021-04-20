/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Hobby;
import entities.Job;
import entities.NickName;
import entities.Person;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Tweny
 */
public class TesterIntegration {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getFacade(emf);
        
        //Add
        Person person = new Person("Sam");
        Job job = new Job("Developer");
        NickName nickName = new NickName("The Coder");
        Hobby hobby1 = new Hobby("PHP");
        Hobby hobby2 = new Hobby("JavaScript");
        person.setJob(job);
        person.setNickName(nickName);
        System.out.println(hobby1);
        person.addHobby(hobby1);
        person.addHobby(hobby2);
        PersonDTO personDTO = new PersonDTO(person);
        System.out.println(person);
        personDTO = pf.addPerson(personDTO);
        System.out.println(personDTO);
        System.out.println("---");
        
        //Edit
        personDTO.setName("Sam-New");
        personDTO.getJob().setTitle("Developer-New");
        personDTO.getNickName().setNickName("The Coder New");
        HobbyDTO hobbyDTO = new HobbyDTO("CSS");
        personDTO.getHobbies().add(hobbyDTO);
        System.out.println(personDTO);
        personDTO = pf.editPerson(personDTO);
        System.out.println(personDTO);
        System.out.println("---");
        
        //Get
        PersonDTO personToFind = pf.getPerson(personDTO.getId());
        System.out.println(personToFind);
        
        //Get all
        System.out.println("---");
        List<PersonDTO> list = pf.getAllPersons();
        for(PersonDTO p : list){
            System.out.println(p);
        }
        
        //Delete
        System.out.println("---");
//        PersonDTO personToDelete = pf.deletePerson(personDTO.getId());
//        System.out.println(personToDelete);
        
        
        
        
        
    }
    
}
