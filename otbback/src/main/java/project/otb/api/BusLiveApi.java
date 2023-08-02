package project.otb.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BusLiveApi {
        public static void main(String[] args) throws IOException {
            /*URL*/
            String serviceKey = "au774mPDNO37gAJrlTNvjrymn07a%2Ff739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw%3D%3D";

            String urlBuilder = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid" + "?"
                    + "serviceKey" +"="+ serviceKey + /*Service Key*/
                    "&" +"busRouteId=" +"121900016"/*노선ID*/
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
        }
}
