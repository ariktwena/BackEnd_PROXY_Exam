/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import entities.Hobby;
import entities.Job;
import entities.NickName;
import entities.Person;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Tweny
 */
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Person p1 = new Person("Person 1");
    private Person p2 = new Person("Person 2");
    private Job j1 = new Job("Job 1");
    private NickName n1 = new NickName("Nickname 1");
    private Hobby h1 = new Hobby("Hobby 1");
    private Hobby h2 = new Hobby("Hobby 2");

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/persons/msg").then().statusCode(200);
    }

    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/persons/msg").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void printJSON() {
        given().log().all().when().get("/persons").then().log().body();
    }

    @Test
    public void testAll() {
        given()
                .contentType("application/json")
                .get("/persons").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems("Person 1", "Person 2"));
    }

    @Test
    public void testPersonById() {
        given().log().all().when().get("/persons/{id}", p1.getId()).then().log().body();

        given()
                .contentType("application/json")
                .get("/persons/{id}", p1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(p1.getName()));
    }

    @Test
    public void testPersonsSize() {
        given()
                .contentType("application/json")
                .get("/persons").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(2))
                .body("", hasSize(2))
                .body("$", hasSize(2));
        ;
    }

    //For at denne test kan virke, så skal man lave en equals i i DTO klassen
    @Test
    void testAreWeGettingThePersons() {
        List<PersonDTO> personDTOs;
        personDTOs = given()
                .contentType("application/json")
                .when()
                .get("/persons")
                .then()
                .extract().body().jsonPath().getList(".", PersonDTO.class);

        PersonDTO m1DTO = new PersonDTO(p1);
        PersonDTO m2DTO = new PersonDTO(p2);

        assertThat(personDTOs, containsInAnyOrder(m1DTO, m2DTO));
    }

    //******* POST *******
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void testPOSTrequest1() {
        Person p3 = new Person("Person 3");
        Job j2 = new Job("Job 2");
        NickName n2 = new NickName("Nickname 2");
        Hobby h3 = new Hobby("Hobby 3");
        p3.setJob(j2);
        p3.setNickName(n2);
        p3.addHobby(h3);
        PersonDTO personDTO = new PersonDTO(p3);
        String requestBody = GSON.toJson(personDTO);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/persons")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Person 3", response.jsonPath().getString("name"));
    }

    @Test
    public void testPOSTrequest2() {
        Person p3 = new Person("Person 3");
        Job j2 = new Job("Job 2");
        NickName n2 = new NickName("Nickname 2");
        Hobby h3 = new Hobby("Hobby 3");
        p3.setJob(j2);
        p3.setNickName(n2);
        p3.addHobby(h3);
        PersonDTO personDTO = new PersonDTO(p3);
        String requestBody = GSON.toJson(personDTO);

        given()
                .contentType("application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/persons")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .and()
                .body("name", equalTo("Person 3"));
    }

    //******* PUT *******
    @Test
    public void testPUTrequest1() {
        p1.setName("Person newName");
        PersonDTO personDTO = new PersonDTO(p1);
        String requestBody = GSON.toJson(personDTO);

        given()
                .contentType("application/json")
                .and()
                .body(requestBody)
                .when()
                .put("/persons/{id}", p1.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .and()
                .body("name", equalTo(p1.getName()),
                        "id", equalTo(p1.getId()));

    }

    //******* DELETE *******
    @Test
    public void testDELETErequest1() {
        given()
                .contentType("application/json")
                .when()
                .delete("/persons/{id}", p2.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .and()
                .body("status", equalTo("removed"));
    }

    //***** ERROR ****
    @Test
    public void testPersonByIdError() {
        int id = 435345344;
        given()
                .contentType("application/json")
                .get("/persons/{id}", id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("message", equalTo("No Person with provided id: " + id));
    }

    @Test
    public void testPOSTrequestError() {
        String requestBody = "{\n"
                + "  \"name\": \"Error name\"}";

        given()
                .contentType("application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/persons")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .and()
                .body("message", equalTo("Job title is missing."));
    }

    @Test
    public void testPUTrequestError() {
        p2.setName("The Error");
        PersonDTO personDTO = new PersonDTO(p2);
        String requestBody = GSON.toJson(personDTO);

        given()
                .contentType("application/json")
                .and()
                .body(requestBody)
                .when()
                .put("/persons/{id}", p2.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .and()
                .body("message", equalTo("Job title is missing."));
    }

    @Test
    public void testDELETErequestError() {
        int id = 435345344;
        
        given()
                .contentType("application/json")
                .when()
                .delete("/persons/{id}", id)
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .and()
                .body("message", equalTo("Could not delete, provided id: " + id + " does not exist"));
    }

}
