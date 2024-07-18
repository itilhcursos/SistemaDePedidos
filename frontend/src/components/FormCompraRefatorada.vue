<template>
    <div class="container">
        <h1>Lista de Compras</h1>
        <p>Quantidade de Produtos: {{ titulo }} </p>
        <hr/>
        <p><input type="text" v-model="produto" placeholder="Nome do produto" class="form-control" /></p>
        <p><input type="text" v-model="quantidade" placeholder="Quantidade do produto" class="form-control" /></p>
        <div v-if="isInvalido" class="alert alert-warning" role="alert">
            Nome do produto e quantidade devem ser preenchidos!
        </div>
        <!-- <div v-if="isInvalido" class="alert alert-warning d-flex align-items-center" role="alert">
            <svg class="bi flex-shrink-2 me-2" role="img" aria-label="Warning:"><use xlink:href="#exclamation-triangle-fill"/></svg>
            <div>
            Nome do produto e quantidade devem ser preenchidos!!
            </div>
        </div> -->
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

