package com.capgemini.asynchronous;

import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.Executors;

@ApplicationScoped
@RouteBase(path = "alternatives", produces = "text/plain")
@Slf4j
public class VertxRoutes {

    @Route(methods = HttpMethod.GET, path = "thread")
    public Uni<String> blockingInThread() throws InterruptedException {
        return Uni.createFrom().item(() -> {

            log.info("Starting Request ");
            try {
                Thread.sleep(10000); // Sleep for 10 secs (2 secs is the limit)
            } catch (InterruptedException e) {
                return "Error";
            }
            log.info("Stopping Request");
            return " Finally did it";
        }).runSubscriptionOn(Executors.newCachedThreadPool());
    }

    @Inject
    EventBus eventBus;

    @Route(methods = HttpMethod.GET, path = "event/:name")
    public void blockingInEvent(@Param("name") String name) throws InterruptedException {
        log.info("Start Request");
         eventBus.sendAndForget("greeting", name);
    }
}
