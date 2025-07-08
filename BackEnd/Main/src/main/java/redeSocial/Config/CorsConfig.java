package redeSocial.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS para todas as rotas
                .allowedOrigins(
                        "http://localhost:5501", // Live Server padrão
                        "http://localhost:5051", // Caso use outra porta
                        "http://127.0.0.1:5501/",
                        "http://104.28.196.103:8087/",
                        "https://rede-social-marcao.netlify.app/") // URL do seu frontend (Live Server)
                                                                   // traceroute
                .allowedMethods("*") // Permite todos os métodos (GET, POST, PUT, etc.)
                .allowedHeaders("*"); // Permite todos os headers (opcional)
    }
}