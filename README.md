# Gerenciador de Planilhas

> Aplicação Java para edição, processamento e persistência de planilhas via ZK Spreadsheet CE.

## Índice

- [Sobre](#sobre)
- [Principais Funcionalidades](#principais-funcionalidades)
- [Stack Tecnológica](#stack-tecnológica)
- [Pré-requisitos](#pré-requisitos)
- [Como executar o projeto](#como-executar-o-projeto)
- [Configuração opcional da JVM](#configuração-opcional-da-jvm)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Contribuindo](#contribuindo)
- [Licença](#licença)

## Sobre

Este repositório demonstra como integrar o **ZK Spreadsheet CE** a uma aplicação Spring Boot para ler dados de uma planilha e persistir registros em uma base **H2**. A proposta é servir como referência para projetos que desejam manipular planilhas diretamente no navegador utilizando componentes open source.

## Principais Funcionalidades

- Renderização de planilhas diretamente no front-end ZK.
- Processamento de células selecionadas para entidades de domínio (`Despesa`).
- Persistência em banco relacional via Spring Data JPA.
- Script utilitário para criação de modelos de planilha.

## Stack Tecnológica

- **Java 17 (LTS)**
- **Spring Boot 2.7.x**
- **ZK Framework / ZK Spreadsheet CE**
- **Spring Data JPA + H2 Database**
- **Maven**

## Pré-requisitos

- Java Development Kit 17
- Maven 3.8+
- Git (opcional, para clonar o repositório)

## Como executar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/gerenciador-planilhas.git
   cd gerenciador-planilhas
   ```
2. Compile e execute a aplicação com o Maven Wrapper:
   ```bash
   ./mvnw clean spring-boot:run
   ```
3. Acesse a interface web em `http://localhost:8080`.

> **Observação:** Caso esteja utilizando Windows, substitua `./mvnw` por `mvnw.cmd`.

## Configuração opcional da JVM

Para manter consistência no tratamento de dados localizados (datas, moedas e números), você pode inicializar a JVM com o parâmetro abaixo:

```bash
-Djava.locale.providers=JRE,CLDR
```

## Estrutura do projeto

```
.
├── pom.xml
├── scripts/
│   └── create_modelo.ps1
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   └── webapp/
│   └── test/
└── README.md
```

## Contribuindo

1. Faça um fork do projeto.
2. Crie uma branch para sua feature: `git checkout -b feature/minha-feature`.
3. Commit suas alterações: `git commit -m "feat: minha nova feature"`.
4. Faça push para a branch: `git push origin feature/minha-feature`.
5. Abra um Pull Request descrevendo as mudanças propostas.

Feedbacks, issues e PRs são bem-vindos! Consulte o guia de contribuição se houver.

## Licença

Este projeto está licenciado sob os termos informados no arquivo `LICENSE`. Se o arquivo não existir, defina uma licença compatível com a redistribuição e uso open source antes de publicar.