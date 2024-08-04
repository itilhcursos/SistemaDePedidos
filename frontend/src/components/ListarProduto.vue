<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>PRODUTO</h3>
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
            @salvar_produto="buscarProduto"
          />
        </div>
      </div>
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Descrição</th>
          <th scope="col">Quantidade em estoque</th>
          <th scope="col">Preço por unidade</th>
          <th scope="col">Ativo</th>
          <th scope="col" class="d-flex justify-content-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="produto in listaProduto" :key="produto.id" scope="row">
          <th>
            {{ produto.id }}
          </th>
          <td>
            {{ produto.descricao }}
          </td>
          <td>
            {{ produto.quantidadeEstoque }}
          </td>
          <td>
            {{'R$ ' + produto.precoUnidadeAtual.toString().replace(/\./g, ",") }}
          </td>
          <td>
            {{ produto.ativo == true ? "Sim" : "Não" }}
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
          <button @click.prevent="buscarProduto" class="btn btn-success">
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
import axios from "axios";
export default {
  components: {
    FormProduto,
  },
  data() {
    return {
      listaProduto: [],
      produtoEscolhido: null,
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
    async buscarProduto() {
      this.produtoEscolhido = null;
      this.formVisible = false;
      //buscar a lista de produtos no servidor
      // http://localhost:8080/produtos
      const response = await axios.get(
        `http://localhost:8080/produtos?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      this.listaProduto = response.data.content;
      this.totalPages = response.data.totalPages;
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
      const response = await axios.delete(`http://localhost:8080/produto/${id}`);
      this.buscarProduto();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarProduto();
    },
  },
  mounted() {
    this.buscarProduto();
  },
};
</script>
