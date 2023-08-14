package project.otb.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import project.otb.DTO.BusLiveByRouteDTO;
import project.otb.DTO.BusRouteNmDTO;
import project.otb.DTO.BusStationDTO;
import project.otb.entity.BusRoute;
import project.otb.entity.BusStation;
import project.otb.repositiry.BusRouteRepository;
import project.otb.repositiry.BusStationRepository;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BusApiService {
    private final BusRouteRepository busRouteRepository;
    private final BusStationRepository busStationRepository;
    public BusApiService(BusRouteRepository busRouteRepository, BusStationRepository busStationRepository) {
        this.busRouteRepository = busRouteRepository;
        this.busStationRepository = busStationRepository;
    }
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
            System.out.println("readBusArrivalInformation Response code: " + conn.getResponseCode());
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
            throw new RuntimeException(e.getMessage()+"\nmessage : readBusArrivalInformation api 오류");
        }
    }
    public String readBusPlaceNum(String dto){
        /*서울특별시 버스위치정보조회 서비스 - 	차량ID로 위치정보를 조회한다*/
        try {
            String serviceKey = "" +
                    "au774mPDNO37gAJrlTNvjrymn07a/f739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw==";

            String urlBuilder = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByVehId" + "?"
                    + "serviceKey" +"="+ serviceKey /*Service Key*/
                    +"&vehId=" + dto  /*노선ID 100100056)341*/
                    +"&resultType="+"json";

            URL url = new URL(urlBuilder);
            System.out.println(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
        }catch (IOException e){
            throw new RuntimeException(e.getMessage()+"\nmessage : readBusPlaceNum api 오류");
        }
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
    public String GetBusStationAPI(){
        try {
            Reader reader = new FileReader("./src/main/resources/busstionlist.json");
            Gson pretty = new GsonBuilder().setPrettyPrinting().create();
            BusStationApiDTO busdto = pretty.fromJson(reader, BusStationApiDTO.class);
            for (int i = 0; i < busdto.getDATA().size(); i++) {
                BusStation busStation = BusStation.builder()
                        .stationid(busdto.getDATA().get(i).sttn_id)
                        .stationuniid(busdto.getDATA().get(i).sttn_no)
                        .stationname(busdto.getDATA().get(i).sttn_nm)
                        .build();
                busStationRepository.save(busStation);
            }
            return "버스정류장정보 리스트를 저장했습니다!";
        }catch (IOException e){
            throw new RuntimeException("버스 정보를 가져오지 못했습니다");
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
    public List<BusRouteNmDTO> GetBusStationRoute(String dto,String station){
        BusRoute bus = busRouteRepository.findByrouteidEquals(dto);
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        if(bus.getStationlist()!=null){
            BusStationRouteDTO busdto = pretty.fromJson(bus.getStationlist(), BusStationRouteDTO.class);
            List<BusRouteNmDTO> res = new ArrayList<>();
            for (int i = 0; i < busdto.getMsgBody().itemList.size(); i++) {
                if(Objects.equals(busdto.getMsgBody().getItemList().get(i).arsId, station)){
                    for (int j = i; j < busdto.getMsgBody().itemList.size();j++) {
                        res.add(j-i, BusRouteNmDTO.builder()
                                .stationNm(busdto.getMsgBody().getItemList().get(j).stationNm)
                                .arsId(busdto.getMsgBody().getItemList().get(j).arsId)
                                .build());
                    }
                    break;
                }
            }
            return res;
        }
        else{
            String api = readGetBusStationRoute(dto);
            bus.updateStation(api);
            busRouteRepository.save(bus);
            BusStationRouteDTO busdto = pretty.fromJson(api, BusStationRouteDTO.class);
            List<BusRouteNmDTO> res = new ArrayList<>();
            for (int i = 0; i < busdto.getMsgBody().itemList.size(); i++) {
                if(Objects.equals(busdto.getMsgBody().getItemList().get(i).arsId, station)){
                    for (int j = i; j < busdto.getMsgBody().itemList.size();j++) {
                        res.add(j-i, BusRouteNmDTO.builder()
                                .stationNm(busdto.getMsgBody().getItemList().get(j).stationNm)
                                .arsId(busdto.getMsgBody().getItemList().get(j).arsId)
                                .build());
                    }
                    break;
                }
            }
            return res;
        }
    }
    public List<BusLiveByRouteDTO> GetBusLiveByRoute(String dto){
        String api = readBusArrivalInformation(dto);
//        BusRoute busRoute =busRouteRepository.findByrouteidEquals(dto);
//        busRoute.updatelive(api);
//        busRouteRepository.save(busRoute);
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
    public String GetBusRoutePlaceNum(String dto){
        String api = readBusPlaceNum(dto);
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        BusRoutePlateDTO busdto = pretty.fromJson(api, BusRoutePlateDTO.class);
        return busdto.getMsgBody().getItemList().get(0).plainNo;
    }
}