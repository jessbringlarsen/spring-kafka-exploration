package dk.bringlarsen.springkafkaexploration.consumer;

import dk.bringlarsen.springkafkaexploration.Properties;
import dk.bringlarsen.springkafkaexploration.processor.Quote;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaQuoteConsumerConfig {

    Properties properties;

    @Autowired
    public KafkaQuoteConsumerConfig(Properties properties) {
        this.properties = properties;
    }

    public ConsumerFactory<String, Quote> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getKafkaGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Quote> kafkaQuoteListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Quote>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
