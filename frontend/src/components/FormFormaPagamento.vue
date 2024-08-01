<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Forma de Pagamento</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="Id Forma de Pagamento"
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
        <label class="form-label">Entrega</label>
        <input
        class="form-control"
        type="text"
        v-model="entrega"
        placeholder="Entrega"
        
        />
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Descrição deve ser preenchida!!</div>
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

<script>
import axios from "axios";
export default {
  props: {
    propsFormaPagamento: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      entrega: "",
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
        const response = await axios.post("http://localhost:8080/formapagamento", {
          id: this.id,
          descricao: this.descricao,
          entrega: this.entrega,
        });
        this.listaFormasPagamento = response.data;
      } else {
        // alterar pelo PUT da API
        const response = await axios.put(
          `http://localhost:8080/formapagamento/${this.id}`,
          {
            id: this.id,
            descricao: this.descricao,
            entrega: this.entrega,
          }
        );
        this.listaFormasPagamento = response.data;
      }

      this.$emit("salvar_formapagamento", {
        id: this.id,
        descricao: this.descricao,
        entrega: this.entrega,

      });

      this.id = "";
      this.descricao = "";
      this.entrega = "";
    },
    cancelar() {
      this.id = "";
      this.descricao = "";
      this.entrega ;
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsFormaPagamento) {
      this.id = this.propsFormaPagamento.id;
      this.descricao = this.propsFormaPagamento.descricao;
      this.entrega = this.propsFormaPagamento.entrega? "Verdadeiro":"Falso";
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

