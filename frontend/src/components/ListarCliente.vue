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
    <div v-if="!formVisible" class="pagination-controls mt-4">
      <div class="row d-flex justify-content-center">
        <div class="col-auto">
          <button
            v-for="pagina in totalPages"
            :key="pagina"
            @click.prevent="irPara(pagina)"
            class="btn btn-light ms-1"
          >
            {{ pagina }}
          </button>
        </div>
        <div class="col-auto">
          <input
            type="text"
            v-model="pageNumber"
            placeholder="Número da página"
            class="form-control w-25"
          />
        </div>
        <div class="col-auto">
          <select v-model="pageSize" class="form-select">
            <option value="2">2</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="property" class="form-select">
            <option value="id">ID</option>
            <option value="nomeRazaoSocial">Nome/Razão Social</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscarClientes" class="btn btn-success">
            <i class="bi bi-binoculars"></i> Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import clienteService from "@/services/clienteService";
import FormCliente from "./FormCliente.vue";

export default {
  components: {
    FormCliente,
  },
  data() {
    return {
      listaClientes: [],
      clienteEscolhido: null,
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

      // Chamada do serviço para buscar clientes com paginação
      const response = await clienteService.listar(this.pageNumber, this.pageSize, this.direction, this.property);
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
    alterarCliente(cliente) {
      this.clienteEscolhido = cliente;
      this.formVisible = true;
    },
    async excluirCliente(id) {
      const confirmacao = confirm("Tem certeza que deseja excluir este cliente?");
      if (confirmacao) {
        await clienteService.apagar(id);
        this.buscarClientes();
      }
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarClientes();
    },
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

/* Estilo dos cartões */
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

.pagination-controls {
  margin-top: 20px;
}

.pagination-controls .form-control,
.pagination-controls .form-select {
  background-color: #444;
  color: #fbfbfb;
  border: 1px solid #252323;
}
</style>
