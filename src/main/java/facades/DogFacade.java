/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.DogDTO;
import dto.WalkerDTO;
import dto.WalkerSmallDTO;
import entities.Dog;
import interfaces.IDogFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Tweny
 */
public class DogFacade implements IDogFacade {

    private static DogFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private DogFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static DogFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<WalkerDTO> getAllWalkers() throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WalkerSmallDTO> getAllWalkersByDogId(int dogId) throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DogDTO addDog(DogDTO dogDTO) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        if (dogDTO.getName() == null || dogDTO.getName().equals("")) {
            throw new WebApplicationException("Name is missing", 401);
        } else if (dogDTO.getBreed() == null || dogDTO.getBreed().equals("")) {
            throw new WebApplicationException("Breed is missing", 401);
        } else if (dogDTO.getImage() == null || dogDTO.getImage().equals("")) {
            throw new WebApplicationException("Image is missing", 401);
        } 
        else if (dogDTO.getGender() == null || dogDTO.getGender().equals("")) {
            throw new WebApplicationException("Gender is missing", 401);
        } 
        else if (dogDTO.getBirthdate() == null || dogDTO.getBirthdate().equals("")) {
            throw new WebApplicationException("Birthdate is missing", 401);
        }

        Dog dog = doesDogExist(dogDTO);
        if (dog.getId() > -1) {
            dog = em.find(Dog.class, dog.getId());
            
        } else {
            try {
                em.getTransaction().begin();

                em.persist(dog
                );

                em.getTransaction().commit();

            } catch (RuntimeException ex) {
                throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
            } finally {
                em.close();
            }
        }
        
        return new DogDTO(dog);

    }

    @Override
    public WalkerDTO addDogToWalker(DogDTO dogDTO) throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DogDTO editDog(DogDTO dogDTO) throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DogDTO deleteDog(int dogId) throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Dog doesDogExist(DogDTO dogDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT d FROM Dog d WHERE d.name = :name ", Dog.class);
            query.setParameter("name", dogDTO.getName());
            Dog dog = (Dog) query.getSingleResult();
            return dog;
        } catch (NoResultException ex) {
            return new Dog(dogDTO);
        } finally {
            em.close();
        }
    }

}
