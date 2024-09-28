<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <div class="mb-3">

        <v-select class="form-control" label="Produto" :filterable="false"
          v-model="produtoSelecionado" :options="optProdutos" @search="onSearchProdutos">
          <template v-slot:no-options>
            Sem produtos para exibir.
          </template>
          <template v-slot:option="option" >
              <img class="mini" :src='option.urlImagem'/> 
              {{ option.descricao }}
          </template>
          <template v-slot:selected-option="option" >
              <img class="mini" :src='option.urlImagem'/> 
              {{ option.descricao }}
          </template>
        </v-select>

      </div>
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input
          class="form-control"
          type="text"
          v-model="nome"
          placeholder="Nome"
        />
      </div>
      <div
        v-if="isInvalido"
        class="alert alert-danger d-flex align-items-center"
        role="alert"
      >
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button
          class="btn btn-primary m-2"
          type="submit"
          v-on:click.prevent="salvarEstado"
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
import estadoService from "@/services/estadoService";
import produtoService from "@/services/produtoService";
export default {
  props: {
    propsPedido: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      isInvalido: false,
      mensagem: "",
      isLoading: false,
      produtoSelecionado: '',
      optProdutos: [],
    };
  },
  methods: {
    async onSearchProdutos(search, loading) {
      if(search.length) {
        loading(true);
        await produtoService.buscar(search).then((response) => {
        //console.log(response);
        this.optProdutos = response.content;
        loading(false);
      });
      }
    },

    getDados() {
      return {
        id: this.id,
        nome: this.nome,
      };
    },
    async salvarEstado() {
      if (this.nome === "") {
        this.isInvalido = true;
        this.mensagem = "Nome deve ser preenchido!!";
        return;
      }
      this.isInvalido = false;

      try {
        if (this.id === "") {
          const response = await estadoService.criar(this.getDados());
          this.listaEstados = response;
        } else {
          const response = await estadoService.atualizar(
            this.id,
            this.getDados()
          );
          this.listaEstados = response;
        }
        this.$emit("salvar_estado", {
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
          error.response.status === 400 &&
          error.response.data.exception === "EstadoDuplicadoException"
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
    if (this.propsEstado) {
      this.id = this.propsEstado.id;
      this.nome = this.propsEstado.nome;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>

<style >
  .mini{
    height: auto;
    max-width: 2.5rem;
    margin-right: 1rem;
  }
</style>