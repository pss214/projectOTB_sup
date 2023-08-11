package project.otb.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import project.otb.DTO.BusArrivalDTO;
import project.otb.DTO.BusLiveByRouteDTO;
import project.otb.DTO.BusRouteNmDTO;
import project.otb.DTO.BusStationDTO;
import project.otb.entity.BusRoute;
import project.otb.repositiry.BusRouteRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("RouteApi-Response code: " + conn.getResponseCode());
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
            throw new RuntimeException(e.getMessage()+"\nmessage : readRouteApi api 오류");
        }
    }
    public String readBusStopInformation(String dto){
        try {
            //서울특별시 정류장정보조회 서비스 - 정류소고유번호를 입력받아 버스도착정보목록을 조회한다.
            String serviceKey = "au774mPDNO37gAJrlTNvjrymn07a/f739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw==";

            String urlBuilder = new String("http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid") /*URL*/
                    +("?serviceKey=" + URLEncoder.encode(serviceKey,"UTF-8"))/*Service Key*/
                    +("&arsId=" + URLEncoder.encode(dto, "UTF-8")) /*정류장 고유번호*/
                    +("&resultType="+ URLEncoder.encode("json", "UTF-8"));
            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("BusStopInformation-Response code: " + conn.getResponseCode());
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
            throw new RuntimeException(e.getMessage()+"\nmessage : readBusStopInformation api 오류");
        }

    }
    public String readGetBusStationRoute(String dto){
        try{
            //서울특별시 버스노선정보조회 서비스 - 고유번호에 해당하는 경유 정류장 목록 조회한다.
            String serviceKey = "au774mPDNO37gAJrlTNvjrymn07a/f739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw==";

            String urlBuilder = new String("http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute") /*URL*/
                    +("?serviceKey=" + URLEncoder.encode(serviceKey,"UTF-8"))/*Service Key*/
                    +("&busRouteId=" + URLEncoder.encode(dto, "UTF-8")) /*버스 고유 id*/
                    +("&resultType="+ URLEncoder.encode("json", "UTF-8"));
            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("GetBusStationRoute-Response code: " + conn.getResponseCode());
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
        }catch (IOException e){
            throw new RuntimeException(e.getMessage()+"\nmessage : readGetBusStationRoute api 오류");
        }
    }
    public String readBusArrivalInformation(String dto){
        try {
            //서울특별시 버스도착정보조회 서비스 - 고유번호에 해당하는 경유 정류장 목록 조회한다.
            String serviceKey = "au774mPDNO37gAJrlTNvjrymn07a/f739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw==";

            String urlBuilder = new String("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll") /*URL*/
                    + "?serviceKey=" + serviceKey/*Service Key*/
                    + "&busRouteId=" +dto /*버스 고유 id*/
                    + "&resultType=" + "json";
            URL url = new URL(urlBuilder);
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
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage()+"\nmessage : api 오류");
        }
    }

    public BusApiService(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }
    public String GetBusRouteApi(){
        try {
            String api = readRouteApi();
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
    public List<BusStationDTO> GetBusStation(String dto){
        String api = readBusStopInformation(dto);
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        BusStopInformationDTO busdto = pretty.fromJson(api, BusStopInformationDTO.class);
        List<BusStationDTO> res = new ArrayList<>();
        for (int i = 0; i < busdto.getMsgBody().itemList.size(); i++) {
            res.add(i, BusStationDTO.builder().busRouteId(busdto.getMsgBody().itemList.get(i).busRouteId)
                            .rtNm(busdto.getMsgBody().itemList.get(i).rtNm)
                            .arrmsg1(busdto.getMsgBody().itemList.get(i).arrmsg1)
                            .arrmsg2(busdto.getMsgBody().itemList.get(i).arrmsg2)
                            .vehId1(busdto.getMsgBody().itemList.get(i).vehId1)
                            .build());
        }
        return res;
    }
    public List<BusRouteNmDTO> GetBusStationRoute(String dto){
        String api = readGetBusStationRoute(dto);
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        BusStationRouteDTO busdto = pretty.fromJson(api, BusStationRouteDTO.class);
        List<BusRouteNmDTO> res = new ArrayList<>();
        for (int i = 0; i < busdto.getMsgBody().itemList.size(); i++) {
            res.add(i, BusRouteNmDTO.builder()
                    .stationNm(busdto.getMsgBody().getItemList().get(i).stationNm)
                    .arsId(busdto.getMsgBody().getItemList().get(i).arsId)
                    .build());
        }
        return res;
    }
    public List<BusLiveByRouteDTO> GetBusLiveByRoute(String dto){
        String api = readBusArrivalInformation(dto);
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        BusLiveByRouteApiDTO busdto = pretty.fromJson(api, BusLiveByRouteApiDTO.class);
        List<BusLiveByRouteDTO> res = new ArrayList<>();
        for (int i = 0; i < busdto.getMsgBody().getItemList().size(); i++) {
            res.add(BusLiveByRouteDTO.builder()
                    .stNm(busdto.getMsgBody().getItemList().get(i).stNm)
                    .rtNm(busdto.getMsgBody().getItemList().get(i).rtNm)
                    .arsId(busdto.getMsgBody().getItemList().get(i).arsId)
                    .plainNo1(busdto.getMsgBody().getItemList().get(i).plainNo1)
                    .arrmsg1(busdto.getMsgBody().getItemList().get(i).arrmsg1)
                    .stationNm1(busdto.getMsgBody().getItemList().get(i).stationNm1)
                    .nstnId1(busdto.getMsgBody().getItemList().get(i).nstnId1)
                    .build());
        }
        return res;
    }
}



