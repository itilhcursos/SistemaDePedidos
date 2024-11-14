<template>
    <div class="container">
      <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Cliente</h4>
      <hr />
      <form>
        <div class="row">
          <div class="mb-3">
            <label class="form-label">ID</label>
            <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="ID cliente" />
          </div>

          <div class="mb-3">
            <label class="form-label">Município</label>
            <select v-model="municipioId" class="form-select">
              <option v-for="municipio in municipios" :value="municipio.id" :key="municipio.id">
                {{ municipio.nome }}
              </option>
            </select>
          </div>
        </div>
        <hr>
        <div class="row">
          <div class="col">
            <label class="form-label">Nome</label>
            <input class="form-control" type="text" v-model="nomeRazaoSocial"
            placeholder="Insira o nome da empresa" />
          </div>

          <div class="col">
            <label class="form-label">CNPJ</label>
            <input class="form-control" type="text" v-model="cnpj"
            placeholder="Insira o CNPJ" />
          </div>

          <div class="col">
            <label class="form-label">CPF</label>
            <input class="form-control" type="text" v-model="cpf"
            placeholder="Insira o CPF" />
          </div>
        </div>
        <hr>
        <div class="row">
          <div class="col">
            <label class="form-label">Telefone</label>
            <input class="form-control" type="text" v-model="telefone"
            placeholder="Insira o telefone" />
          </div>

          <div class="col">
            <label class="form-label">Endereço</label>
            <input class="form-control" type="text" v-model="endereco"
            placeholder="Insira o endereço" />
          </div>

          <div class="col">
            <label class="form-label">Bairro</label>
            <input class="form-control" type="text" v-model="bairro"
            placeholder="Insira o bairro" />
          </div>
        </div>
        <hr>
        <div class="row">
          <div class="col">
            <label class="form-label">CEP</label>
            <input class="form-control" type="text" v-model="cep"
            placeholder="Insira o CEP" />
          </div>

          <div class="col">
            <label class="form-label">E-mail</label>
            <input class="form-control" type="text" v-model="email"
            placeholder="Insira o email" />
          </div>

          <div class="col">
            <label class="form-label">Entrega</label>
            <select v-model="ativo" class="form-select">
              <option :value="true">Sim</option>
              <option :value="false">Não</option>
            </select>
          </div>
        </div>
        <hr>
        <div class="row">
          <div class="mb-3">
            <label class="form-label">Informação</label>
            <input class="form-control" type="text" v-model="informacao"
            placeholder="Insira as informações" />
          </div>
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
        informacao: "",
        ativo: "",
        isInvalido: false,
        mensagem : '',
        municipioId: "",
        municipioNome: "",
        municipioSelected:"",
        municipios:[],
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
          this.listaClientes = response;
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
        this.nomeRazaoSocial = "";
        this.$emit("cancelar", true);
      },
      
      limparForm() {
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
      },

      async buscarMunicipios(){
        const response = await municipioService.listar(1,1000, 'ASC', 'id');
        this.municipios = response.content;
      }
    },
    mounted() {
      if (this.propsCliente) {
        Object.assign(this, this.propsCliente);
      }
      this.buscarMunicipios();
    },
    computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },
    },
  };
</script>