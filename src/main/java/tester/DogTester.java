/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import dto.DogDTO;
import dto.DogSmallDTO;
import dto.OwnerDTO;
import dto.OwnerSmallDTO;
import dto.WalkerDTO;
import dto.WalkerSmallDTO;
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

        DogDTO ddto = new DogDTO("Name 10", "Breed 10", "http", "M", "28-04-1980"); 
        WalkerSmallDTO walkerSmallDTO = new WalkerSmallDTO("Walker 10", "Address 10", "Phone 10");
        OwnerSmallDTO ownerSmallDTO = new OwnerSmallDTO("Owner new 10", "Address 10", "Phone 20");
        ddto.addWalkerSmallDTO(walkerSmallDTO);
        ddto.setOwner(ownerSmallDTO);
        System.out.println(ddto);
        
        DogDTO dogDTO = FACADE.editDog(ddto, 3);
        
       //ddto = FACADE.addDog(ddto);
        System.out.println(ddto);
//        ddto.setWalker(walkerSmallDTO);
//        WalkerDTO walkerDTO = FACADE.addDogToWalker(ddto);
//        System.out.println(walkerDTO);
        
        List<WalkerSmallDTO> walkers = FACADE.getAllWalkers();
        for(WalkerSmallDTO w : walkers){
            System.out.println(w);
        }
        
        List<WalkerSmallDTO> walkers1 = FACADE.getAllWalkersByDogId(1);
        for(WalkerSmallDTO w : walkers1){
            System.out.println(w);
        }
//        DogDTO ddto1 = FACADE.deleteDog(5);
//        System.out.println(ddto1);
        
        
        List<DogSmallDTO> dogSmallDTOs = FACADE.getAllDogsByOwnerId(2);
        for(DogSmallDTO d : dogSmallDTOs){
            System.out.println(d);
        }
        
//        OwnerSmallDTO ownerSmallDTO1 = new OwnerSmallDTO("Owner 6", "Address 6", "Phone 6");
//        OwnerDTO owdto = FACADE.addOwner(ownerSmallDTO1);
//        System.out.println(owdto);


//WalkerSmallDTO walkerSmallDTO1 = new WalkerSmallDTO("Walker 110", "Address 110", "Phone110");
//DogDTO thDTO = FACADE.removeWalkerToDog(walkerSmallDTO1, 4);
//        System.out.println(thDTO);

    }

}
