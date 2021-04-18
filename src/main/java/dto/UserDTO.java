/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.User;

/**
 *
 * @author Tweny
 */
public class UserDTO {
    private String userName;
    private String userPass;

    public UserDTO() {
    }
    
    public UserDTO(User user) {
        this.userName = user.getUserName();
    }
    
    public UserDTO(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
    
    
    
    
            
}
