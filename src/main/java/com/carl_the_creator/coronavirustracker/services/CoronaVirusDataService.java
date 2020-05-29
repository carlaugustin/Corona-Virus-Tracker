package com.carl_the_creator.coronavirustracker.services;

import com.carl_the_creator.coronavirustracker.models.LocationGlobalDeaths;
import com.carl_the_creator.coronavirustracker.models.LocationStats;
import com.carl_the_creator.coronavirustracker.models.LocationUsDeaths;
import com.carl_the_creator.coronavirustracker.models.LocationUsStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String US_CONFIRM_VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";
    private static String US_CONFIRM_DEATHS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_US.csv" ;
    private static String GLOBAL_CONFIRM_VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String GLOBAL_CONFIRM_DEATHS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv" ;

    private List<LocationUsStats> usStats = new ArrayList<>();
    private List<LocationUsDeaths> usDeathsStats= new ArrayList<>();
    private List<LocationStats> allStats = new ArrayList<>();
    private List<LocationGlobalDeaths> globalDeathsStats= new ArrayList<>();


    public List<LocationUsDeaths> getUsDeathStats(){ return usDeathsStats; }
    public List<LocationUsStats> getUsStats(){ return usStats; }
    public List<LocationStats> getAllStats() {
        return allStats;
    }
    public List<LocationGlobalDeaths> getGlobalDeathsStats() { return globalDeathsStats; }




    @PostConstruct
    @Scheduled(cron = "0 */30 * ? * *")
    public void fetchUsVirusData() throws IOException, InterruptedException {
        List<LocationUsStats> newUsStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(US_CONFIRM_VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader cvsBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsBodyReader);
        for (CSVRecord record : records) {
            LocationUsStats locationUsStats = new LocationUsStats();
            locationUsStats.setState(record.get("Province_State"));
            locationUsStats.setCombine(record.get("Combined_Key"));
            int latestUsCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayUsCases = Integer.parseInt(record.get(record.size() - 2));
            locationUsStats.setLatestTotal(latestUsCases);
            locationUsStats.setDiffFromPrevDay(latestUsCases - prevDayUsCases);
            newUsStats.add(locationUsStats);

        }

        this.usStats = newUsStats;

    }



    @PostConstruct
    @Scheduled(cron = "0 */30 * ? * *")
    public void fetchUsDeathData() throws IOException, InterruptedException {
        List<LocationUsDeaths> usDeaths = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(US_CONFIRM_DEATHS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader cvsBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsBodyReader);
        for (CSVRecord record : records) {
            LocationUsDeaths locationUsDeaths = new LocationUsDeaths();
            int latestUsDeaths = Integer.parseInt(record.get(record.size() - 1));
            locationUsDeaths.setUsDeaths(latestUsDeaths);
            usDeaths.add(locationUsDeaths);
        }
        this.usDeathsStats = usDeaths;

    }

    @PostConstruct
    @Scheduled(cron = "0 */30 * ? * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GLOBAL_CONFIRM_VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader cvsBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotal(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }
        this.allStats = newStats;

    }

    @PostConstruct
    @Scheduled(cron = "0 */30 * ? * *")
    public void fetchGlobalDeathData() throws IOException, InterruptedException {
        List<LocationGlobalDeaths> globalDeaths = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GLOBAL_CONFIRM_DEATHS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader cvsBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsBodyReader);
        for (CSVRecord record : records) {
            LocationGlobalDeaths locationGlobalDeaths = new LocationGlobalDeaths();
            int latestGlobalDeaths = Integer.parseInt(record.get(record.size() - 1));
            locationGlobalDeaths.setGlobalDeaths(latestGlobalDeaths);
            globalDeaths.add(locationGlobalDeaths);
        }
        this.globalDeathsStats = globalDeaths;

    }

    
}
