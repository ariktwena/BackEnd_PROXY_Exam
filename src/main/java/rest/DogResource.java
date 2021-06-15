/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DogDTO;
import dto.DogSmallDTO;
import dto.OwnerDTO;
import dto.OwnerSmallDTO;
import dto.PersonDTO;
import dto.WalkerSmallDTO;
import facades.DogFacade;
import facades.PersonFacade;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Tweny
 */
@Path("dogs")
public class DogResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final DogFacade FACADE = DogFacade.getFacade(EMF);
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

    @Path("/walkers")
    @RolesAllowed("user")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllWalkers() {
        try {
            List<WalkerSmallDTO> list = FACADE.getAllWalkers();
            return GSON.toJson(list);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @Path("/{id}/walkers")
    @RolesAllowed("user")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllWalkersByDogId(@PathParam("id") int dogId) {
        try {
            List<WalkerSmallDTO> list = FACADE.getAllWalkersByDogId(dogId);
            return GSON.toJson(list);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @Path("/{id}/owners")
    @RolesAllowed("user")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllDogsByOwnerId(@PathParam("id") int ownerId) {
        try {
            List<DogSmallDTO> list = FACADE.getAllDogsByOwnerId(ownerId);
            return GSON.toJson(list);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }
    
    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addDog(String dog) {
        try {
            DogDTO dogDTO = GSON.fromJson(dog, DogDTO.class); //manual conversion
            dogDTO = FACADE.addDog(dogDTO);
            return GSON.toJson(dogDTO);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }
    
    @Path("/owners")
    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addOwner(String owner) {
        try {
            OwnerSmallDTO ownerSmallDTO = GSON.fromJson(owner, OwnerSmallDTO.class); //manual conversion
            OwnerDTO ownerDTO = FACADE.addOwner(ownerSmallDTO);
            return GSON.toJson(ownerDTO);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }
    
    @Path("/{id}")
    @RolesAllowed("admin")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editDog(@PathParam("id") int dogId, String dog) {
        try {
            DogDTO dogDTO = GSON.fromJson(dog, DogDTO.class); //manual conversion
            dogDTO = FACADE.editDog(dogDTO, dogId);
            return GSON.toJson(dogDTO);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @Path("/{id}")
    @RolesAllowed("admin")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDog(@PathParam("id") int dogId) {
        try {
            DogDTO dogDTO = FACADE.deleteDog(dogId);
//            System.out.println(dogDTO);
            return "{\"status\": \"removed\"}";
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }

    }
    
    @Path("/{id}/walkers")
    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addWalker(@PathParam("id") int dogId, String dog) {
        try {
            WalkerSmallDTO walkerSmallDTO = GSON.fromJson(dog, WalkerSmallDTO.class); //manual conversion
            DogDTO dogDTO = FACADE.addWalkerToDog(walkerSmallDTO, dogId);
            return GSON.toJson(dogDTO);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

    @Path("/{id}/walkers")
    @RolesAllowed("admin")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteWalker(@PathParam("id") int dogId, String dog) {
         try {
            WalkerSmallDTO walkerSmallDTO = GSON.fromJson(dog, WalkerSmallDTO.class); //manual conversion
            DogDTO dogDTO = FACADE.removeWalkerToDog(walkerSmallDTO, dogId);
            return GSON.toJson(dogDTO);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }

}
