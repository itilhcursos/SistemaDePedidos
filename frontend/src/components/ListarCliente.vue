<template>
    <div class="container">
      <div class="row">
        <div class="col-10">
          <h3>CLIENTES</h3>
        </div>
        <div class="col-2 d-flex justify-content-end">
          <button v-if="!formVisible" @click="novoCliente" class="btn btn-success">
            <i class="bi bi-clipboard-plus"></i> Incluir
          </button>
        </div>
        <div class="row">
          <div>
            <FormCliente
              v-if="formVisible"
              :propsCliente="clienteEscolhido"
              @cancelar="limpar"
              @salvar_cliente="buscarClientes"
            />
          </div>
        </div>
      </div>
  
      <table class="table table-dark table-striped" v-if="!formVisible">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Município</th>
            <th scope="col">Nome</th>
            <th scope="col">CNPJ</th>
            <th scope="col">CPF</th>
            <th scope="col">Telefone</th>
            <th scope="col">Endereço</th>
            <th scope="col">Bairro</th>
            <th scope="col">CEP</th>
            <th scope="col">E-mail</th>
            <th scope="col">Informação</th>
            <th scope="col">Entrega</th>
            <th scope="col" class="d-flex justify-content-center">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cliente in listaClientes" :key="cliente.id" scope="row">
            <th>
              {{ cliente.id }}
            </th>
            <td>
              {{ cliente.municipioNome }}
            </td>
            <td>
              {{ cliente.nomeRazaoSocial }}
            </td>
            <td>
              {{ cliente.cnpj }}
            </td>
            <td>
              {{ cliente.cpf }}
            </td>
            <td>
              {{ cliente.telefone }}
            </td>
            <td>
              {{ cliente.endereco }}
            </td>
            <td>
              {{ cliente.bairro }}
            </td>
            <td>
              {{ cliente.cep }}
            </td>
            <td>
              {{ cliente.email }}
            </td>
            <td>
              {{ cliente.informacao }}
            </td>
            <td>
              {{ formatarLogico(cliente.ativo) }}
            </td>
            <td class="d-flex justify-content-center">
              <button
                class="btn btn-outline-warning m-2"
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
            <button @click.prevent="buscarClientes" class="btn btn-primary m-2">
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
import Logico from "@/utils/Logico.js";
import clienteService from "@/services/clienteService";
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
    async buscarClientes() {
      this.clienteEscolhido = null;
      this.formVisible = false;
      const response = await clienteService.listar(this.pageNumber, this.pageSize,this.direction, this.property);     
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
      let config = {
        headers: {
          'Authorization': 'Bearer ' +localStorage.getItem('token')
        }
      }
      const response = await clienteService.delete(`http://localhost:8080/cliente/${id}`, config);
      console.log(response.data);
      this.buscarClientes();
    },
    irPara(pagina) {
      this.pageNumber = pagina;
      this.buscarClientes();
    },
    formatarLogico(valor){
      return Logico.toSimNao(valor);
    },
  },
  mounted() {
    this.buscarClientes();
  },
};
</script>  