<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>FORMAS DE PAGAMENTO</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novaFormaPagamento" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
          <FormFormaPagamento
            v-if="formVisible"
            :propsFormaPagamento="formaPagamentoEscolhida"
            @cancelar="limpar"
            @salvar_formaPagamento="buscarFormaPagamento"
          />
        </div>
      </div>
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Descrição</th>
          <th scope="col">Ativo</th>
          <th scope="col" class="d-flex justify-content-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="formaPagamento in listaFormasPagamento" :key="formaPagamento.id" scope="row">
          <th>
            {{ formaPagamento.id }}
          </th>
          <td>
            {{ formaPagamento.descricao }}
          </td>
          <td>
            {{ formatarLogico(formaPagamento.ativo) }}
          </td>
          <td class="d-flex justify-content-end">
            <button
              class="btn btn-btn btn-primary m-2"
              @click="alterarFormaPagamento(formaPagamento)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>

            <button
              class="btn btn-outline-danger m-2"
              @click="excluirFormaPagamento(formaPagamento.id)"
            >
              <i class="bi bi-clipboard2-minus"></i> Excluir
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div v-if="!formVisible">
    <hr />
    <div class="container">
      <div class="row d-flex justify-content-center">
        <div class="col-auto">

          <button
            v-for="pagina in totalPages"
            :key="pagina"
            @click.prevent="irPara(pagina)"
            class="btn btn-light ms-1"
          >
            {{ pagina }}
          </button>


        </div>
        <div class="col-auto">
          <input
            type="text"
            v-model="pageNumber"
            placeholder="Número da pagina"
            class="form-control w-25"
          />
        </div>
        <div class="col-auto">
          <select v-model="pageSize" class="form-select">
            <option value="2">2</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="property" class="form-select">
            <option value="id">ID</option>
            <option value="descricao">Descrição</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscarFormaPagamento" class="btn btn-success">
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import Logico from "@/utils/Logico";
import FormFormaPagamento from "./FormFormaPagamento.vue";
import formaPagamentoService from "@/services/formaPagamentoService";
export default {
  components: {
    FormFormaPagamento,
  },
  data() {
    return {
      listaFormasPagamento: [],
      formaPagamentoEscolhida: null,
      formVisible: false,
      pageNumber: 1,
      pageSize: 10,
      direction: "ASC",
      property: "id",
      totalPages: 0,
    };
  },
  methods: {
    async buscarFormaPagamento() {
      this.formaPagamentoEscolhida = null;
      this.formVisible = false;

      
      const response = await formaPagamentoService.listar(
          this.pageNumber, this.pageSize, this.direction, this.property
      );
    
      this.listaFormasPagamento = response.content;
      this.totalPages = response.totalPages;
     
    },
    limpar() {
      this.formaPagamentoEscolhida = null;
      this.formVisible = !this.formVisible;
    },
    novaFormaPagamento() {
      this.formVisible = !this.formVisible;
    },
    alterarFormaPagamento(formaPagamento) {
      this.formaPagamentoEscolhida = formaPagamento;
      this.formVisible = true;
    },

    async excluirFormaPagamento(id) {
      try{
        const response = await formaPagamentoService.apagar(id);
        console.log(response.data);
      }catch(error){
        if(error.response.status === 403){    // o erro 403 é quando esqueço de fazer a autenticação    
          alert( "Usuário não identificado! Faça o login!!!");
        }else if(error.response.status === 400){ 
          alert(error.response.data.mensagem);
        }else{
          alert( error.message);
        }
      }
     
      this.buscarFormaPagamento();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarFormaPagamento();
    },
    formatarLogico(valor){
      return Logico.toSimNao(valor);
    }
  },
  mounted() {
    this.buscarFormaPagamento();
  },
};
</script>
