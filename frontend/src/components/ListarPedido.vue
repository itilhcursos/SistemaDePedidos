<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>Pedidos</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novo" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
          <FormPedido v-if="formVisible" :propsPedido="pedidoEscolhido" @cancelar="limpar" @salvar_pedido="buscar" />
        </div>
      </div>
    </div>

    <!-- Tabela principal de pedidos -->
    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Número</th>
          <th scope="col">Cliente</th>
          <th scope="col">Forma Pagamento</th>
          <th scope="col">Data Compra</th>
          <th scope="col">Data Entrega</th>
          <th scope="col">Data Pagamento</th>
        </tr>
      </thead>
      <tbody>
        <!-- Loop pelos pedidos -->
        <tr v-for="pedido in listaPedidos" :key="pedido.id" scope="row">
          <th>{{ pedido.id }}</th>
          <th>{{ pedido.numero }}</th>
          <td>{{ pedido.clienteNomeRazaoSocial }}</td>
          <td>{{ pedido.formaPagamentoDescricao }}</td>
          <td>{{ formatar(pedido.dataCompra) }}</td>
          <td>{{ formatar(pedido.dataEntrega) }}</td>
          <td>{{ formatar(pedido.dataPagamento) }}</td>

          <th colspan="7">
            <h5 class="p-2 mb-2 bg-success text-white">Itens do Pedido</h5>
            <table class="table table-dark table-striped">
              <thead>
                <tr>
                  <th scope="col">Imagem</th>
                  <th scope="col">Descrição</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in pedido.itens" :key="item.id">
                  <td><img :src="item.produtoUrlImagem" height="50px" /></td>
                  <td>{{ item.produtoDescricao }}</td>
                </tr>
              </tbody>
              
              <button class="btn btn-btn btn-primary m-2" @click="alterar(pedido)">
                <i class="bi bi-clipboard-pulse"></i> Alterar
              </button>
          
           
              <button class="btn btn-outline-danger m-2" @click="excluirProduto(produto.id)">
                <i class="bi bi-clipboard2-minus"></i> Excluir
              </button>
          
            </table>
          </th>
        </tr>

        <!-- Sub-tabela de Itens do Pedido, logo abaixo de cada pedido -->
      </tbody>
    </table>

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
              <option value="cliente.nomeRazaoSocial">Cliente</option>
              <option value="formaPagamento.descricao">Forma Pagamento</option>
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
import FormPedido from "./FormPedido.vue";
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
      return data.split('-').reverse().join('/');
    },
  },
  mounted() {
    this.buscar();
  },
};
</script>
