/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.NickName;

/**
 *
 * @author Tweny
 */
public class NickNameDTO {

    private int id;
    private String nickName;
//    private PersonDTO person;

    public NickNameDTO() {
    }

    public NickNameDTO(NickName nickName) {
        this.id = nickName.getId();
        this.nickName = nickName.getName();
//        this.person = nickName.getPerson() != null ? new PersonDTO(nickName.getPerson()) : null;
    }

    public NickNameDTO(int id, String nickName) {
        this.id = id;
        this.nickName = nickName;
//        this.person = person;
    }

    public NickNameDTO(String nickName) {
        this.id = 0;
        this.nickName = nickName;
//        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

//    public PersonDTO getPerson() {
//        return person;
//    }

//    public void setPerson(PersonDTO person) {
//        this.person = person;
//    }

    @Override
    public String toString() {
        return "NickNameDTO{" + "id=" + id + ", nickName=" + nickName + '}';
    }



}
