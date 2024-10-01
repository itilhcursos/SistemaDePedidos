<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Município</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">ID</label>
        <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id Municipio" />
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input class="form-control" type="text" v-model="nome" placeholder="Nome" />
      </div>
      <div class="mb-3">
        <label class="form-label">Entrega</label>
        <select v-model="entrega" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
        </select>
      </div>
      <label class="form-label">Estado</label>
      <v-select class="form-control" label="Estado" :filterable="false" v-model="estadoSelecionado" :options="estados"
        @search="onSearchEstados">
        <template v-slot:no-options> Sem Estados para exibir. </template>
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
        <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarMunicipio">
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
import municipioService from "@/services/municipioService";
import estadoService from "../services/estadoService";
export default {
  props: {
    propsMunicipio: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      isInvalido: false,
      mensagem: "",
      listaMunicipios: "",
      estados: "",
    };
  },
  methods: {
    async onSearchEstados(search, loading) {
      if (search.length) {
        loading(true);
        await estadoService.buscar(search).then((response) => {
          console.log(response);
          this.estados = response.content;
          loading(false);
        });
      }
    },
    getDados() {
      return {
        id: this.id,
        nome: this.nome,
        entrega: this.entrega,
        estadoId: this.estadoSelecionado.id,
      };
    },
    async salvarMunicipio() {
      if (!this.nome) {
        this.isInvalido = true;
        this.mensagem = "Nome deve ser preenchido!!";
        return;
      }
      this.isInvalido = false;

      try {
        if (!this.id) {
          const response = await municipioService.criar(this.getDados());
          this.listaMunicipios = response;
        } else {
          const response = await municipioService.atualizar(
            this.id,
            this.getDados()
          );
          this.listaMunicipios = response;
        }
        this.$emit("salvar_municipio", this.getDados());
        this.limparFormulario();
      } catch (error) {
        this.tratarErro(error);
      }
    },
    limparFormulario() {
      this.id = "";
      this.nome = "";
      this.entrega = "";
      this.estadoSelecionado = "";
    },
    cancelar() {
      this.id = "";
      this.nome = "";
      this.entrega = "";
      this.estadoSelecionado = "";
      this.$emit("cancelar", true);
    },
    tratarErro(error) {
      this.isInvalido = true;
      if (error.response.status === 403) {
        this.mensagem = "Usuário não identificado! Faça o login!!!";
      } else if (
        error.response.status === 400 &&
        error.response.data.exception === "MunicipioDuplicadoException"
      ) {
        this.mensagem = error.response.data.mensagem;
      } else {
        this.mensagem = error.message;
      }
    }
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
