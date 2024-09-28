<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} cliente</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id cliente" />
      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input class="form-control" type="text" v-model="nome" placeholder="Nome" />
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarcliente">
          <i class="bi bi-clipboard2-check"></i>
          {{ getAcao }}
        </button>
        <button class="btn btn-warning m-2" type="submit" v-on:click.prevent="cancelar">
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
    propscliente: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      isInvalido: false,
      mensagem : '',
    };
  },
  methods: {
    getDados(){
      return {
              id: this.id,
              nome: this.nome,
            };
    },
    async salvarcliente() {
      if (this.nome === "") {
        this.isInvalido = true;
        this.mensagem = "Nome deve ser preenchido!!";
        return;
      }
      this.isInvalido = false;
      
    try{
        if (this.id === "") {
          const response = await clienteService.criar(
            this.getDados());
          this.listaclientes = response;
        } else {
          const response = await clienteService.atualizar(
            this.id,
            this.getDados()
          );
          this.listaclientes = response;
        }
        this.$emit("salvar_cliente", {
        id: this.id,
        nome: this.nome,
      });

      this.id = "";
      this.nome = "";
    }catch(error){
      this.isInvalido = true;
      if(error.response.status === 403){        
        this.mensagem = "Usuário não identificado! Faça o login!!!";
      }else if(error.response.status === 400 &&
               error.response.data.exception === 'clienteDuplicadoException'){
        this.mensagem = error.response.data.mensagem;     
      }else{
        this.mensagem = error.message;
      }
    }
   },
    cancelar() {
      this.id = "";
      this.nome = "";
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propscliente) {
      this.id = this.propscliente.id;
      this.nome = this.propscliente.nome;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>
