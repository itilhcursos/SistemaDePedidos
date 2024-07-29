<template>
    <div>
        <p>Lista de Estados</p>
        <button v-if="!formVisible" @click="novoEstado">Novo</button>
       
        <FormEstado v-if="formVisible"
        :propsEstado="estadoEscolhido"
         @cancelar="limpar" 
         @salvar_estado="buscarEstados"/>
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Ações</th>
            </tr>
            <tr v-for="estado in listaEstados" :key="estado.id">
                <td>
                    {{ estado.id }}
                </td>
                <td>
                    {{ estado.nome }}
                </td>
                <td>
                    <button @click="alterarEstado(estado)">Alterar</button>
                    <button @click="excluirEstado(estado.id)">Excluir</button>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <hr>
        <h2>Paginação</h2>
        <p><input type="text" v-model="pageNumber" placeholder="Número da pagina"></p>
        <p><button @click.prevent="buscarEstados">Buscar</button></p>
    </div>
</template>


<script> 
import axios from "axios";
import FormEstado from './FormEstado.vue';
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
                url: import.meta.env.VITE_APP_URL_API,
                pageNumber:1
            }
        },
        methods:{
            async buscarEstados(){
                this.estadoEscolhido = null;
                this.formVisible = false;
                //buscar a lista de estados no servidor
                // http://localhost:8080/estados 
                const response = await axios.get(`http://localhost:8080/estados?pageNumber=${this.pageNumber}`);
                console.log(response.data);
                this.listaEstados = response.data.content;
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
