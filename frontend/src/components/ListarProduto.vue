<template>
    <div class="container">
      <div class="row">
        <div class="col-10">
          <h3>PRODUTOS</h3>
        </div>
        <div class="col-2 d-flex justify-content-end">
          <button v-if="!formVisible" @click="novoProduto" class="btn btn-success">
            <i class="bi bi-clipboard-plus"></i> Novo
          </button>
        </div>
        <div class="row">
          <div>
            <FormProduto
              v-if="formVisible"
              :propsProduto="produtoEscolhido"
              @cancelar="limpar"
              @salvar_produto="buscarProdutos"
            />
          </div>
        </div>
      </div>
  
      <table class="table table-dark table-striped" v-if="!formVisible">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Imagem</th>
            <th scope="col">Descrição</th>
            <th scope="col">Quantidade</th>
            <th scope="col">Preço</th>
            <th scope="col">Ativo</th>
            <th scope="col" class="d-flex justify-content-end">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="produto in listaProdutos" :key="produto.id" scope="row">
            <th>
              {{ produto.id }}
            </th>
            <td>
              <img :src=produto.urlImagem height ="100 px">
            </td>
            <td>
              {{ produto.descricao }}
            </td>
            <td>
              {{ formatarQuantidade(produto.quantidadeEstoque) }}
            </td>
            <td>
              {{ formatarPreco(produto.precoUnidadeAtual) }}
            </td>
            <td>
              {{ formatarLogico(produto.ativo) }}
            </td>
            <td class="d-flex justify-content-end">
              <button
                class="btn btn-btn btn-primary m-2"
                @click="alterarProduto(produto)"
              >
                <i class="bi bi-clipboard-pulse"></i> Alterar
              </button>
  
              <button
                class="btn btn-outline-danger m-2"
                @click="excluirProduto(produto.id)"
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
            <button @click.prevent="buscarProdutos" class="btn btn-success">
              <i class="bi bi-binoculars"></i>
              Buscar
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  
  <script>
  import FormProduto from "./FormProduto.vue";
  import Logico from "@/utils/Logico.js";
  import Monetario from "@/utils/Monetario.js";
  import Decimal from "@/utils/Decimal.js";
  import produtoService from "@/services/produtoService";
  export default {
    components: {
      FormProduto,
    },
    data() {
      return {
        listaProdutos: [],
        produtoEscolhido: null,
        formVisible: false,
        pageNumber: 1,
        pageSize: 10,
        direction: "ASC",
        property: "id",
        totalPages: 0,
      };
    },
    methods: {
      async buscarProdutos() {
        this.produtoEscolhido = null;
        this.formVisible = false;
  
        const response = await produtoService.listar(this.pageNumber, this.pageSize, this.direction, this.property);
    
        this.listaProdutos = response.content;
        this.totalPages = response.totalPages;
  
      },
      limpar() {
        this.produtoEscolhido = null;
        this.formVisible = !this.formVisible;
      },
      novoProduto() {
        this.formVisible = !this.formVisible;
      },
      alterarProduto(produto) {
        this.produtoEscolhido = produto;
        this.formVisible = true;
      },
      async excluirProduto(id) {
        try{
        const response = await produtoService.apagar(id);
        console.log(response.data);
        }catch(error){
          if(error.response.status === 403){    // o erro 403 é quando esqueço de fazer a autenticação    
            alert( "Usuário não identificado! Faça o login!!!");
          }else if(error.response.status === 400){ 
            alert(error.response.data.mensagem);
          }else{
            alert( error.message);
          }
        }
        this.buscarProdutos();
      },
      irPara(pagina) {
        this.pageNumber = pagina;
        this.buscarProdutos();
      },
      formatarLogico(valor){
        return Logico.toSimNao(valor);
      },
      formatarPreco(valor){
        return Monetario.toTela(valor);
      },
      formatarQuantidade(valor){
        return Decimal.toTela(valor);
      }
    },
    mounted() {
      this.buscarProdutos();
    },
  };
  </script>
  