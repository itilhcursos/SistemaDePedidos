<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <div class="row">
        <div class="col">
          <label class="form-label">Id</label>
          <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id" />
        </div>
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
      <div class="mb-3">
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
        nome: this.nome,
      };
    },
    async salvar() {
      console.log(this.selectedCliente, this.selectedProduto);
      // if (this.nome === "") {
      //   this.isInvalido = true;
      //   this.mensagem = "Nome deve ser preenchido!!";
      //   return;
      // }
      // this.isInvalido = false;

      try {
        if (this.id === "") {
          //  const response = await estadoService.criar(this.getDados());
          //  this.options = response;
        } else {
          // const response = await estadoService.atualizar(
          //   this.id,
          //   this.getDados()
          // );
          //this.listaEstados = response;
        }
        this.$emit("salvar_pedido", {
          id: this.id,
          nome: this.nome,
        });

        this.id = "";
        this.nome = "";
      } catch (error) {
        this.isInvalido = true;
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
    async incluirItem(){
        const itemPedido = {
              id : null,
              pedidoId : this.id,
              produtoId : this.selectedProduto.id,
              produtoDescricao : this.selectedProduto.descricao,
              produtoUrlImagem : this.selectedProduto.urlImagem,
              quantidadeEstoque : this.quantidadeItem,
              precoUnidadeAtual : null
            }
        console.log(itemPedido);
        try{
          const response = await itemPedidoService.criar(itemPedido);
          // lista de itens na tela
          this.itens.push(response);
        }catch(error){
          if(error.response.status === 403){        
          alert("Usuário não identificado! Faça o login!!!");
          }else if(error.response.status === 400 ){
            alert(error.response.data.mensagem);     
          }else{
            alert(error.message);
        }
      }

    },
    async excluirItemPedido(id){
      try{
        const response = await itemPedidoService.apagar(id);
        console.log(response);
        // lista de itens na tela
        this.itens = this.itens.filter(item => item.id !== id);
      }catch(error){
        if(error.response.status === 403){        
         alert("Usuário não identificado! Faça o login!!!");
        }else if(error.response.status === 400 ){
          alert(error.response.data.mensagem);     
        }else{
          alert(error.message);
        }
      }
    }
  },
  mounted() {
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

      this.selectedFormaPagamento = {
        id: this.propsPedido.formaPagamentoId, descricao:this.propsPedido.formaPagamentoDescricao 
      };
      this.selectedCliente = { 
        id:this.propsPedido.clienteId, nomeRazaoSocial: this.propsPedido.clienteNomeRazaoSocial
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