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
    getDados() {
      return {
        id: this.id,
        nomeRazaoSocial: this.nomeRazaoSocial,
        municipioId: this.municipioId,
        cnpj: this.cnpj,
        cpf: this.cpf,
        telefone: this.telefone,
        endereco: this.endereco,
        bairro: this.bairro,
        cep: this.cep,
        email: this.email,
        informacao: this.informacao,
        ativo: this.ativo,
      };
    },

    async salvarMunicipio() {
      if (this.getDados) {
        this.isInvalido = true;
        this.mensagem = "Todos os campos devem ser preenchidos!";
        return;
      }
      this.isInvalido = false;
      
      try {
        if (this.id === "") {
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
        this.limparForm();
      } catch(error) {
        console.log(error);
        this.isInvalido = true;
        if (error.response.status === 403) {        
          this.mensagem = "Usuário não identificado! Faça o login!!!";
        } else if (error.response.status === 400 && 
                   error.response.data.exception === 'MunicipioDuplicadoException'){
                    this.mensagem = error.response.data.mensagem;
        } else if (error.response.status === 400 &&
                   error.response.data.exception === 'EstadoDuplicadoException'){
                    this.mensagem = error.response.data.mensagem;          
        } else {
          this.mensagem = error.message;
        }
      }
    },

    cancelar() {
      this.id = "";
      this.nome = "";
      this.$emit("cancelar", true);
    },

    limparForm() {
      this.id = "";
      this.nome = "";
      this.entrega = "",
      this.estadoNome = "";
    },

    async buscarEstados(){
      const response = await estadoService.listar(1,1000, 'ASC', 'id');
      this.estados = response.content;
    }
  },
  mounted() {
    if (this.propsMunicipio) {
      Object.assign(this, this.propsMunicipio);
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