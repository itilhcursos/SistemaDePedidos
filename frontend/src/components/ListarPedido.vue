<template>
  <div class="container">
    <!-- Header de Pedidos -->
    <div class="row mb-4">
      <div class="col-10">
        <h3>Pedidos</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novo" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
    </div>

    <!-- Formulário de Pedido -->
    <div v-if="formVisible" class="row">
      <FormPedido
        :propsPedido="pedidoEscolhido"
        @cancelar="limpar"
        @salvar_pedido="buscar"
      />
    </div>

    <!-- Lista de Pedidos em Cartões -->
    <div v-if="!formVisible" class="pedido-cards">
      <div v-for="pedido in listaPedidos" :key="pedido.id" class="card pedido-card">
        <div class="card-header d-flex justify-content-between">
          <span><strong>ID:</strong> {{ pedido.id }}</span>
          <span><strong>Número:</strong> {{ pedido.numero }}</span>
        </div>
        <div class="card-body">
          <p><strong>Cliente:</strong> {{ pedido.clienteNomeRazaoSocial }}</p>
          <p><strong>Forma de Pagamento:</strong> {{ pedido.formaPagamentoDescricao }}</p>
          <p><strong>Data Compra:</strong> {{ formatar(pedido.dataCompra) }}</p>
          <p><strong>Data Entrega:</strong> {{ formatar(pedido.dataEntrega) }}</p>
          <p><strong>Data Pagamento:</strong> {{ formatar(pedido.dataPagamento) }}</p>

          <!-- Itens do Pedido -->
          <div class="pedido-itens">
            <h5>Itens:</h5>
            <div v-for="itens in pedido.itens" :key="itens.id" class="item">
              <img :src="itens.produtoUrlImagem" alt="Imagem do produto" height="50px" class="me-3"/>
              <span>{{ itens.produtoDescricao }}</span>
            </div>
          </div>
        </div>
        <div class="card-footer d-flex justify-content-end">
          <button class="btn btn-primary me-2" @click="alterar(pedido)">
            <i class="bi bi-clipboard-pulse"></i> Alterar
          </button>
          <button class="btn btn-outline-danger" @click="excluir(pedido.id)">
            <i class="bi bi-clipboard2-minus"></i> Excluir
          </button>
        </div>
      </div>
    </div>

    <!-- Controle de Paginação e Filtros -->
    <div v-if="!formVisible" class="pagination-controls mt-4">
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
            <option value="cliente.nomeRazaoSocial">Nome RazaoSocial</option>
            <option value="formaPagamento.descricao">FormaPagamento Descricao</option>
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
</template>

<script>
import FormPedido from "./FormPedido.vue";
import Data from "../utils/Data";
import pedidoService from "@/services/pedidoService";

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
      const response = await pedidoService.listar(this.pageNumber, this.pageSize, this.direction, this.property);
      this.listaPedidos = response.content;
      this.totalPages = response.totalPages;
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
        const response = await pedidoService.apagar(id);
        console.log(response);
      } catch (error) {
        if (error.response.status === 403) {
          alert("Usuário não identificado! Faça o login!!!");
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
/* Estilo de Pedidos em Cartões */
.pedido-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.pedido-card {
  background-color: #333;
  color: #ded7d7;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
}

.card-header,
.card-footer {
  background-color: #1f1e21;
  color: #bcbaba;
  padding: 10px;
  font-weight: bold;
}

.card-body {
  padding: 20px;
}

.pedido-itens {
  margin-top: 10px;
}

.pedido-itens .item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 0;
}

.pagination-controls {
  margin-top: 20px;
}

.pagination-controls .form-control,
.pagination-controls .form-select {
  background-color: #444;
  color: #fbfbfb;
  border: 1px solid #252323;
}
</style>
