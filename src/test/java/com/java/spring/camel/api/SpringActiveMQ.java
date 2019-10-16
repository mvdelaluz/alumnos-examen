package com.java.spring.camel.api;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
public static final String MY_DEFAULT_PROFILE = "myDefaultProfile";
public static final String ACTIVEMQ = "activemq";

public class SpringActiveMQ extends RouteBuilder {

public static void main(String[] args) throws Exception {
SpringApplication.run(SpringBootCamelApplication.class, args);
}

@Bean
CamelContextConfiguration contextConfiguration(@Value("${activemq.admin}") String user,
                                                   @Value("${activemq.admin}") String password,
                                                   @Value("${activemq.url}") String url) {
        return new CamelContextConfiguration() {

 @Override
 public void beforeApplicationStart(CamelContext context) {
                // your custom configuration goes here
                ThreadPoolProfile threadPoolProfile = new ThreadPoolProfile();
                threadPoolProfile.setId(MY_DEFAULT_PROFILE);
                threadPoolProfile.setPoolSize(10);
                threadPoolProfile.setMaxPoolSize(15);
                threadPoolProfile.setMaxQueueSize(250);
                threadPoolProfile.setKeepAliveTime(25L);
                threadPoolProfile.setRejectedPolicy(ThreadPoolRejectedPolicy.Abort);
                context.getExecutorServiceManager().registerThreadPoolProfile(threadPoolProfile);
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
                context.addComponent(ACTIVEMQ, jmsComponentAutoAcknowledge(connectionFactory));
            }
        };

}
}
