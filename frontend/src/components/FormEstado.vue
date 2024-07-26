<template>
    <div>
        <h1>Cadastro de estado</h1>
        <hr/>
        <p><input type="text" v-model="id" :disabled="false" placeholder="ID" /></p>
        <p><input type="text" v-model="nome" placeholder="Nome" /></p>
        <div v-if="isInvalido"  role="alert">
            Nome deve ser preenchido!!
        </div>
        <button type="submit" v-on:click.prevent="salvarEstado" >{{ getBotao }}</button>
        <button type="submit" v-on:click.prevent="cancelar" >Cancelar</button>
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

                if(this.id === ""){
                    const response = await axios.post("http://localhost:8080/estado", {
                        id:this.id,
                        nome:this.nome
                    });
                    this.listaEstados = response.data;
                }else{
                    const response = await axios.put(`http://localhost:8080/estado/${this.id}`, {
                        id:this.id,
                        nome:this.nome
                    });
                    this.listaEstados = response.data;
                }

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
        },
        computed:{
            getBotao(){
                return this.id === ""? "Incluir":"Alterar";
            }
        }
   }
</script>

