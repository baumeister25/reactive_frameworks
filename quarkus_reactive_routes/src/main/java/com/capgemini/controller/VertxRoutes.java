package com.capgemini.controller;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.vertx.core.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RouteBase(path = "vertx", produces = "text/plain")
@Slf4j
public class VertxRoutes {
    private static int counter = 0;
    @Route(methods = HttpMethod.GET, path = "blocking")
    public String blocking() throws InterruptedException {

        int requestNo = counter ++;
        log.info("Starting Request {}", requestNo );
        Thread.sleep(10000); // Sleep for 10 secs (2 secs is the limit)
        log.info("Stopping Request {}", requestNo);
        return " Finally did it";    }
}
