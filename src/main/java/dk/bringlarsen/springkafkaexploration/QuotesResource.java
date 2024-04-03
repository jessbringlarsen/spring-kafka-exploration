package dk.bringlarsen.springkafkaexploration;

import dk.bringlarsen.springkafkaexploration.consumer.QuoteConsumer;
import dk.bringlarsen.springkafkaexploration.producer.QuoteRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping(value = "/quotes")
public class QuotesResource {

    @Autowired
    QuoteRequestProducer quoteRequestProducer;
    @Autowired
    QuoteConsumer quoteConsumer;

    @PostMapping(value = "/request", produces = MediaType.TEXT_PLAIN_VALUE)
    public String requestQuote() {
        return quoteRequestProducer.requestQuote();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getQuotes() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        quoteConsumer.addEmitter(emitter);
        return emitter;
    }
}
