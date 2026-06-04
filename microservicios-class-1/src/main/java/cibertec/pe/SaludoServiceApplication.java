package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SaludoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaludoServiceApplication.class, args);
		System.out.println("Proyecto Saludo Inizializado");
	}

}
