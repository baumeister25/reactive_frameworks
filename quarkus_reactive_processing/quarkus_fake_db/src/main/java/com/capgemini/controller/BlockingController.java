package com.capgemini.controller;

import io.smallrye.common.annotation.Blocking;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("blocking")
@Slf4j
public class BlockingController {
    private static int counter = 0;
    @GET
    @Blocking
    public String blockingRequest() throws InterruptedException {
        int requestNo = counter ++;
        log.info("Starting Request {}", requestNo );
        Thread.sleep(20000); // Sleep for 20 secs
        log.info("Stopping Request {}", requestNo);
        return " Finally did it";
    }
}
