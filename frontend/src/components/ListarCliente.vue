<template>
    <div class="container">
      <div class="row">
        <div class="col-10">
          <h3>Cliente</h3>
        </div>
        <div class="col-2 d-flex justify-content-end">
          <button v-if="!formVisible" @click="novoCliente" class="btn btn-success">
            <i class="bi bi-clipboard-plus"></i> Novo
          </button>
        </div>
        <div class="row">
          <div>
            <FormCliente
              v-if="formVisible"
              :propsCliente="clienteEscolhido"
              @cancelar="limpar"
              @salvar_cliente="buscar"
            />
          </div>
        </div>
      </div>
  
      <table class="table table-dark table-striped" v-if="!formVisible">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">CLIENTE</th>
            <th scope="col">TELEFONE</th>
            <th scope="col">EMAIL</th>
            <th scope="col">CPF</th>
            <th scope="col">MUNICÍPIO</th>
            <th scope="col">ATIVO</th>
            <th scope="col" class="d-flex justify-content-end">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cliente in listaClientes" :key="cliente.id" scope="row">
            <th>
              {{ cliente.id }}
            </th>
            <td>
              {{ cliente.nomeRazaoSocial }}
            </td>
            <td>
              {{ cliente.telefone }}
            </td>
            <td>
              {{ cliente.email }}
            </td>
            <td>
              {{ cliente.cpf }}
            </td>
            <td>
              {{ cliente.municipioNome }}
            </td>
            <td>
              {{ formatarLogico(cliente.ativo) }}
            </td>
            
            <td class="d-flex justify-content-end">
              <button
                class="btn btn-btn btn-primary m-2"
                @click="alterarCliente(cliente)"
              >
                <i class="bi bi-clipboard-pulse"></i> Alterar
              </button>
  
              <button
                class="btn btn-outline-danger m-2"
                @click="excluirCliente(cliente.id)"
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
              <option value="municipioNome">MUNICÍPIO</option>
            </select>
          </div>
          <div class="col-auto">
            <select v-model="direction" class="form-select">
              <option value="ASC">Crescente</option>
              <option value="DESC">Decrescente</option>
            </select>
          </div>
          <div class="col-auto">
            <button @click.prevent="buscar" class="btn btn-success">
              <i class="bi bi-binoculars"></i>
              Buscar
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  
  <script>

  import FormCliente from "./FormCliente.vue";
  import ClienteService from "@/services/ClienteService";
  import Logico from "@/utils/Logico";
  export default {
    components: {
      FormCliente,
    },
    data() {
      return {
        listaClientes: [],
        clienteEscolhido: null,
        formVisible: false,
        pageNumber: 1,
        pageSize: 10,
        direction: "ASC",
        property: "id",
        totalPages: 0,
      };
    },
    methods: {
      async buscar() {
        this.clienteEscolhido = null;
        this.formVisible = false;
        const response = await ClienteService.listar(this.pageNumber, this.pageSize,this.direction, this.property);     
      this.listaClientes = response.content;
      this.totalPages = response.totalPages;
        },
       
    limpar() {
        this.clienteEscolhido = null;
        this.formVisible = !this.formVisible;
        },
    novoCliente() {
        this.formVisible = !this.formVisible;
      },
   alterarCliente(cliente) {
        this.clienteEscolhido = cliente;
        this.formVisible = true;
      },
      async excluirCliente(id) {
        try{
          const response = await ClienteService.apagar(id);
          console.log(response);
      }catch(error){
        if(error.response.status === 403){        
         alert("Usuário não identificado! Faça o login!!!");
        }else if(error.response.status === 400 ){
          alert(error.response.data.mensagem);     
        }else{
          alert(error.message);
        }
      }     
      this.buscar();
      },
      irPara(pagina) {
        this.pageNumber = pagina;
        this.buscar();
      },
      formatarLogico(valor){
        return Logico.toSimNao(valor);
      },
    },
    mounted() {
      this.buscar();
    },
  };
  </script>
  