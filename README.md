# Gerenciador de Planilhas Keikai

Este projeto integra a biblioteca **Keikai (v6.2.0.1)** para manipulação de planilhas web avançadas dentro do ecossistema Java.

## 🛠 Stack Tecnológica & Requisitos
* **Java Runtime:** 17 (LTS)
* **Framework:** Spring Boot 2.7.18
* **Engine de Planilha:** Keikai Spreadsheet 6.2.0.1
* **Persistência:** Spring Data JPA (H2 Database para desenvolvimento)
* **Build System:** Maven

## ⚙️ Configuração do Ambiente (JVM)

Para garantir a consistência no processamento de formatos de data, moeda e números entre diferentes sistemas operacionais, é obrigatório o uso da flag de provedor de localidade.

Adicione os seguintes argumentos na inicialização da sua VM:

```bash
-Djava.locale.providers=JRE,CLDR