<template>
    <div>
        <h4>Municipio</h4>
    </div>
</template>
<script>
 import axios from "axios";
    export default {
        props:{
            propsMunicipio: Object,
        },
        data(){
            return{
                id: "",
                nome: "",
                entrega: "",
                estadoId: "",
                estadoNome: "",
                isInvalido: false,
                mensagem: '',
            };
        },
        methods:{
            async salvarEstado() {
                if (this.nome === "") {
                this.isInvalido = true;
                this.mensagem = "Não deve ser preenchido!!";
                return;
                }
                this.isInvalido = false;
                let config = {
                    headers: {
                        'Authorization': 'Bearer ' +localStorage.getItem('token')
                    }
                }
                try{
                    if(this.id === ""){
                        const response = await axios.post("http://localhost:8080/municipio", {
                        id: this.id,
                        nome: this.nome,
                    }, config);
                    this.listaMunicipio = response.data;
                    }else{
                        const response = await axios.put(
                        `http://localhost:8080/municipio/${this.id}`,
                        {
                            id: this.id,
                            nome: this.nome,
                            entrega:  this.entrega,
                            estadoId: this.estadoId,
                            estadoNome: this.estadoNome,
                        }
                        ,config );
                        this.listaEstados = response.data;
                    }
                    this.$emit("salvar_municipio", {
                        id: this.id,
                        nome: this.nome,
                        entrega:  this.entrega,
                        estadoId: this.estadoId,
                        estadoNome: this.estadoNome,
                    });

                    this.id = "";
                    this.nome = "";
                }catch(error){
                    console.log (error);
                    console.log (error.response.status);
                    this.isInvalido = true;
                    if(error.response.status === 403){        
                        this.mensagem = "Usuário não identificado! Faça o login!!!";
                    }else if(error.response.status === 500){ 
                        this.mensagem = error.response.data.message;
                    }else{
                        this.mensagem = error.message;
                    }
                }
            }
        }

    }
</script>