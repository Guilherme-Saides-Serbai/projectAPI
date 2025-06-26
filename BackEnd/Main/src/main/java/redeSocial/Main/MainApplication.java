package redeSocial.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainApplication {
	/*
	
	// pra rodar, na pasta projectAPI/BackEnd:
	
	/usr/bin/env C:\\Program\ Files\\Java\\jdk-21\\bin\\java.exe @C:\\Users\\Usuario\\AppData\\Local\\Temp\\cp_2rtcsoimgwcfjq2jx8b4igkht.argfile  redeSocial.Main.MainApplication
	
	*/

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		System.out.println("Rede Social Backend is correndo!");
		System.out.println("Acesse http://localhost:8087 para interagir com a API.");
	}

	// http://localhost:8087 porta 8087
	@GetMapping("/")
	public String indexApp() {
		return """
				<h1> Index da APIzuda </h1>
				<p>Bem-vindo à API da Rede Social!</p>
				<hr>
				""";
	}

	@GetMapping("/marcao/")
	public String marcao(@RequestParam(defaultValue = "Marcão") String name) {
		//
		return String.format("Hello %s!", name);
	}

}
