<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Município</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Id</label>
        <input
          class="form-control"
          type="text"
          v-model="id"
          :disabled="true"
          placeholder="Id municipio"
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

      <div class="mb-3">
        <label class="form-label">Entrega</label>
        <select v-model="entrega" class="form-select">
          <option :value="true">Sim</option>
          <option :value="false">Não</option>
        </select>
      </div>

      <div class="mb-3">
        <label class="form-label">Estado</label>
        <input
          class="form-control"
          type="text"
          v-model="estadoNome"
          placeholder="Nome do Estado"
        />
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ mensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button
          class="btn btn-primary m-2"
          type="submit"
          v-on:click.prevent="salvarMunicipio"
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
import axios from "axios";
export default {
  props: {
    propsMunicipio: Object,
  },
  data() {
    return {
      id: "",
      nome: "",
      isInvalido: false,
      mensagem : '',
    };
  },
  methods: {
    async salvarMunicipio() {
      if (this.nome === "") {
        this.isInvalido = true;
        this.mensagem = "Nome deve ser preenchido!!";
        return;
      }
      this.isInvalido = false;
      let config = {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      }

    try{
        if (this.id === "") {
          //incluir pelo POST da API
          const response = await axios.post("http://localhost:8080/municipio", {
            id: this.id,
            nome: this.nome,
            entrega: this.entrega,
            estadoNome: this.estadoNome,
          }, config);
          this.listaMunicipios = response.data;
        } else {
          // alterar pelo PUT da API
          const response = await axios.put(
            `http://localhost:8080/municipio/${this.id}`,
            {
              id: this.id,
              nome: this.nome,
              entrega: this.entrega,
              estadoNome: this.estadoNome,
            }
          ,config );
          this.listaMunicipios = response.data;
        }
        this.$emit("salvar_municipio", {
          id: this.id,
          nome: this.nome,
          entrega: this.entrega,
          estadoNome: this.estadoNome,
      });

      this.id = "";
      this.nome = "";

    } catch(error) {
        // console.log (error);
        // console.log (error.response.status);
        // console.log (error.response.data.exception);
      this.isInvalido = true;
      if(error.response.status === 403){        
        this.mensagem = "Usuário não identificado! Faça o login!!!";
      }else if(error.response.status === 400 &&
               error.response.data.exception === 'MunicipioDuplicadoException'){
        this.mensagem = error.response.data.mensagem;     
      }else{
        this.mensagem = error.message;
      }
    }
   },
    cancelar() {
      this.id = "";
      this.nome = "";
      this.entrega = "",
      this.estadoNome = "",
      this.$emit("cancelar", true);
    },
  },
  mounted() {
    if (this.propsMunicipio) {
      this.id = this.propsMunicipio.id;
      this.nome = this.propsMunicipio.nome;
      this.entrega = this.propsMunicipio.entrega;
      this.estadoNome = this.propsMunicipio.estadoNome;
    }
  },
  computed: {
    getAcao() {
      return this.id === "" ? "Incluir" : "Alterar";
    },
  },
};
</script>