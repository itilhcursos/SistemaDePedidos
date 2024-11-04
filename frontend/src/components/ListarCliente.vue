<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>CLIENTES</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoCliente" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
          <FormCliente
            v-if="formVisible"
            :propsCliente="clienteEscolhido"
            @cancelar="limpar"
            @salvar="buscarClientes"
          />
        </div>
      </div>
    </div>

    <!-- Cartões de Cliente -->
    <div v-if="!formVisible" class="row">
      <div
        class="col-md-4"
        v-for="cliente in listaClientes"
        :key="cliente.id"
      >
        <div class="card mb-4 shadow-sm">
          <div class="card-body">
            <h5 class="card-title">{{ cliente.nomeRazaoSocial }}</h5>
            <p class="card-text"><strong>ID:</strong> {{ cliente.id }}</p>
            <p class="card-text"><strong>CNPJ:</strong> {{ cliente.cnpj }}</p>
            <p class="card-text"><strong>CPF:</strong> {{ cliente.cpf }}</p>
            <p class="card-text"><strong>Telefone:</strong> {{ cliente.telefone }}</p>
            <p class="card-text"><strong>Endereço:</strong> {{ cliente.endereco }}</p>
            <p class="card-text"><strong>Bairro:</strong> {{ cliente.bairro }}</p>
            <p class="card-text"><strong>Município:</strong> {{ cliente.municipioNome }}</p>
            <div class="d-flex justify-content-end">
              <button class="btn btn-primary me-2" @click="alterarCliente(cliente)">
                <i class="bi bi-clipboard-pulse"></i> Alterar
              </button>
              <button class="btn btn-outline-danger" @click="excluirCliente(cliente.id)">
                <i class="bi bi-clipboard2-minus"></i> Excluir
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Paginação e Filtros -->
    <div v-if="!formVisible">
      <hr />
      <div class="container">
        <div class="row d-flex justify-content-center">
          <!-- Botões e filtros aqui como antes -->
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import clienteService from "@/services/clienteService";
import FormCliente from "./FormCliente.vue";
import Logico from "@/utils/Logico.js";
import Monetario from "@/utils/Monetario.js";
import Decimal from "@/utils/Decimal.js";
export default {
  components: {
      FormCliente,
  },
  data() {
    return {
      listaClientes: [],
      formVisible: false,
      pageNumber: 1,
      pageSize: 10,
      direction: "ASC",
      property: "id",
      totalPages: 0,
    };
  },
  methods: {
    async buscarClientes() {
        this.clienteEscolhido = null;
        this.formVisible = false;
      
        const response = await clienteService.listar(this.pageNumber, this.pageSize, this. direction, this.property);
        
        this.listaClientes = response.content;
        this.totalPages = response.totalPages;
      },
      limpar() {
        this.clienteEscolhido = null;
        this.formVisible = !this.formVisible;
      },
      novoCliente() {
        this.formVisible = !this.formVisible;
      },
      alterarCliente(cliente){
        this.clienteEscolhido = cliente;
        this.formVisible = true;
      },
      async excluirCliente(id) {
        const response = await clienteService.apagar(id);
        console.log(response.data);
        this.buscarClientes();
      },
      irPara(pagina) {
        this.pageNumber = pagina;
        this.buscarClientes();
      },
      formatarLogico(valor){
        return Logico.toSimNao(valor);
      },
      formatarPreco(valor){
        return Monetario.toTela(valor);
      },
      formatarQuantidade(valor){
        return Decimal.toTela(valor);
      }
    },

    mounted() {
      this.buscarClientes();
    },
  };
  </script>


<style scoped>
.container {
  margin-top: 20px;
}

h3 {
  font-weight: bold;
  color: #2c3e50;
}

/* Cartões de cliente */
.card {
  background-color: #312d2d;
  border: 1px solid #060606;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-title {
  font-size: 1.25rem;
  color: #fefdfd;
  font-weight: 600;
}

.card-text {
  color: #bab5b5;
  margin-bottom: 5px;
}

.card .d-flex {
  margin-top: 15px;
}

/* Botões */
button.btn {
  font-size: 0.9rem;
  padding: 8px 15px;
}

.btn-success {
  background-color: #28a745;
  color: #fff;
  border: none;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
  border: none;
}

.btn-outline-danger {
  color: #dc3545;
  border: 1px solid #dc3545;
}

/* Estilo para Paginação e Filtros */
.pagination-container {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
}

.pagination-container .btn-light {
  color: #333;
  border: 1px solid #f4ebeb;
}

.pagination-container .form-select,
.pagination-container .form-control {
  width: 100px;
}
</style>


