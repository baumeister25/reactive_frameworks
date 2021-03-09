package com.capgemini.springbootclient.controller;

import com.capgemini.springbootclient.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spring/")
public class InboundController {
    @Autowired
    private RestClientService restClientService;

    @GetMapping("greet")
    public String getGreet() {
        return restClientService.getGreeting();
    }
}
