package com.example.demo.lib;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestRestAPI {

    @Value("${rest.api.url}")
    private String REST_API_LOGIN_URL;


    public Map authRequestAPI(Map data) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(data);

        // SSL 인증서 없이 호출
        CustomTrustManager.trustManagerInit();
        OutputStream os = null;
        BufferedReader br = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(REST_API_LOGIN_URL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            os = conn.getOutputStream();
            os.write(jsonStr.getBytes("UTF-8"));
            os.flush();

            StringBuffer sb = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(conn. getInputStream(), "UTF-8"));
            String line;
            while ( (line = br.readLine()) != null ) {
                sb.append(line);
            }

            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
            return objectMapper.readValue(sb.toString(), typeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("인증 실패.");
        } finally {
            if(os != null) os.close();
            if(br != null) br.close();
            if(conn != null) conn.disconnect();
        }
    }
}


