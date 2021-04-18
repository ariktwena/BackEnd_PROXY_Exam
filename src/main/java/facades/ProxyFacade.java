/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FetchGetDTO;
import entities.Fetch;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Tweny
 */
public class ProxyFacade {
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public List<JSONArray> runParallelWithCallables(ExecutorService threadPool) throws TimeoutException, InterruptedException, ExecutionException {
        List<Fetch> fetchList = new ArrayList();
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=de"));
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=dk"));
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=is"));
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=it"));

        List<Future<String>> futures = new ArrayList<>();

        for (Fetch fetch : fetchList) {
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() {
                    return fetch.makeFetchGet();
                }
            };
            futures.add(threadPool.submit(task));
        }

        Gson gson = new Gson();
        List<JSONArray> fullJSONResult = new ArrayList<>();
        List<FetchGetDTO> fullJSONResultToFetchGetDTO = new ArrayList<>();
        for (Future<String> future : futures) {
            String s = future.get(2000, TimeUnit.MILLISECONDS);
            
            JSONArray json = new JSONArray(s);

            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);
                System.out.println(object.toString());
                FetchGetDTO fetchGetDTO = gson.fromJson(object.toString(), FetchGetDTO.class);
                System.out.println(fetchGetDTO);
                fullJSONResultToFetchGetDTO.add(fetchGetDTO);
            }
            
            fullJSONResult.add(json);

        }
//        return fullJSONResultToFetchGetDTO;
        return fullJSONResult;
    }
    
    public List<FetchGetDTO> runParallelWithCallablesToDTO(ExecutorService threadPool) throws TimeoutException, InterruptedException, ExecutionException {
        List<Fetch> fetchList = new ArrayList();
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=de"));
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=dk"));
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=is"));
        fetchList.add(new Fetch("http://restcountries.eu/rest/v1/alpha?codes=it"));

        List<Future<String>> futures = new ArrayList<>();

        for (Fetch fetch : fetchList) {
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() {
                    return fetch.makeFetchGet();
                }
            };
            futures.add(threadPool.submit(task));
        }

        Gson gson = new Gson();
        List<JSONArray> fullJSONResult = new ArrayList<>();
        List<FetchGetDTO> fullJSONResultToFetchGetDTO = new ArrayList<>();
        for (Future<String> future : futures) {
            String s = future.get(2000, TimeUnit.MILLISECONDS);
            
            JSONArray json = new JSONArray(s);

            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);
                System.out.println(object.toString());
                FetchGetDTO fetchGetDTO = gson.fromJson(object.toString(), FetchGetDTO.class);
                System.out.println(fetchGetDTO);
                fullJSONResultToFetchGetDTO.add(fetchGetDTO);
            }
            
            fullJSONResult.add(json);

        }
        return fullJSONResultToFetchGetDTO;
//        return fullJSONResult;
    }

    public static void main(String[] args) throws TimeoutException, InterruptedException, ExecutionException {
//        Proxy proxy = new Proxy();
//        System.out.println(proxy.fetchData("de"));

        /**
         * Parallel (Future/Callables)
         */
        long timeSequental;
        long start = System.nanoTime();
        //TODO Add your parrallel calculation here 
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<JSONArray> fetchedDataParallelFuture = new ProxyFacade().runParallelWithCallables(threadPool);

        long timeParallel_Future = System.nanoTime() - start;
        System.out.println("Time Parallel (Future/Callables): " + ((timeParallel_Future) / 1_000_000) + " ms.");

        for (JSONArray s : fetchedDataParallelFuture) {
            System.out.println(GSON.toJson(s));
            System.out.println("----------------------------------");
        }
        //Vi lukker forbindelsen, men vi behøver ikke
        threadPool.shutdown();
    }
}
