# Desafio Sicredi - API de Contratação de Crédito

API desenvolvida em Java com Spring Boot para contratação e consulta de operações de crédito.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- Lombok

---

## Como Executar

### Clonar o repositório

```bash
git clone <url-do-repositorio>
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

**GET** `/operacoes-credito/1`

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
- Tratamento de indisponibilidade do serviço externo de produtos.
- Consulta de operação por identificador.

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

## Considerações

Conforme especificado no enunciado:

- Os dados obrigatórios são assumidos como sempre presentes.
- Os dados enviados pelo consumidor são assumidos como válidos.
- Foram implementadas apenas as validações de negócio exigidas pelo desafio.