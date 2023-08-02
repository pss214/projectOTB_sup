package project.otb.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class BusStopInformation {
    public static void main(String[] args) throws IOException {
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
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        BusStopInformationDTO busdto = pretty.fromJson(sb.toString(), BusStopInformationDTO.class);
        System.out.println(busdto.getMsgBody().itemList.get(0).busRouteId);
        System.out.println(busdto.getMsgBody().itemList.get(0).busRouteNm);
    }
}