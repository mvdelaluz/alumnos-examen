package com.java.spring.camel.api;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCamelApplication extends RouteBuilder {

public static void main(String[] args) throws Exception {
SpringApplication.run(SpringBootCamelApplication.class, args);

@Override
public void configure() throws Exception{
	//mover el archivo original a Base de datos
	System.out.println("Iniciando transferencia de archivo /home/viviandll/lab/evaluacion..");
	from("file:/home/viviandll/lab/evaluacion").to("file:/home/viviandll/lab/evaluacion");
	System.out.println("Termino de la transferencia a /home/viviandll/lab/evaluacion...");
	
};
	
}

}
