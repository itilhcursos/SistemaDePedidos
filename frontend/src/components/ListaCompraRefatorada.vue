<template>
    <div class="container">
        <FormCompraRefatorada :titulo="lista.length" @novo-produto="incluirProduto"/>
        <div class="list-group">
            <div class="list-group-item" v-for="(item, index) in lista" v-bind:key="index">
                <span><strong>{{item.produto}}</strong></span>
                <p>{{item.quantidade}}</p>
                <div>
                    <a href="#" @click.prevent="excluirProduto(index)">Excluir</a>
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

