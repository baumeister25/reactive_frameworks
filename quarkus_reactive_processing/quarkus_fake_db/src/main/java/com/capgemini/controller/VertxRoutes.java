package com.capgemini.controller;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.Executors;

@ApplicationScoped
@RouteBase(path = "vertx", produces = "application/json")
@Slf4j
public class VertxRoutes {

    @Route(methods = HttpMethod.GET, path = "greet")
    public Uni<String> getGreeting() {
        return Uni.createFrom().item("Hello").onItem().call((s) -> {
            log.info(s);
            try {
                Thread.sleep(20_000);
            } catch (InterruptedException e) {
                return Uni.createFrom().failure(e);
            }
            return Uni.createFrom().item(s);
        })
                .runSubscriptionOn(Executors.newCachedThreadPool())
        ;
    }
}
