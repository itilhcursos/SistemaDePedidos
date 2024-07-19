<template>
    <div class="cadastro">
        <h1>Cadastro</h1>
        
        <p><input class="id-estado" type="text" v-model="id" :disabled="true" placeholder="Id Estado" /></p>
        <p><input class="nome-estado" type="text" v-model="nome" placeholder="Nome" /></p>
        <div v-if="isInvalido"  role="alert">
            Nome deve ser preenchido!!
        </div>
        <button class="botão-incluir" type="submit" v-on:click.prevent="salvarEstado" >Incluir</button>
        <button class="botão-cancelar" type="submit" v-on:click.prevent="cancelar" >Cancelar</button>
        
    </div>
</template>

<style scoped>
.botão-incluir {
    background: #f0f0f0;
    border-radius: 4px;
    color: #7a7a7a;
    border-color: #7a7a7a;
    font-size: 15px;
    font-weight: 500;
    width: 77.3px;
    margin-right: 10px;
    margin-left: 10px;
}

.botão-incluir:hover{
background-color: #e2f5ff;
box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.botão-cancelar {
    background: #f0f0f0;
    border-radius: 4px;
    color: #7a7a7a;
    border-color: #7a7a7a;
    font-size: 15px;
    font-weight: 500;
    width: 77.3px;
}

.botão-cancelar:hover{
background-color: #e2f5ff;
box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}


.id-estado {
    border-color: #dddddd;
    font-size: 15px;
    font-weight: 400;
    border-radius: 4px;
    color: #7a7a7a;
    margin-top: 10px;
}

.id-estado::placeholder{
    color: #979797;
}

.nome-estado{
    border-color: #dddddd;
    font-size: 15px;
    font-weight: 400;
    border-radius: 4px;
    color: #7a7a7a;
}
.nome-estado::placeholder{
    color: #979797;
}

.cadastro {
    margin-left: 135px;
    margin-bottom: 40px;
}

h1{
    margin-left: 100px;
    margin-left: 10px;
    font-weight: 700;
}


</style>

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

