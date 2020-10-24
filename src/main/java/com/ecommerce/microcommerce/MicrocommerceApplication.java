package com.ecommerce.microcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableEurekaClient
// J'ai mis cette annotation en commentaire parce qu'il semble que la dépendance fait
// crasher l'application, ça semble fonctionner une fois, puis ça crashe. J'ai pas eu le
// temps de trouver d'où ça vient.
public class MicrocommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocommerceApplication.class, args);
	}
}
