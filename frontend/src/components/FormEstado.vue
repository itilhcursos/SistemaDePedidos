<template>
    <div class="form-floating mb-3">
        <h3>Cadastro de Estado</h3>
        <hr/>
        <p><input type="text" class="form-control" v-model="id" :disabled="true" placeholder="ID Estado" /></p>
        <p><input type="text" class="form-control" v-model="nome" placeholder="Nome"/></p>
        <div class="alert alert-warning" v-if="isInvalido"  role="alert">
            Nome do estado deve ser preenchido!
        </div>
        <button type="submit" class="btn btn-success" v-on:click.prevent="salvarEstado" >Incluir</button>
        <button type="submit" class="btn btn-success" v-on:click.prevent="cancelar" >Cancelar</button>
        <hr/>
    </div>
</template>

<style scoped>
    .btn.btn-success {
        margin-left: 3px;
        color: rgb(255, 255, 255);
    }

    input {
        border-radius: 8px;
    }
</style>

<script> 
import axios from "axios";
   export default{
        props:{
            propsEstado: Object
        },
        data(){
            return {
                id:'',
                nome:'',
                isInvalido:false,
            }
        },
        methods:{
            async salvarEstado(){
                if(this.nome ==='' ){
                    this.isInvalido = true;
                    return;
                }
                this.isInvalido = false;

                const response = await axios.post("http://localhost:8080/estado", {
                         id:this.id,
                         nome:this.nome
                     });
                console.log(response.data);
                this.listaEstados = response.data;

                 this.$emit('salvar_estado',{
                         id:this.id,
                         nome:this.nome
                     });

                this.id = '';
                this.nome ='';
            
            },
            cancelar(){
                this.id = '';
                this.nome ='';
                this.$emit('cancelar',true);
            
            },
        },
        mounted() {
            if(this.propsEstado){
                this.id =this.propsEstado.id;
                this.nome =this.propsEstado.nome;
            }
        }
   }
</script>

