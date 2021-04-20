/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import entities.Hobby;
import entities.Job;
import entities.NickName;
import entities.Person;
import entities.RenameMe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Rule;
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
public class PersonTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private Person p1 = new Person("Person 1");
    private Person p2 = new Person("Person 2");
    private Job j1 = new Job("Job 1");
    private NickName n1 = new NickName("Nickname 1");
    private Hobby h1 = new Hobby("Hobby 1");
    private Hobby h2 = new Hobby("Hobby 2");

    public PersonTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacade(emf);
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
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Job.deleteAllRows").executeUpdate();
            em.createNamedQuery("NickName.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();

            j1.addPerson(p1);
            p1.setNickName(n1);
            p1.addHobby(h1);
            p1.addHobby(h2);
            em.persist(j1);
            em.persist(p2);

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
    public void testNumberOfPersons() {
        assertEquals(2, facade.getAllPersons().size(), "Expects two rows in the database");
    }

    @Test
    public void testFindPerson() {
        PersonDTO personDTO = facade.getPerson(p1.getId());
        assertEquals("Person 1", personDTO.getName());
    }

    @Test
    public void testEditPersonExceptionJob() {
        p2.setName("New Name");
        PersonDTO personDTO = new PersonDTO(p2);
        try {
            personDTO = facade.editPerson(personDTO);
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (WebApplicationException ex) {
            assertThat(ex.getMessage(), is("Job title is missing."));
        }
    }

    @Test
    public void testEditPersonExceptionNickName() {
        p2.setName("New Name");
        p2.setJob(new Job("Test Job"));
        PersonDTO personDTO = new PersonDTO(p2);
        try {
            personDTO = facade.editPerson(personDTO);
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (WebApplicationException ex) {
            assertThat(ex.getMessage(), is("Nickname is missing."));
        }
    }

    @Test
    public void testEditPersonExceptionHobby() {
        p2.setName("New Name");
        p2.setJob(new Job("Test Job"));
        p2.setNickName(new NickName("Test Nickname"));
        PersonDTO personDTO = new PersonDTO(p2);
        try {
            personDTO = facade.editPerson(personDTO);
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (WebApplicationException ex) {
            assertThat(ex.getMessage(), is("Hobbies are missing."));
        }
    }

    @Test
    public void testEditPerson() {
        p1.setName("New Name");
        PersonDTO personDTO = new PersonDTO(p1);
        personDTO = facade.editPerson(personDTO);
        assertEquals("New Name", personDTO.getName());
    }

    @Test
    public void testDeletePerson() {
        PersonDTO personDTO = facade.deletePerson(p1.getId());
        assertEquals(1, facade.getAllPersons().size());
    }
}
