<template>
    <div>
        <h4 class="p-1 mb-1 bg-success text-white">{{getAcao}} Produto</h4>
        <hr>
        <form>
            <div class="mb-3">
                <label class="form-label">Id</label>
                <input 
                class="form-control"
                type="text"
                v-model="id"
                :disabled="true"
                placeholder="ID Produto"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Descrição</label>
                <input 
                class="form-control"
                type="text"
                v-model="descricao"
                placeholder="Descrição do Produto"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Quantidade</label>
                <input 
                class="form-control"
                type="text"
                v-model="quantidadeEstoque"
                placeholder="Quantidade em Estoque"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Preço</label>
                <input 
                class="form-control"
                type="text"
                v-model="precoUnidadeAtual"
                placeholder="Preço da Unidade Aual"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Entrega</label>
                <input 
                class="form-control"
                type="text"
                v-model="ativo"
                placeholder="Entrega"
                />
            </div>
            <div  v-if="isInvalido" class="alert alert-danger d-flex align" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">Descrição deve ser prenchida!!</div>
            </div>
            <div class=" mb-3 d-flex justify-content-end">
                <button
                    class=" btn btn-primary m-2"
                    type="submit"
                    v-on:click.prevent="salvarProduto"
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
                    cancelar
                </button>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    props: {
        propsProduto: Object,
    },
    data(){
        return{
            id:"",
            descricao:"",
            quantidadeEstoque:"",
            precoUnidadeAtual:"",
            ativo:"",
            isInvalido:false,

        };
    },
    methods:{
        async salvarProduto(){
            if (this.descricao === ""){
                this.isInvalido = true;
                return;
            }
            this.isInvalido = false;

            if(this.id === ""){
                //incluir pelo POST da API
                const response = await axios.post("http://localhost:8080/produto", {
                    id: this.id,
                    descricao: this.descricao,
                    quantidadeEstoque: this.quantidadeEstoque,
                    precoUnidadeAtual: this.precoUnidadeAtual,
                    ativo: this.ativo,
                });
                this.listaProdutos = response.data;
            }else{
                 // alterar pelo PUT da API
                const response = await axios.put(
                    `http://localhost:8080/produto/${this.id}`, 
                    {
                        id: this.id,
                        descicao :this.descricao,
                        quantidadeEstoque :this.quantidadeEstoque,
                        precoUnidadeAtual: this.precoUnidadeAtual,
                        ativo: this.ativo,
                    }
                 );
                this.listaProdutos = response.data;
            }
            this.$emit("salvar_produto",{
                id: this.id,
                descricao: this.descricao,
                quantidadeEstoque: this.quantidadeEstoque,
                precoUnidadeAtual: this.precoUnidadeAtual,
                ativo: this.ativo,
            });
            this.id="";
            this.descricao ="";
            this.quantidadeEstoque = "";
            this.precoUnidadeAtual ="";
            this.ativo ="";
        },
        cancelar(){
            this.id ="";
            this.descricao ="";
            this.quantidadeEstoque ="";
            this.precoUnidadeAtual ="";
            this.ativo ="";
            this.$emit("cancelar", true);
        },
    },
    mounted(){
        if(this.propsProduto){
            this.id = this.propsProduto.id;
            this.descricao = this.propsProduto.descricao;
            this.quantidadeEstoque = this.propsProduto.quantidadeEstoque;
            this.precoUnidadeAtual = this.propsProduto.precoUnidadeAtual;
            this.ativo = this.propsProduto.ativo? "Verdadeiro": "Falso";
        }
    },
    computed:{
        getAcao(){
            return this.id ==="" ? "incluir": "Alterar";
        },
    },
};
</script>
