package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import entities.User;
import entities.Role;
import entities.Walker;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
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
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

//Disabled
public class LoginEndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Dog d1 = new Dog("Dog 1", "Breed 1", "image 1", Dog.Gender.M, "01-01-2001");
    private Dog d2 = new Dog("Dog 2", "Breed 2", "image 2", Dog.Gender.F, "02-02-2002");
    private Walker w1 = new Walker("Walker 1", "Address 1", "11111111");
    private Walker w2 = new Walker("Walker 2", "Address 2", "22222222");
    private Owner o1 = new Owner("Owner 1", "Address 1", "11111111");
    private Owner o2 = new Owner("Owner 2", "Address 2", "22222222");
    private Owner o3 = new Owner("Owner 9", "Address 9", "99999999");

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
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

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

            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    public void testRestNoAuthenticationRequired() {
        given()
                .contentType("application/json")
                .when()
                .get("/info/").then()
                .statusCode(200)
                .body("msg", equalTo("Hello anonymous"));
    }

    @Test
    public void testRestForAdmin() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: admin"));
    }

    @Test
    public void testRestForUser() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user"));
    }

    @Test
    public void testAutorizedUserCannotAccesAdminPage() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then() //Call Admin endpoint as user
                .statusCode(401);
    }

    @Test
    public void testAutorizedAdminCannotAccesUserPage() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then() //Call User endpoint as Admin
                .statusCode(401);
    }

    @Test
    public void testRestForMultiRole1() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: user_admin"));
    }

    @Test
    public void testRestForMultiRole2() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user_admin"));
    }

    @Test
    public void userNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    @Test
    public void adminNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    /**
     * Custome security test *
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void testGetAllWalkers_US1() {

        login("user", "test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .get("/dogs/walkers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems("Walker 1", "Walker 2"));
    }

    @Test
    public void testGetAllDogByOwnerId_US2() {

        login("user", "test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .get("/dogs/{id}/owners", o1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems(d1.getName()))
                .body("name", hasItems("Dog 1"));

    }

    @Test
    public void testGetAllWalkersByDogId_US3() {

        login("user", "test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .get("/dogs/{id}/walkers", d1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems(d1.getWalkers().get(0).getName()))
                .body("name", hasItems("Walker 1"));

    }

    @Test
    public void testAddDog__US4() {
        Dog d3 = new Dog("Name 10", "Breed 10", "http", Dog.Gender.F, "28-04-1980");
        Walker w3 = new Walker("Walker 3", "Address 3", "33333333");
        Owner o3 = new Owner("Owner 3", "Address 3", "33333333");
        d3.addWalkers(w3);
        d3.setOwner(o3);
        DogDTO dogDTO = new DogDTO(d3);
        System.out.println(dogDTO);
        String requestBody = GSON.toJson(dogDTO);

        login("admin", "test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .post("/dogs")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Name 10"));
    }

    @Test
    public void testConnectOwnerToDog_US5() {

        assertEquals("Owner 2", d2.getOwner().getName());

        login("admin", "test");

        String requestBody = GSON.toJson("");

        DogDTO dogDTO = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .put("/dogs/{id1}/owners/{id2}", d2.getId(), o3.getId())
                .then()
                .extract().body().jsonPath().getObject(".", DogDTO.class);

        assertEquals("Owner 9", dogDTO.getOwner().getName());

    }

    @Test
    public void testEditDog_US6() {

        login("admin", "test");

        d1.setBreed("New Breed");
        DogDTO dogDTO = new DogDTO(d1);
        String requestBody = GSON.toJson(dogDTO);

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .put("/dogs/{id}", d1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("breed", equalTo("New Breed"));
    }

    @Test
    public void testDeleteDog_US7() {

        login("admin", "test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .delete("/dogs/{id}", d1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("status", equalTo("removed"));

    }

    @Test
    public void testExceptions() {
        Dog d3 = new Dog("Name 10", "Breed 10", "http", Dog.Gender.F, "28-04-1980");
        DogDTO dogDTO = new DogDTO(d3);
        String requestBody = GSON.toJson(dogDTO);

        login("admin", "test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .post("/dogs")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("message", equalTo("Walkers are empty"));
    }

    @Test
    public void testIntegration() {
        Dog d = new Dog("Name 10", "Breed 10", "http", Dog.Gender.F, "28-04-1980");
        Walker w = new Walker("Walker 3", "Address 3", "33333333");
        Owner o = new Owner("Owner 3", "Address 3", "33333333");
        d.addWalkers(w);
        d.setOwner(o);
        DogDTO dogDTO = new DogDTO(d);
        System.out.println("----");
        System.out.println(dogDTO.toString());
        System.out.println("----");
        String requestBody = GSON.toJson(dogDTO);

        login("admin", "test");

        dogDTO = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .post("/dogs")
                .then()
                .extract().body().jsonPath().getObject(".", DogDTO.class);
        
        assertEquals("Name 10", dogDTO.getName());
        
        String requestBody1 = GSON.toJson("");
        
        //Connect dog with new owner
        dogDTO = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody1)
                .put("/dogs/{id1}/owners/{id2}", dogDTO.getId(), o3.getId())
                .then()
                .extract().body().jsonPath().getObject(".", DogDTO.class);

        assertEquals("Owner 9", dogDTO.getOwner().getName());

        //Edit dog
        dogDTO.setBreed("New Breed");
        String requestBody2 = GSON.toJson(dogDTO);
        
        dogDTO = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody2)
                .put("/dogs/{id}", dogDTO.getId())
                .then()
                .extract().body().jsonPath().getObject(".", DogDTO.class);

        assertEquals("New Breed", dogDTO.getBreed());

        //Delete dog
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .and()
                .delete("/dogs/{id}", dogDTO.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("status", equalTo("removed"));
    }

}
