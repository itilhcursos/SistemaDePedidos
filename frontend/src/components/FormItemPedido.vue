<script>
import clienteService from "@/services/clienteService";
import formaPagamentoService from "@/services/formaPagamentoService";
import itemPedidoService from "@/services/itemPedidoService";
import produtoService from "@/services/produtoService";

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
    async onSearch(search, loading) {
      if (!search) return;
      loading(true);
      try {
        const response = await clienteService.buscar(search);
        this.optionsCliente = response.content;
      } finally {
        loading(false);
      }
    },
    async onSearchFormaPagamento(search, loading) {
      if (!search) return;
      loading(true);
      try {
        const response = await formaPagamentoService.buscar(search);
        this.optionsFormaPagamento = response.content;
      } finally {
        loading(false);
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
    getDados() {
      return {
        id: this.id,
        clienteId: this.selectedCliente ? this.selectedCliente.id : null,
        formaPagamentoId: this.selectedFormaPagamento ? this.selectedFormaPagamento.id : null,
        numero: this.numero,
        dataCompra: this.dataCompra,
        dataEntrega: this.dataEntrega,
        dataPagamento: this.dataPagamento,
        itens: this.itens
      };
    },
    async salvar() {
      if (!this.selectedCliente || !this.selectedFormaPagamento) {
        this.isInvalido = true;
        this.mensagem = "Cliente e forma de pagamento são obrigatórios!";
        return;
      }
      try {
        const response = this.id
          ? await itemPedidoService.atualizar(this.id, this.getDados())
          : await itemPedidoService.criar(this.getDados());
        
        this.$emit("salvar_pedido", response); 
        this.cancelar(); 
      } catch (error) {
        this.isInvalido = true;
        this.mensagem = error.response ? error.response.data.mensagem : error.message;
      }
    },
    cancelar() {
      this.$emit("cancelar", true);
    },
    async incluirItem() {
      if (!this.selectedProduto || this.quantidadeItem <= 0) {
        alert("Produto e quantidade são obrigatórios!");
        return;
      }

      const itemPedido = {
        id: null,
        pedidoId: this.id,
        produtoId: this.selectedProduto.id,
        produtoDescricao: this.selectedProduto.descricao,
        produtoUrlImagem: this.selectedProduto.urlImagem,
        quantidadeEstoque: this.quantidadeItem,
        precoUnidadeAtual: this.selectedProduto.precoUnidadeAtual
      };

      try {
        const response = await itemPedidoService.criar(itemPedido);
        this.itens.push(response);
      } catch (error) {
        alert(error.response ? error.response.data.mensagem : error.message);
      }
    },
    async excluirItemPedido(id) {
      try {
        await itemPedidoService.apagar(id);
        this.itens = this.itens.filter(item => item.id !== id);
      } catch (error) {
        alert(error.response ? error.response.data.mensagem : error.message);
      }
    }
  },
  mounted() {
    if (this.propsPedido) {
      Object.assign(this, this.propsPedido);
      this.selectedFormaPagamento = {
        id: this.propsPedido.formaPagamentoId,
        descricao: this.propsPedido.formaPagamentoDescricao
      };
      this.selectedCliente = {
        id: this.propsPedido.clienteId,
        nomeRazaoSocial: this.propsPedido.clienteNomeRazaoSocial
      };
    }
  },
  computed: {
    getAcao() {
      return this.id ? "Alterar" : "Incluir";
    },
  },
};
</script>
