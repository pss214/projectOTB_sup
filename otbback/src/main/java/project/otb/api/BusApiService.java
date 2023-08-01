package project.otb.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import project.otb.repositiry.BusRouteRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class BusApiService {
    private final BusRouteRepository busRouteRepository;
    public String readRouteApi() throws Throwable {
        String serviceKey = "6b6667495662616b35394e70675958";
        try {
            URL url = new URL("http://openapi.seoul.go.kr:8088/" + serviceKey + "/json/busRoute/1/673");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            return sb.toString();
        }catch (IOException e){
            throw e.fillInStackTrace();
        }
    }
    public BusApiService(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }
    public String GetBusRouteApi(){
        try {
            String api = null;
            api = readRouteApi();
            Gson pretty = new GsonBuilder().setPrettyPrinting().create();
            BusRouteApiDTO busdto = pretty.fromJson(api, BusRouteApiDTO.class);
            for (int i = 0; i < busdto.getBusRoute().getList_total_count(); i++) {
                project.otb.entity.BusRoute busRoute = project.otb.entity.BusRoute.builder()
                        .route(busdto.getBusRoute().getRow().get(i).ROUTE)
                        .routeid(busdto.getBusRoute().getRow().get(i).ROUTE_ID)
                        .build();
                busRouteRepository.save(busRoute);
            }
            return "버스노선정보 리스트를 저장했습니다!";
        } catch (Throwable e) {
            return e.getMessage();
        }

    }
}
