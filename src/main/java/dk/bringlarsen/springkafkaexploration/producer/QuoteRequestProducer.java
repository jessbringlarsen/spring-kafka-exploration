package dk.bringlarsen.springkafkaexploration.producer;

import dk.bringlarsen.springkafkaexploration.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QuoteRequestProducer {

    private final Properties properties;
    private final KafkaTemplate<String, String> quoteRequestProducerTemplate;

    @Autowired
    public QuoteRequestProducer(Properties properties, KafkaTemplate<String, String> quoteRequestProducerTemplate) {
        this.properties = properties;
        this.quoteRequestProducerTemplate = quoteRequestProducerTemplate;
    }

    public void requestQuote() {
        quoteRequestProducerTemplate.send(properties.getQuoteRequestTopic(), UUID.randomUUID().toString());
    }
}
