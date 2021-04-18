/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FetchGetDTO;
import facades.ProxyFacade;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

    //@Path("fetch")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFetches() throws TimeoutException, InterruptedException, ExecutionException {
        long startTime = System.nanoTime();
        List<JSONArray> dataFeched = proxyFacade.runParallelWithCallables(threadPool);
//        for(JSONArray s : dataFeched){
//            System.out.println(GSON.toJson(s));
//        }
        return GSON.toJson(dataFeched);
    }
    
    @Path("dto")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFetchesDTO() throws TimeoutException, InterruptedException, ExecutionException {
        long startTime = System.nanoTime();
        List<FetchGetDTO> dataFeched = proxyFacade.runParallelWithCallablesToDTO(threadPool);
//        for(JSONArray s : dataFeched){
//            System.out.println(GSON.toJson(s));
//        }
        return GSON.toJson(dataFeched);
    }

}
