package com.capgemini.asynchronous;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EventHandler {
    @ConsumeEvent(value = "greeting")
    @Blocking
    public void getEvent(String name) {
        try {
            Thread.sleep(10000); // Sleep for 10 secs (2 secs is the limit)
        } catch (InterruptedException e) {
            log.info("Error");
        }
        log.info("Hello " + name);
    }
}
