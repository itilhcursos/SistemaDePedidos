<template>
    <div class="container">
        <h1>Lista de compras - {{ titulo }}</h1>
        <p><input type="text" v-model="produto" placeholder="Nome do produto" class="form-control" /></p>
        <p><input type="text" v-model="quantidade" placeholder="Quantidade do produto" class="form-control" /></p>
        <div v-if="isInvalido" class="alert alert-danger" role="alert">
            Nome do produto e quantidade devem ser preenchidos!!
        </div>
        <button type="submit" v-on:click.prevent="incluirProduto" class="btn btn-primary">Incluir</button>
        <hr/>
    </div>
</template>

<style scoped>
.alert {
    color: red;
    font-weight: bold;
    margin-top: 10px;
} 
.container {
    width: 446px;
}
.form-control {
    border-color: #dddddd;
    font-size: 15px;
    font-weight: 400;
    border-radius: 4px;
    color: #7a7a7a;
    margin-top: 10px;
    border: 2px solid #7a7a7a;
}

.form-control::placeholder{
    color: #979797;
}

.btn{
    background: #f0f0f0;                    
    border-radius: 4px;
    color: #7a7a7a;
    border-color: #7a7a7a;
    border-width: 2px;
    border: 2px solid #7a7a7a;
    font-size: 18px;
    font-weight: 500;
    width: 77.3px;
    margin-right: 10px;
    margin-left: 180px;
    height: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>

<script> 
   export default{
        props:{
            titulo:String
        },
        data(){
            return {
                produto:'',
                quantidade:'',
                isInvalido:false,
            }
        },
        methods:{
            incluirProduto(){
                console.log (this.produto , this.quantidade);
                if(this.produto ==='' || this.quantidade==='' ){
                    this.isInvalido = true;
                    return;
                }
                this.isInvalido = false;

                 this.$emit('novo-produto',{
                         produto:this.produto,
                         quantidade:this.quantidade
                     });

                this.produto = '';
                this.quantidade ='';
            
            },
        }
   }
</script>

