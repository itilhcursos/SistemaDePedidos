<template>
    <div class="container">
      <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
      <hr />
      <form>
        <div class="row">
            <div class="col">
                <label class="form-label">ID</label>
                <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id" />
            </div>
            <label class="form-label">Cliente</label>
            <v-select class="meu-select" v-model="selectedCliente" :filterable="false" :options="optionsCliente"
            @search="onSearch">
            <template v-slot:no-options>
                Não encontrado.
            </template>
            <template v-slot:option="option">
                {{ option.nomeRazaoSocial }}
            </template>
            <template v-slot:selected-option="option">
                {{ option.nomeRazaoSocial }}
            </template>
            </v-select>
            <div class="col">
                <label class="form-label">Número</label>
                <input class="form-control" type="text" v-model="numero" placeholder="Número" />
            </div>
            <div class="col">
                <label class="form-label">Forma de pagamento</label>
                <v-select class="meu-select" v-model="selectedFormaPagamento" :filterable="false"
                :options="optionsFormaPagamento" @search="onSearchFormaPagamento">
                <template v-slot:no-options>
                    Não encontrado.
                </template>
                <template v-slot:option="option">
                    {{ option.descricao }}
                </template>
                <template v-slot:selected-option="option">
                    {{ option.descricao }}
                </template>
                </v-select>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label class="form-label">Data Compra</label>
                <input class="form-control" type="text" v-model="dataCompra" placeholder="Data Compra" />
            </div>
            <div class="col">
                <label class="form-label">Data Entrega</label>
                <input class="form-control" type="text" v-model="dataEntrega" placeholder="Data Entrega" />
            </div>
            <div class="col">
                <label class="form-label">Data Pagamento</label>
                <input class="form-control" type="text" v-model="dataPagamento" placeholder="Data Pagamento" />
            </div>
        </div>
        <div class="mb-3">
            <div class="row">
                <div class="col-8">
                    <label class="form-label">Novo Produto</label>
                    <v-select class="meu-select" v-model="selectedProduto" :filterable="false" :options="optionsProduto"
                      @search="onSearchProduto">
                      <template v-slot:no-options>
                        Não encontrado.
                      </template>
                      <template v-slot:option="option">
                        <img class="mini" :src='option.urlImagem' />
                        {{ option.descricao }}
                      </template>
                      <template v-slot:selected-option="option">
                        <img class="mini" :src='option.urlImagem' />
                        {{ option.descricao }}
                      </template>
                    </v-select>
                </div>
                <div class="col-2">
                    <label class="form-label">Quantidade</label>
                    <input class="form-control" type="text" v-model="nome" placeholder="quantidade" />
                </div>
            </div>
        </div>
        <div v-if="isInvalido" class="alert alert-danger d- align-items-center" role="alert">
          <i class="bi bi-exclamation-triangle-fill"></i>
          <div class="p-2">{{ mensagem }}</div>
        </div>
        <div class="mb-3 d-flex justify-content-end">
          <button
            class="btn btn-primary m-2"
            type="submit"
            v-on:click.prevent="salvarPedido"
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
            Cancelar
          </button>
        </div>
      </form>
    </div>
</template>
  
<script>
import pedidoService from "@/services/pedidoService";
import produtoService from "@/services/produtoService";
import clienteService from "@/services/clienteService";
import formaPagamentoService from "@/services/formaPagamentoService";
import "vue-select/dist/vue-select.css";
export default {
    props: {
        propsPedido: Object,
    },
    data() {
      return {
        id: '',
        clienteId: '',
        clienteNomeRazaoSocial: '',
        formaPagamentoId: '',
        formaPagamentoDescricao: '',
        numero: '',
        dataCompra: '',
        dataEntrega: '',
        dataPagamento: '',
        itens: [],

        isInvalido: false,
        isLoading: false,
        mensagem: "",
        optionsCliente: [],
        selectedCliente: null,
        optionsProduto: [],
        selectedProduto: null,
        optionsFormaPagamento: [],
        selectedFormaPagamento: null,
      };
    },
    methods: {
        async onSearch(search, loading) {
          if (search == "")
            return;
          loading(true);
          await clienteService.buscar(search).then((response) => {
            this.optionsCliente = response.content;
            loading(false);
          });
        },
        async onSearchFormaPagamento(search, loading) {
          if (search == "")
            return;
          loading(true);
          await formaPagamentoService.buscar(search).then((response) => {
            this.optionsFormaPagamento = response.content;
            loading(false);
          });
        },
        async onSearchProduto(search, loading) {
          if (search == "")
            return;
          loading(true);
          await produtoService.buscar(search).then((response) => {
            this.optionsProduto = response.content;
            loading(false);
          });
        },
        getDados() {
            return {
                id: this.id,
                nome: this.número,
            };
        },
        async salvarPedido() {
          console.log(this.selectedCliente, this.selectedProduto);
            /* if (this.nome === "") {
                this.isInvalido = true;
                this.mensagem = "Nome deve ser preenchido!!";
                return;
            }
            this.isInvalido = false; */
            
            try {
                if (this.id === "") {
                    const response = await pedidoService.criar(this.getDados());
                    this.listaPedidos = response;
                } else {
                    const response = await pedidoService.atualizar(
                    this.id,
                    this.getDados()
                );
                this.listaPedidos = response;
            }
            this.$emit("salvar_pedido", {
                id: this.id,
                nome: this.nome,
            });
            this.id = "";
            this.nome = "";
        } catch (error) {this.isInvalido = true;
            
            if (error.response.status === 403) {
                this.mensagem = "Usuário não identificado! Faça o login!!!";
            } else if (
                error.response.status === 400
            ) {
                this.mensagem = error.response.data.mensagem;
            } else {
                this.mensagem = error.message;
            }
        }
    },
    cancelar() {
        this.id = "";
        this.nome = "";
        this.$emit("cancelar", true);
    },
},
mounted() {
    if (this.propsPedido) {
        this.id = this.propsPedido.id;
        this.nome = this.propsPedido.nome;
    }
},
computed: {
    getAcao() {
        return this.id === "" ? "Incluir" : "Alterar";
    },
},
};
</script>

<style>
.meu-select {
  width: 100%;
  font-size: 1.0em;
  color: #252525;
  background: #fbf4f4;
  border-radius: 0.375rem;
}

.mini {
  height: auto;
  max-width: 2.5rem;
  margin-right: 1rem;
}
</style>