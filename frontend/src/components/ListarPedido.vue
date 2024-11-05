<template>
  <div class="container-fluid main-container">
    <div class="header row align-items-center">
      <div class="col-10">
        <h3 class="title">Pedidos</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novo" class="btn btn-success btn-sm">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
    </div>

    <div class="form-container" v-if="formVisible">
      <FormPedido :propsPedido="pedidoEscolhido" @cancelar="limpar" @salvar_pedido="buscar" />
    </div>

    <table class="table table-dark table-striped mt-4" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Número</th>
          <th scope="col">Cliente</th>
          <th scope="col">Forma de Pagamento</th>
          <th scope="col">Data de Pagamento</th>
          <th scope="col">Data de Compra</th>
          <th scope="col">Data de Entrega</th>
          <th scope="col" class="text-center">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="pedido in listaPedidos" :key="pedido.id" scope="row">
          <td>{{ pedido.id }}</td>
          <td>{{ pedido.numero }}</td>
          <td>{{ pedido.clienteNomeRazaoSocial }}</td>
          <td>{{ pedido.formaPagamentoDescricao }}</td>
          <td>{{ formatar(pedido.dataPagamento) }}</td>
          <td>{{ formatar(pedido.dataCompra) }}</td>
          <td>{{ formatar(pedido.dataEntrega) }}</td>
          <td class="d-flex justify-content-end">
            <button class="btn btn-primary btn-sm me-2" @click="alterar(pedido)">
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>
            <button class="btn btn-danger btn-sm" @click="excluirPedido(pedido.id)">
              <i class="bi bi-clipboard2-minus"></i> Excluir
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="!formVisible" class="pagination-container">
      <div class="row justify-content-center align-items-center">
        <div class="col-auto">
          <button v-for="pagina in totalPages" :key="pagina" @click.prevent="irPara(pagina)"
            class="btn btn-outline-light ms-1">
            {{ pagina }}
          </button>
        </div>
        <div class="col-auto">
          <input type="text" v-model="pageNumber" placeholder="Página" class="form-control w-50" />
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
            <option value="cliente.nomeRazaoSocial">Nome</option>
            <option value="formaPagamento.descricao">Forma de Pagamento</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscar" class="btn btn-success btn-sm">
            <i class="bi bi-binoculars"></i> Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Data from "@/utils/Data";
import pedidoService from "@/services/PedidoService";
import FormPedido from "./FormPedido.vue";

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
    async excluirPedido(id) {
      try {
        const response = await pedidoService.apagar(id);
        console.log(response.data);
      } catch (error) {
        if (error.response?.status === 403) {
          alert("Usuário não identificado! Faça o login!!!");
        } else if (error.response?.status === 400) {
          alert(error.response.data.mensagem);
        } else {
          alert(error.message);
        }
      }
      this.buscar();
    },
    formatar(data) {
      return Data.formatoDMA(data);
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
.main-container {
  background-color: #23232e;
  border-radius: 8px;
  padding: 20px;
  margin: 0 auto;
  max-width: 95%;
}

.header {
  margin-bottom: 15px;
}

.title {
  color: #f0f0f0;
  font-size: 1.8em;
}

.table {
  margin-top: 10px;
}

.pagination-container {
  margin-top: 20px;
}

.btn-outline-light {
  color: #f0f0f0;
  border-color: #f0f0f0;
}

.form-control,
.form-select {
  max-width: 150px;
}
</style>
