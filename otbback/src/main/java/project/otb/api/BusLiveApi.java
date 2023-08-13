package project.otb.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import project.otb.DTO.BusLiveByRouteDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BusLiveApi {
        public static void main(String[] args) throws IOException {
            /*URL*/
            String serviceKey = "" +
                    "au774mPDNO37gAJrlTNvjrymn07a/f739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw==";

            String urlBuilder = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByVehId" + "?"
                    + "serviceKey" +"="+ serviceKey /*Service Key*/
                    +"&vehId=" + "122014061"  /*노선ID 100100056)341*/
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
            System.out.println(sb);
            Gson pretty = new GsonBuilder().setPrettyPrinting().create();
            BusRoutePlateDTO busdto = pretty.fromJson(sb.toString(), BusRoutePlateDTO.class);
            System.out.println(busdto.getMsgBody().getItemList().get(0).plainNo);
        }
}
