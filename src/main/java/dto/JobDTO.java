/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Job;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tweny
 */
public class JobDTO {
    
    private int id;
    private String title;
//    private List<PersonDTO> persons;

    public JobDTO() {
    }

    public JobDTO(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
//        this.persons = job.getPersons() != null ? makeDTOlist(job.getPersons()) : new ArrayList<>();
    }

    public JobDTO(int id, String title, List<PersonDTO> persons) {
        this.id = id;
        this.title = title;
//        this.persons = persons;
    }
    
    public JobDTO(String title, List<PersonDTO> persons) {
        this.id = 0;
        this.title = title;
//        this.persons = persons;
    }
    
//    private ArrayList<PersonDTO> makeDTOlist(List<Person> personList){
//        ArrayList<PersonDTO> PersonDTOlist = new ArrayList<>();
//        for(Person person : personList){
//            PersonDTOlist.add(new PersonDTO(person));
//        }
//        return PersonDTOlist;
//    }

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

//    public List<PersonDTO> getPersons() {
//        return persons;
//    }

//    public void setPersons(List<PersonDTO> persons) {
//        this.persons = persons;
//    }

//    @Override
//    public String toString() {
//        return "JobDTO{" + "id=" + id + ", title=" + title + ", persons=" + persons + '}';
//    }

    @Override
    public String toString() {
        return "JobDTO{" + "id=" + id + ", title=" + title + '}';
    }
    
    
    
    
    
}
