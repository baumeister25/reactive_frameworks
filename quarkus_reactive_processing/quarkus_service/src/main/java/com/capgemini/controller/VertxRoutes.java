package com.capgemini.controller;

import com.capgemini.service.VertXClient;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@RouteBase(path = "vertx", produces = "application/json")
@Slf4j
public class VertxRoutes {

    @Inject
    VertXClient client;

    @Route(methods = HttpMethod.GET, path = "greet")
    public Uni<Object> greet() {
        return client.getGreet().map(String::valueOf).map(String::toUpperCase);
    }

}
