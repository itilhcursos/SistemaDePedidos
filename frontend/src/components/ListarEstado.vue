<template>
    <div>
        <h1>{{ mode }}</h1>
        <h2>{{ url }}</h2>
        <p>Lista de Estados</p>
        <button v-if="!formVisible" @click="novoEstado">Cadastrar Novo Estado</button>
        <p></p>
       <FormEstado v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
        <!-- <table style="width:100%"> -->
        <table style="background-color: rgb(50, 20, 40); border: 2px ridge rgba(255, 255, 255, 1.0)">
            <tr style="border-bottom: 2px ridge rgba(255,255,255,1); text-align: center">
                <th style="border-right: 2px ridge">ID</th>
                <th style="border-right: 2px ridge">Nome</th>
                <th>Ações</th>
            </tr>
            <tr v-for="estado in listaEstados" :key="estado.id">
                <td style="padding-inline: 7px; border-right: 2px ridge rgba(255,255,255,0.5); border-bottom: 2px ridge rgba(255,255,255,0.5); text-align: center">
                    {{ estado.id }}
                </td>
                
                
                <td style="padding-inline: 10px; border-right: 2px ridge rgba(255,255,255,0.5); border-bottom: 2px ridge rgba(255,255,255,0.5)">
                    {{ estado.nome }}
                </td>
                <td style="padding-inline: 4px; border-bottom: 2px ridge rgba(255,255,255,0.5)">
                    <button @click="alterarEstado(estado.id)">Alterar</button>
                    <button @click="excluirEstado(estado.id)">Excluir</button>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <hr>
        <h2>Paginação</h2>
        <p><input type="text" v-model="pageNumber" placeholder="Numero da pagina"></p>
        <p><button @click.prevent="buscarEstados">Buscar</button></p>  
    </div>
</template>


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
                formVisible:false,
                mode:import.meta.env.MODE,
                url: import.meta.env.VITE_APP_URL_API,
                pageNumber:1
            }
        },
        methods:{
            async buscarEstados(){
                this.formVisible = false;
                //buscar a lista de estados no servidor
                // http://localhost:8080/estados 
                const response = await axios.get(`http://localhost:8080/estados?pageNumber=${this.pageNumber}`);
                console.log(response.data);
                this.listaEstados = response.data.content;
            },
            limpar(){
                this.formVisible = !this.formVisible;
            }, 
            novoEstado(){
                this.formVisible = !this.formVisible;
            },
            alterarEstado(id){
                console.log(id);
                
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
