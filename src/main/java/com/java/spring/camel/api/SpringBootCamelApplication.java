package com.java.spring.camel.api;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
public static final String MY_DEFAULT_PROFILE = "myDefaultProfile";
public static final String ACTIVEMQ = "activemq";

@SpringBootApplication
public class SpringBootCamelApplication extends RouteBuilder {

public static void main(String[] args) throws Exception {
SpringApplication.run(SpringBootCamelApplication.class, args);
}
@Override
public void configure() throws Exception{
	//mover el archivo original a Base de datos
	System.out.println("Iniciando transferencia de archivo /home/viviandll/lab/evaluacion..");
	from("file:/home/viviandll/lab/evaluacion").to("file:/home/viviandll/lab/evaluacion");
	System.out.println("Termino de la transferencia a /home/viviandll/lab/evaluacion...");
	
};
	
@Bean
CamelContextConfiguration contextConfiguration(@Value("${activemq.user}") String user,
                                                   @Value("${activemq.password}") String password,
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
