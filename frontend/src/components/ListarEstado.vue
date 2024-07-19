<template>
    <div>
        <p>Lista de Estados</p>
        <button v-if="!formVisible" @click="novoEstado">Cadastrar Novo Estado</button>
        <p></p>
       <FormEstado v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
        <!-- <table style="width:100%"> -->
        <table style="border: 2px ridge rgba(255, 255, 255, 1.0)">
            <tr style="border-bottom: 2px ridge rgba(255,255,255,1); text-align: center">
                <th style="border-right: 2px solid">ID</th>
                <th style="border-right: 2px solid">Nome</th>
                <th>Ações</th>
            </tr>
            <tr v-for="estado in listaEstados" :key="estado.id">
                <td style="padding-inline: 7px; border-right: 2px ridge rgba(255,255,255,0.5); border-bottom: 1px solid rgba(255,255,255,0.5); text-align: center">
                    {{ estado.id }}
                </td>
                
                <td style="padding-inline: 10px; border-right: 2px ridge rgba(255,255,255,0.5); border-bottom: 1px solid rgba(255,255,255,0.5)">
                    {{ estado.nome }}
                </td>
                <td style="padding-inline: 4px; border-bottom: 1px solid rgba(255,255,255,0.5)">
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
