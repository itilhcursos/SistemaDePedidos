<template>
    <div>
        <p>Lista de Estados</p>
        <button v-if="!formVisible" @click="novoEstado">Novo</button>
        <FormEstado :estado="estadoAtual" v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
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
                estadoAtual:null
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
                this.estadoAtual = null;
            }, 
            novoEstado(){
                this.formVisible = !this.formVisible;
            },
            alterarEstado(estado){
                this.estadoAtual = estado;
                this.formVisible = !this.formVisible;
                console.log(estado, this.estadoAtual);
            },
            async excluirEstado(id){
                const response = await axios.delete(`http://localhost:8080/estado/${id}`);
                console.log(response.data);
                this.buscarEstados();
            }
        } ,  
        mounted() {
            this.buscarEstados();
            console.log(import.meta.env.VITE_APP_TITLE);
            console.log(import.meta.env.MODE);
            console.log(import.meta.env.BASE_URL);
            console.log(import.meta.env.VITE_APP_URL_BACKEND);
        }     
    }
</script>
