<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Pedido</h4>
    <hr />
    <form>
      <div class="mb-3">
        <multiselect
          v-model="selectedValues"
          id="ajax"
          label="name"
          track-by="code"
          placeholder="Type to search"
          open-direction="bottom"
          :options="values"
          :multiple="true"
          :searchable="true"
          :loading="isLoading"
          :internal-search="false"
          :clear-on-select="false"
          :close-on-select="false"
          :options-limit="300"
          :limit="3"
          :limit-text="limitText"
          :max-height="600"
          :show-no-results="false"
          :hide-selected="true"
          @search-change="find"
        >
          <template #tag="{ option, remove }"
            ><span class="custom__tag"
              ><span> asasas {{ option.nome }}</span
              ><span class="custom__remove" @click="remove(option)"
                >❌</span
              ></span
            ></template
          >
          <template #clear="props">
            <div
              class="multiselect__clear"
              v-if="selectedValues.length"
              @mousedown.prevent.stop="clearAll(props.search)"
            ></div>
          </template>
          <template #noResult>
            <span
              >Oops! No elements found. Consider changing the search
              query.</span
            >
          </template>
        </multiselect>
        <pre class="language-json"><code>{{ selectedValues}}</code></pre>
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="Id estado"
        />
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
import Multiselect from "vue-multiselect";
import estadoService from "@/services/estadoService";
export default {
  components: {
    Multiselect,
  },
  props: {
    propsPedido: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      isInvalido: false,
      mensagem: "",
      selectedValues: [],
      values: [],
      isLoading: false,
    };
  },
  methods: {
    limitText(count) {
      return `and ${count} other countries`;
    },
    async find(query) {
      this.isLoading = true;
      await estadoService.listar(query).then((response) => {
        console.log(response);
        this.values = response.content;
        this.isLoading = false;
      });
    },
    clearAll() {
      this.selectedValues = [];
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