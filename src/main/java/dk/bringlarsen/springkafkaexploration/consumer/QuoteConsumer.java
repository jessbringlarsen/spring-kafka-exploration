package dk.bringlarsen.springkafkaexploration.consumer;

import dk.bringlarsen.springkafkaexploration.Properties;
import dk.bringlarsen.springkafkaexploration.processor.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class QuoteConsumer {

    private final Logger log = LoggerFactory.getLogger(QuoteConsumer.class);
    final Properties properties;

    @Autowired
    public QuoteConsumer(Properties properties) {
        this.properties = properties;
    }

    @KafkaListener(topics = "#{properties.getQuoteTopic}")
    void onQuote(String quote) {
        log.debug("Created quote={}", quote);
    }
}
