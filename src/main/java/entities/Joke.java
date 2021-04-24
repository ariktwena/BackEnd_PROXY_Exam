/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Tweny
 */
public class Joke {
    
    private String url;
    private String JSON;

    public Joke(String url, String JSON) {
        this.url = url;
        this.JSON = JSON;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    @Override
    public String toString() {
        return "Jokes{" + "url=" + url + ", JSON=" + JSON + '}';
    }
    
    
    
}
