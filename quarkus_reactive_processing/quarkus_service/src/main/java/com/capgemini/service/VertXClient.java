package com.capgemini.service;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
public class VertXClient {
    @Inject
    Vertx vertx;

    private WebClient client;

    @PostConstruct
    void initialize() {
        this.client = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("db")
                        .setDefaultPort(8080).setSsl(false).setTrustAll(true).setMaxPoolSize(200));
    }

    public Uni<Object> getGreet() {
        log.info("Querying data");
        return client.get("/vertx/greet")
                .send()
                .onItem().transform(resp -> {
            if (resp.statusCode() == 200) {
                return resp.bodyAsString();
            } else {
                log.error("error");
                return
                                 resp.bodyAsString();
            }
        });
    }
}
