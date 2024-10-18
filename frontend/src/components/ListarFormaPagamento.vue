<template>
  <div class="container bg-dark text-white p-4 rounded">
    <div class="header d-flex justify-content-between align-items-center mb-4">
      <h3 class="text-primary">Listagem de Formas de Pagamento</h3>
      <button v-if="!formVisible" @click="novaFormaPagamento" class="btn btn-success">
        <i class="bi bi-clipboard-plus"></i> Nova Forma de Pagamento
      </button>
    </div>

    <div v-if="formVisible">
      <FormFormaPagamento
        :propsFormaPagamento="formaPagamentoEscolhida"
        @cancelar="limpar"
        @salvar_formaPagamento="buscarFormaPagamento"
      />
    </div>

    <table class="table table-striped table-hover mt-4 text-white" v-if="!formVisible">
      <thead class="table-dark">
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Descrição</th>
          <th scope="col">Ativo</th>
          <th scope="col" class="text-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="formaPagamento in listaFormasPagamento" :key="formaPagamento.id">
          <td>{{ formaPagamento.id }}</td>
          <td>{{ formaPagamento.descricao }}</td>
          <td>{{ formaPagamento.ativo }}</td>
          <td class="text-end">
            <button
              class="btn btn-primary m-1"
              @click="alterarFormaPagamento(formaPagamento)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>
            <button
              class="btn btn-danger m-1"
              @click="excluirFormaPagamento(formaPagamento.id)"
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
            <option value="descricao">Descrição</option>
          </select>
          <select v-model="direction" class="form-select me-2">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
          <button @click.prevent="buscarFormaPagamento" class="btn btn-success">
            <i class="bi bi-binoculars"></i> Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormFormaPagamento from "./FormFormaPagamento.vue";
import axios from "axios";
export default {
  components: {
    FormFormaPagamento,
  },
  data() {
    return {
      listaFormasPagamento: [],
      formaPagamentoEscolhida: null,
      formVisible: false,
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

      const response = await axios.get(
        `http://localhost:8080/formas-pagamento?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      this.listaFormasPagamento = response.data.content;
      this.totalPages = response.data.totalPages;
    },
    limpar() {
      this.formaPagamentoEscolhida = null;
      this.formVisible = !this.formVisible;
    },
    novaFormaPagamento() {
      this.formVisible = !this.formVisible;
    },
    alterarFormaPagamento(formaPagamento) {
      this.formaPagamentoEscolhida = formaPagamento;
      this.formVisible = true;
    },
    async excluirFormaPagamento(id) {
      let config = {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
        },
      };
      try {
        await axios.delete(`http://localhost:8080/forma-pagamento/${id}`, config);
        this.buscarFormaPagamento();
      } catch (error) {
        if (error.response.status === 403) {
          alert("Usuário não identificado! Faça o login!!!");
        } else if (error.response.status === 400) {
          alert(error.response.data.mensagem);
        } else {
          alert(error.message);
        }
      }
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

<style scoped>
.container {
  margin-top: 20px;
}

.header h3 {
  font-weight: bold;
}

.table {
  border-radius: 12px;
  overflow: hidden;
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: rgba(255, 255, 255, 0.1);
}

.pagination-container {
  margin-top: 20px;
}

.pagination-controls {
  flex-wrap: wrap;
}

input[type="number"] {
  max-width: 80px;
}
</style>
