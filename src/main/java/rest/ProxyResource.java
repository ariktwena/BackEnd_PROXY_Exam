/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FetchMapDTO;
import dto.FetchPersonDTO;
import dto.FetchPersonDTOtoPost;
import dto.FetchPersonsDTO;
import dto.JokeDTO;
import facades.ProxyFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;

/**
 *
 * @author Tweny
 */
@Path("proxy")
public class ProxyResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private ProxyFacade proxyFacade = new ProxyFacade();
    
    @Path("person")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllFetchePersonDTO() {
        FetchPersonsDTO fetchPersonsDTO = proxyFacade.makeAllPersonFetchGet();
        return GSON.toJson(fetchPersonsDTO);
    }
    
    @Path("jokes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllFetcheJokeDTO() throws TimeoutException, InterruptedException, ExecutionException {
        List<JokeDTO> jokeDTOs = proxyFacade.runParallelWithCallablesJokeToDTO(threadPool);
        return GSON.toJson(jokeDTOs);
    }
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @Path("map")
    @RolesAllowed("user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFetches() throws TimeoutException, InterruptedException, ExecutionException {
        List<JSONArray> fetchMapDTOList = proxyFacade.runParallelWithCallablesMap(threadPool);
        return GSON.toJson(fetchMapDTOList);
    }

    @Path("mapdto")
    @RolesAllowed("user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFetchesDTO() throws TimeoutException, InterruptedException, ExecutionException {
        List<FetchMapDTO> fetchMapDTOList = proxyFacade.runParallelWithCallablesMapToDTO(threadPool);
        return GSON.toJson(fetchMapDTOList);
    }

    @Path("map/{countrycode}")
    @RolesAllowed("user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleFetcheMapDTO(@PathParam("countrycode") String country) {
        FetchMapDTO fetchMapDTO = proxyFacade.makeSingleMapFetchGet(country);
        return GSON.toJson(fetchMapDTO);
    }

    @Path("person/{id}")
    @RolesAllowed("user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleFetchePersonDTO(@PathParam("id") String id) {
        FetchPersonDTO fetchPersonDTO = proxyFacade.makeSinglePersonFetchGet(id);
        return GSON.toJson(fetchPersonDTO);
    }

    @Path("person")
    @RolesAllowed("user")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String proxyAddPersonPost(String personToAdd) {
        FetchPersonDTO fetchPersonDTO = GSON.fromJson(personToAdd, FetchPersonDTO.class); //manual conversion
        fetchPersonDTO = proxyFacade.makeSinglePersonFetchPost(fetchPersonDTO);
        return GSON.toJson(fetchPersonDTO);
    }

    @Path("person/{id}")
    @RolesAllowed("user")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String proxyEditPersonPost(@PathParam("id") String id, String personToEdit) {
        FetchPersonDTO fetchPersonDTO = GSON.fromJson(personToEdit, FetchPersonDTO.class); //manual conversion
        fetchPersonDTO.setId(Integer.parseInt(id));
        fetchPersonDTO = proxyFacade.makeSinglePersonFetchPut(fetchPersonDTO, id);
        return GSON.toJson(fetchPersonDTO);
    }

    @Path("person/{id}")
    @RolesAllowed("user")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String proxyDeletePersonPost(@PathParam("id") String id) {
        String response = proxyFacade.makeSinglePersonFetchDelete(id);
        return response;
    }
    
}
