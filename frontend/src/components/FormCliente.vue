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
        <label class="form-label">Nome/Razão Social</label>
        <input
          class="form-control"
          type="text"
          v-model="nomeRazaoSocial"
          placeholder="NomeRazaoSocial"
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
          placeholder="cpf"
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
          placeholder="Cep"
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
        <select v-model="ativo" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Informações</label>
        <input
          class="form-control"
          type="text"
          v-model="informacoes"
          placeholder="Informacoes"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Municipio ID</label>
        <input
          class="form-control"
          type="text"
          v-model="municipioId"
          placeholder="Municipio ID"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Municipio Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="municipioNome"
          placeholder="Municipio Nome"
        />
      </div>
      <div
        v-if="isInvalido"
        class="alert alert-danger d-flex align-items-center"
        role="alert"
      >
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
import clienteService from "@/services/clienteService";
export default {
  props: {
    propsCliente: Object,
  },
  data() {
    return {
      id: "",
      nomeRazaoSocial: "",
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
        municipioId: this.municipioId,
        municipioNome: this.municipioNome,
      };
    },
    async salvarCliente() {
      if (this.nomeRazaoSocial === "") {
        this.isInvalido = true;
        this.mensagem = "Nome/RazãoSocial do cliente não pode estar vazia.";
        return;
      }
      this.isInvalido = false;

      try {
        if (this.id === "") {
          const response = await clienteService.criar(this.getDados());
          this.listaClientes = response;
        } else {
          const response = await clienteService.atualizar(
            this.id,
            this.getDados()
          );
          this.listaClientes = response;
        }
        this.$emit("salvar_cliente", {
          id: this.id,
          nomeRazaoSocial: this.nomeRazaoSocial,
          cnpj: this.cnpj,
          cpf: this.cpf,
          telefone: this.telefone,
          endereco: this.endereco,
          bairro: this.bairro,
          cep: this.cep,
          municipioId: this.municipioId,
          municipioNome: this.municipioNome
        });

        this.id = "";
        this.nomeRazaoSocial = "";
        this.cnpj = "";
        this.cpf = "";
        this.telefone = "";
        this.endereco = "";
        this.bairro = "";
        this.cep = "";
        this.municipioId = "",
        this.municipioNome = "";
      } catch (error) {
        this.isInvalido = true;
        if (error.response.status === 403) {
          this.mensagem = "Usuário não identificado! Faça o login!!!";
        } else if (
          error.response.status === 400 &&
          error.response.data.exception === "ClienteDuplicadoException"
        ) {
          this.mensagem = error.response.data.mensagem;
        } else {
          this.mensagem = error.message;
        }
      }
    },
    cancelar() {
      this.id = "";
      this.nomeRazaoSocial = "";
      this.cnpj = "";
      this.cpf = "";
      this.telefone = "";
      this.endereco = "";
      this.bairro = "";
      this.cep = "";
      this.municipioNome = "";
      this.$emit("cancelar", true);
    },
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
      this.municipioNome = this.propsCliente.municipioNome;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>
