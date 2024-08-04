<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>FORMAS DE PAGAMENTO</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novaFormaPagamento" class="btn btn-success"><i class="bi bi-clipboard-plus"></i> Novo</button>
      </div>
      <div class="row">
        <div>
          <FormFormaPagamento v-if="formVisible" :propsFormaPagamento="formaPagamentoEscolhida" @cancelar="limpar" @salvar_forma_pagamento="buscarFormaPagamento"/>
        </div>
      </div>
    </div>
    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Nome</th>
          <th scope="col">Ativo</th>
          <th scope="col" class="d-flex justify-content-center">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="forma_pagamento in listaFormasPagamento" :key="forma_pagamento.id" scope="row">
          <th>
            {{ forma_pagamento.id }}
          </th>
          <td>
            {{ forma_pagamento.descricao }}
          </td>
          <td>
            {{ forma_pagamento.ativo ? 'Sim' : 'Não' }}
          </td>
          <td class="d-flex justify-content-center">
            <button class="btn btn-btn btn-primary m-2" @click="alterarFormaPagamento(forma_pagamento)"><i class="bi bi-clipboard-pulse"></i> Alterar</button>
            <button class="btn btn-outline-danger m-2" @click="excluirFormaPagamento(forma_pagamento.id)"><i class="bi bi-clipboard2-minus"></i> Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div v-if="!formVisible">
    <hr />
    <div class="container">
      <div class="row d-flex justify-content-center">
        <div class="col-auto">
          <button v-for="pagina in totalPages" :key="pagina" @click.prevent="irPara(pagina)" class="btn btn-light ms-1">{{ pagina }}</button>
        </div>
        <div class="col-auto">
          <input type="text" v-model="pageNumber" placeholder="Número da pagina" class="form-control w-25"/>
        </div>
        <div class="col-auto">
          <select v-model="pageSize" class="form-select" >
            <option value="2">2</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="property" class="form-select">
            <option value="id">ID</option>
            <option value="descricao">Nome</option>
            <option value="ativo">Ativo</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscarFormaPagamento" class="btn btn-success"><i class="bi bi-binoculars"></i> Buscar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import FormFormaPagamento from "./FormFormaPagamento.vue";
export default {
  components: {
    FormFormaPagamento,
  },
  data() {
    return {
      listaFormasPagamento: [],
      formaPagamentoEscolhida: null,
      formVisible: false,
      mode: import.meta.env.MODE,
      url: import.meta.env.VITE_APP_URL_API,
      pageNumber: 1,
      pageSize: 10,
      direction: "ASC",
      property: "id",
      totalPages: 0,
    };
  },
  methods: {
    async buscarFormaPagamento() {
      this.formaPagamentoEscolhida = null;
      this.formVisible = false;
      // Buscar a lista de forma_pagamento no servidor
      // http://localhost:8080/forma-pagamento
      const response = await axios.get(
        `http://localhost:8080/forma-pagamento?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      console.log(response.data);
      this.listaFormasPagamento = response.data.content;
      this.totalPages = response.data.totalPages;
      console.log(this.totalPages);
    },
    limpar() {
      this.formaPagamentoEscolhida = null;
      this.formVisible = !this.formVisible;
    },
    novaFormaPagamento() {
      this.formVisible = !this.formVisible;
    },
    alterarFormaPagamento(forma_pagamento) {
      this.formaPagamentoEscolhida = forma_pagamento;
      this.formVisible = true;
    },
    async excluirFormaPagamento(id) {
      const response = await axios.delete(`http://localhost:8080/forma-pagamento/${id}`);
      console.log(response.data);
      this.buscarFormaPagamento();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarFormaPagamento();
    },
  },
  mounted() {
    this.buscarFormaPagamento();
  },
};
</script>
