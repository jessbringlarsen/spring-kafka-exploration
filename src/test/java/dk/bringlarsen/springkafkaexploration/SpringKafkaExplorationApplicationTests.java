package dk.bringlarsen.springkafkaexploration;

import dk.bringlarsen.springkafkaexploration.producer.QuoteRequestProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(initializers = {SpringKafkaExplorationApplicationTests.Initializer.class})
class SpringKafkaExplorationApplicationTests {

    @Autowired
    QuoteRequestProducer producer;

    @Autowired
    Properties properties;

    @Container
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Test
    void produce() {
        producer.requestQuote();

        ConsumerRecord<?, ?> result = KafkaTestUtils.getOneRecord(kafka.getBootstrapServers(), properties.kafkaGroupId, properties.getQuoteTopic(), 0, true, false, Duration.ofMillis(5000));

        assertNotNull(result.value());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("quote.kafka.bootstrap-servers=" + kafka.getBootstrapServers()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
