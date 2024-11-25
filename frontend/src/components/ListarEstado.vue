<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>ESTADOS</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoEstado" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
          <FormEstado v-if="formVisible" :propsEstado="estadoEscolhido" @cancelar="limpar"
            @salvar_estado="buscarEstados" />
        </div>
      </div>
    </div>

    <!-- Exibição dos estados em cards -->
    <div class="row mt-4" v-if="!formVisible">
      <div v-for="estado in listaEstados" :key="estado.id" class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
        <div class="card text-white bg-dark h-100">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">ID: {{ estado.id }}</h5>
            <p class="card-text">Nome: {{ estado.nome }}</p>
            <div class="mt-auto d-flex justify-content-between">
              <button class="btn btn-primary" @click="alterarEstado(estado)">
                <i class="bi bi-pencil"></i> Alterar
              </button>
              <button class="btn btn-danger" @click="excluirEstado(estado.id)">
                <i class="bi bi-trash"></i> Excluir
              </button>
            </div>
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
            <button v-for="pagina in totalPages" :key="pagina" @click.prevent="irPara(pagina)"
              class="btn btn-light ms-1">
              {{ pagina }}
            </button>
          </div>
          <div class="col-auto">
            <input type="text" v-model="pageNumber" placeholder="Número da página" class="form-control w-25" />
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
              <option value="nome">Nome</option>
            </select>
          </div>
          <div class="col-auto">
            <select v-model="direction" class="form-select">
              <option value="ASC">Crescente</option>
              <option value="DESC">Decrescente</option>
            </select>
          </div>
          <div class="col-auto">
            <button @click.prevent="buscarEstados" class="btn btn-success">
              <i class="bi bi-binoculars"></i> Buscar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import FormEstado from "./FormEstado.vue";
import estadoService from "@/services/EstadoService";

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
      pageSize: 10,
      direction: "ASC",
      property: "id",
      totalPages: 0,
    };
  },
  methods: {
    async buscarEstados() {
      this.estadoEscolhido = null;
      this.formVisible = false;
      const response = await axios.get(
        `http://localhost:8080/estados?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      this.listaEstados = response.data.content;
      this.totalPages = response.data.totalPages;
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
      try {
        const response = await estadoService.apagar(id);
        console.log(response);
      } catch (error) {
        if (error.response.status === 403) {
          alert("Usuário não identificado, Faça seu login!");
        } else if (error.response.status === 400) {
          alert(error.response.data.mensagem);
        } else {
          alert(error.message);
        }
      }

      this.buscarEstados();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarEstados();
    },
  },
  mounted() {
    this.buscarEstados();
  },
};
</script>

<style>
.card {
  border-radius: 10%;
  transition: transform 0.3s ease;
}

.card-body {
  background-color: black;
  border-radius: 10%;
}

.card:hover {
  transform: scale(1.05);
}

.card-title {
  font-weight: bold;
  font-size: 1.2rem;
}

.card-text {
  font-size: 1rem;
}
</style>
