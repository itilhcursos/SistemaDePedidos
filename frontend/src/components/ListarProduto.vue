<template>
  <div class="container bg-light p-4"> <!-- Fundo claro aplicado ao container -->
    <div class="row">
      <div class="col-10">
        <h3>PRODUTOS</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoProduto" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
    </div>

    <div v-if="formVisible">
      <FormProduto 
        :propsProduto="produtoEscolhido" 
        @cancelar="limpar"
        @salvar_produto="buscarProdutos" 
      />
    </div>

    <!-- Exibindo os produtos em cards -->
    <div class="row mt-4" v-if="!formVisible">
      <div class="col-md-4 mb-4" v-for="produto in listaProdutos" :key="produto.id">
        <div class="card bg-dark text-white" style="border-radius: 10px;">
          <img :src="produto.urlImagem" class="card-img-top" alt="Imagem do produto" height="200px">
          <div class="card-body">
            <h5 class="card-title">{{ produto.descricao }}</h5>
            <p class="card-text">ID: {{ produto.id }}</p>
            <p class="card-text">Quantidade: {{ formatarQuantidade(produto.quantidadeEstoque) }}</p>
            <p class="card-text">Preço: {{ formatarPreco(produto.precoUnidadeAtual) }}</p>
            <p class="card-text">Ativo: {{ formatarLogico(produto.ativo) }}</p>
            <div class="d-flex justify-content-between">
              <button class="btn btn-primary" @click="alterarProduto(produto)">
                <i class="bi bi-clipboard-pulse"></i> Alterar
              </button>
              <button class="btn btn-outline-danger" @click="excluirProduto(produto.id)">
                <i class="bi bi-clipboard2-minus"></i> Excluir
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Paginação -->
    <div v-if="!formVisible">
      <hr />
      <div class="container">
        <div class="row d-flex justify-content-center">
          <div class="col-auto">
            <button v-for="pagina in totalPages" :key="pagina" @click.prevent="irPara(pagina)" class="btn btn-light ms-1">
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
              <i class="bi bi-binoculars"></i> Buscar
            </button>
          </div>
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
import axios from "axios";

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

      const response = await axios.get(
        `http://localhost:8080/produtos?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
      );
      this.listaProdutos = response.data.content;
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
      const config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };
      await axios.delete(`http://localhost:8080/produto/${id}`, config);
      this.buscarProdutos();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarProdutos();
    },
    formatarLogico(valor) {
      return Logico.toSimNao(valor);
    },
    formatarPreco(valor) {
      return Monetario.toTela(valor);
    },
    formatarQuantidade(valor) {
      return Decimal.toTela(valor);
    },
  },
  mounted() {
    this.buscarProdutos();
  },
};
</script>

<style scoped>
.card {
  border-radius: 10px;
}

.card-title {
  font-weight: bold;
  font-size: 1.2rem;
}

.card-text {
  font-size: 1rem;
}

.card-body {
  padding: 1.2rem;
  background-color: black;
}

.card-img-top {
  object-fit: cover;
}

.btn {
  font-size: 0.9rem;
}
</style>
