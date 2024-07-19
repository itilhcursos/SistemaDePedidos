<template>
    <div class="container">
        <h1>Lista de Compras</h1>
        <p style="color: hsla(160, 100%, 37%, 1)"><b>Quantidade de Produtos:</b> {{ titulo }}</p>
        <hr/>
        
        <div class="mb-3">
            <input type="text" v-model="produto" placeholder="Nome do produto" class="form-control" />
        </div>
        <div class="mb-3">
            <div class="row">
                <div class="col-3">
                    <input type="text" v-model="quantidade" placeholder="Quantidade" class="form-control" />
                </div>
            </div>
        </div>
        
        <div v-if="isInvalido" class="alert alert-warning" role="alert">
            Nome do produto e quantidade devem ser preenchidos!
        </div>
        <button type="submit" class="btn btn-success btn-sm" v-on:click.prevent="incluirProduto">Incluir</button>
        <hr/>
    </div>
</template>



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
                if(this.produto === '' || this.quantidade=== '') {
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

<style>
input[v-model="quantidade"] {
    max-width: 100%;
}
</style>


