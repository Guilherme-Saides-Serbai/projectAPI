### Passo a Passo para Descobrir seu IP e Configurar Acesso Remoto

---

#### **Passo 1: Descobrir seu IP Público**

1. **Windows:**

    - Abra o Prompt de Comando (cmd)
    - Digite:
        ```bash
        curl ifconfig.me
        ```
    - Ou use:
        ```bash
        nslookup myip.opendns.com resolver1.opendns.com
        ```

2. **Linux/macOS:**

    ```bash
    curl ifconfig.me
    ```

    ```bash
    dig +short myip.opendns.com @resolver1.opendns.com
    ```

3. **Alternativa visual:**
    - Acesse [whatismyip.com](https://whatismyip.com)
    - Anote seu IPv4 (ex: `189.145.87.221`)

---

#### **Passo 2: Configurar o Spring Boot para IP Remoto**

Atualize `CorsConfig.java`:

```java
package redeSocial.Main;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:5501",
                "http://localhost:5051",
                "http://127.0.0.1:5501",
                "http://SEU_IP_PUBLICO:5501", // Adicione seu IP aqui
                "https://seu-frontend.com"     // Domínios que você controla
            )
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

Substitua `SEU_IP_PUBLICO` pelo IP que você descobriu no Passo 1.

---

#### **Passo 3: Permitir Conexões Externas no Spring Boot**

No `application.properties`:

```properties
# Permitir conexões de qualquer IP
server.address=0.0.0.0
```

Isso faz com que o Spring aceite conexões de qualquer rede (não apenas localhost).

---

#### **Passo 4: Configurar o Frontend**

Altere sua chamada fetch para usar seu IP público:

```javascript
// Substitua pelo seu IP público
const API_URL = "http://SEU_IP_PUBLICO:8087/users/";

async function getUsers() {
    try {
        const response = await fetch(API_URL);
        const data = await response.json();
        console.log(data);
    } catch (error) {
        console.error("Erro:", error);
    }
}
```

---

#### **Passo 5: Liberar Porta no Firewall (Crucial!)**

1. **Windows:**

    - Acesse: `Painel de Controle > Sistema e Segurança > Firewall do Windows`
    - Clique em "Permitir um aplicativo pelo firewall"
    - Libere a porta 8087 para redes públicas/privadas

2. **Linux (Ubuntu):**

    ```bash
    sudo ufw allow 8087/tcp
    ```

3. **Roteador (Port Forwarding):**
    - Acesse o painel do roteador (geralmente 192.168.1.1)
    - Procure por "Port Forwarding"
    - Mapeie:
        ```
        Porta Externa: 8087 → Porta Interna: 8087
        IP Interno: [IP local da sua máquina]
        Protocolo: TCP/UDP
        ```

---

#### **Passo 6: Testar Acesso Remoto**

1. No seu celular (fora do Wi-Fi doméstico):
    ```javascript
     http://SEU_IP_PUBLICO:8087/
    ```
2. Ou use outro computador na mesma rede:
    ```bash
    curl http://SEU_IP_PUBLICO:8087/users/
    ```

---

### ⚠️ Importante: Segurança Básica

1. **Não use `allowedOrigins("*")` em produção!**
2. Atualize seu IP dinamicamente (se não for fixo):
    - Use serviços como [DuckDNS](https://www.duckdns.org/)
3. Para produção real:
    - Use domínio próprio
    - Configure HTTPS
    - Adicione autenticação

---

### Resumo Final:

1. Descobrir IP: `curl ifconfig.me`
2. Configurar Spring: `allowedOrigins("http://SEU_IP:5501")`
3. Habilitar rede: `server.address=0.0.0.0`
4. Liberar porta: Firewall + Roteador
5. Frontend: `fetch("http://SEU_IP:8087/users/")`

Seu API agora está acessível remotamente! 🌐

My IPv4:
104.28.196.103

My IPv6:
2a09:bac5:a69:79b::c2:12

City:
Londrina

State/Region:
Parana

Postal Code:
86200-000

Country:
Brazil

ISP:
CloudFlare Inc.

ASN:
13335

Time Zone:
UTC -03:00

curl http://104.28.196.103:8087/
curl: (28) Failed to connect to 104.28.196.103 port 8087 after 21041 ms: Couldn't connect to server

não esquecer de BLOQUEAR A PORTA 8087 DEPOIS COM SEU FIREWALL.

104.28.163.204 ip da 1.1.1.1 cloudflare.

191.250.125.193 ip da pensao zuda.
