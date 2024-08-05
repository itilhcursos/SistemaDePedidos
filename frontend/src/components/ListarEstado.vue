<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3 >ESTADOS</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoEstado" class="btn btn-success"><i class="bi bi-clipboard-plus"></i> Novo</button>
      </div>
      <div class="row">
        <div>
          <FormEstado
            v-if="formVisible"
            :propsEstado="estadoEscolhido"
            @cancelar="limpar"
            @salvar_estado="buscarEstados"
          />
        </div>
      </div>
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Nome</th>
          <th scope="col" class="d-flex justify-content-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="estado in listaEstados" :key="estado.id" scope="row">
          <th>
            {{ estado.id }}
          </th>
          <td>
            {{ estado.nome }}
          </td>
          <td class="d-flex justify-content-end">
            <button class="btn btn-btn btn-primary  m-2" @click="alterarEstado(estado)"><i class="bi bi-clipboard-pulse"></i> Alterar</button>

            <button class="btn btn-outline-danger m-2" @click="excluirEstado(estado.id)"><i class="bi bi-clipboard2-minus"></i> Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div v-if="!formVisible">
    <hr />
    <h2>Paginação</h2>
    <p>
      <input type="text" v-model="pageNumber" placeholder="Número da pagina" />
    </p>
    <p><button @click.prevent="buscarEstados">Buscar</button></p>
  </div>
</template>


<script>
import FormEstado from "./FormEstado.vue";
import axios from "axios";
export default {
  components: {
    FormEstado,
  },
  data() {
    return {
      listaEstados: [],
      estadoEscolhido: null,
      formVisible: false,
      mode: import.meta.env.MODE,
      url: import.meta.env.VITE_APP_URL_API,
      pageNumber: 1,
    };
  },
  methods: {
    async buscarEstados() {
      this.estadoEscolhido = null;
      this.formVisible = false;
      //buscar a lista de estados no servidor
      // http://localhost:8080/estados
      const response = await axios.get(
        `http://localhost:8080/estados?pageNumber=${this.pageNumber}`
      );
      console.log(response.data);
      this.listaEstados = response.data.content;
    },
    limpar() {
      this.estadoEscolhido = null;
      this.formVisible = !this.formVisible;
    },
    novoEstado() {
      this.formVisible = !this.formVisible;
    },
    alterarEstado(estado) {
      this.estadoEscolhido = estado;
      this.formVisible = true;
    },
    async excluirEstado(id) {
      const response = await axios.delete(`http://localhost:8080/estado/${id}`);
      console.log(response.data);
      this.buscarEstados();
    },
  },
  mounted() {
    this.buscarEstados();
  },
};
</script>
