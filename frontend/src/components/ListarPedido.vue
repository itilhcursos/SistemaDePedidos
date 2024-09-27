<template>
    <div class="container">
        <div class="row">
            <div class="col-10">
                <h3>PEDIDOS</h3>
            </div>


            <div class="col-2 d-flex justify-content-end">
                <button v-if="!formVisible" @click="flipFormVisible" class="btn btn-success">
                    <i class="bi bi-clipboard-plus"></i> Novo
                </button>
            </div>
            <div class="row">
                <div>
                    <FormPedido
                    v-if="formVisible"
                    :propsPedido="pedidoEscolhido"
                    @flip="flipFormVisible"
                    @salvar="listarPedidos"
                    />
                </div>
            </div>


            <table class="table table-dark table-striped" v-if="!formVisible">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nome do Cliente</th>
                        <th scope="col">Forma de Pagamento</th>
                        <th scope="col">Numero</th>
                        <th scope="col">Data da Compra</th>
                        <th scope="col">Data da Entrega</th>
                        <th scope="col">Data do Pagamento</th>
                        <th scope="col" class="d-flex justify-content-end">Itens</th>
                        <!-- <th scope="col" class="d-flex justify-content-end">Ações</th> -->
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="pedido in listaPedidos" :key="pedido.id" scope="row">
                        <th>
                            {{ pedido.id }}
                        </th>
                        <td>
                            {{ pedido.clienteNomeRazaoSocial }}
                        </td>
                        <td>
                            {{ pedido.formaPagamentoDescricao }}
                        </td>
                        <td>
                            {{ pedido.numero }}
                        </td>
                        <td>
                            {{ formatar(pedido.dataCompra) }}
                        </td>
                        <td>
                            {{ pedido.dataEntrega }}
                        </td>
                        <td>
                            {{ pedido.dataPagamento }}
                        </td>
                        <!-- <td class="d-flex justify-content-end">
                            <button class="btn btn-btn btn-primary m-2" @click="alterar(pedido)"><i class="bi bi-clipboard-pulse"></i> Alterar </button>
                            <button class="btn btn-outline-danger m-2" @click="excluir(pedido.id)"><i class="bi bi-clipboard2-minus"></i> Excluir </button>
                        </td> -->
                        <td class="d-flex justify-content-end">
                            <table class="table table-dark table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col">Imagem</th>
                                        <th scope="col">Descricao</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="itens in pedido.itens" :key="itens.id" scope="row">
                                        <th>
                                            <img :src=itens.produtoUrlImagem width="50px"/>
                                        </th>
                                        <th>
                                            {{ itens.produtoDescricao }}
                                        </th>
                                    </tr>
                                </tbody>
                            </table>
                        </td>

                    </tr>
                </tbody>
            </table>
        </div>
        <div v-if="!formVisible">
            <hr />
            <div class="container">
                <div class="row d-flex justify-content-center">
                    <div class="col-auto">
                        <button v-for="pagina in totalPages" :key="pagina" @click.prevent="irPara(pagina)" class="btn btn-light ms-1">
                            {{ pagina }}
                        </button>
                    </div>
                    <div class="col-auto">
                        <input
                            type="text"
                            v-model="pageNumber"
                            placeholder="Número da pagina"
                            class="form-control w-25"
                        />
                    </div>
                    <div class="col-auto">
                        <select v-model="pageSize" class="form-select">
                            <option value="2">2</option>
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                        </select>
                    </div>
                    <div class="col-auto">
                        <select v-model="property" class="form-select">
                            <option value="id">ID</option>
                            <option value="cliente.nomeRazaoSocial">Nome ou Razao Social</option>
                            <option value="formaPagamento.descricao">Forma de Pagamento</option>
                        </select>
                    </div>
                    <div class="col-auto">
                        <select v-model="direction" class="form-select">
                            <option value="ASC">Crescente</option>
                            <option value="DESC">Decrescente</option>
                        </select>
                    </div>
                    <div class="col-auto">
                        <button @click.prevent="listarPedidos" class="btn btn-success">
                            <i class="bi bi-binoculars"></i>
                            Buscar
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Data from '@/utils/Data';
import FormPedido from './FormPedido.vue';
import pedidoService from '@/services/pedidoService';
export default {
    components:{
        FormPedido
    },
    data() {
        return{
            listaPedidos: [],
            pedidoEscolhido: null,
            formVisible: false,
            pageNumber: 1,
            pageSize: 10,
            direction: "ASC",
            property: "id",
            totalPages: 0,
        }
    },
    methods: {
        formatar(data){
            return Data.formatoDMA(data);
        },
        flipFormVisible(){
            this.formVisible = !this.formVisible;
        },
        limpar() {
            this.pedidoEscolhido = null;
            this.flipFormVisible();
        },
        irPara(pagina) {
            this.pageNumber = pagina;
            this.listarPedidos();
        },
        async listarPedidos(){
            this.pedidoEscolhido = null;
            this.formVisible = false;
      
            const response = await pedidoService.listar(this.pageNumber, this.pageSize, this.direction, this.property);

            this.listaPedidos = response.content;
            this.totalPages = response.totalPages;
            console.log(this.totalPages);
        },
        alterar(pedido){
            this.pedidoEscolhido = pedido;
            this.flipFormVisible();
        },
        async excluir(id){
            try{
                const response = await pedidoService.apagar(id);
                console.log(response);
            }catch(error){
                if(error.response.status === 403){        
                alert("Usuário não identificado! Faça o login!!!");
                }else if(error.response.status === 400 ){
                alert(error.response.data.mensagem);     
                }else{
                alert(error.message);
                }
            }     
            this.listarPedidos();
        }
        
    },
    mounted(){
        this.listarPedidos();
    },
};
</script>
