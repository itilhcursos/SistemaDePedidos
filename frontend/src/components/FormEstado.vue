<template>
    <div>
        <h3 style="color: hsla(160, 100%, 37%, 1)">Cadastro de Estado</h3>
        <hr/>
        <p><input type="text" v-model="id" :disabled="true" placeholder="Id Estado" /></p>
        <p><input type="text" v-model="nome" placeholder="Nome do Estado..." /></p>
        
        <div v-if="isInvalido" class="alert alert-warning" role="alert">
            Nome do Estado deve ser preenchido!
        </div>
        <!-- <button type="submit" v-on:click.prevent="salvarEstado" >Incluir</button> -->
        <button type="submit"  class="btn btn-success btn-sm" v-on:click.prevent="salvarEstado">Incluir</button>

        <!-- <button type="submit" v-on:click.prevent="cancelar" >Cancelar</button> -->
        <button type="submit" class="btn btn-secondary btn-sm" v-on:click.prevent="cancelar">Cancelar</button>
        <hr/>
    </div>
</template>

<script> 
import axios from "axios";
   export default{
        props:{
            estado: Object
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
        computed() {
            if(this.estado){
                this.id =this.estado.id;
                this.nome =this.estado.nome;
            }
        }
   }
</script>

