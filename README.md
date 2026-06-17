# Desafio Sicredi - API de Contratação de Crédito

API desenvolvida em Java com Spring Boot para contratação e consulta de operações de crédito.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- H2 Database
- OpenAPI / Swagger
- Resilience4j
- JUnit 5
- Mockito
- Maven
- Lombok

---

## Como Executar

### Clonar o repositório

```bash
git clone https://github.com/hellen365/desafio-sicred.git
```

### Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação será iniciada em:

```text
http://localhost:8080
```

---

## Documentação da API

A documentação da API está disponível através do Swagger:

### Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI JSON

```text
http://localhost:8080/v3/api-docs
```

---

## Endpoints

### Contratar Operação de Crédito

**POST** `/operacoes-credito`

#### Exemplo PF

```json
{
  "idAssociado": 12345,
  "valorOperacao": 3000,
  "segmento": "PF",
  "codigoProdutoCredito": "101A",
  "codigoConta": "1234567890"
}
```

#### Exemplo PJ

```json
{
  "idAssociado": 12345,
  "valorOperacao": 50000,
  "segmento": "PJ",
  "codigoProdutoCredito": "505E",
  "codigoConta": "1234567890"
}
```

#### Exemplo AGRO

```json
{
  "idAssociado": 12345,
  "valorOperacao": 8000,
  "segmento": "AGRO",
  "codigoProdutoCredito": "903C",
  "codigoConta": "1234567890",
  "areaBeneficiadaHa": 150
}
```

#### Resposta

```json
{
  "idOperacaoCredito": 1
}
```

---

### Consultar Operação de Crédito

**GET** `/operacoes-credito/{idOperacaoCredito}`

#### Exemplo

```http
GET /operacoes-credito/1
```

#### Resposta

```json
{
  "idOperacaoCredito": 1,
  "idAssociado": 12345,
  "valorOperacao": 8000,
  "segmento": "AGRO",
  "codigoProdutoCredito": "903C",
  "codigoConta": "1234567890",
  "areaBeneficiadaHa": 150,
  "dataHoraContratacao": "2026-06-16T10:30:15"
}
```

---

## Regras de Negócio Implementadas

- Consulta ao serviço externo de produtos para validação da contratação.
- Operações do segmento **AGRO** exigem `areaBeneficiadaHa` preenchida e maior que zero.
- Operações do segmento **PJ** geram um registro adicional de vínculo entre operação e associado.
- Persistência da data e hora da contratação.
- Geração automática do identificador da operação de crédito.
- Consulta de operação por identificador.

---

## Resiliência

A integração com o serviço externo de produtos possui mecanismo de resiliência utilizando **Resilience4j**.

### Comportamento implementado

- Retry automático em falhas temporárias.
- Tratamento de indisponibilidade do serviço externo.
- Fallback para resposta controlada pela aplicação.

### Exemplo de erro

```json
{
  "mensagem": "Serviço de produtos indisponível"
}
```

Status HTTP:

```text
503 Service Unavailable
```

---

## Testes

Foram implementados testes unitários para validação das principais regras de negócio:

- Contratação de operação com sucesso.
- Validação de contratação de produto.
- Validação das regras específicas por segmento.
- Consulta de operação por identificador.
- Tratamento de operações não encontradas.
- Tratamento de indisponibilidade do serviço externo.

Para executar os testes:

```bash
mvn test
```

---

## Banco de Dados

Foi utilizado o banco de dados em memória **H2** para simplificar a execução do projeto.

### Console H2

```text
http://localhost:8080/h2-console
```

Configurações:

```text
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:
```

---

## Arquitetura

A solução utiliza o padrão **Strategy** para encapsular regras específicas de cada segmento de crédito (**PF**, **PJ** e **AGRO**), reduzindo acoplamento na camada de serviço e facilitando a evolução da aplicação para novos segmentos.

---

## Considerações

Conforme especificado no enunciado:

- Os dados obrigatórios são assumidos como sempre presentes.
- Os dados enviados pelo consumidor são assumidos como válidos.
- Foram implementadas apenas as validações de negócio exigidas pelo desafio.
- O serviço externo de produtos é tratado como fonte oficial para validação da contratação.