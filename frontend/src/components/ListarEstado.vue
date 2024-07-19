<template>
    <div class="estado-list">
        <h1>Lista de Estados</h1>
        <!-- <button v-if="!formVisible" @click="novoEstado">Novo</button> -->
        <button type="button" class="btn btn-primary btn-sm" v-if="!formVisible" @click="novoEstado">Novo</button>
        <hr/>
        <FormEstado v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
        
      <div class="table-responsive">
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th></th>
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
                    <!-- <button @click="alterarEstado(estado)">Alterar</button> -->
                    <button type="button" class="btn btn-warning btn-sm" @click="alterarEstado(estado)">Alterar</button>
                    <!-- <button @click="excluirEstado(estado.id)">Excluir</button> -->
                    <button type="button" class="btn btn-danger btn-sm" @click="excluirEstado(estado.id)">Excluir</button>
                </td>
            </tr>
            </tbody>
        </table>
      </div>
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
.estado-list {
  padding: 100px;
}

.table-responsive {
  overflow-x: auto;
}

.text-center {  
  text-align: center;
}
</style>
