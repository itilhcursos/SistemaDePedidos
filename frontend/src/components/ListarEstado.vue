<template>
  <div class="container">
    <div class="header d-flex justify-content-between align-items-center">
      <h3>Listagem de Estados</h3>
      <button v-if="!formVisible" @click="novoEstado" class="btn btn-success">
        <i class="bi bi-clipboard-plus"></i> Novo Estado
      </button>
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

    <table class="table table-striped table-hover mt-4" v-if="!formVisible">
      <thead class="table-dark">
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Nome</th>
          <th scope="col" class="text-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="estado in listaEstados" :key="estado.id">
          <td>{{ estado.id }}</td>
          <td>{{ estado.nome }}</td>
          <td class="text-end">
            <button
              class="btn btn-primary m-1"
              @click="alterarEstado(estado)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>
            <button
              class="btn btn-danger m-1"
              @click="excluirEstado(estado.id)"
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
            type="text"
            v-model="pageNumber"
            placeholder="Número da página"
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
          <button @click.prevent="buscarEstados" class="btn btn-success">
            <i class="bi bi-binoculars"></i> Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormEstado from "./FormEstado.vue";
import estadoService from "@/services/estadoService";

export default {
  components: {
    FormEstado,
  },
  data() {
    return {
      listaEstados: [],
      estadoEscolhido: null,
      formVisible: false,
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
      try {
        const response = await estadoService.listar(
          this.pageNumber,
          this.pageSize,
          this.direction,
          this.property
        );
        this.listaEstados = response.content;
        this.totalPages = response.totalPages;
      } catch (error) {
        this.handleError(error);
      }
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
        await estadoService.apagar(id);
        this.buscarEstados();
      } catch (error) {
        this.handleError(error);
      }
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarEstados();
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
    this.buscarEstados();
  },
};
</script>

<style scoped>
.container {
  margin-top: 20px;
}

.header {
  margin-bottom: 20px;
}

.table {
  border-radius: 12px; 
  overflow: hidden;
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: rgba(0, 123, 255, 0.1);
}

.pagination-container {
  margin-top: 20px;
}

.pagination-controls {
  flex-wrap: wrap;
}
</style>
