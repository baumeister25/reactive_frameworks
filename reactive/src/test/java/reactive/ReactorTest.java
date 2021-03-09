package reactive;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.stream.BaseStream;

@Log4j2
class ReactorTest {

    @Test
    public void simpleMono() {
        Mono<String> mono = Mono.just("Hello World");
        mono.subscribe(log::info);
    }

    @Test
    public void simpleFlux() {
        Flux<String> flux = Flux.just("1", "2", "3", "4");
        flux.subscribe(log::info);
    }

    @Test
    public void dictionaryAsFlux() throws InterruptedException {
        Flux<String> dictionary = Flux.defer(() -> Flux.fromIterable(Dictionary.getDictionaryAsIterator()));
        dictionary.log().subscribeOn(Schedulers.boundedElastic()).subscribe(log::info);
        Thread.sleep(2000);
    }

    /**
     * Counts ne number of words starting with a specific letter
     */
    @Test
    public void countWords() throws InterruptedException {
        Flux<String> stringFlux = Flux.using(Dictionary::getDictionaryAsStream, (stream) -> Flux.defer(() -> Flux.fromStream(stream)), BaseStream::close);
        stringFlux
                .filter(s -> s.length() > 1)
                .map(String::toUpperCase)
                .groupBy(s -> s.charAt(0))
                .flatMap(group -> Mono.just(group.key()).zipWith(group.count()))
                .subscribe(tuple -> log.info(tuple.getT1() + ": " + tuple.getT2()));
    }
}
