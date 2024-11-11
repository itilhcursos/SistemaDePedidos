<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Município</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">ID</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="ID municipio"
        />
      </div>

      <div class="mb-3">
          <label class="form-label">Estado</label>
          <select v-model="estadoId" class="form-select">
              <option v-for="estado in estados" :value="estado.id"
              :key="estado.id">
                {{ estado.nome }}
              </option>
          </select>
        </div>

      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="nome"
          placeholder="Nome Município"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Entrega</label>
        <select v-model="entrega" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
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
          v-on:click.prevent="salvarMunicipio"
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
import estadoService from '@/services/estadoService';
import municipioService from '@/services/municipioService';
export default {
  props: {
    propsMunicipio: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      entrega: "",
      isInvalido: false,
      mensagem : '',
      estadoId: "",
      estadoNome: "",
      estadoSelected:"",
      estados:[],
    };
  },
  methods: {
    async salvarMunicipio() {
      if (this.nome === "") {
        this.isInvalido = true;
        this.mensagem = "Nome deve ser preenchido!!";
        return;
      }
      this.isInvalido = false;
      
      if (this.id === "") {
        const response = await municipioService.criar({
          id: this.id,
          nome: this.nome,
          entrega: this.entrega,
          estadoNome: this.estadoNome,
        });
        this.listaMunicipios = response.data;
      } else {
        const response = await municipioService.atualizar(
          this.id,
          {
            id: this.id,
            nome: this.nome,
            entrega: this.entrega,
            estadoNome: this.estadoNome,
          }
        );
        this.listaMunicipios = response.data;
      }
      this.$emit("salvar_municipio", {
        id: this.id,
        nome: this.nome,
        entrega: this.entrega,
        estadoNome: this.estadoNome,
      });

      this.id = "";
      this.nome = "";
    },
    cancelar() {
      this.id = "";
      this.nome = "";
      this.entrega = "",
      this.estadoNome = "",
      this.$emit("cancelar", true);
    },

    async buscarEstados(){
      const response = await estadoService.listar(1,1000, 'ASC', 'id');
      this.estados = response.content;
    }
  },
  mounted() {
    if (this.propsMunicipio) {
      this.id = this.propsMunicipio.id;
      this.nome = this.propsMunicipio.nome;
      this.entrega = this.propsMunicipio.entrega;
      this.estadoNome = this.propsMunicipio.estadoNome;
      this.estadoId = this.propsMunicipio.estadoId;
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