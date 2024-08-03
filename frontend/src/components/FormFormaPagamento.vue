<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Forma de Pagamento</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">ID</label>
        <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="ID Forma de Pagamento"/>
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input class="form-control" type="text" v-model="descricao" placeholder="Forma de Pagamento"/>
      </div>
      <div class="mb-3">
          <label class="form-label">Ativo</label>
          <select class="form-control" type="boolean" v-model="ativo">
            <option :value="true">Sim</option>
            <option :value="false">Não</option>
          </select>
        </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Nome deve ser preenchido!!</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit" @click.prevent="salvarFormaPagamento"><i class="bi bi-clipboard2-check"></i>{{ getAcao }}</button>
        <button class="btn btn-warning m-2" type="submit" @click.prevent="cancelar"><i class="bi bi-clipboard2-x"></i>Cancelar</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: {
    propsFormaPagamento: Object,
  },
  data() {
    return {
      id: "",
      descricao: "",
      ativo: true,
      isInvalido: false,
    };
  },
  methods: {
    async salvarFormaPagamento() {
      if (this.descricao === "") {
        this.isInvalido = true;
        return;
      }
      this.isInvalido = false;

      if (this.id === "") {
        //incluir pelo POST da API
        const response = await axios.post("http://localhost:8080/forma-pagamento", {
          id: this.id,
          descricao: this.descricao,
          ativo: this.ativo,
        });
        this.listaFormasPagamento = response.data;
      } else {
        // alterar pelo PUT da API
        const response = await axios.put(
          `http://localhost:8080/forma-pagamento/${this.id}`, {
            id: this.id,
            descricao: this.descricao,
            ativo: this.ativo,
          }
        );
        this.listaFormasPagamento = response.data;
      }

      this.$emit("salvar_forma_pagamento", {
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
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsFormaPagamento) {
      this.id = this.propsFormaPagamento.id;
      this.descricao = this.propsFormaPagamento.descricao;
      this.ativo = this.propsFormaPagamento.ativo;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

