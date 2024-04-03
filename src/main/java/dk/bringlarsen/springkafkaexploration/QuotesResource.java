package dk.bringlarsen.springkafkaexploration;

import dk.bringlarsen.springkafkaexploration.producer.QuoteRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/quotes")
public class QuotesResource {

    @Autowired
    QuoteRequestProducer quoteRequestProducer;

    @PostMapping(value = "/request", produces = MediaType.TEXT_PLAIN_VALUE)
    public String requestQuote() {
        return quoteRequestProducer.requestQuote();
    }
}
