<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <!-- Detalhes do Pedido -->
      <div class="row">
        <div class="col">
          <label class="form-label">Id</label>
          <input class="form-control" type="text" v-model="id" disabled placeholder="Id" />
        </div>
        <div class="col">
          <label class="form-label">Número</label>
          <input class="form-control" type="text" v-model="numero" placeholder="Número" />
        </div>
      </div>

      <div class="row">
        <div class="col">
          <label class="form-label">Data Compra</label>
          <input class="form-control" type="date" v-model="dataCompra" />
        </div>
        <div class="col">
          <label class="form-label">Data Entrega</label>
          <input class="form-control" type="date" v-model="dataEntrega" />
        </div>
        <div class="col">
          <label class="form-label">Data Pagamento</label>
          <input class="form-control" type="date" v-model="dataPagamento" />
        </div>
      </div>

      

      <div class="mb-3">
        <label class="form-label">CLIENTE</label>
        <select v-model="selectedCliente" class="form-select">
          <option v-for="cliente in optionsCliente" :key="cliente.id" :value="cliente.id">
            {{ cliente.nomeRazaoSocial }}
          </option>
        </select>
      </div>

      <!-- <div class="mb-3">
        <label class="form-label">PRODUTO</label>
        <select v-model="selectedProduto" class="form-select">
          <option v-for="produto in optionsProduto" :key="produto.id" :value="produto.id">
            {{ produto.descricao }}
          </option>
        </select>
      </div> -->

      <div class="mb-3">
        <label class="form-label">Forma de Pagamento</label>
        <select v-model="selectedFormaPagamento" class="form-select">
          <option v-for="formaPagamento in optionsformaPagamento" :value="formaPagamento.id" :key="formaPagamento.id">
              {{ options.formaPagamento }}
          </option>
        </select>
      </div>

      <div class="mb-3">
        <label class="form-label">Itens Pedido</label>
        <table class="table table-dark table-striped">
          <thead>
            <tr>
              <th scope="col">Itens</th>
              <th scope="col">Descricão</th>
              <th scope="col">Quantidade</th>
              <th scope="col">valor</th>
              <th scope="col">Total</th>
              <th scope="col">Excluir</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in listaItens" :key="item.id" scope="row">
              <th>
                <img :src=item.produtoUrlImagem height="50px">
              </th>
              <th>
                {{ item.produtoDescricao }}
              </th>
              <th>
                {{ item.quantidadeEstoque }}
              </th>
              <th>
                {{ item.precoUnidadeAtual }}
              </th>
               <th>
                {{ item.quantidadeEstoque * item.precoUnidadeAtual }}
              </th> 
              <th>
                <button class="btn btn-outline-danger m-2" @click.prevent="excluirItemPedido(item.id)">
                  <i class="bi bi-clipboard2-minus"></i>
                </button>
              </th>
            </tr>
          </tbody>
        </table>
      </div>
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
        <div class="col-2 position-relative">

          <button class="btn btn-primary position-absolute top-50 start-50 translate-middle" type="submit"
            v-on:click.prevent="incluirItem">
            <i class="bi bi-clipboard2-check"></i>
            Incluir
          </button>
        </div>
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvar">
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
      listaItens: [],
      isInvalido: false,
      mensagem: "",
      optionsCliente: [],
      selectedCliente: null,
      optionsProduto: [],
      selectedProduto: null,
      quantidadeItem: 0,
      optionsFormaPagamento: [],
      selectedFormaPagamento: null,
    };
  },
  methods: {
    async buscarClientes() {
      try {
        const response = await clienteService.listar(1, 1000, 'ASC', 'id');
        this.optionsCliente = response.content;
      } catch (error) {
        console.error("Erro ao buscar clientes:", error);
      }
    },
    async buscarProdutos() {
      try {
        const response = await produtoService.listar(1, 1000, 'ASC', 'id');
        this.optionsProduto = response.content;
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      }
    },
    async buscarFormaPagamento() {
      try {
        const response = await formaPagamentoService.listar(1, 1000, 'ASC', 'id');
        this.optionsFormaPagamento = response.content;
      } catch (error) {
        console.error("Erro ao buscar formas de pagamento:", error);
      }
    },
    getDados() {
      return {
        id: this.id,
        clienteId: this.selectedCliente,
        formaPagamentoId: this.selectedFormaPagamento,
        numero: this.numero,
        dataCompra: this.dataCompra,
        dataEntrega: this.dataEntrega,
        dataPagamento: this.dataPagamento,
        itens: this.listaItens,
      };
    },
    async salvar() {
      if (!this.selectedCliente) {
        this.isInvalido = true;
        this.mensagem = "Selecione um cliente";
        return;
      }
      this.isInvalido = false;

      try {
        if (!this.id) {
          const response = await pedidoService.criar(this.getDados());
          this.id = response.id;
        }

        for (const item of this.listaItens) {
          item.pedidoId = this.id;
          await itemPedidoService.criar(item);
        }

        this.listaItens = [];
        alert("Pedido e itens salvos com sucesso!");
        this.$emit("salvar_pedido", this.getDados());
      } catch (error) {
        this.isInvalido = true;
        this.mensagem = error.response ? error.response.data.mensagem : error.message;
      }
    },
    cancelar() {
      this.id = '';
      this.numero = '';
      this.dataCompra = '';
      this.dataEntrega = '';
      this.dataPagamento = '';
      this.listaItens = [];
      this.selectedCliente = null;
      this.selectedFormaPagamento = null;
      this.mensagem = '';
      this.$emit("cancelar", true);
    },
    incluirItem() {
      if (!this.selectedProduto || !this.quantidadeItem) {
        this.isInvalido = true;
        this.mensagem = "Selecione um produto e informe a quantidade";
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

      this.listaItens.push(itemPedido);
      this.selectedProduto = null;
      this.quantidadeItem = 0;
    },
    excluirItemPedido(id) {
      this.listaItens = this.listaItens.filter(item => item.id !== id);
      }
    },

  mounted() {
    if (this.propsPedido) {
      this.id = this.propsPedido.id;
      this.selectedCliente = this.propsPedido.clienteId;
      this.numero = this.propsPedido.numero;
      this.dataCompra = this.propsPedido.dataCompra;
      this.dataEntrega = this.propsPedido.dataEntrega;
      this.dataPagamento = this.propsPedido.dataPagamento;
      this.selectedFormaPagamento = this.propsPedido.formaPagamentoId;
      this.listaItens = this.propsPedido.itens || [];
    }
    this.buscarClientes();
    this.buscarProdutos();
    this.buscarFormaPagamento();
  },
  computed: {
    getAcao() {
      return this.id ? "Alterar" : "Incluir";
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
