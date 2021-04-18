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
import facades.ProxyFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @Path("map")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFetches() throws TimeoutException, InterruptedException, ExecutionException {
        List<JSONArray> fetchMapDTOList = proxyFacade.runParallelWithCallablesMap(threadPool);
        return GSON.toJson(fetchMapDTOList);
    }

    @Path("mapdto")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFetchesDTO() throws TimeoutException, InterruptedException, ExecutionException {
        List<FetchMapDTO> fetchMapDTOList = proxyFacade.runParallelWithCallablesMapToDTO(threadPool);
        return GSON.toJson(fetchMapDTOList);
    }

    @Path("map/{countrycode}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleFetcheMapDTO(@PathParam("countrycode") String country) {
        FetchMapDTO fetchMapDTO = proxyFacade.makeSingleMapFetchGet(country);
        return GSON.toJson(fetchMapDTO);
    }

    @Path("person")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllFetchePersonDTO() {
        FetchPersonsDTO fetchPersonsDTO = proxyFacade.makeAllPersonFetchGet();
        return GSON.toJson(fetchPersonsDTO);
    }
    
    @Path("person/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleFetchePersonDTO(@PathParam("id") String id) {
        FetchPersonDTO fetchPersonDTO = proxyFacade.makeSinglePersonFetchGet(id);
        return GSON.toJson(fetchPersonDTO);
    }

    @Path("person")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String proxyAddPersonPost(String PersonData) throws IOException {
        FetchPersonDTO fetchPersonDTO = GSON.fromJson(PersonData, FetchPersonDTO.class); //manual conversion
        fetchPersonDTO = proxyFacade.makeSinglePersonFetchPost(fetchPersonDTO);
        return GSON.toJson(fetchPersonDTO);
    }
}
