package com.capgemini.springbootclient.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClientService {
    public String getGreeting(){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "http://db:8080/blocking";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl , String.class);
        return response.getBody();
    }
}
