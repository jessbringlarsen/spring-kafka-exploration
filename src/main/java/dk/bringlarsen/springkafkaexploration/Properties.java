package dk.bringlarsen.springkafkaexploration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Properties {

    @Value("${quote.kafka.bootstrap-servers}")
    String bootstrapServer;

    @Value("${quote.kafka.group-id}")
    String kafkaGroupId;

    @Value("${quote.topic.request}")
    String quoteRequestTopic;

    @Value("${quote.topic}")
    String quoteTopic;

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getKafkaGroupId() {
        return kafkaGroupId;
    }

    public String getQuoteRequestTopic() {
        return quoteRequestTopic;
    }

    public String getQuoteTopic() {
        return quoteTopic;
    }
}
