<template>
    <div>
        <div>
        <h1 class="green" v-if="!formVisible">Lista de Estados</h1>
        <a v-if="!formVisible">Clique no botão para criar um novo estado ---></a>
        <button class="btn btn-light btn-sm" v-if="!formVisible" @click="novoEstado">Criar Estado</button>
        <hr v-if="!formVisible">
        </div>
        <FormEstado v-if="formVisible"
            :propsEstado="estadoEscolhido"
            @cancelar="limpar" 
            @salvar_estado="buscarEstados"/>
        <div class="table-overflow">
        <table class="table table-dark table-striped table-bordered table-sm table align-middle">
            <thead class="table-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="estado in listaEstados" :key="estado.id">
                    <td>
                        {{ estado.id }}
                    </td>
                    <td>
                        {{ estado.nome }}
                    </td>
                    <td>
                        <button class="btn btn-secondary btn-sm" @click="alterarEstado(estado)">Alterar</button>
                        <button class="btn btn-secondary btn-sm" @click="excluirEstado(estado.id)">Excluir</button>
                    </td>
                </tr>
            </tbody>
        </table>
        </div>
    </div>
</template>

<style scoped>
    .btn.btn-secondary.btn-sm{
        margin-left: 5px;
    }

    .btn.btn-light.btn-sm {
        padding: 6px;
        margin-bottom: 5px;
        margin-left: 190px;
    }

    .table-overflow {
        max-height: 354px;
        overflow-y:auto;
    }

    hr {
        margin: 2px;
        padding: 2px;
    }
</style>

<script> 
import FormEstado from './FormEstado.vue';
import axios from "axios";
    export default {
        components:{
            FormEstado
        },
        data(){
            return{
                listaEstados:[],
                estadoEscolhido:null,
                formVisible:false,
                mode: import.meta.env.MODE,
                url: import.meta.env.VITE_APP_URL_API
            }
        },
        methods:{
            async buscarEstados(){
                this.formVisible = false;
                this.estadoEscolhido = null;
                //buscar a lista de estados no servidor
                // http://localhost:8080/estados 
                const response = await axios.get("http://localhost:8080/estados");
                console.log(response.data);
                this.listaEstados = response.data;
            },
            limpar(){
                this.estadoEscolhido = null;
                this.formVisible = !this.formVisible;
            }, 
            novoEstado(){
                this.formVisible = !this.formVisible;
            },
            alterarEstado(estado){
                this.estadoEscolhido = estado;
                this.formVisible = true;
            },
            async excluirEstado(id){
                const response = await axios.delete(`http://localhost:8080/estado/${id}`);
                console.log(response.data);
                this.buscarEstados();
            }
        } ,  
        mounted() {
            this.buscarEstados();
        }     
    }
</script>
