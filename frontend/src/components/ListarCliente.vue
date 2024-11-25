<template>
  <div class="container bg-dark text-white py-3">
    <div class="row">
      <div class="col-10">
        <h3>Clientes</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoCliente" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
    </div>

    <!-- Formulário de Cliente -->
    <div class="row" v-if="formVisible">
      <FormCliente
        :propsCliente="clienteEscolhido"
        @cancelar="limpar"
        @salvar_cliente="buscarClientes"
      />
    </div>

    <!-- Exibição dos clientes em formato de cartões -->
    <div class="row mt-3" v-if="!formVisible">
      <div class="col-md-6 col-lg-4" v-for="cliente in listaClientes" :key="cliente.id">
        <div class="card cliente-card mb-3">
          <div class="card-header d-flex justify-content-between align-items-center">
            <span>#{{ cliente.id }} - {{ cliente.nomeRazaoSocial }}</span>
            <button class="btn btn-sm btn-danger" @click="excluirCliente(cliente.id)">
              <i class="bi bi-trash"></i>
            </button>
          </div>
          <div class="card-body">
            <h5 class="card-title">CNPJ/CPF: {{ cliente.cnpj || cliente.cpf }}</h5>
            <p class="card-text">
              <strong>Telefone:</strong> {{ cliente.telefone }}<br>
              <strong>Endereço:</strong> {{ cliente.endereco }}<br>
              <strong>Bairro:</strong> {{ cliente.bairro }}<br>
              <strong>CEP:</strong> {{ cliente.cep }}<br>
              <strong>Email:</strong> {{ cliente.email }}<br>
              <strong>Ativo:</strong> {{ formatarLogico(cliente.ativo) }}
            </p>
            <button
              class="btn btn-secondary btn-sm"
              @click="alterarCliente(cliente)"
            >
              Editar
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Paginação -->
    <div v-if="!formVisible">
      <hr />
      <div class="container">
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
              <option value="nomeRazaoSocial">Nome ou Razão Social</option>
              <option value="cnpj">CNPJ</option>
              <option value="telefone">Telefone</option>
              <option value="endereco">Endereço</option>
              <option value="bairro">Bairro</option>
              <option value="cep">CEP</option>
              <option value="email">Email</option>
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
  </div>
</template>

<script>
import FormCliente from "./FormCliente.vue";
import axios from "axios";
import Logico from "@/utils/Logico.js";

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
      const response = await axios.get(
        `http://localhost:8080/clientes?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      this.listaClientes = response.data.content;
      this.totalPages = response.data.totalPages;
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
      try {
        const config = {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        };
        await axios.delete(`http://localhost:8080/cliente/${id}`, config);
        this.buscarClientes();
      } catch (error) {
        console.error(error);
      }
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarClientes();
    },
    formatarLogico(valor) {
      return Logico.toSimNao(valor);
    },
  },
  mounted() {
    this.buscarClientes();
  },
};
</script>

<style scoped>
.container {
  background-color: #343a40;
  color: #fff;
}
.cliente-card {
  background-color: #000000cd;
  color: #fff;
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: transform 0.2s, box-shadow 0.2s;
}
.cliente-card:hover {
  transform: scale(1.03);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
}
</style>
