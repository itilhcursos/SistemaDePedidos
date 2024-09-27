<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Estado</h4>
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
          v-model="nome"
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
          v-on:click.prevent="salvarEstado"
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
  export default {
    props: {
      propsEstado: Object,
    },
    data() {
      return {
        id: "",
        nome: "",
        isInvalido: false,
        mensagem: '',
      };
    },
    methods: {
      getDados(){
        return{
            id: this.id,
            nome: this.nome,
        }
      },
      async salvarEstado() {
        if (this.nome === "") {
          this.isInvalido = true;
          this.mensagem = "O Nome deve ser preenchido!!";
          return;
        } 
        this.isInvalido = false;

        try{
          if (this.id === "") {
            //incluir pelo POST da API
            const response = await estadoService.criar(
            this.getDados());
            this.listaEstados = response.data;
          } else {
            // alterar pelo PUT da API
            const response = await estadoService.atualizar(
              this.id,
              this.getDados()
            );
            this.listaEstados = response.data;
          }
          this.$emit("salvar_estado", {
            id: this.id,
            nome: this.nome,
          });

          this.id = "";
          this.nome = "";
        
        }catch(error){
        
          // console.log (error);
          // console.log (error.response.status);
          // console.log (error.response.data.exception);
          this.isInvalido = true;
          if(error.response.status === 403){    // o erro 403 é quando esqueço de fazer a autenticação    
            this.mensagem = "Usuário não identificado! Faça o login!!!";
          }else if(error.response.status === 400 &&
                   error.response.data.exception === 'EstadoDuplicadoException'){ 
            this.mensagem = error.response.data.mensagem;
          }else{
            this.mensagem = error.message;
          }
        }
      },

      cancelar(){
        this.id = "";
        this.nome = "";
        this.$emit("cancelar", true);
      },
    },   
    mounted() {
      if (this.propsEstado) {
        this.id = this.propsEstado.id;
        this.nome = this.propsEstado.nome;
      }
    },
    computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },
    },

  };

</script>