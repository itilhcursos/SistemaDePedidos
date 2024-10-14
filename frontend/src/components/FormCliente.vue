<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Cliente</h4>
    <hr />
    <form @submit.prevent="salvarCliente">
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input class="form-control" type="text" v-model="id" disabled placeholder="ID Cliente" />
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input class="form-control" type="text" v-model="nomeRazaoSocial" placeholder="Nome/Razao Social" required />
      </div>
      <div class="mb-3">
        <label class="form-label">CNPJ</label>
        <input class="form-control" type="text" v-model="cnpj" placeholder="CNPJ" required />
      </div>
      <div class="mb-3">
        <label class="form-label">CPF</label>
        <input class="form-control" type="text" v-model="cpf" placeholder="CPF" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Telefone</label>
        <input class="form-control" type="text" v-model="telefone" placeholder="Telefone" />
      </div>
      <div class="mb-3">
        <label class="form-label">Endereço</label>
        <input class="form-control" type="text" v-model="endereco" placeholder="Endereço" />
      </div>
      <div class="mb-3">
        <label class="form-label">Bairro</label>
        <input class="form-control" type="text" v-model="bairro" placeholder="Bairro" />
      </div>
      <div class="mb-3">
        <label class="form-label">CEP</label>
        <input class="form-control" type="text" v-model="cep" placeholder="CEP" />
      </div>
      <div class="mb-3">
        <label class="form-label">Email</label>
        <input class="form-control" type="email" v-model="email" placeholder="Email" />
      </div>
      <div class="mb-3">
        <label class="form-label">Ativo</label>
        <select v-model="ativo" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Informação</label>
        <input class="form-control" type="text" v-model="informacao" placeholder="Informações" />
      </div>
      <label class="form-label">Municipio</label>
      <v-select class="form-control" label="Produto" :filterable="false"
          v-model="municipioSelecionado" :options="municipios" @search="onSearchMunicipios">
          <template v-slot:no-options>
            Sem Municipios para exibir.
          </template>
          <template v-slot:option="option" > 
              {{ option.nome }}
          </template>
          <template v-slot:selected-option="option" >
              {{ option.nome }}
          </template>
      </v-select>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit">
          <i class="bi bi-clipboard2-check"></i> {{ getAcao }}
        </button>
        <button class="btn btn-warning m-2" type="button" @click="cancelar">
          <i class="bi bi-clipboard2-x"></i> Cancelar
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import clienteService from '@/services/clienteService';
import municipioService from '@/services/municipioService';

export default {
  props: {
    propsCliente: Object,
  },
  data() {
    return {
      id: "",
      nomeRazaoSocial: "",
      cnpj: "",
      cpf: "",
      telefone: "",
      endereco: "",
      bairro: "",
      cep: "",
      email: "",
      ativo: false,
      informacao: "",
      municipioSelecionado: null,
      municipios: [],
      isInvalido: false,
      mensagem: "",
    };
  },
  methods: {
    getDados() {
      return {
        id: this.id,
        nomeRazaoSocial: this.nomeRazaoSocial,
        cnpj: this.cnpj,
        cpf: this.cpf,
        telefone: this.telefone,
        endereco: this.endereco,
        bairro: this.bairro,
        cep: this.cep,
        email: this.email,
        ativo: this.ativo,
        informacao: this.informacao,
        municipioNome: this.municipioNome,
      };
    },
    async onSearchMunicipios(search, loading) {
      if(search.length) {
        loading(true);
        await municipioService.buscar(search).then((response) => {
        console.log(response);
        this.municipios = response.content;
        loading(false);
      });
      }
    },
    
    async salvarCliente() {
      if (!this.nomeRazaoSocial || !this.cpf || !this.cnpj) {
        this.isInvalido = true;
        this.mensagem = "Nome, CPF e CNPJ devem ser preenchidos!";
        return;
      }
      this.isInvalido = false;

      try {
        if (this.id === "") {
          const response = await clienteService.criar(this.getDados());
        } else {
          const response = await clienteService.atualizar(this.id, this.getDados());
          this.listaClientes = response;
        }
        this.$emit("salvar_cliente", this.getDados());
        this.limparCampos();
      } catch (error) {
        this.tratarErro(error);
      }
    },
    tratarErro(error) {
      this.isInvalido = true;
      if (error.response && error.response.status === 403) {
        this.mensagem = "Usuário não identificado! Faça o login!!!";
      } else if (error.response && error.response.status === 400) {
        this.mensagem = error.response.data.mensagem;
      } else {
        this.mensagem = error.message;
      }
    },
    cancelar() {
      this.limparCampos();
      this.$emit("cancelar", true);
    },
    limparCampos() {
      this.id = "";
      this.nomeRazaoSocial = "";
      this.cnpj = "";
      this.cpf = "";
      this.telefone = "";
      this.endereco = "";
      this.bairro = "";
      this.cep = "";
      this.email = "";
      this.ativo = false;
      this.informacao = "";
      this.municipioNome = "";
      this.municipioSelecionado = null;
    }
  },
  mounted() {
    if (this.propsCliente) {
      Object.assign(this, this.propsCliente);
    }
  },
  computed: {
    getAcao() {
      return this.id ? "Alterar" : "Incluir";
    },
  },
};
</script>

