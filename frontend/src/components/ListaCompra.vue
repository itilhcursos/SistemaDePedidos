<template>
    <div class="container">
        <h1 class="titulo-listaCompra" >Lista de compras</h1>
        <p><input type="text" v-model="produto" placeholder="Nome do produto" class="form-control" /></p>
        <p><input type="text" v-model="quantidade" placeholder="Quantidade do produto" class="form-control" /></p>
        <div v-if="isInvalido" class="alert alert-danger" role="alert">
            Nome do produto e quantidade devem ser preenchidos!!
        </div>
        <button type="submit" v-on:click.prevent="incluirProduto" class="btn btn-primary">Incluir</button>
        <hr/>
        <div class="list-group">
            <div class="list-group-item" v-for="(item, index) in lista" v-bind:key="index">
                <span><strong>{{item.produto}}</strong></span>
                <p>{{item.quantidade}}</p>
                <div>
                    <a class="botao-listaExcluir" href="#" @click.prevent="excluirProduto(index)">Excluir</a>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>

.botao-listaExcluir {
    font-weight: 500;
}

.alert {
    color: red;
    font-weight: bold;
    margin-top: 10px;
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

.titulo-listaCompra{
  color: #3F8DBF;
  font-weight: 1000;
  font-size: 2.4rem;
  position: relative;           
  top: -10px;
  margin-bottom: 5px;
  margin-top: 30px;
  text-align: center;
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

.btn:hover{
background-color: #e2f5ff;
box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.list-group-item{
    background-color: #fff;
    border-color: #ddd;
    color: #7a7a7a;
    font-weight: bold;
    
}

.list-group-item:nth-child(even) {
    background-color: #f3f2f2; 
}

.list-group-item:hover{
    background-color: #ddd;
}
</style>

<script> 
   export default{
        data(){
            return {
                produto:'',
                quantidade:'',
                lista: [],
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

                this.lista.push({
                        produto:this.produto,
                        quantidade:this.quantidade
                    });
                this.produto = '';
                this.quantidade ='';
            
            },
            excluirProduto(posicao){
                console.log(posicao);
                this.lista.splice(posicao,1);
            }
        }
   }
</script>

