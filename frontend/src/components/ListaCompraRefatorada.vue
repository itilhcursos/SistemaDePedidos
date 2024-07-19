<template>
    <div class="container">
        <FormCompraRefatorada class="titulo-listaCompraRe" :titulo="lista.length" @novo-produto="incluirProduto"/>
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

.titulo-listaCompraRe{
  color: #3F8DBF;
  font-weight: 1000;
  font-size: 2.4rem;
  position: relative;           
  top: -10px;
  margin-bottom: 5px;
  margin-top: 30px;
  text-align: center;
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
import FormCompraRefatorada from './FormCompraRefatorada.vue';
   export default{
        components:{
            FormCompraRefatorada
        },
        data(){
            return {
                lista: []
            }
        },
        methods:{
            incluirProduto(valor){
                this.lista.push(valor);        
            },
            excluirProduto(posicao){
                console.log(posicao);
                this.lista.splice(posicao,1);
            }
        }
   }
</script>

