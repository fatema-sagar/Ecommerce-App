package com.ecommerce.ecommApp.commons.Util;

import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Communication {

    public static String sendGetRequest(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(RequestMethod.GET.toString());
            int responseCode = con.getResponseCode();
            System.out.println("response code : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        } catch (IOException ex) {

        }
        return null;
    }

    public static String sendPostRequest(String endpoint, String json) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(RequestMethod.POST.toString());
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (IOException io) {

        }
        return null;
    }

//    public static void main(String []g)
//    {
//        System.out.println(sendGetRequest("https://jsonplaceholder.typicode.com/todos/2"));
//        System.out.println(sendPostRequest("https://jsonplaceholder.typicode.com/posts","{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}"));
//    }

}

