<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Item Pedido</h4>
        <hr />
        <form>
            <div class="mb-3">
                <label class="form-label">Id</label>
                <input
                class="form-control"
                type="text"
                v-model="id"
                :disabled="true"
                placeholder="Id ItemPedido"
                />
            </div>
            <div class="mb-3">
          <label class="form-label">Pedido</label>
          <select v-model="pedidoSelected" class="form-select">
              <option v-for="pedido in pedidos" :value="pedido.id" :key="pedido.id">
                {{pedido.id }}
              </option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Produto</label>
          <select v-model="produtoSelected" class="form-select">
              <option v-for="produto in produtos" :value="produto.id" :key="produto.id">
                {{ produto.descricao }}
              </option>
          </select>
        </div>
            <div class="mb-3">
                <label class="form-label">Quantidade em Estoque</label>
                <input
                class="form-control"
                type="text"
                v-model="quantidadeEstoque"
                placeholder="Quantidade em Estoque"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Preço Atual</label>
                <input
                class="form-control"
                type="text"
                v-model="precoUnidadeAtual"
                placeholder="Preço Atual"
                />
            </div>
            <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">{{ mensagem }}</div>
            </div>
            <div class="mb-3 d-flex justify-content-end">

                <button
                class="btn btn-primary m-2"
                type="submit"
                v-on:click.prevent="salvarProduto"
                >
                    <i class="bi bi-clipboard2-check"></i>
                    {{ getAcao }}
                </button>

                <button
                class="btn btn-warning m-2"
                type="submit"
                v-on:click.prevent="cancelar"
                >
                    <i class="bi bi-clipboard2-x"></i>
                    Cancelar
                </button>
            </div>
        </form>
    </div>
</template>

<script>
import axios from "axios";
import pedidoService from "@/services/pedidoService";
import produtoService from "@/services/produtoService";
export default {


    props: {
        propsItemPedido: Object,
    },

    data() {
        return {
            id: "",
            quantidadeEstoque: "",
            precoUnidadeAtual: "",
            pedidoSelected:"",
            pedidos:[],
            produtoSelected:"",
            produtos:[],
            mensagem:"",
            isInvalido: false,
        };
    },


    methods: {
        async salvarItemPedido() {
            if (this.descricao === "") {
                this.isInvalido = true;
                   this.mensagem = "Quantidade ou Preço dos itens pedidos não pode ser vazia."
                return;
            }
            this.isInvalido = false;
            let config = {
                headers: {
                'Authorization': 'Bearer ' +localStorage.getItem('token')
                }
            }
            try{
            if (this.id === "") {
                const response = await axios.post("http://localhost:8080/itemPedido", {
                    id: this.id, 
                    quantidadeEstoque: this.quantidadeEstoque, 
                    precoUnidadeAtual: this.precoUnidadeAtual, 
                    pedidoId:this.pedidoSelected,
                    produtoId:this.produtoSelected,                    
                }, config);
                this.listaitemPedido = response.data;
            } else {
                const response = await axios.put(`http://localhost:8080/itemPedido/${this.id}`, 
                {
                    id: this.id,
                    quantidadeEstoque: this.quantidadeEstoque, 
                    precoUnidadeAtual: this.precoUnidadeAtual, 
                    pedidoId:this.pedidoSelected,
                    produtoId:this.produtoSelected,  
                }, config);
                this.listaitemPedido = response.data;
            }

            this.$emit("salvar_itemPedido", {
                id: this.id,
                quantidadeEstoque: this.quantidadeEstoque, 
                precoUnidadeAtual: this.precoUnidadeAtual, 
                pedidoId:this.pedidoSelected,
                produtoId:this.produtoSelected,  
            });
            this.id = "";
            this.quantidadeEstoque = "";
            this.precoUnidadeAtual = ""; 
            this.pedidoSelected = "";
            this.produtoSelected = "";
        }catch(error){ 
     this.isInvalido = true;
     if(error.response.status === 403){        
       this.mensagem = "Usuário não identificado! Faça o login!!!";
     }else if(error.response.status === 400 &&
              error.response.data.exception === 'PedidoDuplicadoException'){
       this.mensagem = error.response.data.mensagem;
     }else if(error.response.status === 400 &&
              error.response.data.exception === 'ProdutoDuplicadoException'){
       this.mensagem = error.response.data.mensagem;
    }else if(error.response.status === 400 &&
              error.response.data.exception === 'ItemPedidoDuplicadoException'){
       this.mensagem = error.response.data.mensagem;
    }else if(error.response.status === 400 &&
              error.response.data.exception === 'ItemPedidoEstoqueNegativoException'){
       this.mensagem = error.response.data.mensagem;
    }else if(error.response.status === 400 &&
              error.response.data.exception === 'ItemPedidoPrecoNegativoException'){
       this.mensagem = error.response.data.mensagem;
     }else{
       this.mensagem = error.message;
        }
    }},
    cancelar(){
                 this.id =""; 
                 this.quantidadeEstoque = "";
                 this.precoUnidadeAtual = ""; 
                this.pedidoSelected = "";
                this.produtoSelected = "";
                
            this.$emit("cancelar", true);},
            
            async buscarPedidos(){
      const response = await pedidoService.listar(1,1000, 'ASC', 'id');
      this.pedido = response.content;
    },   
    async buscarProdutos(){
      const response = await produtoService.listar(1,1000, 'ASC', 'id');
      this.produto = response.content;
    }},
    mounted(){
        if (this.propsProduto) {
            this.id = this.propsItemPedido.id;
            this.quantidadeEstoque = this.propsItemPedido.quantidadeEstoque;
            this.precoUnidadeAtual = this.propsItemPedido.precoUnidadeAtual; 
            this.pedidoSelected = this.propsItemPedido.pedidoId;
            this.produtoSelected = this.propsItemPedido.produtoId;
        }
    },


    computed: {
        getAcao(){
            return this.id === "" ? "Incluir" : "Alterar";
        },
    }
}
</script>
