<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Municipio</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="Id municipio"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="nome"
          placeholder="Nome"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Selecione o Estado </label>
        <select v-model="estadoSelected" class="form-select">
          <option v-for="estado in estados" :value="estado.id" :key="estado.id"> {{ estado.nome }}</option>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Entrega disponível para este municipio?</label>
        <select v-model="entrega" class="form-select">
          <option value="true">Sim</option>
          <option value="false">Não</option>
        </select>
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button
          class="btn btn-primary m-2"
          type="submit"
          v-on:click.prevent="salvar"
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
      estadoId: "",
      estadoSelected: "",
      estados: [],
      isInvalido: false,
      mensagem: '',
    };
  },
  methods: {
    getDados(){
      return {
        id:this.id,
        nome:this.nome,
        entrega:this.entrega,
        estadoId:this.estadoSelected
      }
    },

    async buscarEstados(){
      const response = await estadoService.listar(1, 1000, 'ASC', 'id');
      this.estados = response.content;
    },

    async salvar(){
      if (this.nome === "") {
        this.isInvalido = true;
        this.mensagem = "Preencha o nome do municipio."
        return;
      }
      if (this.entrega === ""){
        this.isInvalido = true;
        this.mensagem = "Defina a disponibilidade de entrega no municipio."
        return;
      }
      this.isInvalido = false;

      try {
        if (this.id === "") {
          const response = await municipioService.criar(this.getDados());
          this.listaMunicipios = response;
        } else {
          const response = await municipioService.atualizar(this.id, this.getDados());
          this.listaMunicipios = response;
        }
        this.limparCampos();
        this.$emit("salvar", true);
      } catch (error) {
        this.isInvalido = true;
        if (error.response.status === 403) {
          this.mensagem = "Usuário não identificado! Faça o login!!!";
        } else if (error.response.status === 400) {
          this.mensagem = error.response.data.mensagem;
        } else {
          this.mensagem = error.message;
        }
      }
    },

    cancelar() {
      this.limparCampos();
      this.$emit("cancelar", true);
    },

    limparCampos(){
      this.id = "";
      this.nome = "";
      this.entrega = "";
      this.estadoSelected = "";
    }
  },

  mounted() {
    if (this.propsMunicipio) {
      this.id = this.propsMunicipio.id;
      this.nome = this.propsMunicipio.nome;
      this.entrega = this.propsMunicipio.entrega;
      this.estadoSelected = this.propsMunicipio.estadoId;
    }
    this.buscarEstados();
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>