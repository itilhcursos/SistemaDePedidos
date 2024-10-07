<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <div class="row">
        <div class="col">
          <label class="form-label">Id</label>
          <input class="form-control" type="text"  v-model="id" :disabled ="true"  placeholder="Id"/>
        </div>

        <div class="col">
          <label class=" form-label" >Numero</label>
          <input  class="form-control" type="text" v-model="numero" placeholder="Numero" >
        </div>

        <div class="col">
          <label class=" form-label" >Forma de Pagemento</label>
          <v-select class="meu-select" v-model="selectedFormaPagamento" :filterable="false"
            :options="optionsFormaPagamento" @search="onSearchFormaPagamento">
                <template v-slot:no-options>
                  Não encontrado.
                </template>
                <template v-slot:option="option">
                  {{ option.descricao }}
                </template>
                <template v-slot:selected-option="option">
                  {{ option.descricao }}
                </template>
                
          </v-select>
        </div>
      </div>
      <div class="row">
          <div class="col">
            <label class="form-label">Data Compra</label>
            <input class="form-control" type="date" v-model="dataCompra" placeholder="Data Compra" />
          </div>
          <div class="col">
            <label class="form-label">Data Entrega</label>
            <input class="form-control" type="date" v-model="dataEntrega" placeholder="Data Entrega" />
          </div>
          <div class="col">
            <label class="form-label">Data Pagamento</label>
            <input class="form-control" type="date" v-model="dataPagamento" placeholder="Data Pagamento" />
          </div>
      </div>
      
      <div class="mb-3">
          <label class="form-label">Cliente</label>
          <v-select  class="meu-select" label="Cliente"  v-model="clienteSelecionado" :filterable="false" 
              placeholder="Cliente" :options="clientes" @search="onSearchClientes">

                <template v-slot:no-options>
                    Sem clientes para exibir.
                </template>
                <template v-slot:option="option">
                    {{ option.nomeRazaoSocial }}
                </template>
                <template v-slot:selected-option="option">
                    {{ option.nomeRazaoSocial }}
                </template>
          </v-select>
      </div>
      <div>
        <table>
          <thead>

          </thead>
          <tbody>

          </tbody>
        </table>
      </div>
      <div class="row">
          <div class="col">
            <label class=" form-label"> Novo Produto</label>
            <v-select class="meu-select" label="Produto" :filterable="false" placeholder="Produto"
              v-model="produtoSelecionado" :options="produtos" @search="onSearchProdutos">
                  
                <template v-slot:no-options>
                    Sem produtos para exibir.
                </template>
                <template v-slot:option="option">
                    <img  class="mini" :src='option.urlImagem'/> 
                    {{ option.descricao }}
                </template>
                <template v-slot:selected-option="option">
                    <img class="mini" :src='option.urlImagem'/> 
                    {{ option.descricao }}
                </template>
            </v-select>
          </div>
          <div class="col-2">
              <label class="form-label" >Quantidade</label>
              <input  class="form-control " type="number" v-model="quantidadeItens" placeholder="0">
          </div>

          <div class="col-2 position-relative">
              <button  class="btn btn-primary position-absolute top-50 start-50 translate-middle" type="submit">
                <i class="bi bi-clipboard2-check" ></i>Incluir
              </button>
          </div>
      </div>

        <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
          <i class="bi bi-exclamation-triangle-fill"></i>
          <div class="p-2">{{ mensagem }}</div>
        </div>

        <div class="mb-3 d-flex justify-content-end">
          <button
            class="btn btn-primary m-2"
            type="submit"
            v-on:click.prevent="salvarPedido"
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
 
import pedidoService from '@/services/pedidoService';
import produtoService from '@/services/produtoService';
import formaPagamentoService from '@/services/formaPagamentoService';
import clienteService from '@/services/clienteService';
  export default {
    props: {
      propsPedido: Object,
    },
    data() {
      return {
        id: "",
        clienteId:"",
        clienteNomeRazaoSocial:"",
        formaPagamentoId:"",
        formaPagamentoDescricao:"",
        numero: "",
        dataCompra: "",
        dataEntrega: "",
        dataPagamento: "",
        itens:[],
        
        selectedFormaPagamento:null,
        optionsFormaPagamento:[],
        clienteSelecionado:null,
        clientes:[],
        isInvalido: false,
        mensagem: '',
        produtoSelecionado:"",
        produtos:[],
      };
    },
    methods: {
      async onSearchProdutos(search, loading){
        if(search.length){
          loading(true);
          await produtoService.buscar(search).then((response) => {
            console.log(response);
            this.produtos = response.content;
            loading(false); 
          });
        }
      },

      async onSearchFormaPagamento(search, loading){
        if(search == "")
          return;
          loading(true);
          await formaPagamentoService.buscar(search).then((response) => {
            console.log(response); 
            this.optionsFormaPagamento = response.content;
            loading(false); 
          });
      },
      async onSearchClientes(search, loading){
        if(search == "")
          return;
          loading(true);
          await clienteService.buscar(search).then((response) => {
            console.log(response); 
            this.clientes = response.content;
            loading(false); 
          });
      },
      getDados(){
        return{
            id: this.id,
            clienteNomeRazaoSocial: this.clienteNomeRazaoSocial,
        }
      },
      async salvarPedido() {
        if (this.clienteNomeRazaoSocial === "") {
          this.isInvalido = true;
          this.mensagem = "O Nome deve ser preenchido!!";
          return;
        } 
        this.isInvalido = false;

        try{
          if (this.id === "") {
            //incluir pelo POST da API
            const response = await pedidoService.criar(
            this.getDados());
            this.listaPedidos = response.data;
          } else {
            // alterar pelo PUT da API
            const response = await pedidoService.atualizar(
              this.id,
              this.getDados()
            );
            this.listaPedidos = response.data;
          }
          this.$emit("salvar_pedido", {
            id: this.id,
            clienteNomeRazaoSocial: this.clienteNomeRazaoSocial,
          });

          this.id = "";
          this.clienteNomeRazaoSocial = "";
        
        }catch(error){
        
          // console.log (error);
          // console.log (error.response.status);
          // console.log (error.response.data.exception);
          this.isInvalido = true;
          if(error.response.status === 403){    // o erro 403 é quando esqueço de fazer a autenticação    
            this.mensagem = "Usuário não identificado! Faça o login!!!";
          }else if(error.response.status === 400 &&
                   error.response.data.exception === 'EstadoDuplicadoException'){ 
            this.mensagem = error.response.data.mensagem;
          }else{
            this.mensagem = error.message;
          }
        }
      },

      cancelar(){
        this.id = "";
        this.clienteNomeRazaoSocial = "";
        this.$emit("cancelar", true);
      },
    },   
    mounted() {
      if (this.propsPedido) {
        this.id = this.propsEstado.id;
        this.clienteNomeRazaoSocial = this.propsEstado.nome;
      }
    },
    computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },
    },

  };

</script>
<style>
  .meu-select{
    width: 100%;
    font-size: 1.0em;
    color:#252525;
    background: #fbf4f4;
    border-radius: 0.375rem;
  }
  .mini {
    height: auto;
    max-width: 2.5rem;
    margin-right: 1rem;
  }

</style>