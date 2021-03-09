package reactive;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.appender.mom.jeromq.JeroMqManager;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class Dictionary {

    static Flowable<String> pushFlowableFromDict() {
        return Flowable.create(emitter -> {
            try (BufferedReader br = getDictionaryAsBufferedReader()) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!emitter.isCancelled()) {
                        emitter.onNext(line);
                        //System.out.println("Emited " + line);
                    }
                }
            } catch (Exception e) {
                if (!emitter.isCancelled()) {
                    emitter.onError(e);
                }
            }
            if (!emitter.isCancelled()) {
                emitter.onComplete();
            }
        }, BackpressureStrategy.DROP);
    }
    static Observable<String> pushFromDict() {
        return Observable.create(emitter -> {

            try (BufferedReader br = getDictionaryAsBufferedReader()) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(line);
                        //System.out.println("Emited " + line);
                    }
                }
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            }
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    static Stream<String> getDictionaryAsStream() throws IOException, URISyntaxException {
        return Files.lines(Path.of("german.dic"), StandardCharsets.ISO_8859_1);
    }

    private static BufferedReader getDictionaryAsBufferedReader()  {
        try {
            InputStream inputStream = Files.newInputStream(Path.of("german.dic"));
            return new BufferedReader(new InputStreamReader(inputStream),1024);
        } catch (IOException e) {
            return null;
        }
    }

    static Flowable<String> pullFromDictionary() {

        return Flowable.generate(
                () -> {
                    try {
                        return getDictionaryAsBufferedReader();
                    } catch (Exception e) {
                        return null;
                    }
                },
                (br, emitter) -> {
                    String line = br.readLine();
                    if (line == null) {
                        emitter.onComplete();
                    } else {
                        emitter.onNext(line);
                    }
                    return br;
                });
    }

    static Iterable<String> getDictionaryAsIterator() {
        return getDictionaryAsBufferedReader().lines().collect(Collectors.toList());
    }

    static void countWords() throws IOException {
        Map<Character, Integer> map = new HashMap();
        try (BufferedReader br = getDictionaryAsBufferedReader()) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    Character character = line.charAt(0);
                    Integer count = map.get(character);
                    if (count == null) {
                        count = 0;
                    }
                    map.put(character, ++count);
                }
            }
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            log.info(entry.getKey() + ": " + entry.getValue());

        }

    }
}
