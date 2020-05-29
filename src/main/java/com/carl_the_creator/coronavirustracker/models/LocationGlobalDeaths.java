package com.carl_the_creator.coronavirustracker.models;

public class LocationGlobalDeaths {

    private int globalDeaths;
    private int totalDeaths;

    public int getGlobalDeaths() {
        return globalDeaths;
    }

    public void setGlobalDeaths(int globalDeaths) {
        this.globalDeaths = globalDeaths;
    }

    @Override
    public String toString() {
        return "LocationGlobalDeaths{" +
                "globalDeaths=" + globalDeaths +
                '}';
    }
}
