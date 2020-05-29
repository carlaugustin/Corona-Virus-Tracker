package com.carl_the_creator.coronavirustracker.models;

public class LocationUsDeaths {

    private  int usDeaths;

    public int getUsDeaths() {
        return usDeaths;
    }

    public void setUsDeaths(int usDeaths) {
        this.usDeaths = usDeaths;
    }

    @Override
    public String toString() {
        return "LocationUsDeaths{" +
                "usDeaths=" + usDeaths +
                '}';
    }
}
