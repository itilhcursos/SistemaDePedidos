<template>
<<<<<<< HEAD
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Produto</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="id produto"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Descrição</label>
        <input
          class="form-control"
          type="text"
          v-model="descricao"
          placeholder="Descrição"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Quantidade</label>
        <input
          class="form-control"
          type="text"
          v-model="quantidadeEstoque"
          placeholder="Quantidade"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Preço</label>
        <input
          class="form-control"
          type="text"
          v-model="precoUnidadeAtual"
          placeholder="Preço"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Ativo</label>
          <select v-model="ativo" class="form-select">
            <option :value="true">true</option>
            <option :value="false">false</option>
          </select>
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">Os campos devem ser preenchidos!!</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button 
          class="btn btn-primary m-2"
          type="submit"
          v-on:click.prevent="salvarProduto"
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
=======
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Produto</h4>
        <hr />
        <form>
            <div class="mb-3">
                <label class="form-label">Id</label>
                <input
                class="form-control"
                type="text"
                v-model="id"
                :disabled="true"
                placeholder="Id produto"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Descrição</label>
                <input
                class="form-control"
                type="text"
                v-model="descricao"
                placeholder="Descrição"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Quantidade em Estoque</label>
                <input
                class="form-control"
                type="text"
                v-model="quantidadeEstoque"
                placeholder="Quantidade em Estoque"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Preço Unitário</label>
                <input
                class="form-control"
                type="text"
                v-model="precoUnidadeAtual"
                placeholder="Preço Unitário"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Ativo</label>
                <select v-model="ativo" class="form-select">
            <option :value="true">Sim</option>
            <option :value="false">Não</option>
          </select>
            </div>
            <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">Nome deve ser preenchido</div>
            </div>
            <div class="mb-3 d-flex justify-content-end">

                <button
                class="btn btn-primary m-2"
                type="submit"
                v-on:click.prevent="salvarProduto"
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
>>>>>>> develop
</template>

<script>
import axios from "axios";
export default {
<<<<<<< HEAD
  props: {
    propsProduto: Object,
  },
  data() {
    return {
      id: "",
      descricao: "",
      quantidadeEstoque: "",
      precoUnidadeAtual: "",
      ativo: "",
      isInvalido: false,
    };
  },
  methods: {
    async salvarProduto() {
      if (this.descricao === "") {
        this.isInvalido = true;
        return;
      }
      this.isInvalido = false;

      if (this.id === "") {
        //incluir pelo POST da API
        const response = await axios.post("http://localhost:8080/produto", {
          id: this.id,
          descricao: this.descricao,
          quantidadeEstoque: this.quantidadeEstoque,
          precoUnidadeAtual: this.precoUnidadeAtual,
          ativo: this.ativo,
        });
        this.listaProdutos = response.data;
      } else {
        // alterar pelo PUT da API
        const response = await axios.put(
          `http://localhost:8080/produto/${this.id}`,
          {
            id: this.id,
            descricao: this.descricao,
            quantidadeEstoque: this.quantidadeEstoque,
            precoUnidadeAtual: this.precoUnidadeAtual,
            ativo: this.ativo,
          }
        );
        this.listaProdutos = response.data;
      }

      this.$emit("salvar_produto", {
        id: this.id,
        descricao: this.descricao,
        quantidadeEstoque: this.quantidadeEstoque,
        precoUnidadeAtual: this.precoUnidadeAtual,
        ativo: this.ativo,
      });
    
      this.id = "";
      this.descricao = "";
      this.quantidadeEstoque = "";
      this.precoUnidadeAtual = "";
      this.ativo = "";
    },
    cancelar() {
      this.id = "";
      this.descricao = "";
      this.quantidadeEstoque = "";
      this.precoUnidadeAtual = "";
      this.ativo = "";
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsProduto) {
      this.id = this.propsProduto.id;
      this.descricao = this.propsProduto.descricao;
      this.quantidadeEstoque = this.propsProduto.quantidadeEstoque;
      this.precoUnidadeAtual = this.propsProduto.precoUnidadeAtual;
      this.ativo = this.propsProduto.ativo;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>
=======


    props: {
        propsProduto: Object,
    },


    data() {
        return {
            id: "",
            descricao: "",
            isInvalido: false,
        };
    },


    methods: {
        async salvarProduto() {
            if (this.descricao === "") {
                this.isInvalido = true;
                return;
            }
            this.isInvalido = false;

            if (this.id === "") {
                const response = await axios.post("http://localhost:8080/produto", {
                    id: this.id, 
                    descricao: this.descricao, 
                    quantidadeEstoque: this.quantidadeEstoque, 
                    precoUnidadeAtual: this.precoUnidadeAtual, 
                    ativo: this.ativo
                });
                this.listaProdutos = response.data;
            } else {
                const response = await axios.put(`http://localhost:8080/produto/${this.id}`, 
                {
                    id: this.id,
                    descricao: this.descricao, 
                    quantidadeEstoque: this.quantidadeEstoque, 
                    precoUnidadeAtual: this.precoUnidadeAtual, 
                    ativo: this.ativo
                });
                this.listaEstados = response.data;
            }

            this.$emit("salvar_produto", {
                id: this.id,
                descricao: this.descricao, 
                quantidadeEstoque: this.quantidadeEstoque, 
                precoUnidadeAtual: this.precoUnidadeAtual, 
                ativo: this.ativo
            });
            this.id = "";
            this.descricao = "";
            this.quantidadeEstoque = "";
            this.precoUnidadeAtual = ""; 
            this.ativo = "";
        },

        cancelar(){
            this.id = "";
            this.descricao = "";
            this.quantidadeEstoque = "";
            this.precoUnidadeAtual = ""; 
            this.ativo = "";
            this.$emit("cancelar", true);
        },
    },


    mounted(){
        if (this.propsProduto) {
            this.id = this.propsProduto.id;
            this.descricao = this.propsProduto.descricao;
            this.quantidadeEstoque = this.propsProduto.quantidadeEstoque;
            this.precoUnidadeAtual = this.propsProduto.precoUnidadeAtual; 
            this.ativo = this.propsProduto.ativo;
        }
    },


    computed: {
        getAcao(){
            return this.id === "" ? "Incluir" : "Alterar";
        },
    }
}
</script>
>>>>>>> develop
