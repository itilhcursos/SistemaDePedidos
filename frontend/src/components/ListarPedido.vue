<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>PEDIDOS</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoPedido" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
          <FormEstado
            v-if="formVisible"
            :propsPedido="pedidoEscolhido"
            @cancelar="limpar"
            @salvar_pedido="buscarPedidos"
          />
        </div>
      </div>
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">NÚMERO</th>
          <th scope="col">CLIENTE</th>
          <th scope="col">FORMA PAGAMENTO</th>
          <th scope="col">DATA COMPRA</th>
          <th scope="col" class="d-flex justify-content-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="pedido in listaPedidos" :key="pedido.id" scope="row">
          <th>
            {{ pedido.id }}
          </th>

          <td>
            {{ pedido.numero }}
          </td>

          <th>
            {{ pedido.clienteNomeRazaoSocial }}
          </th>

          <th>
            {{ pedido.formaPagamentoDescricao }}
          </th>

          <th>
            {{ formatar(pedido.dataCompra) }}
          </th>

          <td class="d-flex justify-content-end">
            <button
              class="btn btn-btn btn-primary m-2"
              @click="alterarPedido(pedido)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>

            <button
              class="btn btn-outline-danger m-2"
              @click="excluirPedido(pedido.id)"
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
            <option value="nome">Nome</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscarEstados" class="btn btn-success">
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import FormEstado from "./FormEstado.vue";
import Data from "../utils/Data"
import axios from "axios";
export default {
  components: {
    FormEstado,
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
    async buscarPedidos() {
      this.pedidoEscolhido = null;
      this.formVisible = false;
      //buscar a lista de estados no servidor
      // http://localhost:8080/estados
      const response = await axios.get(
        `http://localhost:8080/pedidos?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      console.log(response.data);
      this.listaPedidos = response.data.content;
      this.totalPages = response.data.totalPages;
      console.log(this.totalPages);
    },
    limpar() {
      this.pedidoEscolhido = null;
      this.formVisible = !this.formVisible;
    },
    novoPedido() {
      this.formVisible = !this.formVisible;
    },
    alterarPedido(pedido) {
      this.pedidoEscolhido = pedido;
      this.formVisible = true;
    },
    async excluirPedido(id) {
      // if(localStorage.getItem('token') === null) {
      //     alert("Usuário não identificado! Faça o login!!!");
      //     return;
      // }
      let config = {
        headers: {
          'Authorization': 'Bearer ' +localStorage.getItem('token')
        }
      }
      try{
          const response = await axios.delete(`http://localhost:8080/pedido/${id}`, config);
          console.log(response.data);
      }catch(error){
        if(error.response.status === 403){        
         alert("Usuário não identificado! Faça o login!!!");
        }else if(error.response.status === 400 ){
          alert(error.response.data.mensagem);     
        }else{
          alert(error.message);
        }
      }     
      this.buscarPedidos();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarPedidos();
    },formatar(data){
      return Data.formatoDMA(data);
    }
  },
  mounted() {
    this.buscarPedidos();
  },
};
</script>
