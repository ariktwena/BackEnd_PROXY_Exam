/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;

/**
 *
 * @author Tweny
 */
public class FetchMapDTO {

    //Skal bruge  org.json.JSONArray -> søg efter org.json i dependencies
    //JSONObject json = new JSONObject("");
    //JSONArray json = new JSONArray("");
    //private JSONArray json;
    private String name;
    private String nativeName;
    private String capital;
    private String area;
    private List<String> languages;
    private List<String> borders;
    private long population;
    private List<String> timezones;

    public FetchMapDTO() {
    }

    public FetchMapDTO(String name, String nativeName, String capital, String area, List<String> languages, List<String> borders, long population, List<String> timezones) {
        this.name = name;
        this.nativeName = nativeName;
        this.capital = capital;
        this.area = area;
        this.languages = languages;
        this.borders = borders;
        this.population = population;
        this.timezones = timezones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    @Override
    public String toString() {
        return "FetchGetDTO{" + "name=" + name + ", nativeName=" + nativeName + ", capital=" + capital + ", area=" + area + ", languages=" + languages + ", borders=" + borders + ", population=" + population + ", timezones=" + timezones + '}';
    }
    
}
