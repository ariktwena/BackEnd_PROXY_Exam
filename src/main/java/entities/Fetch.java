/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.google.gson.Gson;
import dto.FetchPersonDTO;
import dto.FetchPersonDTOtoPost;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    @Override
    public String toString() {
        return "Fetch{" + "uri=" + uri + ", isCalled=" + isCalled + '}';
    }

}
