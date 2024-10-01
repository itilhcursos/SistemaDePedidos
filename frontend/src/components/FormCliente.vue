<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Cliente</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="Id cliente"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="nomeRazaoSocial"
          placeholder="Nome"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">CNPJ</label>
        <input
          class="form-control"
          type="text"
          v-model="cnpj"
          placeholder="CNPJ"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">CPF</label>
        <input
          class="form-control"
          type="text"
          v-model="cpf"
          placeholder="CPF"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Telefone</label>
        <input
          class="form-control"
          type="text"
          v-model="telefone"
          placeholder="Telefone"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Endereço</label>
        <input
          class="form-control"
          type="text"
          v-model="endereco"
          placeholder="Endereço"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Bairro</label>
        <input
          class="form-control"
          type="text"
          v-model="bairro"
          placeholder="Bairro"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">CEP</label>
        <input
          class="form-control"
          type="text"
          v-model="cep"
          placeholder="CEP"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Email</label>
        <input
          class="form-control"
          type="text"
          v-model="email"
          placeholder="Email"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Ativo</label>
        <input
          class="form-control"
          type="boolean"
          v-model="ativo"
          placeholder="Ativo"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Informação</label>
        <input
          class="form-control"
          type="text"
          v-model="informacao"
          placeholder="Informação"
        />
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button
          class="btn btn-primary m-2"
          type="submit"
          v-on:click.prevent="salvarCliente"
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
import clienteService from '@/services/clienteService';
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
      isInvalido: false,
      mensagem : '',
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
      };
    },
    async salvarCliente() {
      if (this.nomeRazaoSocial === "" || this.cpf === "" || this.cnpj === "") {
        this.isInvalido = true;
        this.mensagem = "Nome, CPF e CNPJ devem ser preenchidos!";
        return;
      }
      this.isInvalido = false;
      
      try {
        let response;
        if (this.id === "") {
          response = await clienteService.criar(this.getDados());
        } else {
          response = await clienteService.atualizar(this.id, this.getDados());
        }
        this.$emit("salvar_cliente", response);
        this.limparCampos();
      } catch (error) {
        this.isInvalido = true;
        if (error.response.status === 403) {        
          this.mensagem = "Usuário não identificado! Faça o login!";
        } else if (error.response.status === 400 && error.response.data.exception === 'ClienteDuplicadoException') {
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
    }
  },
  mounted() {
    if (this.propsCliente) {
      this.id = this.propsCliente.id;
      this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
      this.cnpj = this.propsCliente.cnpj;
      this.cpf = this.propsCliente.cpf;
      this.telefone = this.propsCliente.telefone;
      this.endereco = this.propsCliente.endereco;
      this.bairro = this.propsCliente.bairro;
      this.cep = this.propsCliente.cep;
      this.email = this.propsCliente.email;
      this.ativo = this.propsCliente.ativo;
      this.informacao = this.propsCliente.informacao;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>
