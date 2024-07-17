<template>
    <div>
        <h1 class="green">Lista de Estados</h1>
        <button class="btn btn-primary" v-if="!formVisible" @click="novoEstado">Novo</button>
       <FormEstado v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
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
</template>

<style scoped>
    .btn.btn-secondary.btn-sm{
        margin-left: 5px;
    }

    .btn.btn-primary {
        margin-bottom: 10px;
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
                formVisible:false
            }
        },
        methods:{
            async buscarEstados(){
                this.formVisible = false;
                //buscar a lista de estados no servidor
                // http://localhost:8080/estados 
                const response = await axios.get("http://localhost:8080/estados");
                console.log(response.data);
                this.listaEstados = response.data;
            },
            limpar(){
                this.formVisible = !this.formVisible;
            }, 
            novoEstado(){
                this.formVisible = !this.formVisible;
            },
            alterarEstado(estado){
                console.log(estado);
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
