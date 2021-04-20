/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tweny
 */
public class PersonDTO {

    private int id;
    private String name;
    private JobDTO job;
    private NickNameDTO nickName;
    private ArrayList<HobbyDTO> hobbies;

    public PersonDTO() {
    }

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.job = person.getJob() != null ? new JobDTO(person.getJob()) : null;
        this.nickName = person.getNickName() != null ? new NickNameDTO(person.getNickName()) : null;
        this.hobbies = person.getHobbies() != null ? makeDTOlist(person.getHobbies()) : new ArrayList<>();
    }

    public PersonDTO(int id, String name, JobDTO job, NickNameDTO nickName, ArrayList<HobbyDTO> hobbies) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.nickName = nickName;
        this.hobbies = hobbies;
    }

    public PersonDTO(String name, JobDTO job, NickNameDTO nickName, ArrayList<HobbyDTO> hobbies) {
        this.id = 0;
        this.name = name;
        this.job = job;
        this.nickName = nickName;
        this.hobbies = hobbies;
    }

    public PersonDTO(String name, JobDTO job, ArrayList<HobbyDTO> hobbies) {
        this.id = 0;
        this.name = name;
        this.job = job;
        this.nickName = null;
        this.hobbies = hobbies;
    }

    public PersonDTO(String name, ArrayList<HobbyDTO> hobbies) {
        this.id = 0;
        this.name = name;
        this.job = null;
        this.nickName = null;
        this.hobbies = hobbies;
    }

    public PersonDTO(String name) {
        this.id = 0;
        this.name = name;
        this.job = null;
        this.nickName = null;
        this.hobbies = new ArrayList<>();
    }

    private ArrayList<HobbyDTO> makeDTOlist(List<Hobby> hobbies) {
        ArrayList<HobbyDTO> hobbiesDTOlist = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            hobbiesDTOlist.add(new HobbyDTO(hobby));
        }
        return hobbiesDTOlist;
    }

    public ArrayList<Hobby> makeHobbylist() {
        ArrayList<Hobby> hobbyList = new ArrayList<>();
        for (HobbyDTO hobbyDTO : hobbies) {
            hobbyList.add(new Hobby(hobbyDTO.getName()));
        }
        return hobbyList;
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

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public NickNameDTO getNickName() {
        return nickName;
    }

    public void setNickName(NickNameDTO nickName) {
        this.nickName = nickName;
    }

    public ArrayList<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
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
        final PersonDTO other = (PersonDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        String jobTitle = job != null ? job.getTitle() : "Job not jet set";
        String theNickName = nickName == null ? "Nickname not jet set" : nickName.getNickName();
        return "PersonDTO{" + "id=" + id + ", name=" + name + ", nickname=" + theNickName + ", job=" + jobTitle + ", hobbies=" + hobbies + '}';
    }

}
