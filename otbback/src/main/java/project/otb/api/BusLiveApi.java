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
            String serviceKey = "au774mPDNO37gAJrlTNvjrymn07a%2Ff739RcICwnifiDnut1ekKDvSB8VpIbxYugjR0bPwIe1TM7uTzYk3yjsiw%3D%3D";

            String urlBuilder = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll" + "?"
                    + "serviceKey" +"="+ serviceKey /*Service Key*/
                    +"&busRouteId=" + "121900016"  /*노선ID 100100056)341*/
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
            Gson pretty = new GsonBuilder().setPrettyPrinting().create();
            BusLiveByRouteApiDTO busdto = pretty.fromJson(sb.toString(), BusLiveByRouteApiDTO.class);
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
        }
}
