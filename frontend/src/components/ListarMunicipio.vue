<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>Municípios</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoMunicipio" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
    </div>

    <!-- Exibindo o formulário de município -->
    <div v-if="formVisible">
      <FormMunicipio v-if="formVisible" :propsMunicipio="municipioEscolhido" @cancelar="limpar" @salvar_municipio="buscar" />
    </div>

    <!-- Listagem dos Municípios em Cards -->
    <div class="row mt-3" v-if="!formVisible">
      <div class="col-md-4" v-for="municipio in listaMunicipios" :key="municipio.id">
        <div class="card mb-4 bg-black text-white">
          <div class="card-body">
            <h5 class="card-title">{{ municipio.nome }}</h5>
            <p class="card-text">ID: {{ municipio.id }}</p>
            <p class="card-text">Estado: {{ municipio.estadoNome }}</p>
            <p class="card-text">Entrega: {{ formatarEntrega(municipio.entrega) }}</p>
            <div class="d-flex justify-content-between">
              <button class="btn btn-primary" @click="alterarMunicipio(municipio)">
                <i class="bi bi-clipboard-pulse"></i> Alterar
              </button>
              <button class="btn btn-danger" @click="excluirMunicipio(municipio.id)">
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
          <div class="col-auto">
            <button v-for="pagina in totalPages" :key="pagina" @click.prevent="irPara(pagina)" class="btn btn-light ms-1">
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
            <button @click.prevent="buscar" class="btn btn-success">
              <i class="bi bi-binoculars"></i> Buscar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormMunicipio from "./FormMunicipio.vue";
import Logico from "@/utils/Logico.js";
import axios from "axios";

export default {
  components: {
    FormMunicipio,
  },
  data() {
    return {
      listaMunicipios: [],
      municipioEscolhido: null,
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
    async buscar() {
      this.municipioEscolhido = null;
      this.formVisible = false;
      const response = await axios.get(
        `http://localhost:8080/municipios?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      this.listaMunicipios = response.data.content;
      this.totalPages = response.data.totalPages;
    },
    formatarEntrega(valor) {
      return Logico.toSimNao(valor);
    },
    limpar() {
      this.municipioEscolhido = null;
      this.formVisible = !this.formVisible;
    },
    novoMunicipio() {
      this.formVisible = !this.formVisible;
    },
    alterarMunicipio(municipio) {
      this.municipioEscolhido = municipio;
      this.formVisible = true;
    },
    async excluirMunicipio(id) {
      let config = {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      }
      try {
        const response = await axios.delete(`http://localhost:8080/municipio/${id}`, config);
        console.log(response.data);
      } catch (error) {
        if (error.response.status === 403) {
          alert("Usuário não identificado, Faça seu login!");
        } else if (error.response.status === 400) {
          alert(error.response.data.mensagem);
        } else {
          alert(error.message);
        }
      }
      this.buscar();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscar();
    },
  },
  mounted() {
    this.buscar();
  },
};
</script>

<style scoped>
.card {
  border-radius: 10px;
  overflow: hidden;
}

.card-title {
  font-weight: bold;
  font-size: 1.2rem;
}

.card-text {
  font-size: 1rem;
}

.card-body {
  padding: 1.2rem;
  border-radius: 10%;
}

.bg-black {
  background-color: #000 !important;
}

.text-white {
  color: #fff !important;
}

.btn {
  font-size: 0.9rem;
}
</style>
