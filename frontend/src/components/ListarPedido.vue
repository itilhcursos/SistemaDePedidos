<template>
  <div class="container">
    <div class="row">
      <div class="col-12 d-flex justify-content-between">
        <h3>Pedidos</h3>
        <button v-if="!formVisible" @click="novo" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
    </div>

    <!-- Formulário de Pedido -->
    <div class="row" v-if="formVisible">
      <FormPedido :propsPedido="pedidoEscolhido" @cancelar="limpar" @salvar_pedido="buscar" />
    </div>

    <!-- Cards de Pedidos -->
    <div class="row mt-3" v-if="!formVisible">
      <div class="col-md-6 col-lg-4" v-for="pedido in listaPedidos" :key="pedido.id">
        <div class="card text-bg-dark mb-3">
          <div class="card-header d-flex justify-content-between align-items-center">
            <span>#{{ pedido.id }} - {{ pedido.numero }}</span>
            <button class="btn btn-sm btn-danger" @click="excluir(pedido.id)">
              <i class="bi bi-trash"></i>
            </button>
          </div>
          <div class="card-body">
            <h5 class="card-title">{{ pedido.clienteNomeRazaoSocial }}</h5>
            <p class="card-text">
              <strong>Forma de Pagamento:</strong> {{ pedido.formaPagamentoDescricao }}<br>
              <strong>Data Compra:</strong> {{ formatar(pedido.compra) }}<br>
              <strong>Data Entrega:</strong> {{ formatar(pedido.entrega) }}<br>
              <strong>Data Pagamento:</strong> {{ formatar(pedido.pagamento) }}
            </p>
            <button class="btn btn-secondary btn-sm" @click="alterar(pedido)">
              Visualizar ou Editar
            </button>
          </div>
          <div class="collapse" :id="'itens-' + pedido.id">
            <ul class="list-group list-group-flush">
              <li class="list-group-item text-bg-dark d-flex justify-content-between align-items-center"
                v-for="item in pedido.itens" :key="item.id">
                <img :src="item.produtoUrlImagem" alt="Produto" height="40" />
                <span>{{ item.produtoDescricao }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormPedido from "./FormPedido.vue";
import Data from "../utils/Data";
import pedidoService from "@/services/PedidoService";
import axios from "axios";

export default {
  components: {
    FormPedido,
  },
  data() {
    return {
      listaPedidos: [],
      pedidoEscolhido: null,
      formVisible: false,
      pageNumber: 1,
      pageSize: 10,
      direction: "ASC",
      property: "id",
      totalPages: 0,
    };
  },
  methods: {
    async buscar() {
      this.pedidoEscolhido = null;
      this.formVisible = false;
      try {
        const response = await axios.get(
          "http://localhost:8080/pedidos?pageNumber=1&pageSize=10&direction=ASC&property=id"
        );
        this.listaPedidos = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Erro ao buscar pedidos:", error);
        alert("Não foi possível carregar os pedidos.");
      }
    },
    limpar() {
      this.pedidoEscolhido = null;
      this.formVisible = !this.formVisible;
    },
    novo() {
      this.formVisible = !this.formVisible;
    },
    alterar(pedido) {
      this.pedidoEscolhido = pedido;
      this.formVisible = true;
    },
    async excluir(id) {
      try {
        await pedidoService.apagar(id);
        this.buscar();
      } catch (error) {
        if (error.response?.status === 403) {
          alert("Usuário não identificado! Faça o login.");
        } else if (error.response?.status === 400) {
          alert(error.response.data.mensagem);
        } else {
          alert("Erro ao excluir pedido: " + error.message);
        }
      }
    },
    formatar(data) {
      return Data.formatoDMA(data);
    },
  },
  mounted() {
    this.buscar();
  },
};
</script>

<style scoped>
.card {
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: transform 0.2s, box-shadow 0.2s;
}

.card:hover {
  transform: scale(1.03);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
}

.collapse {
  margin-top: 10px;
}

.card-body {
  background-color: black;
}
</style>
