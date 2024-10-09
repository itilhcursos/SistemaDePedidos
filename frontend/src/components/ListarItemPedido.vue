<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>Itens de Pedidos </h3>
      </div>
      <!-- <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoItemPedido" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div> -->
      <div class="row">
        <div>
          <FormProduto v-if="formVisible" :propsItemPedido="pedidoItemEscolhido" @cancelar="limpar"
            @salvar_itemPedido="buscarItensPedido" />
        </div>
      </div>
    </div>
    
    <div v-for="itemPedido in listaItensPedido" :key="itemPedido.id" scope="row">
    <table class="table table-dark table-striped" >
      <thead  v-if="novoPedido(itemPedido.pedidoId)">
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Id Pedido</th>
          <th scope="col">Id Produto</th>
          <th scope="col">Produto Descrição</th>
          <th scope="col">Quantidade</th>
          <th scope="col">Preço Unitário</th>
          <th scope="col">Total</th>
        </tr>
      </thead>
      <thead  v-if="!novoPedido(itemPedido.pedidoId)">
        <tr>
          <th scope="col" colspan="6" ></th>
          <!-- <th scope="col">Id Pedido</th>
          <th scope="col">Id Produto</th>
          <th scope="col">Produto Descrição</th>
          <th scope="col">Quantidade</th>
          <th scope="col">Preço Unitário</th>
          <th scope="col">Total</th> -->
        </tr>
      </thead>
      <tbody>
          
          <tr>
            
            <td>{{ itemPedido.id }}</td>
            <td>{{ itemPedido.pedidoId}}</td>
            <td>{{ itemPedido.produtoId}}</td>
            <td>{{ itemPedido.produtoDescricao}}</td>
            <td>{{ itemPedido.quantidadeEstoque }}</td>
            <td>{{ itemPedido.precoUnidadeAtual }}</td>
            <td>{{ itemPedido.quantidadeEstoque * itemPedido.precoUnidadeAtual }}</td>
          </tr>
        </tbody>
      </table>
    </div>
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
            <option value="pedido.id">Pedido</option>
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
          <button @click.prevent="buscarItensPedido" class="btn btn-success">
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import itemPedidoService from '@/services/itemPedidoService';

export default {
  data() {
    return {
      itensPedido: [],
      pedidoItemEscolhido: null,
      auxPedidoId: 0,
      formVisible: false,
      pageNumber: 1,
      pageSize: 10,
      direction: "ASC",
      property: "id",
      totalPages: 0,
    };
  },
  methods: {
    async buscarItensPedido() {
      this.pedidoItemEscolhido = null;
      this.formVisible = false;
      const response = await itemPedidoService.listar(this.pageNumber, this.pageSize,this.direction, this.property);     
      this.listaItensPedido = response.content;
      this.totalPages = response.totalPages; 
     
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarItensPedido();
    },
    novoPedido(id){
      const valor = id != this.auxPedidoId;
      this.auxPedidoId = id;
      return valor;
    }
  },
  mounted() {
    this.buscarItensPedido(); 
  },
};
</script>

