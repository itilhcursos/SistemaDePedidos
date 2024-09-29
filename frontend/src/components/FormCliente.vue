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
          placeholder="Id estado"
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
          placeholder="Nome"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">CPF</label>
        <input
          class="form-control"
          type="text"
          v-model="cpf"
          placeholder="Nome"
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
        <label class="form-label">Endereco</label>
        <input
          class="form-control"
          type="text"
          v-model="endereco"
          placeholder="Endereco"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">CPF</label>
        <input
          class="form-control"
          type="text"
          v-model="cpf"
          placeholder="Nome"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">CPF</label>
        <input
          class="form-control"
          type="text"
          v-model="cpf"
          placeholder="Nome"
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
        isInvalido: false,
        mensagem: '',
      };
    },
    methods: {
      getDados(){
        return{
            id: this.id,
            nomeRazaoSocial: this.nomeRazaoSocial,
            cnpj: this.cnpj,
            cpf: this.cpf,
        }
      },
      async salvarCliente() {
        if (this.nomeRazaoSocial === "") {
          this.isInvalido = true;
          this.mensagem = "O Nome deve ser preenchido!!";
          return;
        }
        this.isInvalido = false;

        try{
          if (this.id === "") {
            //incluir pelo POST da API
            const response = await clienteService.criar(
            this.getDados());
            this.listaClientes = response.data;
          } else {
            // alterar pelo PUT da API
            const response = await clienteService.atualizar(
              this.id,
              this.getDados()
            );
            this.listaClientes = response.data;
          }
          this.$emit("salvar_cliente", {
            id: this.id,
            nomeRazaoSocial: this.nomeRazaoSocial,
            cnpj: this.cnpj,
            cpf: this.cpf,
          });

          this.id = "";
          this.nomeRazaoSocial = "";
          this.cnpj = "";
          this.cpf ="";
        
        }catch(error){
        
          console.log (error);
          console.log (error.response.status);
          this.isInvalido = true;
          if(error.response.status === 403){        
            this.mensagem = "Usuário não identificado! Faça o login!!!";
          }else if(error.response.status === 500){ 
            this.mensagem = error.response.data.message;
          }else{
            this.mensagem = error.message;
          }
        }
      },

      cancelar(){
        this.id = "";
        this.nomeRazaoSocial = "";
        this.cnpj ="";
        this.cpf ="";
        this.$emit("cancelar", true);
      },
    },   
    mounted() {
      if (this.propsCliente) {
        this.id = this.propsCliente.id;
        this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
        this.cnpj = this.propsCliente.cnpj;
        this.cpf = this.propsCliente.cpf;
      }
    },
    computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },
    },

  };

</script>