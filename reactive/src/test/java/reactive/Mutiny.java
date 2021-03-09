package reactive;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Log4j2
public class Mutiny {
    @Test
    public void simpleUni() throws InterruptedException {
        Uni<String> uni = Uni.createFrom().item("Hello")
                .onItem().transform(s -> s + " World");
        uni.subscribe().with(log::info);
        Thread.sleep(20000);
    }

    @Test
    public void simpleMulti() throws InterruptedException {
        Multi<String> mono = Multi.createFrom().items("1", "2", "3", "4");
        mono.onItem().transform(s -> s + " Multi");
        mono.subscribe().with(log::info);
        Thread.sleep(20000);
    }

    @Test
    public void overflowMulti() throws InterruptedException {
        Multi<Long> ticks = Multi.createFrom().ticks().every(Duration.ofMillis(10))
                .emitOn(Infrastructure.getDefaultExecutor());
        ticks.onItem().invoke((l) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Interrupted Thread sleep", e);
            }
        }).subscribe().with(log::info, e -> log.error("Error", e));
        Thread.sleep(20000);
    }

    @Test
    public void backPressureMulti() throws InterruptedException {
        Multi<Long> ticks = Multi.createFrom().ticks().every(Duration.ofMillis(10))
                .onOverflow().drop()
                .emitOn(Infrastructure.getDefaultExecutor());
        ticks.onItem().invoke((l) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Interrupted Thread sleep", e);
            }
        }).subscribe().with(log::info);
        Thread.sleep(20000);
    }

    @Test
    public void combineMutlis() throws InterruptedException {
        Multi<Long> ticks = Multi.createFrom().ticks().every(Duration.ofSeconds(10))
                .emitOn(Infrastructure.getDefaultExecutor());

        Multi<String> someString = Multi.createFrom().items("NULL", "EINS", "ZWEI", "DREI", "VIER", "FUENF");

        Multi.createBy().combining().streams(ticks, someString).using((l, s) -> l + ": " + s).subscribe().with(log::info);
        Thread.sleep(20000);
    }
}

