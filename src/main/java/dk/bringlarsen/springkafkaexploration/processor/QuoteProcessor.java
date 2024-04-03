package dk.bringlarsen.springkafkaexploration.processor;

import dk.bringlarsen.springkafkaexploration.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Component
public class QuoteProcessor {

    private final Logger log = LoggerFactory.getLogger(QuoteProcessor.class);
    private final KafkaTemplate<String, Quote> kafkaQuoteProducerTemplate;

    final Properties properties;

    @Autowired
    public QuoteProcessor(KafkaTemplate<String, Quote> kafkaQuoteProducerTemplate, Properties properties) {
        this.kafkaQuoteProducerTemplate = kafkaQuoteProducerTemplate;
        this.properties = properties;
    }


    @KafkaListener(topics = "#{properties.getQuoteRequestTopic}")
    void onMessage(String quoteId) {
        CompletableFuture<SendResult<String, Quote>> quote = kafkaQuoteProducerTemplate.send(properties.getQuoteTopic(), new Quote(quoteId, new Random().nextInt(10, 100 )));
        quote.thenAccept(result -> {
            Quote value = result.getProducerRecord().value();
            log.debug("Created quote with id={} and price={}", value.getId(), value.getPrice());
        });
    }
}
