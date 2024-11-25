<template>
  <div class="container bg-light p-4">
    <div class="row">
      <div class="col-10">
        <h3>Formas de Pagamento</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novaFormaPagamento" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
    </div>

    <div class="row mt-4" v-if="!formVisible">
      <div v-for="formaPagamento in listaFormasPagamento" :key="formaPagamento.id"
        class="col-lg-4 col-md-6 col-sm-12 mb-4">
        <div class="card shadow-sm" style="background-color: inherit;"> <!-- Herdando o fundo -->
          <div class="card-body">
            <h5 class="card-title">#{{ formaPagamento.id }}</h5>
            <p class="card-text">
              <strong>Descrição:</strong> {{ formaPagamento.descricao }} <br />
              <strong>Ativo:</strong> {{ formaPagamento.ativo ? 'Sim' : 'Não' }}
            </p>
            <div class="d-flex justify-content-between">
              <button class="btn btn-primary" @click="alterarFormaPagamento(formaPagamento)">
                <i class="bi bi-pencil"></i> Alterar
              </button>
              <button class="btn btn-danger" @click="excluirFormaPagamento(formaPagamento.id)">
                <i class="bi bi-trash"></i> Excluir
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

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
              <option value="descricao">Descrição</option>
            </select>
          </div>
          <div class="col-auto">
            <select v-model="direction" class="form-select">
              <option value="ASC">Crescente</option>
              <option value="DESC">Decrescente</option>
            </select>
          </div>
          <div class="col-auto">
            <button @click.prevent="buscarFormaPagamento" class="btn btn-success">
              <i class="bi bi-binoculars"></i> Buscar
            </button>
          </div>
        </div>
      </div>
    </div>

    <FormFormaPagamento v-if="formVisible" :propsFormaPagamento="formaPagamentoEscolhida" @cancelar="limpar"
      @salvar_formaPagamento="buscarFormaPagamento" />
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

      try {
        const response = await axios.get(
          `http://localhost:8080/formas-pagamento?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
        );
        this.listaFormasPagamento = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Erro ao buscar formas de pagamento:", error);
      }
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
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      };
      try {
        await axios.delete(`http://localhost:8080/formas-pagamento/${id}`, config);
        this.buscarFormaPagamento();
      } catch (error) {
        if (error.response) {
          const status = error.response.status;
          if (status === 403) {
            alert("Usuário não identificado, faça seu login!");
          } else if (status === 400) {
            alert(error.response.data.mensagem);
          } else {
            alert("Erro ao excluir forma de pagamento.");
          }
        } else {
          console.error("Erro ao excluir forma de pagamento:", error);
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
.card-body {
  background-color: black;
  border-radius: 10px;
}
</style>
