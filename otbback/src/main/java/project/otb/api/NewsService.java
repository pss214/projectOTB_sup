package project.otb.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NewsService {
    public String readNewsapi(String dto) {
        try {
            if(dto == null){
                dto = "";
            }
            String serviceKey = "71e24a4b43ac4a689f929182c81dc940";

            String urlBuilder = "https://newsapi.org/v2/top-headlines?"
                    + "country=kr"
                    + dto
                    + "&apiKey=" + serviceKey; /*Service Key*/


            URL url = new URL(urlBuilder);
            System.out.println(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            throw new RuntimeException(e.getMessage() + "\nmessage : readBusPlaceNum api 오류");
        }
    }
    public NewsDTO NewsGetApi(String dto){
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        return pretty.fromJson(readNewsapi(dto), NewsDTO.class);
    }



}