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
          <label class="form-label">NOME</label>
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
            placeholder="cnpj cliente"
          />
        </div>

        <div class="mb-3">
          <label class="form-label">CPF</label>
          <input
            class="form-control"
            type="text"
            v-model="cpf"
            placeholder="cpf cliente"
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
  import axios from "axios";
  export default {
    props: {
      propsCliente: Object,
    },
    data() {
      return {
        id: "",
        nomeRazaoSocial: "",
        isInvalido: false,
        mensagem : '',
      };
    },
    methods: {
      async salvarCliente() {
        if (this.nomeRazaoSocial === "") {
          this.isInvalido = true;
          this.mensagem = "Nome deve ser preenchido!!";
          return;
        }
        this.isInvalido = false;
        let config = {
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }
        }
  
      try{
          if (this.id === "") {
            //incluir pelo POST da API
            const response = await axios.post("http://localhost:8080/cliente", {
              id: this.id,
              nomeRazaoSocial: this.nomeRazaoSocial,
              cnpj: this.cnpj,
              cpf: this.cpf,
            }, config);
            this.listaClientes = response.data;
          } else {
            // alterar pelo PUT da API
            const response = await axios.put(`http://localhost:8080/cliente/${this.id}`,
              {
                id: this.id,
                nomeRazaoSocial: this.nomeRazaoSocial,
              }
            ,config );
            this.listaCliente = response.data;
          }
          this.$emit("salvar_cliente", {
          id: this.id,
          nomeRazaoSocial: this.nomeRazaoSocial,
        });
  
        this.id = "";
        this.nomeRazaoSocial = "";
      }catch(error){
        //mesagens de erro
         //exibe o objeto do error completo
          // console.log (error);
         //exibe o codigo do status de retorno       
         // console.log (error.response.status);
          //exibe o mensagem de erro personalidado do backend
          // console.log (error.response.data.exception);
        this.isInvalido = true;
        if(error.response.status === 403){        
          this.mensagem = "Usuário não identificado! Faça o login!!!";
        }else if(error.response.status === 400 &&
                 error.response.data.exception === 'ClienteDuplicadoException'){
          this.mensagem = error.response.data.mensagem;     
        }else{
          this.mensagem = error.message;
        }
      }
     },
      cancelar() {
        this.id = "";
        this.nomeRazaoSocial = "";
        this.$emit("cancelar", true);
      },
    },
    mounted() {
      if (this.propsCliente) {
        this.id = this.propsCliente.id;
        this.nome = this.propsCliente.nomeRazaoSocial;
      }
    },
    computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },
    },
  };
  </script>
  
  