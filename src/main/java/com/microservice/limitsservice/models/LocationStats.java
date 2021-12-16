package com.microservice.limitsservice.models;

public class LocationStats {
    private String state;
    private String country;
    private int covidCase;
    private int differenceInCasesFromPreviousDay;

    public int getDifferenceInCasesFromPreviousDay() {
        return differenceInCasesFromPreviousDay;
    }

    public void setDifferenceInCasesFromPreviousDay(int differenceInCasesFromPreviousDay) {
        this.differenceInCasesFromPreviousDay = differenceInCasesFromPreviousDay;
    }

    public String getState() {
        return state;
    }



    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCovidCase() {
        return covidCase;
    }

    public void setCovidCase(int covidCase) {
        this.covidCase = covidCase;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", covidCase=" + covidCase +
                '}';
    }
}
