/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Tweny
 */
public class Fetch {
    
    private String uri;
    private boolean isCalled = false;

    public Fetch(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String makeFetchGet() throws WebApplicationException {
        isCalled = true;
        try {
            URL url = new URL(uri);
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
}
