/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import dto.DogDTO;
import entities.Dog;
import facades.DogFacade;
import facades.PersonFacade;
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
public class DogTester {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        DogFacade FACADE = DogFacade.getFacade(emf);
        
        DogDTO ddto = new DogDTO("Name 2", "Breed 2", "http", Dog.Gender.F, "28-04-1980", null);
        ddto = FACADE.addDog(ddto);
        System.out.println(ddto);
    }
    
}
