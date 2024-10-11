<template>
    <div class="container">
        <div class="row">
            <div class="col-10">
                <h3>CLIENTES</h3>
            </div>

            <div class="col d-flex justify-content-end m-2">
                <button v-if="!formVisible" @click="flipFormVisible" class="btn btn-success">
                    <i class="bi bi-clipboard-plus"></i> Novo
                </button>
            </div>
            <div class="row">
                <div>
                    <FormCliente
                    v-if="formVisible"
                    :propsCliente="clienteEscolhido"
                    @flip="flipFormVisible"
                    @cancelar="limpar"
                    @salvar="listarClientes"
                    />
                </div>
            </div>


            <div class="row" v-if="!formVisible">
                <div class="col col-lg-4" v-for="cliente in listaClientes" :key="cliente.id" scope="row">
                    <div class="card text-white bg-dark mb-4" style="width: 22rem;">
                        <div class="card-head">
                            <h4 class="card-title">ID: {{ cliente.id }}</h4>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title mb-5">{{ cliente.nomeRazaoSocial }}</h5>
                            <p class="card-text"><b>{{ cliente.cnpj != '' ? 'CNPJ: ' : 'CPF: ' }}</b>{{ cliente.cnpj != '' ?  cliente.cnpj  : cliente.cpf }}</p>
                            <p class="card-text"><b>Telefone: </b>{{ cliente.telefone }}</p>
                            <p class="card-text"><b>Endereço: </b>{{ cliente.endereco }}</p>
                            <p class="card-text"><b>Bairro: </b>{{ cliente.bairro }}</p>
                            <p class="card-text"><b>Municipio: </b>{{ cliente.municipioNome }}</p>
                            <p class="card-text"><b>CEP: </b>{{ cliente.cep }}</p>
                            <p class="card-text"><b>Email: </b>{{ cliente.email }}</p>
                            <p class="card-text"><b>Informações: </b>{{ cliente.informacao }}</p>
                            <p class="card-text"><b>Cliente Ativo/Inativo: </b>{{ formatarAtivo(cliente.ativo) }}</p>
                            <button class="btn btn-primary m-2" @click="alterar(cliente)"><i class="bi bi-clipboard-pulse"></i> Alterar </button>
                            <button class="btn btn-danger m-2" @click="excluir(cliente.id)"><i class="bi bi-clipboard2-minus"></i> Excluir </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="!formVisible">
            <hr />
            <div class="container">
                <div class="row d-flex justify-content-center">
                    <div class="col-auto">
                        <button v-for="pagina in totalPages" :key="pagina" @click.prevent="irPara(pagina)" class="btn btn-light ms-1">
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
                        <button @click.prevent="listarClientes" class="btn btn-success">
                            <i class="bi bi-binoculars"></i>
                            Buscar
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import FormCliente from './FormCliente.vue';
import clienteService from '@/services/clienteService';
import Logico from '@/utils/Logico';
export default {
    components:{
        FormCliente
    },
    data() {
        return{
            listaClientes: [],
            clienteEscolhido: null,
            formVisible: false,
            pageNumber: 1,
            pageSize: 9,
            direction: "ASC",
            property: "id",
            totalPages: 0,
        }
    },
    methods: {
        flipFormVisible(){
            this.formVisible = !this.formVisible;
        },
        limpar() {
            this.clienteEscolhido = null;
            this.flipFormVisible();
        },
        irPara(pagina) {
            this.pageNumber = pagina;
            this.listarClientes();
        },
        formatarAtivo(valorBool){
            return Logico.toAtivoInativo(valorBool)
        },
        async listarClientes(){
            this.clienteEscolhido = null;
            this.formVisible = false;
      
            const response = await clienteService.listar(this.pageNumber, this.pageSize, this.direction, this.property);

            this.listaClientes = response.content;
            this.totalPages = response.totalPages;
            console.log(this.totalPages);
        },
        alterar(cliente){
            this.clienteEscolhido = cliente;
            this.flipFormVisible();
        },
        async excluir(id){
            try{
                const response = await clienteService.apagar(id);
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
            this.listarClientes();
        }
        
    },
    mounted(){
        this.listarClientes();
    },
};
</script>
