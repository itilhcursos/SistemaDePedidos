<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white"> {{ getAcao }} Pedido</h4>
        <hr />
        <form>

            <div>
                <v-select class="form-control" label="Produto" :filterable="false" v-model="produtoSelecionado" :options="optProdutos" @search="onSearchProdutos">
                    <template v-slot:no-options>
                      Digite para pesquisar um Produto.
                    </template>
                    <template v-slot:option="option">
                        <img class="mini" :src='option.urlImagem'/> 
                        {{ option.descricao }}
                    </template>
                    <template v-slot:selected-option="option">
                        <img class="mini" :src='option.urlImagem'/> 
                        {{ option.descricao }}
                    </template>
                </v-select>
            </div>

            <div class="row">
                <div class="col">
                    <label class="form-label">Id</label>
                    <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id" />
                </div>
                <div class="col">
                    <label class="form-label">Número</label>
                    <input class="form-control"
                        type="text"
                        v-model="numero"
                        placeholder="Número"/>
                </div>
                <div class="mb-3">
                <label class="form-label">Forma de Pagamento</label>
                <input class="form-control"
                    type="text"
                    v-model="formaPagamentoSelecionada"
                    placeholder="Qual foi a Forma de Pagamento utilizada?"/>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <label class="form-label">Data de Compra</label>
                    <input class="form-control"
                        type="date"
                        v-model="dataCompra"
                        placeholder="Dia que a compra foi feita."/>
                </div>
                <div class="col">
                    <label class="form-label">Data de Pagamento</label>
                    <input class="form-control"
                        type="date"
                        v-model="dataPagamento"
                        placeholder="Dia que o pagamento foi efetuado."/>
                </div>
                <div class="col">
                    <label class="form-label">Data de Entrega</label>
                    <input class="form-control"
                        type="date"
                        v-model="dataEntrega"
                        placeholder="Dia que a entrega foi concluída."/>
                </div>
            </div>
            <div class="mb-3">
                    <label class="form-label">Cliente</label>
                    <input class="form-control"
                        type="text"
                        v-model="clienteSelecionado"
                        placeholder="Quem fez o pedido?"/>
            </div>
            <table class="table table-dark table-striped">
              <thead>
                <tr>
                  <th scope="col">Itens</th>
                  <th scope="col">Descricão</th>
                </tr>
              </thead>
            <tbody>
                <tr v-for="item in itensPedidos" :key="item.id" scope="row">
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
                    {{ item.quantidadeEstoque + produto.quantidadeEstoque}}
                  </th>
                </tr>
              </tbody>
            </table>

            <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">{{ mensagem }}</div>
            </div>
            <div class="mb-3 d-flex justify-content-end">
                <button class="btn btn-primary m-2" type="submit" @click.prevent="salvar"><i class="bi bi-clipboard2-check"></i> {{ getAcao }} </button>
                <button class="btn btn-warning m-2" type="submit" @click.prevent="cancelar"><i class="bi bi-clipboard2-x"></i> Cancelar </button>
            </div>
        </form>
    </div>
</template>

<script>
import pedidoService from '@/services/pedidoService';
import clienteService from '@/services/clienteService';
import formaPagamentoService from '@/services/formaPagamentoService';
import itemPedidoService from '@/services/itemPedidoService';
import produtoService from '@/services/produtoService';
export default {
    props:{
        propsPedido: Object,
    },
    data(){
        return {
            isInvalido: false,
            mensagem: '',

            id: "",
            numero: "",
            dataCompra: "",
            dataEntrega: "",
            dataPagamento: "",
            clienteId: "",
            clienteSelecionado: "",
            clientes: [],
            formaPagamentoId: "",
            formaPagamentoSelecionada: "",
            formasPagamento: [],
            itemPedidoId: "",
            itemPedidoSelecionado: "",
            itensPedidos: [],
            produtoId: "",
            produtoSelecionado: '',
            optProdutos: []
        }
    },

    /* mounted() {
    if (this.propsPedido) {
      this.id = this.propsPedido.id;
      this.clienteId= this.propsPedido.clienteId;
      this.clienteNomeRazaoSocial= this.propsPedido.clienteNomeRazaoSocial;
      this.formaPagamentoId= this.propsPedido.formaPagamentoId;
      this.formaPagamentoDescricao= this.propsPedido.formaPagamentoDescricao;
      this.numero= this.propsPedido.numero;
      this.dataCompra= this.propsPedido.dataCompra;
      this.dataEntrega= this.propsPedido.dataEntrega;
      this.dataPagamento= this.propsPedido.dataPagamento;
      this.itens= this.propsPedido.itens;
      this.selectedFormaPagamento = {id: this.propsPedido.formaPagamentoId, descricao:this.propsPedido.formaPagamentoDescricao };
      this.selectedCliente = {id:this.propsPedido.clienteId, nomeRazaoSocial: this.propsPedido.clienteNomeRazaoSocial};
      
      }
  }, */

    mounted(){
        if (this.propsPedido) {
            this.id = this.propsPedido.id;
            this.numero = this.propsPedido.numero;
            this.dataCompra = this.propsPedido.dataCompra;
            this.dataEntrega = this.propsPedido.dataEntrega;
            this.dataPagamento = this.propsPedido.dataPagamento;
            this.clienteSelecionado = this.propsPedido.clienteSelecionado;
            this.formaPagamentoSelecionada = this.propsPedido.formaPagamentoSelecionada;
            this.itemPedidoSelecionado = this.propsPedido.itemPedidoSelecionado;
        }
        this.buscarClientes();
        this.buscarFormasPagamento();
        this.buscarItensPedidos();
    },
    methods:{
        async onSearchProdutos(search, loading) {
            if (search == "") return;
            loading(true);
            await produtoService.buscar(search).then((response) => {
                //console.log(response);
                this.optProdutos = response.content;
                loading(false);
            })
        },
        getDados(){
            return{
                id: this.id,
                numero: this.numero,
                dataCompra: this.dataCompra,
                dataEntrega: this.dataEntrega,
                dataPagamento: this.dataPagamento,
                clienteId: this.clienteSelecionado,
                formaPagamentoId: this.formaPagamentoSelecionada,
                itemPedidoId: this.itemPedidoSelecionado
            };
        },
        limparCampos(){
            this.id = "";
            this.numero = "";
            this.dataCompra = "";
            this.dataEntrega = "";
            this.dataPagamento = "";
            this.clienteId = "";
            this.formaPagamentoId = "";
            this.itemPedidoId = "";
            this.municipioId = "";
        },
        cancelar(){
            this.limparCampos();
            this.$emit("flip", true)
        },
        async salvar(){
            if (this.nome === ""){
                this.isInvalido = true;
                this.mensagem = "Nome deve ser preenchido!";
                return;
            }
            this.isInvalido = false;
            try{
                if (this.id === ""){
                    const response = await pedidoService.criar(this.getDados());
                    this.listaPedidos = response;
                } else {
                    const response = await pedidoService.atualizar(this.id, this.getDados());
                    this.listaPedidos = response;
                }
                this.$emit("salvar", {
                });

                this.limparCampos();

            } catch (error) {
                this.isInvalido = true;
                if(error.response.status === 403){        
                    this.mensagem = "Usuário não identificado! Faça o login!!!";
                }else if(error.response.status === 400 &&
                    error.response.data.exception === 'PedidoDuplicadoException'){
                    this.mensagem = error.response.data.mensagem;     
                }else{
                    this.mensagem = error.message;
                } 
            }
        }
    },
    computed:{
        getAcao() {
            return this.id === "" ? "Incluir" : "Alterar";
        },
    },
}
</script>

<style>
    .mini{
        height: auto;
        max-width: 2.5rem;
        margin-right: 1rem;
    }
</style>