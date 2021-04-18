/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;

/**
 *
 * @author Tweny
 */
public class FetchPersonsDTO {
    
    private List<FetchPersonDTO> all;

    public FetchPersonsDTO(List<FetchPersonDTO> all) {
        this.all = all;
    }

    public List<FetchPersonDTO> getAll() {
        return all;
    }

    public void setAll(List<FetchPersonDTO> all) {
        this.all = all;
    }

    
    
    
    
}
