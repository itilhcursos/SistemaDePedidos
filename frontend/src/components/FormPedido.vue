<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <!-- Informações principais -->
      <div class="row">
        <div class="col">
          <label class="form-label">Id</label>
          <input class="form-control" type="text" v-model="id" disabled placeholder="Id" />
        </div>
        <div class="col">
          <label class="form-label">Número</label>
          <input class="form-control" type="text" v-model="numero" placeholder="Número" />
        </div>
        <div class="col">
          <label class="form-label">Forma de pagamento</label>
          <v-select class="meu-select" v-model="selectedFormaPagamento" :filterable="false"
            :options="optionsFormaPagamento" @search="onSearchFormaPagamento">
            <template v-slot:no-options>Não encontrado.</template>
            <template v-slot:option="option">{{ option.descricao }}</template>
            <template v-slot:selected-option="option">{{ option.descricao }}</template>
          </v-select>
        </div>
      </div>

      <!-- Datas -->
      <div class="row">
        <div class="col">
          <label class="form-label">Data Compra</label>
          <input class="form-control" type="date" v-model="dataCompra" placeholder="Data Compra" />
        </div>
        <div class="col">
          <label class="form-label">Data Entrega</label>
          <input class="form-control" type="date" v-model="dataEntrega" placeholder="Data Entrega" />
        </div>
        <div class="col">
          <label class="form-label">Data Pagamento</label>
          <input class="form-control" type="date" v-model="dataPagamento" placeholder="Data Pagamento" />
        </div>
      </div>

      <!-- Cliente -->
      <div class="mb-3">
        <label class="form-label">Cliente</label>
        <v-select class="meu-select" v-model="selectedCliente" :filterable="false" :options="optionsCliente"
          @search="onSearchCliente">
          <template v-slot:no-options>Não encontrado.</template>
          <template v-slot:option="option">{{ option.nomeRazaoSocial }}</template>
          <template v-slot:selected-option="option">{{ option.nomeRazaoSocial }}</template>
        </v-select>
      </div>

      <!-- Itens do pedido -->
      <div class="mb-3">
        <label class="form-label">Itens Pedido</label>
        <table class="table table-dark table-striped">
          <thead>
            <tr>
              <th scope="col">Imagem</th>
              <th scope="col">Descrição</th>
              <th scope="col">Quantidade</th>
              <th scope="col">Valor</th>
              <th scope="col">Total</th>
              <th scope="col">Excluir</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in itens" :key="item.id">
              <td><img :src="item.produtoUrlImagem" height="50px" /></td>
              <td>{{ item.produtoDescricao }}</td>
              <td>{{ item.quantidadeEstoque }}</td>
              <td>{{ item.precoUnidadeAtual }}</td>
              <td>{{ item.quantidadeEstoque * item.precoUnidadeAtual }}</td>
              <td>
                <button class="btn btn-outline-danger" @click.prevent="excluirItemPedido(item.id)">
                  <i class="bi bi-trash"></i>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Novo Produto -->
      <div class="row">
        <div class="col-8">
          <label class="form-label">Novo Produto</label>
          <v-select class="meu-select" v-model="selectedProduto" :filterable="false" :options="optionsProduto"
            @search="onSearchProduto">
            <template v-slot:no-options>Não encontrado.</template>
            <template v-slot:option="option">
              <img class="mini" :src="option.urlImagem" /> {{ option.descricao }}
              Qtd({{ option.quantidadeEstoque }}) - Preço({{ option.precoUnidadeAtual }})
            </template>
            <template v-slot:selected-option="option">
              <img class="mini" :src="option.urlImagem" /> {{ option.descricao }}
              - Qtd({{ option.quantidadeEstoque }}) - Preço({{ option.precoUnidadeAtual }})
            </template>
          </v-select>
        </div>
        <div class="col-2">
          <label class="form-label">Quantidade</label>
          <input class="form-control" type="number" v-model="quantidadeItem" placeholder="0" />
        </div>
        <div class="col-2 d-flex align-items-center justify-content-center">
          <button class="btn btn-primary" @click.prevent="incluirItem">
            <i class="bi bi-plus"></i> Incluir
          </button>
        </div>
      </div>

      <!-- Mensagem de erro -->
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center mt-3">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        <span>{{ mensagem }}</span>
      </div>

      <!-- Ações -->
      <div class="d-flex justify-content-end mt-3">
        <button class="btn btn-primary m-2" @click.prevent="salvar">
          <i class="bi bi-save"></i> {{ getAcao }}
        </button>
        <button class="btn btn-warning m-2" @click.prevent="cancelar">
          <i class="bi bi-x-circle"></i> Cancelar
        </button>
      </div>
    </form>
  </div>
</template>

<script>
// Importação dos serviços
import clienteService from "@/services/ClienteService";
import produtoService from "@/services/ProdutoSevice";
import itemPedidoService from "@/services/ItemPedidoService";
import formaPagamentoService from "@/services/FormaPagamentoService";

export default {
  props: {
    propsPedido: Object,
  },
  data() {
    return {
      id: "",
      clienteId: "",
      clienteNomeRazaoSocial: "",
      formaPagamentoId: "",
      formaPagamentoDescricao: "",
      numero: "",
      dataCompra: "",
      dataEntrega: "",
      dataPagamento: "",
      itens: [],
      isInvalido: false,
      mensagem: "",
      optionsCliente: [],
      selectedCliente: null,
      optionsProduto: [],
      selectedProduto: null,
      optionsFormaPagamento: [],
      selectedFormaPagamento: null,
      quantidadeItem: 0,
    };
  },
  methods: {
    async onSearchCliente(search, loading) {
      if (!search) return;
      loading(true);
      try {
        const response = await clienteService.buscar(search);
        this.optionsCliente = response.content;
      } finally {
        loading(false);
      }
    },
    async onSearchFormaPagamento() {
      try {
        const response = await formaPagamentoService.buscar();
        this.optionsFormaPagamento = response.content;
      } catch (error) {
        console.error("Erro ao buscar formas de pagamento:", error);
      }
    },
    async onSearchProduto(search, loading) {
      if (!search) return;
      loading(true);
      try {
        const response = await produtoService.buscar(search);
        this.optionsProduto = response.content;
      } finally {
        loading(false);
      }
    },
    async incluirItem() {
      if (!this.selectedProduto || this.quantidadeItem <= 0) {
        alert("Produto inválido ou quantidade inválida!");
        return;
      }

      const itemPedido = {
        id: null,
        pedidoId: this.id,
        produtoId: this.selectedProduto.id,
        produtoDescricao: this.selectedProduto.descricao,
        produtoUrlImagem: this.selectedProduto.urlImagem,
        quantidadeEstoque: this.quantidadeItem,
        precoUnidadeAtual: this.selectedProduto.precoUnidadeAtual,
      };

      try {
        const response = await itemPedidoService.criar(itemPedido);
        this.itens.push(response);
        this.selectedProduto = null;
        this.quantidadeItem = 0;
      } catch (error) {
        console.error("Erro ao incluir item:", error);
      }
    },
    async excluirItemPedido(id) {
      try {
        await itemPedidoService.apagar(id);
        this.itens = this.itens.filter((item) => item.id !== id);
      } catch (error) {
        console.error("Erro ao excluir item:", error);
      }
    },
    async salvar() {
      alert("Salvo com!");
    },
    cancelar() {
      this.$emit("cancelar");
    },
  },
  mounted() {
    if (this.propsPedido) {
      Object.assign(this, this.propsPedido);
      this.selectedFormaPagamento = {
        id: this.formaPagamentoId,
        descricao: this.formaPagamentoDescricao,
      };
      this.selectedCliente = {
        id: this.clienteId,
        nomeRazaoSocial: this.clienteNomeRazaoSocial,
      };
    }
  },
  computed: {
    getAcao() {
      return this.id ? "Editar" : "Cadastrar";
    },
  },
};
</script>

<style>
.mini {
  width: 40px;
  height: 40px;
}
</style>
