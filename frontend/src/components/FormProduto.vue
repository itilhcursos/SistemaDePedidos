<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Produto</h4>
    <hr />
    <form>
      <div class="row">
        <div class="mb-3 col-3">
          <label class="form-label">Id</label>
          <input
            class="form-control"
            type="text"
            v-model="id"
            :disabled="true"
            placeholder="Id do produto"
          />
        </div>
        <div class="mb-3 col-9">
          <label class="form-label">Descrição</label>
          <input
            class="form-control"
            type="text"
            v-model="descricao"
            placeholder="Descrição do produto"
          />
        </div>
      </div>

      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">A descrição deve ser preenchida!!</div>
      </div>

      <div class="row">
        <div class="mb-3 col-4">
          <label class="form-label">Quantidade em estoque</label>
          <input
            class="form-control"
            type="number"
            v-model="quantidadeEstoque"
            placeholder="Quantidade em estoque"
          />
        </div>
        <div class="mb-3 col-4">
          <label class="form-label">Preço por unidade</label>
          <input
            class="form-control"
            type="number"
            v-model="precoUnidadeAtual"
            placeholder="Preço por unidade"
          />
        </div>
        <div class="mb-3 col-4">
          <label class="form-label">Ativo</label>
          <select
            class="form-control"
            v-model="ativo"
          >
            <option value="" disabled>Selecione uma opção</option>
            <option value="true">Sim</option>
            <option value="false">Não</option>
          </select>
        </div>
      </div>

      <div v-if="isInvalidoAtivo" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Informe se o produto está ativo!!</div>
      </div>
      <div v-if="isInvalidoQuantidadeEstoque" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Informe uma quantidade para o produto maior ou igual a zero!!</div>
      </div>
      <div v-if="isInvalidoPrecoUnidadeAtual" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Informe um preço para o produto maior ou igual a zero!!</div>
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
      ativo: "",
      quantidadeEstoque: "",
      precoUnidadeAtual: "",
      isInvalido: false,
      isInvalidoAtivo: false,
      isInvalidoQuantidadeEstoque: false,
      isInvalidoPrecoUnidadeAtual: false,
    };
  },
  methods: {
    async salvarProduto() {
      if (this.descricao === "") {
        this.isInvalido = true;
        return;
      } else {
        this.isInvalido = false;
      }

      if (this.quantidadeEstoque < 0 || this.quantidadeEstoque == "" || this.quantidadeEstoque == undefined) {
        this.isInvalidoQuantidadeEstoque = true;
        return;
      } else {
        this.isInvalidoQuantidadeEstoque = false;
      }

      if (this.precoUnidadeAtual < 0 || this.precoUnidadeAtual == "" || this.precoUnidadeAtual == undefined) {
        this.isInvalidoPrecoUnidadeAtual = true;
        return;
      } else {
        this.isInvalidoPrecoUnidadeAtual = false;
      }

      if (this.ativo === "" || this.ativo == null || this.ativo == undefined) {
        this.isInvalidoAtivo = true;
        return;
      } else {
        this.isInvalido = false;
      }

      this.isInvalido = false;
      this.isInvalidoAtivo = false;
      this.isInvalidoQuantidadeEstoque = false;
      this.isInvalidoPrecoUnidadeAtual = false;

      if (this.id === "") {
        //incluir pelo POST da API
        const response = await axios.post("http://localhost:8080/produto", {
          id: this.id,
          descricao: this.descricao,
          ativo: this.ativo,
          quantidadeEstoque: this.quantidadeEstoque,
          precoUnidadeAtual: this.precoUnidadeAtual,
        });
        this.listaProdutos = response.data;
      } else {
        // alterar pelo PUT da API
        const response = await axios.put(
          `http://localhost:8080/produto/${this.id}`,
          {
            id: this.id,
            descricao: this.descricao,
            ativo: this.ativo,
            quantidadeEstoque: this.quantidadeEstoque,
            precoUnidadeAtual: this.precoUnidadeAtual,
          }
        );
        this.listaProdutos = response.data;
      }

      this.$emit("salvar_produto", {
        id: this.id,
        descricao: this.descricao,
        ativo: this.ativo,
      });

      this.id = "";
      this.descricao = "";
      this.ativo = "";
    },
    cancelar() {
      this.id = "";
      this.descricao = "";
      this.ativo = "";
      this.quantidadeEstoque = "";
      this.precoUnidadeAtual = "";
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsProduto) {
      this.id = this.propsProduto.id;
      this.descricao = this.propsProduto.descricao;
      this.ativo = this.propsProduto.ativo;
      this.quantidadeEstoque = this.propsProduto.quantidadeEstoque;
      this.precoUnidadeAtual = this.propsProduto.precoUnidadeAtual;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

