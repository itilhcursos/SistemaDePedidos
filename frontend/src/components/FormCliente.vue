<template>
    <div class="container">
      <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Cliente</h4>
      <hr />
      <form>
        <div class="mb-3">
          <label class="form-label">ID</label>
          <input
            class="form-control"
            type="text"
            v-model="id"
            :disabled="true"
            placeholder="Id cliente"
          />
        </div>

        <!-- <div class="mb-3">
          <label class="form-label">Município</label>
          <input
            class="form-control"
            type="text"
            v-model="municipioNome"
            placeholder="Nome do Municipio"
          />
        </div> -->

        <label class="form-label">Município</label>
        <v-select class="meu-select" v-model="selectedMunicipio"   :filterable="false" :options="optionsMunicipio"
        @search="onSearch">
        <template v-slot:no-options>
            Não encontrado.
        </template>
        <template v-slot:option="option">
            {{ option.nomeRazaoSocial }}
        </template>
        <template v-slot:selected-option="option">
            {{ option.nomeRazaoSocial }}
        </template>
        </v-select>


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
            v-model="endereco"
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
          <label class="form-label">E-mail</label>
          <input
            class="form-control"
            type="text"
            v-model="email"
            placeholder="E-mail"
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

        <div class="mb-3">
          <label class="form-label">Entrega</label>
          <select v-model="ativo" class="form-select">
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
  import "vue-select/dist/vue-select.css";
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
        optionsMunicipio: [],
        selectedMunicipio: null,
      };
    },
    methods: {
      async onSearch(search, loading) {
        if (search == "")
          return;
        loading(true);
        await municipioService.buscar(search).then((response) => {
          this.optionsMunicipio = response.content;
          loading(false);
        });
      },

      async salvarCliente() {
        if (this.nomeRazaoSocial === "") {
          this.isInvalido = true;
          this.mensagem = "Nome deve ser preenchido!!";
          return;
        }
        this.isInvalido = false;
        let config = {
          headers: {
            'Authorization': 'Bearer ' +localStorage.getItem('token')
          }
        }
  
        if (this.id === "") {
          //incluir pelo POST da API
          const response = await clienteService.post("http://localhost:8080/cliente", {
            id: this.id,
            nomeRazaoSocial: this.nomeRazaoSocial,
            municipioNome: this.municipioNome,
            cnpj: this.cnpj,
            cpf: this.cpf,
            telefone: this.telefone,
            endereco: this.endereco,
            bairro: this.bairro,
            cep: this.cep,
            email: this.email,
            informacao: this.informacao,
            ativo: this.ativo,
          }, config);
          this.listaClientes = response.data;
        } else {
          // alterar pelo PUT da API
          const response = await clienteService.put(
            `http://localhost:8080/cliente/${this.id}`,
            {
              id: this.id,
              nomeRazaoSocial: this.nomeRazaoSocial,
              municipioNome: this.municipioNome,
              cnpj: this.cnpj,
              cpf: this.cpf,
              telefone: this.telefone,
              endereco: this.endereco,
              bairro: this.bairro,
              cep: this.cep,
              email: this.email,
              informacao: this.informacao,
              ativo: this.ativo,
            }
          ,config );
          this.listaClientes = response.data;
        }
  
        this.$emit("salvar_cliente", {
          id: this.id,
          nomeRazaoSocial: this.nomeRazaoSocial,
          municipioNome: this.municipioNome,
          cnpj: this.cnpj,
          cpf: this.cpf,
          telefone: this.telefone,
          endereco: this.endereco,
          bairro: this.bairro,
          cep: this.cep,
          email: this.email,
          informacao: this.informacao,
          ativo: this.ativo,
        });
  
        this.id = "";
        this.nomeRazaoSocial = "";
      },
      cancelar() {
        this.id = "",
        this.nomeRazaoSocial = "",
        this.municipioNome = "",
        this.cnpj = "",
        this.cpf = "",
        this.telefone = "",
        this.endereco = "",
        this.bairro = "",
        this.cep = "",
        this.email = "",
        this.informacao = "",
        this.ativo = "",
        this.$emit("cancelar", true);
      },
    },
    mounted() {
      if (this.propsCliente) {
        this.id = this.propsCliente.id;
        this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
        this.municipioNome = this.propsCliente.municipioNome;
        this.cnpj = this.propsCliente.cnpj;
        this.cpf = this.propsCliente.cpf;
        this.telefone = this.propsCliente.telefone;
        this.endereco = this.propsCliente.endereco;
        this.bairro = this.propsCliente.bairro;
        this.cep = this.propsCliente.cep;
        this.email = this.propsCliente.email;
        this.informacao = this.propsCliente.informacao;
        this.ativo = this.propsCliente.ativo;
      }
    },
    computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },
    },
  };
</script>