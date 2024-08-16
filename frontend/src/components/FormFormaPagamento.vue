<template>
  <div class="container">
<<<<<<< HEAD
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Forma de Pagamento</h4>
=======
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} forma de pagamento</h4>
>>>>>>> develop
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
<<<<<<< HEAD
          placeholder="Id forma-de-pagamento"
=======
          placeholder="ID Pagamento"
>>>>>>> develop
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Descrição</label>
        <input
          class="form-control"
          type="text"
          v-model="descricao"
          placeholder="Descrição"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Ativo</label>
          <select v-model="ativo" class="form-select">
<<<<<<< HEAD
            <option :value="true">true</option>
            <option :value="false">false</option>
=======
            <option :value="true">Sim</option>
            <option :value="false">Não</option>
>>>>>>> develop
          </select>
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
<<<<<<< HEAD
        <div class="p-2">Os campos acima devem ser preenchidos!!</div>
=======
        <div class="p-2">Descrição e ativo devem ser preenchidos!</div>
>>>>>>> develop
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button
          class="btn btn-primary m-2"
          type="submit"
          v-on:click.prevent="salvarFormaPagamento"
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
<<<<<<< HEAD
  
=======

>>>>>>> develop
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
<<<<<<< HEAD
  
=======

>>>>>>> develop
      if (this.id === "") {
        //incluir pelo POST da API
        const response = await axios.post("http://localhost:8080/forma-pagamento", {
          id: this.id,
          descricao: this.descricao,
<<<<<<< HEAD
          ativo: this.ativo,
=======
          ativo: this.ativo
>>>>>>> develop
        });
        this.listaFormasPagamento = response.data;
      } else {
        // alterar pelo PUT da API
        const response = await axios.put(
          `http://localhost:8080/forma-pagamento/${this.id}`,
          {
            id: this.id,
            descricao: this.descricao,
<<<<<<< HEAD
            ativo: this.ativo,
=======
            ativo: this.ativo
>>>>>>> develop
          }
        );
        this.listaFormasPagamento = response.data;
      }
<<<<<<< HEAD
  
      this.$emit("salvar_formaPagamento", {
        id: this.id,
        descricao: this.descricao,
        ativo: this.ativo,
      });
  
=======

      this.$emit("salvar_formaPagamento", {
        id: this.id,
        descricao: this.descricao,
        ativo: this.ativo
      });

>>>>>>> develop
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
<<<<<<< HEAD
  
  
=======

>>>>>>> develop
