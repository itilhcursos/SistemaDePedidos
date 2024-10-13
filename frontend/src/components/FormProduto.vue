<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Produto</h4>
        <hr />
        <form>
            <div class="mb-3">
                <label class="form-label">Id</label>
                <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id produto" />
            </div>
            <div class="mb-3">
                <label class="form-label">Descrição</label>
                <input class="form-control" type="text" v-model="descricao" placeholder="Descrição" />
            </div>
            <div class="mb-3">
                <label class="form-label">Quantidade em Estoque</label>
                <input class="form-control" type="text" v-model="quantidadeEstoque"
                    placeholder="Quantidade em Estoque" />
            </div>
            <div class="mb-3">
                <label class="form-label">Preço Unitário</label>
                <input class="form-control" type="text" v-model="precoUnidadeAtual" placeholder="Preço Unitário" />
            </div>
            <div class="mb-3">
                <label class="form-label">Ativo</label>
                <select v-model="ativo" class="form-select">
                    <option :value="true">Sim</option>
                    <option :value="false">Não</option>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Url Imagem</label>
                <input class="form-control" type="text" v-model="urlImagem" placeholder="Imagem" />
            </div>
            <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">{{ mensagem }}</div>
            </div>
            <div class="mb-3 d-flex justify-content-end">

                <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarProduto">
                    <i class="bi bi-clipboard2-check"></i>
                    {{ getAcao }}
                </button>

                <button class="btn btn-warning m-2" type="submit" v-on:click.prevent="cancelar">
                    <i class="bi bi-clipboard2-x"></i>
                    Cancelar
                </button>
            </div>
        </form>
    </div>
</template>

<script>
import produtoService from "@/services/produtoService";
import axios from "axios";
export default {
    props: {
        propsProduto: Object,
    },
    data() {
        return {
            id: "",
            descricao: "",
            quantidadeEstoque: "",
            precoUnidadeAtual: "",
            ativo: "",
            urlImagem: "",
            isInvalido: false,
            mensagem: "",
            listaProdutos: [],
        };
    },
    methods: {
        async salvarProduto() {
            if (!this.descricao) {
                this.isInvalido = true;
                this.mensagem = "Descrição do produto não pode ser vazia."
                return;
            }
            this.isInvalido = false;
            let config = {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            }
            try {
                if (!this.id) {
                    const response = await produtoService.criar(this.getDados());
                    this.listaProdutos = response;
                } else {
                    const response = await axios.put(`http://localhost:8080/produto/${this.id}`, this.getDados(), config);
                    this.listaProdutos = response;
                }
                this.$emit("salvar_produto", this.getDados());
                this.limparFormulario();
            } catch (error) {
                this.tratarErro(error);
            }
        },
        cancelar() {
            this.limparFormulario();
            this.$emit("cancelar", true);
        },
        getDados() {
            return {
                id: this.id,
                descricao: this.descricao,
                quantidadeEstoque: this.quantidadeEstoque,
                precoUnidadeAtual: this.precoUnidadeAtual,
                ativo: this.ativo,
                urlImagem: this.urlImagem
            }
        },
        limparFormulario() {
            this.id = "";
            this.descricao = "";
            this.quantidadeEstoque = "";
            this.precoUnidadeAtual = "";
            this.ativo = "";
            this.urlImagem = "";
        },
        tratarErro(error) {
            this.isInvalido = true;
            if (error.response.status === 403) {
                this.mensagem = "Usuário não identificado! Faça o login!!!";
            } else if (error.response.status === 400) {
                this.mensagem = error.response.data.mensagem;
            } else {
                this.mensagem = error.message;
            }
        }
    },
    mounted() {
        if (this.propsProduto) {
            this.id = this.propsProduto.id;
            this.descricao = this.propsProduto.descricao;
            this.quantidadeEstoque = this.propsProduto.quantidadeEstoque;
            this.precoUnidadeAtual = this.propsProduto.precoUnidadeAtual;
            this.ativo = this.propsProduto.ativo;
            this.urlImagem = this.propsProduto.urlImagem;
        }
    },
    computed: {
        getAcao() {
            return this.id === "" ? "Incluir" : "Alterar";
        },
    }
}
</script>
