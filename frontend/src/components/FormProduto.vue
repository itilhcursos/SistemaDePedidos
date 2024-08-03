<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Produtos</h4>
    <hr />
<form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="Id Produto"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Descrição</label>
        <input
          class="form-control"
          type="text"
          v-model="descricao"
          placeholder="descricao"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Quantidade no Estoque</label>
        <input
          class="form-control"
          type="text"
          v-model="quantidadeEstoque"
          placeholder="Quantidade"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Preço Atual</label>
        <input
          class="form-control"
          type="text"
          v-model="PrecoUnidadeAtual"
          placeholder="Preço Atual"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Ativo</label>
        <select v-model="ativo" class="form-select">
            <option :value="true">SIM</option>
            <option :value="false">NÃO</option>
          </select>
        
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Descrição do produto deve ser preenchido!!</div>
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
export default {
  props: {
    propsProduto: Object,
    
  },
  data() {
    return {
      id: "",
      descricao: "",
      quantidadeEstoque:"",
      PrecoUnidadeAtual:"",
      ativo:"",
      isInvalido: false,
    };
  },
  methods: {
    async salvarProduto() {
      if (this.descricao === "") {
        this.isInvalido = true;
        return;
      }
      this.isInvalido = false;

      if (this.id === "") {

        const response = await axios.post("http://localhost:8080/produto", {
          id: this.id,
          descricao: this.descricao,
          quantidadeEstoque:this.quantidadeEstoque,
          PrecoUnidadeAtual:this.PrecoUnidadeAtual,
          ativo:this.ativo,
        });
        this.listaProdutos = response.data;
      } else {
       
        const response = await axios.put(
          `http://localhost:8080/produto/${this.id}`,
          {
            id: this.id,
            descricao: this.descricao,
            quantidadeEstoque:this.quantidadeEstoque,
            PrecoUnidadeAtual:this.PrecoUnidadeAtual,
            ativo:this.ativo,

          }
        );
        this.listaProdutos = response.data;
      }

      this.$emit("salvar_produto", {
        id: this.id,
        descricao: this.descricao,
        quantidadeEstoque:this.quantidadeEstoque,
          PrecoUnidadeAtual:this.PrecoUnidadeAtual,
          ativo:this.ativo,
      });

      this.id = "";
      this.descricao = "";
      this.quantidadeEstoque="";
      this.PrecoUnidadeAtual="";
      this.ativo="";
    },
    cancelar() {
      this.id = "";
      this.descricao = "";
      this.quantidadeEstoque="";
      this.PrecoUnidadeAtual="";
      this.ativo="";

      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsProduto) {
      this.id = this.propsProduto.id;
      this.descricao = this.propsProduto.descricao;
      this.quantidadeEstoque=this.propsProduto.quantidadeEstoque;
      this.precoUnidadeAtual=this.propsProduto.PrecoUnidadeAtual;
      this.ativo=this.propsProduto.ativo;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

