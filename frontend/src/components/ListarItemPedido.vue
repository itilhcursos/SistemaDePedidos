<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>Itens Pedidos</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoitemPedido" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
          <FormItemPedido
            v-if="formVisible"
            :propsItemPedido="itemPedidoEscolhido"
            @cancelar="limpar"
            @salvar_itemPedido="buscaritemPedido"
          />
        </div>
      </div>
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Quantidade no Estoque</th>
          <th scope="col">Preço Atual</th>
          <th scope="col">Pedido</th>
          <th scope="col">Descrição do Produto</th>
          <th scope="col" class="d-flex justify-content-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="itemPedido in listaItemPedido" :key="itemPedido.id" scope="row">
          <th>
            {{ itemPedido.id }}
          </th>
          <td>
            {{ formatarQuantidade(itemPedido.quantidadeEstoque) }}
          </td>
          <td>
            {{ formatarPreco(itemPedido.precoUnidadeAtual) }}
          </td>
          <td>
            {{ itemPedido.pedidoId }}
          </td>
          <td>
            {{ itemPedido.produtoDescricao }}
          </td>
          <td class="d-flex justify-content-end">
            <button
              class="btn btn-btn btn-primary m-2"
              @click="alterarItemPedido(itemPedido)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>

            <button
              class="btn btn-outline-danger m-2"
              @click="excluirItemPedido(itemPedido.id)"
            >
              <i class="bi bi-clipboard2-minus"></i> Excluir
            </button>
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
            <option value="quantidadeEstoque">Quantidade no Estoque</option>
            <option value="precoUnidadeAtual">Preço Atual</option>
            <option value="pedidoId">Pedidos</option>
            <option value="produtoDescricao">Descrição do Produto</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscaritemPedido" class="btn btn-success">
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import FormItemPedido from "./FormItemPedido.vue";
import Monetario from "@/utils/Monetario.js";
import Decimal from "@/utils/Decimal.js";
import axios from "axios";
export default {
  components: {
    FormItemPedido,
  },
  data() {
    return {
      listaitemPedido: [],
      itemPedidoEscolhido: null,
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
    async buscaritemPedido() {
      this.itemPedidoEscolhido = null;
      this.formVisible = false;
      const response = await axios.get(
        `http://localhost:8080/itemPedido?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`);
      console.log(response.data);
      this.listaitemPedido = response.data.content;
      this.totalPages = response.data.totalPages;
      console.log(this.totalPages);
    },
    limpar() {
      this.itemPedidoEscolhido = null;
      this.formVisible = !this.formVisible;
    },
    novoitemPedido() {
      this.formVisible = !this.formVisible;
    },
    alterarItemPedido(itemPedido) {
      this.itemPedidoEscolhido = itemPedido;
      this.formVisible = true;
    },
    async excluirItemPedido(id) {
      let config = {
        headers: {
          'Authorization': 'Bearer ' +localStorage.getItem('token')
        }
      }
      const response = await axios.delete(`http://localhost:8080/itemPedido/${id}`, config);
      console.log(response.data);
      this.buscaritemPedido();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscaritemPedido();
    },
      formatarPreco(valor){
        return Monetario.toTela(valor);
      },
      formatarQuantidade(valor){
        return Decimal.toTela(valor);
      }
  },
  mounted() {
    this.buscaritemPedido();
  },
};
</script>
