/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import entities.Person;
import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Tweny
 */
@Path("persons")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @Path("/msg")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        try {
            List<PersonDTO> list = FACADE.getAllPersons();
            return GSON.toJson(list);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }
    
    @Path("/security")
    @RolesAllowed("user")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersonsSecurity() {
        try {
            List<PersonDTO> list = FACADE.getAllPersons();
            return GSON.toJson(list);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") int id) {
        try {
            PersonDTO personDTO = FACADE.getPerson(id);
            return GSON.toJson(personDTO);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPerson(String person) {
        try {
            //System.out.println(person);
            PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class); //manual conversion
            personDTO = FACADE.addPerson(personDTO);
            return GSON.toJson(personDTO);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editPerson(@PathParam("id") int id, String person) {
        try {
            //System.out.println(person);
            PersonDTO personDTOEditInfo = GSON.fromJson(person, PersonDTO.class); //manual conversion
            personDTOEditInfo.setId(id);
            personDTOEditInfo = FACADE.editPerson(personDTOEditInfo);
            return GSON.toJson(personDTOEditInfo);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCar(@PathParam("id") int id) {
        try {
            PersonDTO personDTO = FACADE.deletePerson(id);
            System.out.println(personDTO);
            return "{\"status\": \"removed\"}";
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }

    }

}
