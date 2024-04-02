package dk.bringlarsen.springkafkaexploration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Properties {

    @Value("${quote.kafka.bootstrap-servers}")
    String bootstrapServer;

    @Value("${quote.topic.request}")
    String quoteRequestTopic;

    @Value("${quote.topic.reply}")
    String quoteReplyTopic;

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getQuoteRequestTopic() {
        return quoteRequestTopic;
    }

    public String getQuoteReplyTopic() {
        return quoteReplyTopic;
    }
}
