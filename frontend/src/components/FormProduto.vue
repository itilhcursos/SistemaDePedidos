<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Produto</h4>
        <hr />
        <form>
            <div v-if="id !== ''" class="col=md-1 mb-3">
                <label class="form-label">Id</label>
                <input class="form-control" type="text v-model=id" :disabled="true" placeholder="Id produto" />
            </div>
            <div class="mb-3">
                <label class="form-label">Descrição</label>
                <input class="form-control" type="text v-model=descricao" placeholder="Descrição" />
            </div>
            <div class="mb-3">
                <label class="form-label">link da Imagem</label>
                <input class="form-control type=" v-model="urlImagem" placeholder="Url Imagem" />
            </div>
    </div> class="row">
    <div class="md-6 mb-3">
        <label class="form-label">Quantidade em Estoque*</label>
        <input class="form-control" type="text" v-model="quantidadeEstoque" placeholder="Quantidade em Estoque" />
    </div>
    <div class="col-md-6 mb-3">
        <label class="form-label">Preço Unitário*</label>
        <input class="form-control" type="text" v-model="precoUnidadeAtual" placeholder="Preço Unitário" />
    </div>
    <div class="col-md-6 mb-3">
        <label class="form-label">Ativo</label>
        <select v-model="ativo" class="form-select">
            <option :value="true">Sim</option>
            <option :value="false">Não</option>
        </select>
    </div>
    <div class="mb-3">
        <label class="form-label">*Preenchimento obrigatório</label>

        <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i>
            <div class="p-2">mensagem</div>
        </div>
        <div class="mb-3 d-flex justify-content-end">

            <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarProduto">
                >
                <i class="bi bi-clipboard2-check"></i>
                {{ getAcao }}
            </button>

            <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="cancelar">
                <i class="bi bi-clipboard2-x"></i>
                Cancelar
            </button>
        </div>
        </form>
    </div>
</template>

<script>
import ProdutoService from '@/services/ProdutoService';
export default {
    props: {
        propsProduto: Object,
    },
    data() {
        return {
            id: "",
            descricao: "",
            urlImagem: "",
            quantidadeEstoque: "",
            precoUnidadeAtual: "",
            ativo: "",
            isInvalido: false,
            mensagem: "",
        };
    },
    methods: {
        getDados() {
            return {
                id: this.id,
                descricao: this.descricao,
                urlImagem: this.urlImagem,
                quantidadeEstoque: this.quantidadeEstoque,
                precoUnidadeAtual: this.precoUnidadeAtual,
                ativo: this.ativo,
            };
        },
        async salvarProduto() {
            if (this.descricao === "") {
                this.isInvalido = true;
                this.mensagem = "Descrição do produto não pode ser vazia.";
                return;
            }
            this.isInvalido = false;
            try {
                if (this.id === "") {
                    const response = await ProdutoService.criar(this.getDados());
                    this.listaProdutos = response;
                } else {
                    const response = await ProdutoService.atualizar(this.id, this.getDados());
                    this.listaProdutos = response;
                }
                this.$emit("salvar_produto", this.getDados());
                // Resetar campos
                this.resetarCampos();
            } catch (error) {
                this.isInvalido = true;
                this.mensagem = this.getMensagemErro(error);
            }
        },
        cancelar() {
            this.resetarCampos();
            this.$emit("cancelar", true);
        },
        resetarCampos() {
            this.id = "";
            this.descricao = "";
            this.urlImagem = "";
            this.quantidadeEstoque = "";
            this.precoUnidadeAtual = "";
            this.ativo = "";
        },
        getMensagemErro(error) {
            if (error.response && error.response.status === 403) {
                return "Usuário não identificado! Faça o login!!!";
            } else if (error.response && error.response.status === 400) {
                return error.response.data.mensagem;
            } else {
                return error.message;
            }
        }
    },

    mounted() {
        if (this.propsProduto) {
            this.id = this.propsProduto.id;
            this.descricao = this.propsProduto.descricao;
            this.urlImagem = this.propsProduto.urlImagem;
            this.quantidadeEstoque = this.propsProduto.quantidadeEstoque;
            this.precoUnidadeAtual = this.propsProduto.precoUnidadeAtual;
            this.ativo = this.propsProduto.ativo;
        }
    },


    computed: {
        getAcao() {
            return this.id === "" ? "Incluir" : "Alterar";
        },
    }
}
</script>
