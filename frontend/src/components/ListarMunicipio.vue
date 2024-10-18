<template>
  <div class="container">
    <div class="header d-flex justify-content-between align-items-center mb-4">
      <h3 class="text-primary">Listagem de Municípios</h3>
      <button v-if="!formVisible" @click="novoMunicipio" class="btn btn-success">
        <i class="bi bi-clipboard-plus"></i> Novo Município
      </button>
    </div>

    <div v-if="formVisible">
      <FormMunicipio
        :propsMunicipio="municipioEscolhido"
        @cancelar="limpar"
        @salvar_municipio="buscar"
      />
    </div>

    <table class="table table-striped table-hover mt-4" v-if="!formVisible">
      <thead class="table-dark">
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Nome</th>
          <th scope="col">Entrega</th>
          <th scope="col">Estado</th>
          <th scope="col" class="text-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="municipio in listaMunicipios" :key="municipio.id">
          <td>{{ municipio.id }}</td>
          <td>{{ municipio.nome }}</td>
          <td>{{ formatarEntrega(municipio.entrega) }}</td>
          <td>{{ municipio.estadoNome }}</td>
          <td class="text-end">
            <button
              class="btn btn-primary m-1"
              @click="alterarMunicipio(municipio)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>
            <button
              class="btn btn-danger m-1"
              @click="excluirMunicipio(municipio.id)"
            >
              <i class="bi bi-clipboard2-minus"></i> Excluir
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="pagination-container" v-if="!formVisible">
      <hr />
      <div class="d-flex justify-content-between align-items-center mt-2">
        <div class="pagination-controls d-flex align-items-center">
          <button
            v-for="pagina in totalPages"
            :key="pagina"
            @click.prevent="irPara(pagina)"
            class="btn btn-outline-primary ms-1"
          >
            {{ pagina }}
          </button>
        </div>

        <div class="d-flex align-items-center">
          <input
            type="number"
            v-model="pageNumber"
            placeholder="Número da página"
            min="1"
            class="form-control w-25 me-2"
          />
          <select v-model="pageSize" class="form-select me-2">
            <option value="2">2</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
          <select v-model="property" class="form-select me-2">
            <option value="id">ID</option>
            <option value="nome">Nome</option>
          </select>
          <select v-model="direction" class="form-select me-2">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
          <button @click.prevent="buscar" class="btn btn-success">
            <i class="bi bi-binoculars"></i> Buscar
          </button>
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
        `${this.url}/municipios?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
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
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
      };
      try {
        await axios.delete(`${this.url}/municipio/${id}`, config);
        this.buscar();
      } catch (error) {
        this.handleError(error);
      }
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscar();
    },
    handleError(error) {
      if (error.response.status === 403) {
        alert("Usuário não identificado! Faça o login!!!");
      } else if (error.response.status === 400) {
        alert(error.response.data.mensagem);
      } else {
        alert(error.message);
      }
    },
  },
  mounted() {
    this.buscar();
  },
};
</script>

<style scoped>
.container {
  margin-top: 20px;
}

.header h3 {
  font-weight: bold;
}

.table {
  border-radius: 12px; /* Borda arredondada para a tabela */
  overflow: hidden; /* Para evitar que o conteúdo transborde */
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: rgba(0, 123, 255, 0.1); /* Cor de fundo alternada */
}

.pagination-container {
  margin-top: 20px;
}

.pagination-controls {
  flex-wrap: wrap; /* Para permitir que os botões se movam para a linha seguinte, se necessário */
}

input[type="number"] {
  max-width: 80px; /* Limitar largura do input de número */
}
</style>
