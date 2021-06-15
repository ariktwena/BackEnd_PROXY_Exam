/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dto.DogDTO;
import dto.DogSmallDTO;
import dto.OwnerDTO;
import dto.OwnerSmallDTO;
import dto.WalkerDTO;
import dto.WalkerSmallDTO;
import java.util.List;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Tweny
 */
public interface IDogFacade {
    
    public List<WalkerSmallDTO> getAllWalkers() throws WebApplicationException;
    
    public List<WalkerSmallDTO> getAllWalkersByDogId(int dogId) throws WebApplicationException;
    
    public DogDTO addDog(DogDTO dogDTO) throws WebApplicationException;
    
    public DogDTO addWalkerToDog(WalkerSmallDTO walkerSmallDTO, int dogId) throws WebApplicationException;
    
    public DogDTO removeWalkerToDog(WalkerSmallDTO walkerSmallDTO, int dogId) throws WebApplicationException;
    
    public DogDTO editDog(DogDTO dogDTO, int dogId) throws WebApplicationException;
    
    public DogDTO deleteDog(int dogId) throws WebApplicationException;
    
    public List<DogSmallDTO> getAllDogsByOwnerId(int OwnerId) throws WebApplicationException;
    
    public OwnerDTO addOwner(OwnerSmallDTO ownerSmallDTO) throws WebApplicationException;
        
}
