package unlu.sip.pga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"unlu.sip.pga.controllers", "unlu.sip.pga.pago"})
public class PgaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgaApplication.class, args);
	}

}
