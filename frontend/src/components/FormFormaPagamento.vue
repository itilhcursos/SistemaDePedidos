<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} forma de pagamento</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="ID Pagamento"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Descrição</label>
        <input
          class="form-control"
          type="text"
          v-model="descricao"
          placeholder="Descrição"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Ativo</label>
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
          v-on:click.prevent="salvarFormaPagamento"
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
import formaPagamentoService from '@/services/formaPagamentoService';
export default {
  props: {
    propsFormaPagamento: Object,
  },
  data() {
    return {
      id: "",
      descricao: "",
      ativo: "",
      isInvalido: false,
      mensagem: ""
    };
  },
  methods: {
    
    getDados(){
      return{
        id: this.id,
        descricao: this.descricao,
        ativo: this.ativo,
      }
    },
    async salvarFormaPagamento() {
        if (this.descricao === "") {
          this.isInvalido = true;
          this.mensagem = "A Descrição deve ser Preenchida"
          return;
        }
        this.isInvalido = false;

        try{

          if(this.id === "") {
            //incluir pelo POST da API
            const response = await formaPagamentoService.criar(
              this.getDados());
              this.listaFormasPagamento = response.data;
          }else {
            // alterar pelo PUT da API
            
            const response = await formaPagamentoService.atualizar(
              this.id,
            this.getDados());
              this.listaFormasPagamento = response.data;
          }

          this.$emit("salvar_formaPagamento", {
            id: this.id,
            descricao: this.descricao,
            ativo: this.ativo
          });
          this.id = "";
          this.descricao = "";
          this.ativo = "";

        }catch(error){
          this.isInvalido = true;
          if(error.response.status === 403){
            this.mensagem = "Usuario não identificado! faça o login!!!";
          }else if(error.response.status === 400){
            this.mensagem = error.response.data.mensagem;
          }else{
            this.mensagem = error.message;
          }     
        }
    },
      
    cancelar() {
      this.id = "";
      this.descricao = "";
      this.ativo = "";
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsFormaPagamento) {
        this.id = this.propsFormaPagamento.id;
        this.descricao = this.propsFormaPagamento.descricao;
        this.ativo = this.propsFormaPagamento.ativo;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

