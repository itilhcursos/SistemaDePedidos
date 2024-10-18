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
          <label class="form-label">Municipio</label>
          <select v-model="municipioSelected" class="form-control">
            <option v-for="municipio in municipios" :value="municipio.id" :key="municipio.id">
              {{ municipio.nome }}
            </option>
          </select >
      </div>

      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="nomeRazaoSocial"
          placeholder="NOME"
          
        />
      </div>
      <!-- O v-if é como visible e Invisible que faz com que o cnpj ou cpf suma do formulario caso uma das duas estejam preenchidas. -->
      <div class="mb-3">
        <label class="form-label">CNPJ*</label>
        <input
          class="form-control"
          type="text"
          v-model="cnpj"
          placeholder="CNPJ"
          :disabled="!getCnpj"
        />
       <!-- ja o disabled é justamente o que diz desabilitar, mesmo ainda aparecendo no form, não se pode mecher nele. -->
      </div>
      <div class="mb-3"  >
        <label class="form-label">CPF*</label>
        <input
          class="form-control"
          type="text"
          v-model="cpf"
          placeholder="CPF"
          :disabled="!getCpf"
        />
      </div>
          
      <div class="mb-3">
        <label class="form-label">Telefone</label>
        <input
          class="form-control"
          type="text"
          v-model="telefone"
          placeholder="TELEFONE"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Endereco*</label>
        <input
          class="form-control"
          type="text"
          v-model="endereco"
          placeholder="ENDEREÇO"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Bairro*</label>
        <input
          class="form-control"
          type="text"
          v-model="bairro"
          placeholder="Bairro"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">cep</label>
        <input
          class="form-control"
          type="text"
          v-model="cep"
          placeholder="CEP"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">email</label>
        <input
          class="form-control"
          type="text"
          v-model="email"
          placeholder="nome@exemplo.com"
        />
      </div>

      <div class="mb-3">
        <label class="form-label">Ativo</label>
        <select class="form-control" v-model="ativo" >
          <option value="true">Sim</option>
          <option value="false">Não</option>
        </select>
      </div>

      <div class="mb-3">
        <label class="form-label">Informacao</label>
        <input
          class="form-control"
          type="text"
          v-model="informacao"
          placeholder="Informacao"
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
  import municipioService from '@/services/municipioService';
  import estadoService from '@/services/estadoService';
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
        telefone:"",
        bairro:"",
        email: "",
        ativo: "",
        informacao: "",
        estadoSelected: "",
        estados:[],
        municipioSelected: "",
        municipios:[],
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
          telefone: this.telefone,
          endereco: this.endereco,
          bairro: this.bairro,
          email: this.email,
          ativo: this.ativo,
          informacao: this.informacao,
          municipioId: this.municipioSelected,
          municipioEstadoId: this.estadoSelected,
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
            telefone: this.telefone,
            endereco: this.endereco,
            bairro: this.bairro,
            cep: this.cep,
            email: this.email,
            ativo: this.ativo,
            informacao: this.informacao,
            municipioId: this.municipioSelected,
            municipioEstadoId: this.estadoSelected,
          });

          this.id = "";
          this.nomeRazaoSocial = "";
          this.cnpj ="";
          this.cpf ="";
          this.telefone="";
          this.bairro = "";
          this.endereco ="";
          this.cep = "";
          this.email = "";
          this.ativo = "";
          this.informacao = "";
          this.municipioSelected = "";
          this.estadoSelected = "";
        
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
        this.telefone="";
        this.bairro = "";
        this.endereco ="";
        this.cep = "";
        this.email = "";
        this.ativo = "";
        this.informacao = "";
        this.municipioSelected = "";
        this.estadoSelected = "";
        this.$emit("cancelar", true);
      },

      async buscarMunicipios(){
        const response = await municipioService.listar(1,1000, 'ASC', 'id');
        this.municipios = response.content;
      },
      async buscarEstados(){
        const response = await estadoService.listar(1,1000,'ASC','id');
        this.estados = response.content;
      }
    },   
    mounted() {
      if(this.propsCliente){
        this.id = this.propsCliente.id;
        this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
        this.cnpj = this.propsCliente.cnpj;
        this.cpf = this.propsCliente.cpf;
        this.telefone = this.propsCliente.telefone;
        this.bairro = this.propsCliente.bairro;
        this.endereco = this.propsCliente.endereco;
        this.cep =  this.propsCliente.cep;
        this.email = this.propsCliente.email;
        this.ativo = this.propsCliente.ativo;
        this.informacao = this.propsCliente.informacao;
        this.municipioSelected = this.propsCliente.municipioId;
        this.estadoSelected = this.propsCliente.municipioEstadoId;
      }
      this.buscarMunicipios();
      this.buscarEstados();
    },
    computed: {
      getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
      },

      // getCpf só vai aparecer se o cnpj for vazio('')
      getCpf(){
        return this.cnpj === '' ? true : false;
      },
      // getCnpj só vai aparecer se o cpf for vazio('')
      getCnpj(){
        return this.cpf === '' ? true : false;
      },
      


    },
    
  };

</script>