<template>
    <div>
        <p>Lista de Estados</p>
        <button v-if="!formVisible" @click="novoEstado">Novo</button>
       <FormEstado v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
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
        <h2> Paginação</h2>
        <p><input type="text" v-model="pageNumber" placeholder="Número de pagina"></p>
        <p><button @click.prevent="buscarEstados">buscar</button></p>
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
                pageNumber:1

            }
        },
        methods:{
            async buscarEstados(){
                this.formVisible = false;
                
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
