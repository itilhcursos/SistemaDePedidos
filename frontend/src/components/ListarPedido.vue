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
          <FormPedido
            v-if="formVisible"
            :propsPedido="pedidoEscolhido"
            @cancelar="limpar"
            @salvar_pedido="buscar"
          />
        </div>
      </div>
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Numero</th>
          <th scope="col">Cliente</th>
          <th scope="col">Forma de Pagamento</th>
          <th scope="col">Data de Pagamento</th>
          <th scope="col">Data de Compra</th>
          <th scope="col">Data de Entrega</th>
          <th scope="col" class="Item">Itens</th>
        </tr>
        <!-- class="d-flex justify-content-end" -->
      </thead>
      <tbody>
        <tr v-for="pedido in listaPedidos" :key="pedido.id" scope="row">
          <th>
            {{ pedido.id }}
          </th>
          <td>
            {{ pedido.numero }}
          </td>
          <td>
            {{ pedido.clienteNomeRazaoSocial }}
          </td>
          <td>
            {{ pedido.formaPagamentoDescricao }}
          </td>
          <td>
            {{ formatar(pedido.dataPagamento) }}
          </td>
          <td>
            {{ formatar(pedido.dataCompra) }}
          </td>
          <td>
            {{ formatar(pedido.dataEntrega) }}
          </td>
          <div class="espaco">
            <button
              class="btn btn-btn btn-primary m-2"
              @click="alterar(pedido)"
            >
              <i class="bi bi-clipboard-pulse"></i>Alterar
            </button>
            <button
              class="btn btn-outline-danger m-2"
              @click="excluirPedido(pedido.id)"
            >
              <i class="bi bi-clipboard2-minus"></i>
              excluir
            </button>
          </div>
          <td class="d-flex justify-content-end">
            <table class="table table-dark table-striped">
              <thead>
                <tr>
                  <th scope="col">Itens</th>
                  <th scope="col">Descrição</th>
                  <th scope="col">Cliente</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="itens in pedido.itens" :key="itens.id" scope="row">
                  <th>
                    <img :src="itens.produtoUrlImagem" height="50 px" />
                  </th>
                  <td>
                    {{ itens.produtoDescricao }}
                  </td>
                </tr>
              </tbody>
            </table>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div v-if="!formVisible">
    <hr />
    <div class="container">
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
            placeholder="Número da pagina"
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
            <option value="cliente.nomeRazaoSocial">Nome RazãoSocial</option>
            <option value="formaPagamento.descricao">
              FormaPagamento Descrição
            </option>
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
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import Data from "@/utils/Data";
import pedidoService from "@/services/pedidoService";
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
      this.pedidoEscolhido = null;
      this.formVisible = false;
      //buscar a lista de Pedidos no servidor

      const response = await pedidoService.listar(
        this.pageNumber,
        this.pageSize,
        this.direction,
        this.property
      );

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
      // primeiro tipo de validação
      //
      // if(localStorage.getItem('token') === null) {
      //   alert("Usuário não identificado! faça o login!!!");
      //   return
      // } o if testa antes - ele manda uma mensagem e evitando que o sujeito faça a tentativa de mandar uma mensagem sem ele estar logado
      //O try testa depois - primeiro ele execulta e depois quando ele ve que vai dar errado ele faz o tratamento

      //segundo tipo de validação.

      try {
        const response = await pedidoService.apagar(id);
        console.log(response.data);
      } catch (error) {
        if (error.response.status === 403) {
          // o erro 403 é quando esqueço de fazer a autenticação
          alert("Usuário não identificado! Faça o login!!!");
        } else if (error.response.status === 400) {
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
<style>
.Item {
  text-align: center;
}
.espaco{
  text-align: center;
}
</style>
