<template>
  <div class="container">
    <div class="row">
      <div class="col-10">
        <h3>ESTADOS</h3>
      </div>
      <div class="col-2 d-flex justify-content-end">
        <button v-if="!formVisible" @click="novoEstado" class="btn btn-success">
          <i class="bi bi-clipboard-plus"></i> Novo
        </button>
      </div>
      <div class="row">
        <div>
          <FormEstado
            v-if="formVisible"
            :propsEstado="estadoEscolhido"
            @cancelar="limpar"
            @salvar_estado="buscarEstados"
          />
        </div>
      </div>
    </div>

    <table class="table table-dark table-striped" v-if="!formVisible">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Nome</th>
          <th scope="col" class="d-flex justify-content-end">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="estado in listaEstados" :key="estado.id" scope="row">
          <th>
            {{ estado.id }}
          </th>
          <td>
            {{ estado.nome }}
          </td>
          <td class="d-flex justify-content-end">
            <button
              class="btn btn-btn btn-primary m-2"
              @click="alterarEstado(estado)"
            >
              <i class="bi bi-clipboard-pulse"></i> Alterar
            </button>

            <button
              class="btn btn-outline-danger m-2"
              @click="excluirEstado(estado.id)"
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
            <option value="nome">Nome</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscarEstados" class="btn btn-success">
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import FormEstado from "./FormEstado.vue";
import estadoService from "@/services/estadoService";
export default {
  components: {
    FormEstado,
  },
  data() {
    return {
      listaEstados: [],
      estadoEscolhido: null,
      formVisible: false,
      mode: import.meta.env.MODE,
      url: import.meta.env.VITE_APP_URL_API,
      pageNumber: 1,
      pageSize: 10,
      direction: "ASC",
      property: "id",
      totalPages: 0,
    };
  },
  methods: {
    async buscarEstados() {
      this.estadoEscolhido = null;
      this.formVisible = false;
      //buscar a lista de estados no servidor
      // http://localhost:8080/estados

      const response =  await estadoService.listar(this.pageNumber, this.pageSize, this.direction, this.property);

   
      this.listaEstados = response.content;
      this.totalPages = response.totalPages;
     
    },
    limpar() {
      this.estadoEscolhido = null;
      this.formVisible = !this.formVisible;
    },
    novoEstado() {
      this.formVisible = !this.formVisible;
    },
    alterarEstado(estado) {
      this.estadoEscolhido = estado;
      this.formVisible = true;
    },
    
    async excluirEstado(id) {
      // primeiro tipo de validação
      //
      // if(localStorage.getItem('token') === null) {
      //   alert("Usuário não identificado! faça o login!!!");
      //   return
      // } o if testa antes - ele manda uma mensagem e evitando que o sujeito faça a tentativa de mandar uma mensagem sem ele estar logado
      //O try testa depois - primeiro ele execulta e depois quando ele ve que vai dar errado ele faz o tratamento
      

      //segundo tipo de validação.
      
      try {
        const response = await estadoService.apagar(id);
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
      this.buscarEstados();
    },

    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarEstados();
    },
  },
  mounted() {
    this.buscarEstados();
  },
};
</script>
