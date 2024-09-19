<template>
    <div class="constainer">
        <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Municipio</h4>
    </div>
    <hr>
    <form>
        <div>
            <label for="form-label">ID</label>
            <input 
                class="form-control"
                type="text"
                v-model="id"
                :disabled="true"
                placeholder="Id municipio"

            />
        </div>
        <div class="mb-3">
            <label class="form-label">Nome</label>
            <input
                class="form-control"
                type="text"
                v-model="nome"
                placeholder="Nome"
            />
        </div>
        <div class="mb-3">
            <label class="form-label">Entrega</label>
            <input
                class="form-control"
                type="text"
                v-model="entrega"
                placeholder="Entrega"
            />
        </div>
        <div class="mb-3">
            <label class="form-label">Estado Id</label>
            <input
                class="form-control"
                type="text"
                v-model="estadoId"
                placeholder="Estado Id"
            />
        </div>
        <div class="mb-3">
            <label class="form-label">Estado Nome</label>
            <input
                class="form-control"
                type="text"
                v-model="estadoNome"
                placeholder="Estado Nome"
            />
        </div>
        <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i>
            <div class="p-2">{{ mensagem }}</div>
        </div>
        <div class="mb-3 d-flex justify-content-end">
            <button
                class="btn btn-primary m-2"
                type="submit"
                v-on:click.prevent="salvarMunicipio"
            >
                <i class="bi bi-clipboard2-check"></i>
                {{ getAcao }}
            </button>
            <button
                class="btn btn-warning m-2"
                type="submit"
                v-on:click.prevent="cancelar"
            >
                <i class="bi bi-clipboard2-x"></i>
                Cancelar
            </button>
      </div>
    </form>

</template>
<script>
    import municipioService from '@/services/municipioService';
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
            getDados(){
                return{
                    id: this.id,
                    nome: this.nome,
                    entrega: this.entrega,
                    estadoId: this.estadoId,
                    estadoNome: this.estadoNome,
                }
            },
            async salvarMunicipio() {
                if (this.nome === "") {
                this.isInvalido = true;
                this.mensagem = "O Nome deve ser preenchido!!";
                return;
                }
                this.isInvalido = false;
                try{
                    if(this.id === ""){
                        const response = await municipioService.criar(
                        this.getDados());
                        this.listaMunicipio = response.data;
                    }else{
                        const response = await municipioService.atualizar(
                        this.getDados());
                        this.listaMunicipio = response.data;
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
                    this.entrega = "";
                    this.estadoId = "";
                    this.estadoNome = "";
                }catch(error){
                    console.log (error);
                    console.log (error.response.status);
                    this.isInvalido = true;
                    if(error.response.status === 403){        
                        this.mensagem = "Usuário não identificado! Faça o login!!!";
                    }else if(error.response.status === 500){ 
                        this.mensagem = error.response.data.mensagem;
                    }else{
                        this.mensagem = error.message;
                    }
                }
            },
            cancelar(){
                this.id = "";
                this.nome = "";
                this.entrega = "";
                this.estadoId = "";
                this.estadoNome = "";
                this.$emit("cancelar", true);
            },
        },
        mounted(){
            if(this.propsMunicipio){
                this.id = this.propsMunicipio.id;
                this.nome =this.propsMunicipio.nome;
                this.entrega = this.propsMunicipio.entrega;
                this.estadoId = this.propsMunicipio.estadoId;
                this.estadoNome = this.propsMunicipio.estadoNome;
            }
        },
        computed:{
            getAcao(){
                return this.id === "" ? "Incluir": "Alterar";
            },
        },

    };

</script>