<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white"> {{ getAcao }} Pedido</h4>
        <hr />
        <form>
            <div class="mb-3" >
                <label class="form-label">ID</label>
                <input class="form-control" style="background-color:rgb(150, 150, 150)"
                    type="text"
                    v-model="id"
                    :disabled="true"
                    placeholder="ID do Pedido (Automático)"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Número</label>
                <input class="form-control"
                    type="text"
                    v-model="numero"
                    placeholder="Número"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Data de Compra</label>
                <input class="form-control"
                    type="text"
                    v-model="dataCompra"
                    placeholder="Dia que a compra foi feita."/>
            </div>
            <div class="mb-3">
                <label class="form-label">Data de Pagamento</label>
                <input class="form-control"
                    type="text"
                    v-model="dataPagamento"
                    placeholder="Dia que o pagamento foi efetuado."/>
            </div>
            <div class="mb-3">
                <label class="form-label">Data de Entrega</label>
                <input class="form-control"
                    type="text"
                    v-model="dataEntrega"
                    placeholder="Dia que a entrega foi concluída."/>
            </div>
            <div class="mb-3">
                <label class="form-label">Cliente</label>
                <input class="form-control"
                    type="text"
                    v-model="clienteSelecionado"
                    placeholder="Quem fez o pedido?"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Forma de Pagamento</label>
                <input class="form-control"
                    type="text"
                    v-model="formaPagamentoSelecionada"
                    placeholder="Qual foi a Forma de Pagamento utilizada?"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Itens do Pedido</label>
                <input class="form-control"
                    type="text"
                    v-model="itemPedidoSelecionado"
                    placeholder="Selecione a lista de produtos pedidos pelo cliente."/>
            </div>

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
            itensPedidos: []
        }
    },
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
        async buscarClientes(){
            const response = await clienteService.listar(1, 1000, 'ASC', 'nome');
            this.clientes = response.content;
        },
        async buscarFormasPagamento(){
            const response = await formaPagamentoService.listar(1, 1000, 'ASC', 'nome');
            this.formasPagamento = response.content;
        },
        async buscarItensPedidos(){
            const response = await itemPedidoService.listar(1, 1000, 'ASC', 'nome');
            this.itensPedidos = response.content;
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