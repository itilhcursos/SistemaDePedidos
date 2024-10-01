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
          <label class="form-label">Municipio</label>
          <select v-model="municipioSelected" class="form-select">
              <option v-for="municipio in municipios" :value="municipio.id" :key="municipio.id">
                {{ municipio.nome }}
              </option>
          </select>
        </div>
            <div class="mb-3">
                <label class="form-label">Nome ou Razção Social</label>
                <input
                class="form-control"
                type="text"
                v-model="nomeRazaoSocial"
                placeholder="nomeRazaoSocial"
                />
            </div>
            <div class="mb-3" >
                <label class="form-label">CNPJ</label>
                <input
                class="form-control"
                type="text"
                v-model="cnpj"
                placeholder="CNPJ"
                :disabled="!getCnpj"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">CPF</label>
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
                placeholder="Telefone"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Endereço</label>
                <input
                class="form-control"
                type="text"
                v-model="endereco"
                placeholder="Endereço"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Bairro</label>
                <input
                class="form-control"
                type="text"
                v-model="bairro"
                placeholder="Bairro"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">CEP</label>
                <input
                class="form-control"
                type="text"
                v-model="cep"
                placeholder="CEP"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input
                class="form-control"
                type="text"
                v-model="email"
                placeholder="Email"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Informações</label>
                <input
                class="form-control"
                type="text"
                v-model="informacoes"
                placeholder="Informacoes"
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
import axios from "axios";
import municipioService from "@/services/municipioService";
export default {
    props: {
        propsCliente: Object,
    },


    data() {
        return {
            id: "",
            nomeRazaoSocial: "",
            municipioSelected: "",
            municipio:[],
            cnpj: "",
            cpf: "",
            telefone: "",
            endereco: "",
            bairro: "", 
            cep:"",           
            email: "",
            informacoes: "",
            isInvalido: false,
            mensagem: ""
        };
    },


    methods: {
        async salvarCliente() {
            if (this.descricao === "") {
                this.isInvalido = true;
                  this.mensagem = "Nome ou Razão Social do Cliente não pode ser vazia."
                return;
            }
            this.isInvalido = false;
            let config = {
                headers: {
                'Authorization': 'Bearer ' +localStorage.getItem('token')
                }
            }
            try{
            if (this.id === "") {
                const response = await axios.post("http://localhost:8080/cliente", {
                    id: this.id, 
                    nomeRazaoSocial:this.nomeRazaoSocial,  
                    muncipioId:this.municipioSelected,
                    cnpj:this.cnpj,
                    cpf:this.cpf,
                    telefone:this.telefone,
                    endereco:this.endereco,
                    bairro:this.bairro,
                    cep:this.cep,
                    email:this.email,
                    informacoes:this.informacoes,
                    ativo: this.ativo
                }, config);
                this.listaClientes = response.data;
            } else {
                const response = await axios.put(`http://localhost:8080/cliente/${this.id}`, 
                {
                    id: this.id, 
                    nomeRazaoSocial:this.nomeRazaoSocial,  
                    muncipioId:this.municipioSelected,
                    cnpj:this.cnpj,
                    cpf:this.cpf,
                    telefone:this.telefone,
                    endereco:this.endereco,
                    bairro:this.bairro,
                    cep:this.cep,
                    email:this.email,
                    informacoes:this.informacoes,
                    ativo: this.ativo
                }, config);
                this.listaClientes = response.data;
            }

            this.$emit("salvar_cliente", {
                    id:this.id, 
                    nomeRazaoSocial:this.nomeRazaoSocial,  
                    muncipioId:this.municipioSelected,
                    cnpj:this.cnpj,
                    cpf:this.cpf,
                    telefone:this.telefone,
                    endereco:this.endereco,
                    bairro:this.bairro,
                    cep:this.cep,
                    email:this.email,
                    informacoes:this.informacoes,
                    ativo: this.ativo
            });
                 this.id =""; 
                 this.nomeRazaoSocial =""; 
                 this.municipioSelected =""; 
                 this.cnpj =""; 
                 this.cpf =""; 
                 this.telefone ="";  
                 this.endereco =""; 
                 this.bairro =""; 
                 this.cep =""; 
                 this.email =""; 
                 this.informacoes =""; 
                this.ativo =""; 
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
     }}},

        cancelar(){
                 this.id =""; 
                 this.nomeRazaoSocial =""; 
                 this.municipioSelected =""; 
                 this.cnpj =""; 
                 this.cpf =""; 
                 this.telefone ="";  
                 this.endereco =""; 
                 this.bairro =""; 
                 this.cep =""; 
                 this.email =""; 
                 this.informacoes =""; 
                this.ativo =""; 
            this.$emit("cancelar", true);
        },
        async buscarMunicipios(){
      const response = await municipioService.listar(1,1000, 'ASC', 'id');
      this.municipio = response.content;
    }
    },


    mounted(){
        if (this.propsCliente) {
            this.id = this.propsCliente.id;
            this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
            this.municipioSelected = this.propsCliente.muncipioId;
            this.cnpj = this.propsCliente.cnpj;
            this.cpf = this.propsCliente.cpf;
            this.telefone= this.propsCliente.telefone;
            this.endereco =this.propsCliente.endereco;
            this.bairro = this.propsCliente.bairro;
            this.cep = this.propsCliente.cep;
            this.email = this.propsCliente.email;
            this.informacoes = this.propsCliente.informacoes;
            this.ativo = this.propsCliente.ativo;
        }
    },


    computed: {
        getAcao(){
            return this.id === "" ? "Incluir" : "Alterar";
        },
        getCnpj(){
            return this.cpf === '' ? true : false;
        },
        getCpf(){
            return this.cnpj === '' ? true : false;
        }
    }
}
</script>
