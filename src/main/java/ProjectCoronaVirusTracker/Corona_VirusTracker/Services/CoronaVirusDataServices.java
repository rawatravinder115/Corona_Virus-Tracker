package ProjectCoronaVirusTracker.Corona_VirusTracker.Services;

import ProjectCoronaVirusTracker.Corona_VirusTracker.Models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataServices {

    private static String VIRUS_DATA_URL ="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")  // min(2),hr(2),day(2), ... etc // use to refresh the data.
    public void fetchVirusData() throws IOException, InterruptedException {

         List<LocationStats> newStats = new ArrayList<>();
        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder()
                        .uri(URI.create(VIRUS_DATA_URL)).build();

        HttpResponse<String> httpResponse =client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
//            String id = record.get("ID");
//            String customerNo = record.get("CustomerNo");
//            String name = record.get("Name");
//            String state= record.get("Province_State");
//            System.out.println(state);

            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int num = Integer.parseInt(record.get(record.size()-1));
            locationStats.setLatestTotalCases(num);
//            System.out.println(locationStats);
            newStats.add(locationStats);
        }
        this.allStats= newStats;
    }
}
