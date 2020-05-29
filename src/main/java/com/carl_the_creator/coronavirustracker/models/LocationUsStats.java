package com.carl_the_creator.coronavirustracker.models;

public class LocationUsStats {

    private String state;
    private int latestTotal;
    private  int diffFromPrevDay;
    private String combine;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getLatestTotal() {
        return latestTotal;
    }

    public void setLatestTotal(int latestTotal) {
        this.latestTotal = latestTotal;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getCombine() {
        return combine;
    }

    public void setCombine(String combine) {
        this.combine = combine;
    }


    @Override
    public String toString() {
        return "LocationUsStats{" +
                "state='" + state + '\'' +
                ", latestTotal=" + latestTotal +
                ", diffFromPrevDay=" + diffFromPrevDay +
                ", combine='" + combine + '\'' +
                '}';
    }
}
