package reactive;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public class RxJava {

    @Test
    public void simpleExample() {
        Observable<String> observable = Observable.just("Hello World");
        observable.subscribe(log::info);
    }

    @Test
    public void zipToStreams() throws InterruptedException {
        Observable<Integer> integerObservable = Observable.range(1, 1_000_000);
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);

        Observable.zip(integerObservable, interval, (obs, timer) -> obs).subscribe(log::info);
        Thread.sleep(20_000);
    }

    /**
     * Counts all lines in the german dictionary
     */
    @Test
    public void countAllLines() throws InterruptedException {
        log.info(ZonedDateTime.now());
        AtomicLong count = new AtomicLong(0L);
        Dictionary.pushFromDict()
                .observeOn(Schedulers.newThread())
                .doOnError(e -> log.error("Error", e))
                .map((s) -> s.toUpperCase(Locale.ROOT))
                .count()
                .subscribe(
                        (s) -> {
                            count.set(s);
                            log.info(s);
                            log.info(ZonedDateTime.now());
                        },
                        (e) -> log.error("Error ", e));
        Thread.sleep(20_000);
        Assertions.assertEquals(16_759_184L, count.get());
    }

    /**
     * The Buffered Reader Stores the file + The buffer Used by the observable.
     * Because of the delay the buffers fill rapidly and throw and OutOfMemory.
     * <p>
     * - Observable uses Buffer as default BackPressure Strategie
     */
    @Test
    public void overflowOutOfMemory() throws InterruptedException {
        log.info(ZonedDateTime.now());
        Assertions.assertThrows(OutOfMemoryError.class, () -> Dictionary.pushFromDict()
                .observeOn(Schedulers.newThread())
                .doOnError(e -> log.error("Error", e))
                .map((s) -> s.toUpperCase(Locale.ROOT))
                .delay(2, TimeUnit.SECONDS)
                .count()
                .subscribe(
                        (s) -> {
                            log.info(s);
                            log.info(ZonedDateTime.now());
                        },
                        (e) -> log.error("Error ", e)));
        Thread.sleep(20_000);
    }


    /**
     * Uses the Drop Strategy to Drop Items that cannot be handled
     * The Number of read lines is smaller then the complete number of lines.
     */
    @Test
    public void pushWithBackPressureDrop() throws InterruptedException {
        log.info(ZonedDateTime.now());
        AtomicLong count = new AtomicLong(0L);
        Dictionary.pushFlowableFromDict()
                .observeOn(Schedulers.newThread())
                .map((s) -> s.toUpperCase(Locale.ROOT))
                .delay(2, TimeUnit.SECONDS)
                .count()
                .subscribe(
                        (s) -> {
                            count.set(s);
                            log.info(s);
                            log.info(ZonedDateTime.now());
                        }
                        , (e) -> log.error("Error ", e));
        Thread.sleep(10_000);
        // The actual read number is smaller than the full number of words
        Assertions.assertTrue(Long.compare(16_759_184L, count.get()) > 0);
    }

    /**
     * In this scenarious are pulled from a generated Flowable instead of pushed.
     * Therefore the time for execution takes much longer, but at the end independently of delay all events can be read.
     */
    @Test
    public void pullFromDict() throws InterruptedException {
        log.info(ZonedDateTime.now());
        AtomicLong count = new AtomicLong(0L);
        Dictionary.pullFromDictionary()
                .observeOn(Schedulers.newThread())
                .map((s) -> s.toUpperCase(Locale.ROOT))
                .delay(2, TimeUnit.SECONDS)
                .count()
                .blockingSubscribe(
                        (s) -> {
                            count.set(s);
                            log.info(s);
                            log.info(ZonedDateTime.now());
                        }
                        , (e) -> log.error("Error ", e));
        Assertions.assertEquals(16_759_184L, count.get());
    }

}
