<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} FormaPagamento</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id forma-pagamento" />
      </div>
      <div class="mb-3">
        <label class="form-label">Descrição</label>
        <input class="form-control" type="text" v-model="descricao" placeholder="Descricao" />
      </div>
      <div class="mb-3">
        <label class="form-label">Situação</label>
        <select class="form-control"  v-model="ativo">
          <option value="true">Ativo</option>
          <option value="false">Inativo</option>
        </select>
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Descrição deve ser preenchido!!</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarFormaPagamento">
          <i class="bi bi-clipboard2-check"></i>
          {{ getAcao }}
        </button>
        <button class="btn btn-warning m-2" type="submit" v-on:click.prevent="cancelar">
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
    propsFormaPagamento: Object,
  },
  data() {
    return {
      id: "",
      descricao: "",
      ativo: "",
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
        const response = await axios.post("http://localhost:8080/forma-pagamento", {
          id: this.id,
          descricao: this.descricao,
          ativo: this.ativo,
        });
        console.log(response.data);
      } else {
        // alterar pelo PUT da API
        const response = await axios.put(
          `http://localhost:8080/forma-pagamento/${this.id}`,
          {
            id: this.id,
            descricao: this.descricao,
            ativo: this.ativo
          }
        );
        console.log(response.data);
      }

      this.$emit("salvar-forma-pagamento", {
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
    console.log(this.propsFormaPagamento)
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