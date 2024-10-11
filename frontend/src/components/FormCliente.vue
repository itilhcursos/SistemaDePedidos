<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white"> {{ getAcao }} Cliente</h4>
        <!-- <hr />
        <div>Campos marcados com * são obrigatórios.</div> <div>Campos marcados com ** significa que somente um deles é obrigatório.</div> <hr /> -->
        <form>
            <div class="row" style="padding: 10px">
                <div class="col" >
                <label class="form-label">ID (Automático)</label>
                <input class="form-control" style="background-color:rgb(150, 150, 150)"
                    type="text"
                    v-model="id"
                    :disabled="true"
                    placeholder="ID do Cliente."/>
                </div>
                
                <div class="col">
                    <label class="form-label">CNPJ</label>
                    <input class="form-control"
                        type="text"
                        v-model="cnpj"
                        placeholder="CNPJ..."
                        :disabled= "setCnpjState"/>  
                </div>
                <div class="col">
                    <label class="form-label">CPF</label>
                    <input class="form-control"
                        type="text"
                        v-model="cpf"
                        placeholder="CPF..."
                        :disabled= "setCpfState"/>              
                </div>
            </div>

            <div class="row" style="padding: 10px">
                <div class="col">
                    <label class="form-label">Nome ou Razão Social</label>
                    <input class="form-control"
                        type="text"
                        v-model="nomeRazaoSocial"
                        placeholder="Nome ou Razão Social da empresa..."/>
                </div>
                <div class="col">
                    <label class="form-label">Telefone</label>
                    <input class="form-control"
                        type="text"
                        v-model="telefone"
                        placeholder="Número de Telefone..."/>
                </div>
                <div class="col">
                    <label class="form-label">E-mail</label>
                    <input class="form-control"
                        type="text"
                        v-model="email"
                        placeholder="E-mail..."/>
                </div>
            </div>
            
            <div class="row" style="padding: 10px">
                <div class="col">
                <label class="form-label">Endereço</label>
                <input class="form-control"
                    type="text"
                    v-model="endereco"
                    placeholder="Endereço..."/>
                </div>
                <div class="col">
                    <label class="form-label">Bairro</label>
                    <input class="form-control"
                        type="text"
                        v-model="bairro"
                        placeholder="Bairro de residência..."/>
                </div>
                <div class="col">
                    <label class="form-label">CEP</label>
                    <input class="form-control"
                        type="text"
                        v-model="cep"
                        placeholder="CEP..."/>
                </div>        
            </div>
            
            <div class="row" style="padding: 10px">
                <div class="col">
                    <label class="form-label">Municipio* </label>
                    <v-select class="meu-select" v-model="municipioSelecionado" :filterable="false" :options="optionsMunicipio"
                        @search="onSearchMunicipio">
                        <template v-slot:no-options>
                        Pesquise um Municipio...
                        </template>
                        <template v-slot:option="option">
                        {{ option.nome+';' }}&nbsp;{{ option.estadoNome}}
                        </template>
                        <template v-slot:selected-option="option">
                        {{ option.nome }}
                        </template>
                    </v-select>
                </div>
                <div class="col">
                <label class="form-label">Informações</label>
                <input class="form-control"
                    type="text"
                    v-model="informacao"
                    placeholder="Informações sobre o Cliente..."/>
                </div>
                <div class="col">
                    <label class="form-label">Ativo?</label>
                    <select v-model="ativo" class="form-select">
                        <option value="true">Sim (Ativo)</option>
                        <option value="false">Não (Inativo)</option>
                    </select>
                </div>
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
            municipioNome: "",

            isLoading: false,
            optionsMunicipio: [],
            municipioSelecionado: null
        }
    },
    mounted(){
        if (this.propsCliente) {
            this.id = this.propsCliente.id;
            this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
            this.cnpj = this.propsCliente.cnpj;
            this.cpf = this.propsCliente.cpf;
            this.telefone = this.propsCliente.telefone;
            this.endereco = this.propsCliente.endereco;
            this.bairro = this.propsCliente.bairro;
            this.cep= this.propsCliente.cep;
            this.email= this.propsCliente.email;
            this.informacao= this.propsCliente.informacao;
            this.ativo=this.propsCliente.ativo;
            this.municipioId=this.propsCliente.municipioId;
            this.municipioNome= this.propsCliente.municipioNome;
            
            this.municipioSelecionado = {id: this.propsCliente.municipioId, nome:this.propsCliente.municipioNome};
        }
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
                municipioId: this.municipioSelecionado.id
             };
        },
        async onSearchMunicipio(search, loading) {
            if (search == "")
            return;
            loading(true);
            await municipioService.buscar(search).then((response) => {
                //console.log(response);
                this.optionsMunicipio = response.content;
                loading(false);
            });
        },
        /* async buscarMunicipios(){
            const response = await municipioService.listar(1, 1000, 'ASC', 'nome');
            this.municipios = response.content;
        }, */
        cancelar(){
            this.limparCampos();
            this.$emit("cancelar", true);
        },
        async salvar(){
            if (this.municipioSelecionado == null){
                this.isInvalido = true;
                this.mensagem = "Preencha com um municipio!";
                return;
            }
            this.isInvalido = false;
            try{
                if (this.id === ""){
                    const response = await clienteService.criar(this.getDados());
                    console.log(response);
                } else {
                    const response = await clienteService.atualizar(this.id, this.getDados());
                    this.listaClientes = response;
                }
                this.$emit("salvar"/* , true */
                /* {
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
                }*/);

                this.limparCampos();

            } catch (error) {
                this.isInvalido = true;
                this.mensagem = error.message;
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
        },
        limparCampos(){
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
            this.municipioId == "";
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

<style>
.meu-select {
    width: 100%;
    font-size: 1.0em;
    color: #252525;
    background: #fbf4f4;
    border-radius: 0.375rem;
  }
</style>