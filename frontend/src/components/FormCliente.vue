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
          placeholder="Insira o Nome"
        />
      </div>

        <div class="mb-3" v-if="getCnpj">
              <label class="form-label">CNPJ</label>
              <input
              class="form-control"
              type="text"
              v-model="cnpj"
              placeholder="CNPJ"
              disable="!getCnpj"
              />
          </div>

          <div class="mb-3" v-if="getCpf">
              <label class="form-label">CPF</label>
              <input
              class="form-control"
              type="text"
              v-model="cpf"
              placeholder="CPF"
              disable="!getCpf"
              />
          </div>

      <div class="mb-3">
        <label class="form-label">TELEFONE</label>
        <input
          class="form-control"
          type="text"
          v-model="telefone"
          placeholder="Insira o Telefone"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">ENDEREÇO</label>
        <input
          class="form-control"
          type="text"
          v-model="endereco"
          placeholder="Insira o Endereço"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">BAIRRO</label>
        <input
          class="form-control"
          type="text"
          v-model="bairro"
          placeholder="Insira o Bairro"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">CEP</label>
        <input
          class="form-control"
          type="text"
          v-model="cep"
          placeholder="Insira o Cep"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">EMAIL</label>
        <input
          class="form-control"
          type="text"
          v-model="email"
          placeholder="Insira o Email"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">INFORMAÇÕES</label>
        <input
          class="form-control"
          type="text"
          v-model="informacao"
          placeholder="Insira as Informações"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Ativo</label>
        <select v-model="ativo" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
        </select>
      </div>

      <!-- <div class="mb-3">
        <label class="form-label">ID MUNICÍPIO</label>
        <input class="form-control"
         type="text"
         v-model="municipioId"
        placeholder="Preencha o ID do Municipio do Cliente"/>
      </div> -->


  

    <!-- <div class="mb-3">
  <label class="form-label">Entrega</label>
  <div>
    <label class="btn btn-outline-success" :class="{ active: ativo === true }">
      <input type="radio" v-model="entrega" :value="true" autocomplete="off"> Sim
    </label>
    <label class="btn btn-outline-primary" :class="{ active: ativo === false }">
      <input type="radio" v-model="entrega" :value="false" autocomplete="off"> Não
    </label>
  </div>
</div> -->

     

        <div class="mb-3">
          <label class="form-label">Município</label>
          <select v-model="municipioSelected" class="form-select">
              <option v-for="municipio in municipios" :value="municipio.id" :key="municipio.id">
                {{ municipio.nome }}
              </option>
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
import clienteService from "@/services/clienteService";
//import axios from "axios";
import municipioService from "@/services/municipioService";
export default {
  props: {
    propsCliente: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      cpf: "",
      cnpj: "",
      municipioSelected:"",
      municipios:[],
      isInvalido: false,
      mensagem : '',
    };
  },
  methods: {
    async salvarMunicipio() {
      if (this.nome === "") {
        this.isInvalido = true;
        this.mensagem = "Nome deve ser preenchido!!";
        return;
      }
      
      this.isInvalido = false;
      

      

    try {
                if (this.id === "") {
                    const response = await clienteService.criar(this.getDados());
                    this.listaClientes = response.data;
                } else {
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
              telefone: this.telefone,
              endereco: this.endereco,
              bairro: this.bairro,
              cep: this.cep,
              email: this.email,
              informacao: this.informacao,
              ativo: this.ativo,
              municipioId: this.municipioId,
              municipioNome: this.municipioNome,
      });

      this.id = "";
              this.nomeRazaoSocial = "";
              this.cnpj= "";
              this.cpf= "";
              this.telefone= "";
              this.endereco= "";
              this.bairro= "";
              this.cep= "";
              this.email= "";
              this.informacao= "";
              this.ativo="";
              this.municipioId= "";
    }catch(error){
     
      this.isInvalido = true;
      if(error.response.status === 403){        
        this.mensagem = "Usuário não identificado! Faça o login!!!";
      }else if(error.response.status === 400 &&
               error.response.data.exception === 'ClienteDuplicadoException'){
        this.mensagem = error.response.data.mensagem;
      }else if(error.response.status === 400 &&
               error.response.data.exception === 'MunicipioDuplicadoException'){
        this.mensagem = error.response.data.mensagem;          
      }else{
        this.mensagem = error.message;
      }
    }
  },

    getDados() {
            return {
              id: this.id,
              nomeRazaoSocial: this.nomeRazaoSocial,
              cnpj: this.cnpj,
              cpf: this.cpf,
              telefone: this.telefone,
              endereco: this.endereco,
              bairro: this.bairro,
              cep: this.cep,
              email: this.email,
              informacao: this.informacao,
              ativo: this.ativo,
              municipioId: this.municipioId
            };
          },
  
    cancelar() {
      this.id = "";
      this.nomeRazaoSocial = "";
      this.$emit("cancelar", true);
    },

    async buscarMunicipios(){
      const response = await municipioService.listar(1,1000, 'ASC', 'id');
      this.municipios = response.content;
    }

  },
  mounted() {
    if (this.propsCliente) {
      this.id = this.propsCliente.id;
      this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
      this.cnpj = this.propsCliente.cnpj;
      this.cpf = this.propsCliente.cpf;
      this.telefone = this.propsCliente.telefone;
      this.endereco = this.propsCliente.endereco;
      this.bairro = this.propsCliente.bairro;
      this.cep = this.propsCliente.cep;
      this.email = this.propsCliente.email;
      this.informacao = this.propsCliente.informacao;
      this.ativo = this.propsCliente.ativo;
      this.municipioSelected = this.propsCliente.municipioId;
      
    }
    this.buscarMunicipios();
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },

   
    getCnpj(){
          return this.cpf === '' ? true : false;
      },
      getCpf(){
          return this.cnpj === '' ? true : false;
      }
  },
};
</script>












































































































































































































































<!-- <template>
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
          placeholder="Insira o Nome"
        />
      </div>

      <div class="mb-3" v-if="getCnpj">
                <label class="form-label">CNPJ</label>
                <input
                class="form-control"
                type="text"
                v-model="cnpj"
                placeholder="CNPJ"
                disable="!getCnpj"
                />
            </div>

            <div class="mb-3" v-if="getCpf">
                <label class="form-label">CPF</label>
                <input
                class="form-control"
                type="text"
                v-model="cpf"
                placeholder="CPF"
                disable="!getCpf"
                />
            </div>

      <div class="mb-3">
        <label class="form-label">TELEFONE</label>
        <input
          class="form-control"
          type="text"
          v-model="telefone"
          placeholder="Insira o Telefone"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">ENDEREÇO</label>
        <input
          class="form-control"
          type="text"
          v-model="endereco"
          placeholder="Insira o Endereço"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">BAIRRO</label>
        <input
          class="form-control"
          type="text"
          v-model="bairro"
          placeholder="Insira o Bairro"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">CEP</label>
        <input
          class="form-control"
          type="text"
          v-model="cep"
          placeholder="Insira o Cep"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">EMAIL</label>
        <input
          class="form-control"
          type="text"
          v-model="email"
          placeholder="Insira o Email"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">INFORMAÇÕES</label>
        <input
          class="form-control"
          type="text"
          v-model="informacao"
          placeholder="Insira as Informações"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Ativo</label>
        <select v-model="ativo" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Municipio ID</label>
        <input class="form-control" type="text" v-model="municipioId" placeholder="Municipio ID" />
      </div>
      <label class="form-label">Municipio</label>
      <v-select class="form-control" label="nome" :filterable="false" v-model="municipioSelecionado"
        :options="municipios" @search="onSearchMunicipios">
        <template v-slot:no-options>
          Sem Municipios para exibir.
        </template>
        <template v-slot:option="option">
          {{ option.nome }}
        </template>
        <template v-slot:selected-option="option">
          {{ option.nome }}
        </template>
      </v-select>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarCliente">
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
import clienteService from "@/services/clienteService";
import municipioService from "@/services/municipioService";
export default {
  props: {
    propsCliente: Object,
  },
  data() {
    return {
      id: "",
      nomeRazaoSocial: "",
      cpf: "",
      cnpj: "",
      isInvalido: false,
      mensagem: "",
      municipioSelecionado: "",
      municipios: [],
    };
  },
  methods: {
    async onSearchMunicipios(search, loading) {
      if (search.length) {
        loading(true);
        await municipioService.buscar(search).then((response) => {
          console.log(response);
          this.municipios = response.content;
          loading(false);
        });
      }
      
    },
    getDados() {
      return {
        id: this.id,
        nomeRazaoSocial: this.nomeRazaoSocial,
        cnpj: this.cnpj,
        cpf: this.cpf,
        telefone: this.telefone,
        endereco: this.endereco,
        bairro: this.bairro,
        cep: this.cep,
        municipioId: this.municipioId,
        municipioNome: this.municipioNome,
      };
    },
    async salvarCliente() {
      if (!this.nomeRazaoSocial) {
        this.isInvalido = true;
        this.mensagem = "Nome/RazãoSocial do cliente não pode estar vazia.";
        return;
      }
      this.isInvalido = false;
      try {
        if (!this.id) {
          const response = await clienteService.criar(this.getDados());
          this.listaClientes = response;
        } else {
          const response = await clienteService.atualizar(
            this.id,
            this.getDados()
          );
          this.listaClientes = response;
        }
        this.$emit("salvar_cliente", this.getDados());
        this.limparFormulario();
      } catch (error) {
        this.isInvalido = true;
        if (error.response.status === 403) {
          this.mensagem = "Usuário não identificado! Faça o login!!!";
        } else if (
          error.response.status === 400 &&
          error.response.data.exception === "ClienteDuplicadoException"
        ) {
          this.mensagem = error.response.data.mensagem;
        } else {
          this.mensagem = error.message;
        }
      }
    },
    cancelar() {
      this.limparFormulario();
      this.$emit("cancelar", true);
    },
    limparFormulario() {
      this.id = "";
      this.nomeRazaoSocial = "";
      this.cnpj = "";
      this.cpf = "";
      this.telefone = "";
      this.endereco = "";
      this.bairro = "";
      this.cep = "";
      this.municipioNome = "";
    }
  },
  mounted() {
    if (this.propsCliente) {
      this.id = this.propsCliente.id;
      this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
      this.cnpj = this.propsCliente.cnpj;
      this.cpf = this.propsCliente.cpf;
      this.telefone = this.propsCliente.telefone;
      this.endereco = this.propsCliente.endereco;
      this.bairro = this.propsCliente.bairro;
      this.cep = this.propsCliente.cep;
      this.municipioNome = this.propsCliente.municipioNome;
    }
  },
  computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },

     
      getCnpj(){
            return this.cpf === '' ? true : false;
        },
        getCpf(){
            return this.cnpj === '' ? true : false;
        }
    },
  };
  </script> -->