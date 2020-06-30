package br.me.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AppWebContaneirizadaApplication {

	@GetMapping("/")
	public String home() {
		return "<center><h1>Bem vindo!</h1> <br><br><br><b>Hoje, " + new SimpleDateFormat("dd/mm/yyyy hh:mm:ss").format(new Date());
	}

	public static void main(String[] args) {
		SpringApplication.run(AppWebContaneirizadaApplication.class, args);
	}

}
