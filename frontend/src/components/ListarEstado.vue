<template>
    <div>
        <p>Lista de Estados</p>
        <button v-if="!formVisible" @click="novoEstado" class="btn btn-primary">Novo</button>
        <p></p>
       <FormEstado v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
        <table class="tb_estado">
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

<style scoped>
  table.tb_estado {
    border-collapse: collapse; /* CSS2 */
    background: #FFFFF0;
}
 
table.tb_estado td {
    border: 1px solid black;
}
 
table.tb_estado th {
    border: 1px solid black;
    background: #F0FFF0;
}
</style>
