package dk.bringlarsen.springkafkaexploration.consumer;

import dk.bringlarsen.springkafkaexploration.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class QuoteConsumer {

    private final Logger log = LoggerFactory.getLogger(QuoteConsumer.class);

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    final Properties properties;

    @Autowired
    public QuoteConsumer(Properties properties) {
        this.properties = properties;
    }

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }


    @KafkaListener(topics = "#{properties.getQuoteTopic}")
    void onQuote(String quote) {
        log.debug("Created quote={}", quote);
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(quote);
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }
}
