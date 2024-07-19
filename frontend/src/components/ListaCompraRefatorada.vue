<template>
    <div class="container">
        <FormCompraRefatorada :titulo="lista.length" @novo-produto="incluirProduto"/>
        <div class="list-group">
            <div class="list-group-item" v-for="(item, index) in lista" v-bind:key="index">
                <p><strong>Produto: </strong>{{item.produto}}</p>
                <p><strong>Quantidade: </strong>{{ item.quantidade }}</p>
                <div>
                    <a type="button" class="btn btn-danger btn-sm" href="#" @click.prevent="excluirProduto(index)">Excluir</a>
                </div>
            </div>
        </div>
    </div>
</template>

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

<style>
.list-group-item + .list-group-item {
    border-top-width: 5px;
}

.list-group-item {
    width: 560px;
    margin: 10px;
}

.list-group {
    max-width: 560px;
}

</style>