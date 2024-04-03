package dk.bringlarsen.springkafkaexploration.processor;

import dk.bringlarsen.springkafkaexploration.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class QuoteProcessor {

    private final KafkaTemplate<String, Quote> kafkaQuoteProducerTemplate;

    final Properties properties;

    @Autowired
    public QuoteProcessor(KafkaTemplate<String, Quote> kafkaQuoteProducerTemplate, Properties properties) {
        this.kafkaQuoteProducerTemplate = kafkaQuoteProducerTemplate;
        this.properties = properties;
    }

    @KafkaListener(topics = "#{properties.getQuoteRequestTopic}")
    void onQuoteRequest(String quoteId) {
        kafkaQuoteProducerTemplate.send(properties.getQuoteTopic(), new Quote(quoteId, new Random().nextInt(10, 100 )));
    }
}
