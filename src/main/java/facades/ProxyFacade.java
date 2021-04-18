/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FetchMapDTO;
import dto.FetchPersonDTO;
import dto.FetchPersonDTOtoPost;
import dto.FetchPersonsDTO;
import entities.Fetch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.WebApplicationException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Tweny
 */
public class ProxyFacade {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public FetchPersonDTO makeSinglePersonFetchPost(FetchPersonDTO fetchPersonDTO) throws IOException {
        String URL = "https://codergram.dk/Flow2Week2Person-Address/api/person";
        Fetch fetch = new Fetch(URL);
        System.out.println(fetchPersonDTO.toString());
        System.out.println("------");
        String JSONString = makeFetchPost(fetch, fetchPersonDTO);
        System.out.println("------");
        System.out.println(JSONString);
        Gson gson = new Gson();
        fetchPersonDTO = gson.fromJson(JSONString, FetchPersonDTO.class);
        System.out.println(fetchPersonDTO);
        return fetchPersonDTO;
    }

    public FetchPersonDTO makeSinglePersonFetchGet(String id) {
        String URL = "https://codergram.dk/Flow2Week2Person-Address/api/person/" + id;
        Fetch fetch = new Fetch(URL);
        String JSONString = makeFetchGetObject(fetch);
        System.out.println(JSONString);

        Gson gson = new Gson();
        FetchPersonDTO fetchPersonDTO = gson.fromJson(JSONString, FetchPersonDTO.class);
        System.out.println(fetchPersonDTO);
        return fetchPersonDTO;
    }

    public FetchPersonsDTO makeAllPersonFetchGet() {
        String URL = "https://codergram.dk/Flow2Week2Person-Address/api/person/all";
        Fetch fetch = new Fetch(URL);
        String JSONString = makeFetchGetObject(fetch);
        System.out.println(JSONString);

        Gson gson = new Gson();
        ArrayList<FetchPersonDTO> fetchPersonDTOs = new ArrayList<>();

        FetchPersonsDTO fetchPersonsDTO = gson.fromJson(JSONString, FetchPersonsDTO.class);
        System.out.println(fetchPersonsDTO.getAll().toString());

        return fetchPersonsDTO;
    }

    public FetchMapDTO makeSingleMapFetchGet(String country) {
        String URL = "http://restcountries.eu/rest/v1/alpha?codes=" + country;
        Fetch fetch = new Fetch(URL);
        String JSONString = makeFetchGetArray(fetch);

        Gson gson = new Gson();
        JSONArray json = new JSONArray(JSONString);
        FetchMapDTO fetchMapDTO = null;
        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            System.out.println(object.toString());
            fetchMapDTO = gson.fromJson(object.toString(), FetchMapDTO.class);
            System.out.println(fetchMapDTO);
        }
        return fetchMapDTO;
    }

    public List<JSONArray> runParallelWithCallablesMap(ExecutorService threadPool) throws TimeoutException, InterruptedException, ExecutionException {
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
                    return makeFetchGetArray(fetch);
                }
            };
            futures.add(threadPool.submit(task));
        }

        Gson gson = new Gson();
        List<JSONArray> fullJSONResult = new ArrayList<>();
        List<FetchMapDTO> fullJSONResultToFetchGetDTO = new ArrayList<>();
        for (Future<String> future : futures) {
            String s = future.get(2000, TimeUnit.MILLISECONDS);

            JSONArray json = new JSONArray(s);

            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);
                System.out.println(object.toString());
                FetchMapDTO fetchMapDTO = gson.fromJson(object.toString(), FetchMapDTO.class);
                System.out.println(fetchMapDTO);
                fullJSONResultToFetchGetDTO.add(fetchMapDTO);
            }

            fullJSONResult.add(json);

        }
//        return fullJSONResultToFetchGetDTO;
        return fullJSONResult;
    }

    public List<FetchMapDTO> runParallelWithCallablesMapToDTO(ExecutorService threadPool) throws TimeoutException, InterruptedException, ExecutionException {
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
                    return makeFetchGetArray(fetch);
                }
            };
            futures.add(threadPool.submit(task));
        }

        Gson gson = new Gson();
        List<JSONArray> fullJSONResult = new ArrayList<>();
        List<FetchMapDTO> fullJSONResultToFetchGetDTO = new ArrayList<>();
        for (Future<String> future : futures) {
            String s = future.get(2000, TimeUnit.MILLISECONDS);

            JSONArray json = new JSONArray(s);

            for (int i = 0; i < json.length(); i++) {
                JSONObject object = json.getJSONObject(i);
                System.out.println(object.toString());
                FetchMapDTO fetchMapDTO = gson.fromJson(object.toString(), FetchMapDTO.class);
                System.out.println(fetchMapDTO);
                fullJSONResultToFetchGetDTO.add(fetchMapDTO);
            }

            fullJSONResult.add(json);

        }
        return fullJSONResultToFetchGetDTO;
//        return fullJSONResult;
    }

    
    private String makeFetchGetObject(Fetch fetch) throws WebApplicationException {
        try {
            URL url = new URL(fetch.getUri());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                //print result
                System.out.println(response.toString());
                return response.toString();
            }

        } catch (MalformedURLException ex) {
            throw new WebApplicationException("This is a MalformedURLException", 404);
        } catch (IOException ex) {
            throw new WebApplicationException("This is a MalformedURLException", 404);
        }
    }

    private String makeFetchGetArray(Fetch fetch) throws WebApplicationException {
        try {
            URL url = new URL(fetch.getUri());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            Scanner scan = new Scanner(con.getInputStream());
            String jsonStr = null;
            if (scan.hasNext()) {
                jsonStr = scan.nextLine();
            }
            scan.close();
            System.out.println(jsonStr);
            return jsonStr;
        } catch (MalformedURLException ex) {
            throw new WebApplicationException("This is a MalformedURLException", 404);
        } catch (IOException ex) {
            throw new WebApplicationException("This is a MalformedURLException", 404);
        }
    }

    private String makeFetchPost(Fetch fetch, FetchPersonDTO fetchPersonDTO) throws WebApplicationException {
        try {
            URL obj = new URL(fetch.getUri());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

            // For POST only - START
            con.setDoOutput(true);

            Gson gson = new Gson();
            String urlParameters = gson.toJson(fetchPersonDTO);

            OutputStreamWriter writer = new OutputStreamWriter(
                    con.getOutputStream());
            writer.write(urlParameters);
            writer.flush();
            // For POST only - END

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());

                return response.toString();
            } else {
                System.out.println("POST request not worked");
                return "";
            }
        } catch (MalformedURLException ex) {
            throw new WebApplicationException("This is a MalformedURLException", 404);
        } catch (IOException ex) {
            throw new WebApplicationException("This is a MalformedURLException", 404);
        }
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
        List<JSONArray> fetchedDataParallelFuture = new ProxyFacade().runParallelWithCallablesMap(threadPool);

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
