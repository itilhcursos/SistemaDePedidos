<template>
    <div>
        <h1>{{ mode }}</h1>
        <p class="titulo-lista">Lista de Estados</p>
        <button class="botão-novoEstado" v-if="!formVisible" @click="novoEstado">NOVO</button>
       <FormEstado v-if="formVisible" @cancelar="limpar" @salvar_estado="buscarEstados"/>
        <table class="lista-estados">
            <thead>
            <tr>
                <th>ID</th>
                <th>NOME</th>
                <th>AÇÕES</th>
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
                    <button class="botão-alterar" @click="alterarEstado(estado)">ALTERAR</button>
                    <button class="botão-excluir" @click="excluirEstado(estado.id)">EXCLUIR</button>
                </td>
             </tr>
            </tbody>
        </table>

    </div>
</template>

<style scoped>
.lista-estados {
    margin: 25px 0;
    font-size: 0.9em;
    min-width: 400px;
    border-collapse: collapse; 
    border-radius: 7px 7px 0 0;
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    margin: auto;
    margin-bottom: 50px;
}

.lista-estados thead tr {
    background-color: #3F8DBF;
    color: #fff; 
    text-align: left;
    font-weight: bold;
}

.lista-estados th,
.lista-estados td {
    padding: 12px 15px;
    border: 1px solid #dddddd; 
    font-weight: 600;
}

.lista-estados tbody tr {
    border-bottom: 1px solid #dddddd;
    font-size: 15px;
}

.lista-estados tbody tr:nth-child(even) {
    background-color: #f2f2f2; 
}

.lista-estados tbody tr:hover {
    background-color: #ddd; 
}

.titulo-lista {
  color: #3F8DBF;
  font-weight: 1000;
  font-size: 2.4rem;
  position: relative;
  top: -10px;
  margin-bottom: 5px;
  margin-top: 30px;
  text-align: center;

}
.botão-novoEstado{
    margin-left: 12rem;
    margin-bottom: 15px;
    background: #f0f0f0;
    border-radius: 4px;
    color: #7a7a7a;
    border-color: #7a7a7a;
    font-size: 15px;
    font-weight: 500;
    width: 77.3px;
}
.botão-novoEstado:hover{
background-color: #e2f5ff;
box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}
.botão-alterar {
    margin-right: 10px;
    margin-left: 10px;
    border-radius: 4px;
    color: #7a7a7a;
    border-color: #7a7a7a;
    font-size: 15px;
    font-weight: 500;
}
.botão-alterar:hover {
    background-color: #e2f5ff;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.botão-excluir {
    margin-right: 10px;
    margin-left: 10px;
    border-radius: 4px;
    color: #7a7a7a;
    border-color: #7a7a7a;
    font-size: 15px;
    font-weight: 500;
}
.botão-excluir:hover {
    background-color: #e2f5ff;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    
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
                formVisible:false,
                mode: import.meta.env.MODE,
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


