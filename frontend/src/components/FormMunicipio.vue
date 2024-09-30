<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Município</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="Id município"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="nome"
          placeholder="Nome do município"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Estado</label>
        <select v-model="estadoSelected" class="form-select">
          <option v-for="estado in estados" :key="estado.id" :value="estado.id">
            {{ estado.nome }}
          </option>
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
import municipioService from '@/services/municipioService';
import estadoService from '@/services/estadoService';
export default {
  props: {
    propsMunicipio: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      estadoSelected: null,
      estados: [],
      isInvalido: false,
      mensagem : '',
    };
  },
  methods: {
    getDados(){
      return {
              id: this.id,
              nome: this.nome,
              estadoId: this.estadoSelected,
            };
    },
    async salvarMunicipio() {
      if (this.nome === "" || this.estadoSelected === null) {
        this.isInvalido = true;
        this.mensagem = "Nome do Município e Estado devem ser preenchidos!";
        return;
      }
      this.isInvalido = false;
      
    try{
        if (this.id === "") {
          const response = await municipioService.criar(
            this.getDados());
          this.listaMunicipios = response;
        } else {
          const response = await municipioService.atualizar(
            this.id,
            this.getDados()
          );
          this.listaMunicipios = response;
        }
        this.$emit("salvar_municipio", {
        id: this.id,
        nome: this.nome,
        estadoId: this.estadoSelected,
      });

      this.resetForm();
    }catch(error){
      this.isInvalido = true;
      if(error.response.status === 403){        
        this.mensagem = "Usuário não identificado! Faça o login!!!";
      }else if(error.response.status === 400 &&
               error.response.data.exception === 'MunicipioDuplicadoException'){
        this.mensagem = error.response.data.mensagem;     
      }else{
        this.mensagem = error.message;
      }
    }
   },
    cancelar() {
      this.resetForm();
      this.$emit("cancelar", true);
    },
    async carregarEstados() {
      try {
      const response = await estadoService.listar(1, 100, 'ASC', 'id');
      this.estados = response.content;
    } catch (error) {
        console.error("Erro ao carregar estados:", error);
      }
    },
    resetForm() {
      this.id = "";
      this.nome = "";
      this.estadoSelected = null;
    }
  },
  mounted() {
    if (this.propsMunicipio) {
      this.id = this.propsMunicipio.id;
      this.nome = this.propsMunicipio.nome;
      this.estadoSelected = this.propsMunicipio.estadoId;
    }
    this.carregarEstados();
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

<!-- <style>
.form-select {
  max-height: 100px;
  overflow-y: auto;
}
</style> -->
