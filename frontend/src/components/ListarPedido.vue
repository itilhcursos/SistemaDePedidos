<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>Pedidos</h3>
      </div>
<<<<<<< HEAD
      <!-- <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoEstado" class="btn btn-success">
=======
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novo" class="btn btn-success">
>>>>>>> developer
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
<<<<<<< HEAD
          <FormEstado
            v-if="formVisible"
            :propsEstado="estadoEscolhido"
            @cancelar="limpar"
            @salvar_estado="buscarEstados"
          />
        </div>
      </div> -->
=======
          <FormPedido
            v-if="formVisible"
            :propsPedido="pedidoEscolhido"
            @cancelar="limpar"
            @salvar_pedido="buscar"
          />
        </div>
      </div> 
>>>>>>> developer
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Número</th>
          <th scope="col">Cliente</th>
          <th scope="col">Forma Pagamento</th>
<<<<<<< HEAD
          <th scope="col">data Compra</th>
          <th scope="col">data Entrega</th>
          <th scope="col">data Pagamento</th>
          <th scope="col" class="d-flex justify-content-end">Ações</th>
=======
          <th scope="col">Data Compra</th>
          <th scope="col">Data Entrega</th>
          <th scope="col">Data Pagamento</th>
          <th scope="col" class="d-flex justify-content-end">Itens</th>
>>>>>>> developer
        </tr>
      </thead>
      <tbody>
        <tr v-for="pedido in listaPedidos" :key="pedido.id" scope="row">
          <th>
            {{ pedido.id }}
          </th>
<<<<<<< HEAD
          <td>
            {{ pedido.numero }}
          </td>
          <td>
            {{ pedido.clienteNome }}
          </td>          
          <td>
            {{ pedido.formaPagamentoDescricao }}
          </td>          
          <td>
            {{ pedido.dataCompra }}
          </td>          
          <td>
            {{ pedido.dataEntrega }}
          </td>  
          <td>
            {{ pedido.dataPagamento }}
          </td>         
          <td class="d-flex justify-content-end">
=======
          <th>
            {{ pedido.numero }}
          </th>
          <td>
            {{ pedido.clienteNomeRazaoSocial }}
          </td>
          <td>
            {{ pedido.formaPagamentoDescricao }}
          </td>
          <td>
            {{ formatar(pedido.dataCompra) }}
          </td>
          <td>
            {{ formatar(pedido.dataEntrega) }}
          </td>
          <td>
            {{ formatar(pedido.dataPagamento) }}
          </td>
          <td class="d-flex justify-content-end">            
>>>>>>> developer
            <button
              class="btn btn-btn btn-primary m-2"
              @click="alterar(pedido)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>
<<<<<<< HEAD

            <button
              class="btn btn-outline-danger m-2"
              @click="exclui(pedido.id)"
            >
              <i class="bi bi-clipboard2-minus"></i> Excluir
            </button>
=======
            <table class="table table-dark table-striped">
              <thead>
                <tr>
                  <th scope="col">Itens</th>
                  <th scope="col">Descricão</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="itens in pedido.itens" :key="itens.id" scope="row">
                  <th>
                    <img :src=itens.produtoUrlImagem height="50px">
                  </th>
                  <th>
                    {{ itens.produtoDescricao }}
                  </th>
                </tr>
              </tbody>
            </table>
>>>>>>> developer
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
<<<<<<< HEAD
            <option value="nome">Nome</option>
=======
            <option value="cliente.nomeRazaoSocial">Nome RazaoSocial</option>
            <option value="formaPagamento.descricao">FormaPagamento Descricao</option>
>>>>>>> developer
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
<<<<<<< HEAD
          <button @click.prevent="buscarEstados" class="btn btn-success">
=======
          <button @click.prevent="buscar" class="btn btn-success">
>>>>>>> developer
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
<<<<<<< HEAD
// import FormEstado from "./FormEstado.vue";
import pedidoService from "@/services/pedidoService";
export default {
  components: {
   // FormEstado,
=======
import FormPedido from "./FormPedido.vue";
import Data from "../utils/Data"
import pedidoService from "@/services/pedidoService";
export default {
  components: {
    FormPedido,
>>>>>>> developer
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
      const response = await pedidoService.listar(this.pageNumber, this.pageSize,this.direction, this.property);     
      this.listaPedidos = response.content;
      this.totalPages = response.totalPages;   
    },
    limpar() {
      this.pedidoEscolhido = null;
      this.formVisible = !this.formVisible;
    },
<<<<<<< HEAD
    novoEstado() {
      this.formVisible = !this.formVisible;
    },
    alterar(estado) {
      this.pedidoEscolhido = estado;
=======
    novo() {
      this.formVisible = !this.formVisible;
    },
    alterar(pedido) {
      this.pedidoEscolhido = pedido;
>>>>>>> developer
      this.formVisible = true;
    },
    async excluir(id) {
      try{
          const response = await pedidoService.apagar(id);
          console.log(response);
      }catch(error){
        if(error.response.status === 403){        
         alert("Usuário não identificado! Faça o login!!!");
        }else if(error.response.status === 400 ){
          alert(error.response.data.mensagem);     
        }else{
          alert(error.message);
        }
      }     
      this.buscar();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscar();
    },
<<<<<<< HEAD
=======
    formatar(data){
      return Data.formatoDMA(data);
    }
>>>>>>> developer
  },
  mounted() {
    this.buscar();
  },
};
</script>
