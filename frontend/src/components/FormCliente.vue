<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white"> {{ getAcao }} Cliente</h4>
        <hr />
        <form>
            <div class="mb-3" >
                <label class="form-label">ID</label>
                <input class="form-control" style="background-color:rgb(150, 150, 150)"
                    type="text"
                    v-model="id"
                    :disabled="true"
                    placeholder="ID do Cliente"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Nome ou Razão Social</label>
                <input class="form-control"
                    type="text"
                    v-model="nomeRazaoSocial"
                    placeholder="Nome ou Razão Social"/>
            </div>
            <div class="mb-3">
                <label class="form-label">CNPJ</label>
                <input class="form-control"
                    type="text"
                    v-model="cnpj"
                    placeholder="CNPJ"/>
            </div>
            <div class="mb-3">
                <label class="form-label">CPF</label>
                <input class="form-control"
                    type="text"
                    v-model="cpf"
                    placeholder="CPF"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Telefone</label>
                <input class="form-control"
                    type="text"
                    v-model="telefone"
                    placeholder="Número de Telefone"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Endereço</label>
                <input class="form-control"
                    type="text"
                    v-model="endereco"
                    placeholder="Endereço"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Bairro</label>
                <input class="form-control"
                    type="text"
                    v-model="bairro"
                    placeholder="Bairro"/>
            </div>
            <div class="mb-3">
                <label class="form-label">CEP</label>
                <input class="form-control"
                    type="text"
                    v-model="cep"
                    placeholder="CEP"/>
            </div>
            <div class="mb-3">
                <label class="form-label">E-mail</label>
                <input class="form-control"
                    type="text"
                    v-model="email"
                    placeholder="E-mail"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Ativo</label>
                <select v-model="ativo" class="form-select">
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Informações</label>
                <input class="form-control"
                    type="text"
                    v-model="informacao"
                    placeholder="Informações"/>
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
export default {
    props:{
        propsCliente: Object
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
            ativo: "",
            informacao: ""
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
        this.ativo=this.propsCliente.ativo;
        this.informacao= this.propsCliente.informacao;
    }
    },
    methods:{
        getDados(){

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
            this.ativo = "";
            this.informacao = "";
            this.$emit("flip", true)
        },
        async salvar(){
            if (this.nome === ""){
                this.isInvalido = true;
                this.mensagem = "Nome deve ser preenchido!";
                return;
            }
            try{
                if (this.id === ""){
                    const response = await clienteService.criar(this.getDados());
                    this.listaClientes = response;
                } else {
                    const response = await clienteService.atualizar(this.id, this.getDados());
                    this.listaClientes = response;
                }
                this.$emit("salvar", {
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
                informacao: this.informacao
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
                this.ativo="";
                this.informacao= "";

            } catch (error) {
                this.isInvalido = true;
                if(error.response.status === 403){        
                    this.mensagem = "Usuário não identificado! Faça o login!!!";
                }else if(error.response.status === 400 &&
                    error.response.data.exception === 'ClienteDuplicadoException'){
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
    },
}
</script>