package redeSocial.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redeSocial.LIBS.TesteLib;

@SpringBootApplication(
		// Somente estes pacotes terão @Component, @Controller, @Service etc.
		scanBasePackages = {
				"redeSocial.GUI",
				"redeSocial.DAOs",
				"redeSocial.Entidade",
				"redeSocial.Config",
				"redeSocial.Main"
		})
@EnableJpaRepositories(
		// Somente neste pacote o Spring Data JPA procura interfaces JpaRepository
		basePackages = "redeSocial.DAOs")
@EntityScan(
		// Somente neste pacote o JPA procura @Entity
		basePackages = "redeSocial.Entidade")
@RestController
public class MainApplication {
	// pra rodar, na pasta projectAPI/BackEnd:
	// vai no arquivo RodarPorTerminal.txt e copia.

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		// run serve pro RestController funcionar no MainAplication.
		System.out.println("Rede Social Backend está rodando! \n em http://localhost:8087");

	}

	// http://localhost:8087 porta 8087
	@GetMapping({ "", "/" })
	public String indexApp() {
		String obrigado = """
				<h1> Index da APIzuda </h1>
				<p>Bem-vindo à API da Rede Social!</p>
				<hr>
				""";

		TesteLib testeLib = new TesteLib();
		obrigado = obrigado + testeLib.mensagemPraMain();
		return obrigado;
	}

	@GetMapping({ "/marcao", "/marcao/" })
	public String marcao(@RequestParam(defaultValue = "Marcão") String name) {
		//
		return String.format("Hello %s!", name);
	}

}

// Configuração CORS DIRETO na classe principal
// @Bean
// public WebMvcConfigurer corsConfigurer() {
// return new WebMvcConfigurer() {
// @Override
// public void addCorsMappings(CorsRegistry registry) {
// registry.addMapping("/**")
// .allowedOrigins(
// "http://localhost:5501", // Live Server padrão
// "http://localhost:5051",
// // Outra porta comum
// "http://127.0.0.1:5501/")
// .allowedMethods("*")
// .allowedHeaders("*")
// .allowCredentials(true);
// }
// };
// }