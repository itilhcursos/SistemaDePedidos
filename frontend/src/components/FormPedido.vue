<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <div class="row">
        <div v-if="id !== ''" class="col-md-1 mb-3">
          <label class="form-label">Id</label>
          <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id" />
        </div>
        <div class="col-md-2 mb-3">
          <label class="form-label">Número do Pedido</label>
          <input class="form-control" type="text" v-model="numero" placeholder="Número" />
        </div>
      </div>
      <div class="row">
        <div class="col-md-9 mb-3">
          <label class="form-label">Cliente</label>
          <v-select class="meu-select" v-model="selectedCliente" :filterable="false" :options="optionsCliente"
            @search="onSearchCliente">
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
        </div>
        <div class="col-md-3 mb-3">
          <label class="form-label">Forma de Pagamento</label>
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
          <label class="form-label">Data da Compra</label>
          <input class="form-control" type="date" v-model="dataCompra" placeholder="Data Compra" />
        </div>
        <div class="col">
          <label class="form-label">Data da Entrega</label>
          <input class="form-control" type="date" v-model="dataEntrega" placeholder="Data Entrega" />
        </div>
        <div class="col">
          <label class="form-label">Data de Pagamento</label>
          <input class="form-control" type="date" v-model="dataPagamento" placeholder="Data Pagamento" />
        </div>
      </div>
      <hr />

      <div class="container mt-3">
        <h4 class="p-1 mb-1 bg-success text-white">Itens do Pedido</h4>
        <hr />
        <table class="table table-dark table-striped">
          <thead>
            <tr>
              <th scope="col">Itens</th>
              <th scope="col">Descrição</th>
              <th scope="col" class="text-center align-middle">Quantidade</th>
              <th scope="col" class="text-center align-middle">Valor</th>
              <th scope="col" class="text-center align-middle">Total</th>
              <th scope="col">Excluir</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in itens" :key="item.id" scope="row">
              <th>
                <img :src=item.produtoUrlImagem height="50px">
              </th>
              <th class="align-middle">{{ item.produtoDescricao }}</th>
              <th class="text-center align-middle">{{ formatarQuantidade(item.quantidadeEstoque) }}</th>
              <th class="text-center align-middle">{{ formatarPreco(item.precoUnidadeAtual) }}</th>
              <th class="text-center align-middle">{{ formatarPreco(item.quantidadeEstoque * item.precoUnidadeAtual) }}</th>
              <th>
                <button class="btn btn-outline-danger m-2" @click.prevent="excluirItemPedido(item.id)">
                  <i class="bi bi-clipboard2-minus"></i>
                </button>
              </th>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="row align-items-end">
        <div class="col-8">
          <label class="form-label">Incluir Produto</label>
          <v-select class="meu-select" v-model="selectedProduto" :filterable="false" :options="optionsProduto"
            @search="onSearchProduto">
            <template v-slot:no-options>
              Não encontrado.
            </template>
            <template v-slot:option="option">
              <img class="mini" :src='option.urlImagem' />
              {{ option.descricao }} Qtd({{ option.quantidadeEstoque }}) - Preço({{ option.precoUnidadeAtual }})
            </template>
            <template v-slot:selected-option="option">
              <img class="mini" :src='option.urlImagem' />
              {{ option.descricao }} -Qtd({{ option.quantidadeEstoque }}) - Preço({{ option.precoUnidadeAtual }})
            </template>
          </v-select>
        </div>
        <div class="col-2">
          <label class="form-label">Quantidade</label>
          <input class="form-control" type="number" v-model="quantidadeItem" placeholder="0" />
        </div>
        <div class="col-2">
          <label class="form-label d-none">Adicionar</label>
          <button class="btn btn-success w-100" type="submit" v-on:click.prevent="incluirItem">
            <i class="bi bi-clipboard2-check"></i>
            Adicionar
          </button>
        </div>
      </div>
      <hr>

      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
     
      <div class="mb-3 d-flex justify-content-end">
        <button v-if="id === ''" class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarPedido">
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
import Monetario from "@/utils/Monetario";
import Decimal from "@/utils/Decimal";
import clienteService from "@/services/clienteService";
import produtoService from "@/services/produtoService";
import itemPedidoService from "@/services/itemPedidoService";
import formaPagamentoService from "@/services/formaPagamentoService";
import pedidoService from "@/services/pedidoService";

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
      quantidadeItem: 0
    };
  },
  methods: {
    async onSearchCliente(search, loading) {
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
        id: this.id || null,
        clienteId: this.selectedCliente ? this.selectedCliente.id : null,
        clienteNomeRazaoSocial: this.selectedCliente.nomeRazaoSocial,
        formaPagamentoId: this.selectedFormaPagamento ? this.selectedFormaPagamento.id : null,
        formaPagamentoDescricao: this.selectedFormaPagamento.descricao,
        numero: this.numero,
        dataCompra: this.dataCompra,
        dataEntrega: this.dataEntrega,
        dataPagamento: this.dataPagamento,
        itens: this.itens,
      };
    },
    limparMensagemErro() {
    this.isInvalido = false;
    this.mensagem = "";
  },
    async salvarPedido() {
      //console.log('Iniciando processo de salvar pedido');
      if (!this.numero || this.numero === "") {
        this.isInvalido = true;
        this.mensagem = "O Número do Pedido não pode ser vazio!";
        return;
      }
      if (!this.selectedCliente || !this.selectedCliente.id) {
        this.isInvalido = true;
        this.mensagem = "Selecione um Cliente válido!";
        return;
      }
      if (!this.selectedFormaPagamento || !this.selectedFormaPagamento.id) {
        this.isInvalido = true;
        this.mensagem = "Selecione uma Forma de Pagamento válida!";
        return;
      }
      if (this.dataCompra === "") {
        this.isInvalido = true;
        this.mensagem = "Data da Compra não pode ser vazia!";
        return;
      }
      if (this.dataEntrega === "") {
        this.isInvalido = true;
        this.mensagem = "Data da Entrega não pode ser vazia!";
        return;
      }
      if (this.dataPagamento === "") {
        this.isInvalido = true;
        this.mensagem = "Data do Pagamento não pode ser vazia!";
        return;
      }
      
      this.isInvalido = false;
      
      try {
        const dadosPedido = this.getDados();
        if (!this.id) {
          const response = await pedidoService.criar(dadosPedido);
          this.listaPedidos = response;
        } else {
          const response = await pedidoService.atualizar(this.id, dadosPedido);
          this.listaPedidos = response;
        }
        this.$emit("salvar_pedido", dadosPedido); // Emite o evento

      } catch (error) {
        this.isInvalido = true;
        if (error.response && error.response.status === 403) {
          this.mensagem = "Usuário não identificado! Faça o login!!!";
        } else if (error.response && error.response.status === 400) {
          this.mensagem = error.response.data.mensagem;
        } else {
          this.mensagem = error.message;
        }
      }
    },
    cancelar() {
      this.id = "";
      this.numero = "";
      this.selectedCliente = null;
      this.selectedFormaPagamento = null;
      this.dataCompra = "";
      this.dataEntrega = "";
      this.dataPagamento = "";
      this.itens = [];
      this.quantidadeItem = "";
      this.$emit("cancelar", true);
    },
    async incluirItem() {

      if (this.id && (!this.selectedProduto || !this.selectedProduto.id)) {
        this.isInvalido = true;
        this.mensagem = "Selecione um Produto válido para adicionar ao pedido!";
        return;
      }
      if (this.id && this.quantidadeItem <= 0) {
        this.isInvalido = true;
        this.mensagem = "A quantidade deve ser maior que zero(0)!";
        return;
      }

      this.isInvalido = false;

      const itemPedido = {
        id: null,
        pedidoId: this.id,
        produtoId: this.selectedProduto.id,
        produtoDescricao: this.selectedProduto.descricao,
        produtoUrlImagem: this.selectedProduto.urlImagem,
        quantidadeEstoque: this.quantidadeItem,
        precoUnidadeAtual: this.selectedProduto.precoUnidadeAtual
      };

      console.log("Item a ser incluído:", itemPedido);

      try {
        const response = await itemPedidoService.criar(itemPedido);
        this.itens.push(response);
      } catch (error) {
        if (error.response && error.response.status === 403) {
          alert("Usuário não identificado! Faça o login!!!");
        } else if (error.response && error.response.status === 400) {
          alert(error.response.data.mensagem);
        } else {
          alert(error.message);
        }
      }
    },
    async excluirItemPedido(id) {
      try {
        await itemPedidoService.apagar(id);
        this.itens = this.itens.filter(item => item.id !== id);
      } catch (error) {
        if (error.response && error.response.status === 403) {
          alert("Usuário não identificado! Faça o login!!!");
        } else if (error.response && error.response.status === 400) {
          alert(error.response.data.mensagem);
        } else {
          alert(error.message);
        }
      }
    },
    formatarPreco(valor) {
      return Monetario.toTela(valor);
    },
    formatarQuantidade(valor) {
      return Decimal.toTela(valor);
    }
  },
  mounted() {
    if (this.propsPedido) {
      this.id = this.propsPedido.id;
      this.numero = this.propsPedido.numero;
      this.clienteId = this.propsPedido.clienteId;
      this.clienteNomeRazaoSocial = this.propsPedido.clienteNomeRazaoSocial;
      this.formaPagamentoId = this.propsPedido.formaPagamentoId;
      this.formaPagamentoDescricao = this.propsPedido.formaPagamentoDescricao;
      this.dataCompra = this.propsPedido.dataCompra;
      this.dataEntrega = this.propsPedido.dataEntrega;
      this.dataPagamento = this.propsPedido.dataPagamento;
      this.itens = this.propsPedido.itens;

      this.selectedCliente = {
        id: this.propsPedido.clienteId,
        nomeRazaoSocial: this.propsPedido.clienteNomeRazaoSocial
      };
      this.selectedFormaPagamento = {
        id: this.propsPedido.formaPagamentoId,
        descricao: this.propsPedido.formaPagamentoDescricao
      };
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

<style scoped>
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
