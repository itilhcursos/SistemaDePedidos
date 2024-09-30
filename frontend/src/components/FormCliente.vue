<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white"> {{ getAcao }} Cliente</h4>
        <hr />
        <div>Campos marcados com * são obrigatórios.</div> <div>Campos marcados com ** significa que somente um deles é obrigatório.</div> <hr />
        <form>
            <div class="mb-3" >
                <label class="form-label">ID</label>
                <input class="form-control" style="background-color:rgb(150, 150, 150)"
                    type="text"
                    v-model="id"
                    :disabled="true"
                    placeholder="ID do Cliente. (Automático)"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Nome ou Razão Social*</label>
                <input class="form-control"
                    type="text"
                    v-model="nomeRazaoSocial"
                    placeholder="Nome ou Razão Social da empresa..."/>
            </div>
            <div class="mb-3">
                <label class="form-label">CNPJ*</label>
                <input class="form-control"
                    type="text"
                    v-model="cnpj"
                    placeholder="CNPJ..."
                    :disabled= "setCnpjState"/>  
            </div>
            <div class="mb-3">
                <label class="form-label">CPF*</label>
                <input class="form-control"
                    type="text"
                    v-model="cpf"
                    placeholder="CPF..."
                    :disabled= "setCpfState"/>              
            </div>
            <div class="mb-3">
                <label class="form-label">Telefone**</label>
                <input class="form-control"
                    type="text"
                    v-model="telefone"
                    placeholder="Número de Telefone..."/>
            </div>
            <div class="mb-3">
                <label class="form-label">Endereço*</label>
                <input class="form-control"
                    type="text"
                    v-model="endereco"
                    placeholder="Endereço..."/>
            </div>
            <div class="mb-3">
                <label class="form-label">Bairro*</label>
                <input class="form-control"
                    type="text"
                    v-model="bairro"
                    placeholder="Bairro de residência..."/>
            </div>
            <div class="mb-3">
                <label class="form-label">CEP*</label>
                <input class="form-control"
                    type="text"
                    v-model="cep"
                    placeholder="CEP..."/>
            </div>
            <div class="mb-3">
                <label class="form-label">E-mail**</label>
                <input class="form-control"
                    type="text"
                    v-model="email"
                    placeholder="E-mail..."/>
            </div>
            <div class="mb-3">
                <label class="form-label">Informações</label>
                <input class="form-control"
                    type="text"
                    v-model="informacao"
                    placeholder="Informações sobre o Cliente..."/>
            </div>
            <div class="mb-3">
                <label class="form-label">Ativo?</label>
                <select v-model="ativo" class="form-select">
                    <option value="true">Sim (Ativo)</option>
                    <option value="false">Não (Inativo)</option>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Municipio* </label>
                <select v-model="municipioSelecionado">
                    <option v-for="municipio in municipios" :value="municipio.id" :key="municipio.id"> {{ municipio.nome }} </option>
                </select>
            </div>


            <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">{{ mensagem }}</div>
            </div>
            <div class="mb-3 d-flex justify-content-end">
                <button class="btn btn-primary m-2" type="submit" @click.prevent="salvar"><i class="bi bi-clipboard2-check"></i> {{ getAcao }} </button>
                <button class="btn btn-warning m-2" type="submit" @click.prevent="cancelar"><i class="bi bi-clipboard2-x"></i> Cancelar </button>
            </div>
        </form>
    </div>
</template>

<script>
import clienteService from '@/services/clienteService';
import municipioService from '@/services/municipioService';
export default {
    props:{
        propsCliente: Object,
    },
    data(){
        return {
            isInvalido: false,
            mensagem: '',

            id: "",
            nomeRazaoSocial: "",
            cnpj: "",
            cpf: "",
            telefone: "",
            endereco: "",
            bairro: "",
            cep: "",
            email: "",
            informacao: "",
            ativo: "",
            municipioId: "",
            municipioSelecionado: "",
            municipios: []
        }
    },
    mounted(){
        if (this.propsCliente) {
            this.id = this.propsCliente.id;
            this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
            this.cnpj = this.propsCliente.cnpj;
            this.cpf = this.propsCliente.nome;
            this.telefone = this.propsCliente.id;
            this.endereco = this.propsCliente.nome;
            this.bairro = this.propsCliente.id;
            this.cep= this.propsCliente.cep;
            this.email= this.propsCliente.email;
            this.informacao= this.propsCliente.informacao;
            this.ativo=this.propsCliente.ativo;
            this.municipioSelecionado= this.propsCliente.municipioId;
        }
        this.buscarMunicipios();
    },
    methods:{
        getDados(){
            return{
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
                municipioId: this.municipioSelecionado
             };
        },
        async buscarMunicipios(){
            const response = await municipioService.listar(1, 1000, 'ASC', 'nome');
            this.municipios = response.content;
        },
        cancelar(){
            this.id = "";
            this.nomeRazaoSocial = "";
            this.cnpj = "";
            this.cpf = "";
            this.telefone = "";
            this.endereco = "";
            this.bairro = "";
            this.cep = "";
            this.email= "";
            this.informacao = "";
            this.ativo = "";
            this.$emit("cancelar", true)
        },
        async salvar(){
            if (this.nome === "" || this.cep === "" || this.bairro === "" || this.endereco === "" || this.municipioId === null){
                this.isInvalido = true;
                this.mensagem = "Dados obrigatórios devem ser preenchidos!";
                return;
            }
            if (this.cpf === "" && this.cnpj === ""){
                this.isInvalido = true;
                this.mensagem = "Dados obrigatórios devem ser preenchidos!";
                return;
            }
            if (this.email === "" && this.telefone === ""){
                this.isInvalido = true;
                this.mensagem = "Dados obrigatórios devem ser preenchidos!";
                return;
            }
            this.isInvalido = false;
            try{
                if (this.id === ""){
                    const response = await clienteService.criar(this.getDados());
                    this.listaClientes = response;
                } else {
                    const response = await clienteService.atualizar(this.id, this.getDados());
                    this.listaClientes = response;
                }
                this.$emit("salvar", {
                    //Apagável?
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
                municipioId: this.municipioSelecionado
                });

                // Apagável? 
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

            } catch (error) {
                this.isInvalido = true;
                if(error.response.status === 403){        
                    this.mensagem = "Usuário não identificado! Faça o login!!!";
                }else if(error.response.status === 400 &&
                    error.response.data.exception === 'ClienteDuplicadoException'){
                    this.mensagem = error.response.data.mensagem;
                }else if(error.response.status === 400){     
                    this.mensagem = error.response.data.mensagem;
                }else{
                    this.mensagem = error.message;
                } 
            }
        }
    },
    computed:{
        getAcao() {
            return this.id === "" ? "Incluir" : "Alterar";
        },
        setCnpjState(){
            return this.cpf ? true : false;
        },
        setCpfState(){
            return this.cnpj ? true : false;
        },
    }
}   
</script>