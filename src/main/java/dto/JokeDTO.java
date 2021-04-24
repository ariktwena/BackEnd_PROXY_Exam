/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Tweny
 */
public class JokeDTO {
    
    private String url;
    private String joke;

    public JokeDTO(String url, String joke) {
        this.url = url;
        this.joke = joke;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    @Override
    public String toString() {
        return "JokesDTO{" + "url=" + url + ", joke=" + joke + '}';
    }
    
    
    
}
