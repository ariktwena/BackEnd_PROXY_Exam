/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.DogDTO;
import dto.OwnerSmallDTO;
import dto.PersonDTO;
import dto.WalkerSmallDTO;
import entities.Dog;
import entities.Hobby;
import entities.Job;
import entities.NickName;
import entities.Owner;
import entities.Person;
import entities.Walker;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Tweny
 */
public class DogTest {
    
    private static EntityManagerFactory emf;
    private static DogFacade facade;
    private Dog d1 = new Dog("Dog 1", "Breed 1", "image 1", Dog.Gender.M, "01-01-2001");
    private Dog d2 = new Dog("Dog 2", "Breed 2", "image 2", Dog.Gender.F, "02-02-2002");
    private Walker w1 = new Walker("Walker 1", "Address 1", "11111111");
    private Walker w2 = new Walker("Walker 2", "Address 2", "22222222");
    private Owner o1 = new Owner("Owner 1", "Address 1", "11111111");
    private Owner o2 = new Owner("Owner 2", "Address 2", "22222222");
    private Owner o3 = new Owner("Owner 9", "Address 9", "99999999");
    
    public DogTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DogFacade.getFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Dog.deleteAllRows").executeUpdate();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Walker.deleteAllRows").executeUpdate();
            
            em.persist(d1);
            em.persist(d2);
            
            em.persist(o1);
            em.persist(o2);
            
            o1.addDog(d1);
            em.merge(o1);
            
            o2.addDog(d2);
            em.merge(o2);
            
            em.persist(w1);
            em.persist(w2);
            
            d1.addWalkers(w1);
            em.merge(d1);
            
            d2.addWalkers(w2);
            em.merge(d2);
            
            em.persist(o3);
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetAllWalkers_US1() {
        assertEquals(2, facade.getAllWalkers().size(), "Expects two rows in the database");
    }
    
    @Test
    public void testGetAllDogByOwnerId_US2() {
        assertEquals(1, facade.getAllDogsByOwnerId(o1.getId()).size(), "Expects one rows in the database");
    }
    
    @Test
    public void testGetAllWalkersByDogId_US3() {
        assertEquals(1, facade.getAllWalkersByDogId(d1.getId()).size(), "Expects one rows in the database");
    }
    
    @Test
    public void testAddDog_US4() {
        Dog d3 = new Dog("Name 10", "Breed 10", "http", Dog.Gender.F, "28-04-1980");
        Walker w3 = new Walker("Walker 3", "Address 3", "33333333");
        Owner o3 = new Owner("Owner 3", "Address 3", "33333333");
        d3.addWalkers(w3);
        d3.setOwner(o3); 
        DogDTO dogDTO = new DogDTO(d3);     
        dogDTO = facade.addDog(dogDTO);
        assertEquals("Name 10", dogDTO.getName());
    }  

    @Test
    public void testConnectDogToOwner_US5() {
        DogDTO dogDTO = facade.connectOwnerWithDog(d1.getId(), o3.getId());
        assertEquals("Owner 9", dogDTO.getOwner().getName());
        
    }
    
     @Test
    public void testUpdateInformation_US6() {
        Dog d3 = new Dog("Name 10", "Breed 10", "http", Dog.Gender.F, "28-04-1980");
        Walker w3 = new Walker("Walker 3", "Address 3", "33333333");
        Owner o3 = new Owner("Owner 3", "Address 3", "33333333");
        d3.addWalkers(w3);
        d3.setOwner(o3); 
        DogDTO dogDTO = new DogDTO(d3);     
        dogDTO = facade.addDog(dogDTO);
        assertEquals("Name 10", dogDTO.getName());
        
        Owner o4 = new Owner("Owner 4", "Address 4", "44444444");
        dogDTO.getOwner().setName(o4.getName());
        dogDTO.getOwner().setAddress(o4.getAddress());
        dogDTO.getOwner().setPhone(o4.getPhone());
        dogDTO = facade.editDog(dogDTO, dogDTO.getId());
        assertEquals("Owner 4", dogDTO.getOwner().getName());
        
    }
    
    @Test
    public void testDeletePerson_US7() {
        DogDTO dogDTO = facade.deleteDog(d1.getId());
        assertEquals(0, facade.getAllWalkersByDogId(d1.getId()).size());
    }

    @Test
    public void testException() {
        Dog d4 = new Dog("Name 10", "Breed 10", "http", Dog.Gender.F, "28-04-1980");
        DogDTO dogDTO = new DogDTO(d4);
        try {
            dogDTO = facade.addDog(dogDTO);
            fail("Expected an WebApplicationException to be thrown");
        } catch (WebApplicationException ex) {
            assertThat(ex.getMessage(), is("Walkers are empty"));
        }
    }
  
}
