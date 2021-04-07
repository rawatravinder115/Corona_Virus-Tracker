package ProjectCoronaVirusTracker.Corona_VirusTracker.Services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoronaVirusDataServices {

    private static String VIRUS_DATA_URL ="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/01-01-2021.csv";

    public void fetchVirusData() throws IOException, InterruptedException {

        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder()
                        .uri(URI.create(VIRUS_DATA_URL)).build();

        HttpResponse<String> httpResponse =client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());

    }
}
