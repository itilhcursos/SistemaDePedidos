# Sistema de Pedidos

Este projeto é um Sistema de Pedidos que permite a gestão de produtos e pedidos. Ele é composto por um frontend desenvolvido com Vue.js e um backend em Java, utilizando Spring Boot para a lógica de negócios e comunicação com o banco de dados.

## Estrutura do Projeto

- **Frontend**: Construído com Vue.js, o frontend permite que os usuários interajam com o sistema de pedidos de forma amigável, gerenciando os produtos, clientes e pedidos.
- **Backend**: Implementado em Java com Spring Boot, o backend fornece as APIs necessárias para gerenciar os dados do sistema, incluindo operações de CRUD para produtos, clientes e pedidos.
- **Banco de Dados**: A pasta `bd` contém scripts relacionados à criação e gerenciamento do banco de dados.

## Funcionalidades Principais

- Cadastro de produtos
- Cadastro de clientes
- Gestão de pedidos
- Integração entre frontend e backend via APIs RESTful

## Pré-requisitos

- Node.js (para rodar o frontend)
- Java 11+ (para rodar o backend)
- Maven (para gerenciar dependências do backend)
- MySQL ou outro banco de dados compatível

## Como Executar

### Backend
1. Navegue até a pasta `backend`.
2. Configure o arquivo application poriperties  para configurar a conexão com o banco de dados,no seguinte caminho:SistemaDePedidos\SistemaDePedidos\backend\src\main\resources\application.properties
3. Compile e execute a aplicação. Após verivicar a mensagem "Started SistemapedidosApplication"

### Frontend
1. Abra o Arquivo Frontend no seu editor de texto
2. no terminal execute o seguinte comando: npm install
3. Após instalar o npm (Node Package Manager) que é um gerenciador de pacote, execute o seguinte comando: npm run dev
4. Após isso basta clicar no link gerado no terminal. Lembrando que ele vai abrir a aba em seu navegador
