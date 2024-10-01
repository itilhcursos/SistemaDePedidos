<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Município</h4>
    <hr />
    <form @submit.prevent="salvarMunicipio">
      <div class="mb-3">
        <label class="form-label">ID</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="ID Município"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="nome"
          placeholder="Nome"
          required
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Entrega</label>
        <select v-model="entrega" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
        </select>
      </div>
      
      <label class="form-label">Estado</label>
      <v-select
        class="form-control"
        label="Estado"
        :filterable="false"
        v-model="estadoSelecionado"
        :options="estados"
        @search="onSearchEstados"
      >
        <template v-slot:no-options>
          Sem Estados para exibir.
        </template>
        <template v-slot:option="option">
          {{ option.nome }}
        </template>
        <template v-slot:selected-option="option">
          {{ option.nome }}
        </template>
      </v-select>
        
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>

      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit">
          <i class="bi bi-clipboard2-check"></i>
          {{ getAcao }}
        </button>
        <button class="btn btn-warning m-2" type="button" @click="cancelar">
          <i class="bi bi-clipboard2-x"></i>
          Cancelar
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import estadoService from "@/services/estadoService";
import municipioService from "@/services/municipioService";

export default {
  props: {
    propsMunicipio: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      entrega: "",
      estadoSelecionado: null,
      estados: [],
      isInvalido: false,
      mensagem: '',
    };
  },
  methods: {
    getDados() {
      return {
        id: this.id,
        nome: this.nome,
        entrega: this.entrega,
        estadoSelecionado: this.estadoSelecionado,
      };
    },
    async salvarMunicipio() {
      if (this.nome === "") {
        this.isInvalido = true;
        this.mensagem = "Nome do município não pode ser vazio.";
        return;
      }
      this.isInvalido = false;

      try {
        let response;
        if (this.id === "") {
          response = await municipioService.criar(this.getDados());
        } else {
          response = await municipioService.atualizar(this.id, this.getDados());
        }
        this.$emit("salvar_municipio", response);
      } catch (error) {
        this.isInvalido = true;
        this.mensagem = this.getErrorMessage(error);
      }
    },
    async onSearchEstados(search, loading) {
      if (search.length) {
        loading(true);
        try {
          const response = await estadoService.buscar(search);
          this.estados = response.content;
        } catch (error) {
          console.error(error);
        } finally {
          loading(false);
        }
      }
    },
    getErrorMessage(error) {
      if (error.response) {
        if (error.response.status === 403) {
          return "Usuário não identificado! Faça o login!";
        } else if (error.response.status === 400 && error.response.data.exception === 'MunicipioDuplicadoException') {
          return error.response.data.mensagem;
        }
      }
      return error.message || "Ocorreu um erro inesperado.";
    },
    cancelar() {
      this.id = "";
      this.nome = "";
      this.entrega = "";
      this.estadoSelecionado = null;
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsMunicipio) {
      this.id = this.propsMunicipio.id;
      this.nome = this.propsMunicipio.nome;
      this.entrega = this.propsMunicipio.entrega;
      this.estadoSelecionado = this.propsMunicipio.estadoSelecionado;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

