

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MainRouter extends RouteBuilder {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Override
    public void configure() throws Exception {


        //Producer route
        from("timer://test?period=5000")
        .process(new Processor() {
        @Override
         public void process(Exchange exchange) throws Exception {
            String message = UUID.randomUUID().toString();
            log.info("**********************************");
            log.info("Send message '{}' to queue....", message);
            producerTemplate.sendBody("activemq://test-queue", message);
         }
         });

        //==========================================================================//

        //Consumer queue
        from("activemq://test-queue")
        .process(new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {

           String message = exchange.getIn().getBody(String.class);
           log.info("--------------------------------");
           log.info("Receive message '{}' from queue.", message);
         }
        });

    }
}
