


package com.microservice.limitsservice.services;

import com.microservice.limitsservice.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class covid19Data {
    private static final String COVID_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> locationStatsList = new ArrayList<LocationStats>();

    public List<LocationStats> getLocationStatsList() {
        return locationStatsList;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchCovidData() throws IOException, InterruptedException {
        List<LocationStats> newList = new ArrayList<LocationStats>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(COVID_DATA_URL)).build();
        HttpResponse<String>  response = client.send(request,HttpResponse.BodyHandlers.ofString());
        StringReader covidDataReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(covidDataReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            String state = record.get("Province/State");
            state = (state.equals("") ?"Not Found":state);
            String country = record.get("Country/Region");
            Integer todayCovidCase = Integer.parseInt(record.get(record.size()-1));
            Integer yesterdayCovidCase = Integer.parseInt(record.get(record.size()-2));

            locationStat.setCovidCase(todayCovidCase);
            locationStat.setState(state);
            locationStat.setCountry(country);
            locationStat.setDifferenceInCasesFromPreviousDay(todayCovidCase-yesterdayCovidCase);
            newList.add(locationStat);
        }
        this.locationStatsList=newList;
    }

    public int getTotaCases(){
        int sum=this.locationStatsList.stream().mapToInt(item-> item.getCovidCase()).sum();
        return sum;
    }
}
