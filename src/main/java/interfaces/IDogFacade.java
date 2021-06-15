/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dto.DogDTO;
import dto.WalkerDTO;
import dto.WalkerSmallDTO;
import java.util.List;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Tweny
 */
public interface IDogFacade {
    
    public List<WalkerDTO> getAllWalkers() throws WebApplicationException;
    
    public List<WalkerSmallDTO> getAllWalkersByDogId(int dogId) throws WebApplicationException;
    
    public DogDTO addDog(DogDTO dogDTO) throws WebApplicationException;
    
    public WalkerDTO addDogToWalker(DogDTO dogDTO)throws WebApplicationException;
    
    public DogDTO editDog(DogDTO dogDTO) throws WebApplicationException;
    
    public DogDTO deleteDog(int dogId) throws WebApplicationException;
        
}
