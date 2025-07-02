# Liberando a Porta 8087 no Firewall do Windows 10

Este README descreve duas formas de liberar a porta TCP 8087 no Firewall do Windows 10 — pela interface gráfica e pelo PowerShell — e como revogar essa permissão.

---

## Índice

1. [Pré-requisitos](#pré-requisitos)
2. [Interface Gráfica](#interface-gráfica)

    - [Criar Regra de Entrada](#criar-regra-de-entrada)
    - [Remover ou Desativar Regra](#remover-ou-desativar-regra)

3. [PowerShell](#powershell)

    - [Criar Regra](#criar-regra-1)
    - [Remover Regra](#remover-regra)

4. [Testando o Acesso](#testando-o-acesso)
5. [Notas de Segurança](#notas-de-segurança)

---

## Pré-requisitos

-   Windows 10 ou superior, com privilégios de Administrador.
-   Para o método PowerShell, abra o PowerShell como Administrador.

---

## Interface Gráfica

### Criar Regra de Entrada

1. Abra o Menu Iniciar e execute "Windows Defender Firewall com Segurança
   Avançada".
2. No painel esquerdo, selecione **Regras de Entrada**.
3. No painel direito, clique em **Nova Regra...**.
4. Em **Tipo de regra**, escolha **Porta** e clique em **Avançar**.
5. Em **Protocolo e portas**:

    - Selecione **TCP**.
    - Marque **Portas locais específicas** e digite `8087`.
    - Clique em **Avançar**.

6. Selecione **Permitir a conexão** e clique em **Avançar**.
7. Marque os perfis desejados (Domínio, Privado, Público) e avance.
8. Dê um nome à regra, ex.: "Spring Boot 8087", e clique em **Concluir**.

> A porta 8087 agora aceitará conexões TCP de outras máquinas na rede.

### Remover ou Desativar Regra

1. No console "Windows Defender Firewall com Segurança Avançada",
   abra **Regras de Entrada**.
2. Encontre a regra "Spring Boot 8087".
3. Clique com o botão direito e escolha:

    - **Desabilitar Regra** para suspender temporariamente.
    - **Excluir** para remover definitivamente.

---

## PowerShell

Abra o PowerShell como Administrador e execute os comandos abaixo.

### Criar Regra

```powershell
New-NetFirewallRule `
  -DisplayName "Spring Boot 8087" `
  -Direction Inbound `
  -Action Allow `
  -Protocol TCP `
  -LocalPort 8087 `
  -Profile Any
```

### Remover Regra

```powershell
Remove-NetFirewallRule `
  -DisplayName "Spring Boot 8087"
```

---

## Testando o Acesso

De outra máquina na mesma rede, abra o navegador ou terminal e acesse:

```
http://<IP_DA_SUA_MAQUINA>:8087
```

Você deverá ver a resposta da sua API ou serviço.

---

## Notas de Segurança

-   Abra portas apenas quando necessário e feche-as após o uso.
-   Prefira perfis restritos (Domínio ou Privado) em redes não confiáveis.
-   Em produção, considere VPN ou proxy reverso com regras granulares.

---

_Gerado em 28 de junho de 2025._
