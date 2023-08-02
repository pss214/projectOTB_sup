package project.otb.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import project.otb.entity.BusRoute;
import project.otb.repositiry.BusRouteRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

@Service
public class BusApiService {
    private final BusRouteRepository busRouteRepository;
    public String readRouteApi(){
        // 서울특별시 노선 목록 API
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
            throw new RuntimeException(e.getMessage()+"\nmessage : api 오류");
        }
    }
    public String readBusStopInformation(){
        try {
            //서울특별시 정류장정보조회 서비스 - 고유번호에 해당하는 경유노선목록을 조회한다.
            String serviceKey = "au774mPDNO37gAJrlTNvjrymn07a/f739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw==";

            String urlBuilder = new String("http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation") /*URL*/
                    +("?serviceKey=" + URLEncoder.encode(serviceKey,"UTF-8"))/*Service Key*/
                    +("&arsId=" + URLEncoder.encode("22276", "UTF-8")) /*정류장 고유번호*/
                    +("&resultType="+ URLEncoder.encode("json", "UTF-8"));
            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        }catch (IOException e) {
            throw new RuntimeException(e.getMessage()+"\nmessage : api 오류");
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
                project.otb.entity.BusRoute busRoute = BusRoute.builder()
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
    public String GetBusStation(String )
}
