<template>
    <div class="container">
      <div class="row">
        <div class="col-10">
          <h3>Municipios</h3>
        </div>
        <div class="col-2 d-flex justify-content-end">
          <button v-if="!formVisible" @click="novaMunicipio" class="btn btn-success">
            <i class="bi bi-clipboard-plus"></i> Novo
          </button>
        </div>
        <div class="row">
          <div>
            <FormFormaPagamento
              v-if="formVisible"
              :propsFormaPagamento="MunicipioEscolhido"
              @cancelar="limpar"
              @salvar_municipio="buscarMunicipio"
            />
          </div>
        </div>
      </div>
  
      <table class="table table-dark table-striped" v-if="!formVisible">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">NOME</th>
            <th scope="col">ENTREGA</th>
            <th scope="col">ESTADO</th>
            <th scope="col" class="d-flex justify-content-end">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="municipio in listaMunicipios" :key="municipio.id" scope="row">
            <th>
              {{ municipio.id }}
            </th>
            <td>
              {{ municipio.nome }}
            </td>
            <td>
              {{ formatarEntrega(municipio.entrega) }}
            </td>
            <td class="d-flex justify-content-end">
              <button
                class="btn btn-btn btn-primary m-2"
                @click="alterarmunicipio(municipio)"
              >
                <i class="bi bi-clipboard-pulse"></i> Alterar
              </button>
  
              <button
                class="btn btn-outline-danger m-2"
                @click="excluirMunicipio(municipio.id)"
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

  import FormMunicipio from "./FormMunicipio.vue";
  import axios from "axios";
  export default {
    components: {
      FormMunicipio,
    },
    data() {
      return {
        listaFormasPagamento: [],
        formaPagamentoEscolhida: null,
        formVisible: false,
        mode: import.meta.env.MODE,
        url:import.meta.env.VITE_APP_URL_API,
        pageNumber: 1,
        pageSize: 10,
        direction: "ASC",
        property: "id",
        totalPages: 0,
      };
    },
    methods: {
      async buscarMunicipio() {
        this.MunicipioEscolhido = null;
        this.formVisible = false;
        const response = await axios.get(
          `http://localhost:8080/formas-pagamento?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
        );
        console.log(response.data);
        this.listaMunicipios = response.data.content;
        this.totalPages = response.data.totalPages;
        console.log(this.totalPages);
      },
    
        Entrega = (valor){
            return Logico.toSimNAO(valor);

        },
      limpar() {
        this.municipioEscolhido = null;
        this.formVisible = this.formVisible;
        },
        novoMunicipio() {
        this.formVisible = this.formVisible;
      },
      alterarMunicipio(municipio) {
        this.municipioEscolhido = municipio;
        this.formVisible = true;
      },
      async excluirFormaPagamento(id) {
        let config = {
          headers: {
            'Authorization': 'Bearer ' +localStorage.getItem('token')
          }
        }
        const response = await axios.delete(`http://localhost:8080/formas-pagamento/${id}`,config);
        console.log(response.data);
        this.buscarMunicipio();
      },
      irPara(pagina) {
        this.pageNumber = pagina;
        this.buscarFormaPagamento();
      },
    },
    mounted() {
      this.buscarMunicipio();
    },
  };
  </script>
  