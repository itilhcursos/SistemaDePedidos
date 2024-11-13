<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <div class="row">
        <div class="mb-3">
          <label class="form-label">ID</label>
          <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="ID Pedido" />
        </div>
        <div class="mb-3">
          <label class="form-label">Empresa</label>
          <select v-model="selectedCliente" class="form-select">
            <option v-for="cliente in optionsCliente" :key="cliente.id" :value="cliente.id">
              {{ cliente.nomeRazaoSocial }}
            </option>
          </select>
        </div>

        <div class="mb-3">
          <label class="form-label">Forma de Pagamento</label>
          <select v-model="selectedFormaPagamento" class="form-select">
            <option v-for="formaPagamento in optionsFormaPagamento" :value="formaPagamento.id" :key="formaPagamento.id">
              {{ formaPagamento.descricao }}
            </option>
          </select>
        </div>
      </div>
      
      <div class="row">
        <div class="col">
          <label class="form-label">Número</label>
          <input class="form-control" type="number" v-model="numero" placeholder="Insira o número do pedido" />
        </div>
        <div class="col">
          <label class="form-label">Data da Compra</label>
          <input class="form-control" type="date" v-model="dataCompra" />
        </div>
        <div class="col">
          <label class="form-label">Data da Entrega</label>
          <input class="form-control" type="date" v-model="dataEntrega" />
        </div>
        <div class="col">
          <label class="form-label">Data do Pagamento</label>
          <input class="form-control" type="date" v-model="dataPagamento" />
        </div>
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
                <tr v-for="item in itens" :key="item.id" scope="row">
                  <th>
                    <img :src=item.produtoUrlImagem height="50px">
                  </th>
                  <th>
                    {{ item.descricao }}
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
                    <button
                      class="btn btn-outline-danger m-2"
                      @click.prevent="excluirItemPedido(item.id)">
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
         
          <button class="btn btn-success position-absolute top-50 start-50 translate-middle"  type="submit" v-on:click.prevent="incluirItem">
          <i class="bi bi-clipboard2-check"></i>
            IncluirItem
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
// import formaPagamentoService from "@/services/formaPagamentoService";
import produtoService from "@/services/produtoService";
import itemPedidoService from "@/services/itemPedidoService";
import pedidoService from "@/services/pedidoService";
import axios from "axios";

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
      quantidadeItem: 0,
      pageNumber: 1,
      pageSize: 10,
      direction: "ASC",
      property: "id",
    };
  },
  methods: {

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

    async buscarClientes() {
      try {
        const response = await clienteService.listar(1, 1000, 'ASC', 'id');
        this.optionsCliente = response.content;
      } catch (error) {
        console.error("Erro ao buscar clientes:", error);
      }
    },

    async buscarFormaPagamento() {
      try {
        console.log('111')
        const response = await axios.get(
          `http://localhost:8080/formas-pagamento?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
        );
        console.log(response.data.content)
        this.optionsFormaPagamento = response.data.content;
      } catch (error) {
        console.log('222')
        console.error("Erro ao buscar formas de pagamento:", error);
      }
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

    async excluirItemPedido(itemId) {
      try {
        await itemPedidoService.apagar(itemId);
        this.listaItens = this.listaItens.filter(item => item.id !== itemId);
        alert("Item excluído com sucesso!");
      } catch (error) {
        this.isInvalido = true;
        this.mensagem = error.response ? error.response.data.mensagem : error.message;
      }
    },

    async salvar() {
      if (!this.selectedCliente) {
        this.isInvalido = true;
        this.mensagem = "Selecione um cliente";
        return;
      }
      this.isInvalido = false;
      
      try { // Verifique se estamos atualizando ou criando um novo ID;
        if (this.id) {
          await pedidoService.atualizar(this.id, this.getDados());
        } else {
          const response = await pedidoService.criar(this.getDados());
          this.id = response.id;
        } // Limpar e recriar itens associados a este pedido

        for (const item of this.listaItens) {
          item.pedidoId = this.id;
          if (item.id) {
            await itemPedidoService.atualizar(item.id, item);
          } else {
            await itemPedidoService.criar(item);
          }
        }
        this.listaItens = [];
        alert("Pedido e itens salvos com sucesso!");
        this.$emit("salvar_pedido", this.getDados());
      } catch (error) {
        this.isInvalido = true;
        this.mensagem = error.response ? error.response.data.mensagem : error.message;
      }
    },

    async excluirPedido() {
      if (!this.id) return;
      try {// Remove todos os itens associados a este pedido primeiro
        for (const item of this.listaItens) {
          await itemPedidoService.apagar(item.id);
        }
        await pedidoService.apagar(this.id);
        alert("Pedido e todos os itens foram excluídos com sucesso!");
        this.cancelar();
        this.$emit("pedido_excluido", this.id);
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
    this.buscarFormaPagamento();
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