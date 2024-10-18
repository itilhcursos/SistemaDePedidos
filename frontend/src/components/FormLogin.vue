<template>
  <div class="container">
    <div class="row" v-if="formVisible">
      <div>
        <FormRegistro
          :propsRegistro="registroEscolhido"
          @cancelar="limpar"
          
        />
      </div>
    </div>
    <div v-if="!formVisible">
      <div class="row">
        <div class="col-10">
          <h3>Login</h3>
        </div>

        <div class="col-2 d-flex justify-content-end">
          <button @click="novo" class="btn btn-success">
            <i class="bi bi-clipboard-plus"></i> Nova conta
          </button>
        </div>
      </div>

      <hr />
      <form>
        <div class="mb-3">
          <label class="form-label">Login</label>
          <input
            class="form-control"
            type="text"
            v-model="login"
            placeholder="login"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Senha</label>
          <input
            class="form-control"
            type="password"
            v-model="senha"
            placeholder="senha"
          />
        </div>
        <div
          v-if="isInvalido"
          class="alert alert-danger d-flex align-items-center"
          role="alert"
        >
          <i class="bi bi-exclamation-triangle-fill"></i>
          <div class="p-2">{{ erroMensagem }}</div>
        </div>
        <div class="mb-3 d-flex justify-content-end">
          <button
            class="btn btn-primary m-2"
            type="submit"
            v-on:click.prevent="logar"
          >
            <i class="bi bi-clipboard2-check"></i>
            Logar
          </button>
          <button
            class="btn btn-warning m-2"
            type="submit"
            v-on:click.prevent="cancelar"
          >
            <i class="bi bi-clipboard2-x"></i>
            Cancelar
          </button>
          <button
            class="btn btn-danger m-2"
            type="submit"
            v-on:click.prevent="logout"
          >
            <i class="bi bi-x-octagon"></i>
            Logout
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import FormRegistro from "./FormRegistro.vue";
export default {
  components: {
    FormRegistro,
  },
  data() {
    return {
      login: "",
      senha: "",
      token: "",
      isInvalido: false,
      erroMensagem: "",
      formVisible: false,
      registroEscolhido: null
      
    };
  },
  methods: {
    async logar() {
      if (this.login === "" || this.senha === "") {
        return;
      }

      try {
        const response = await axios.post("http://localhost:8080/auth/login", {
          login: this.login,
          senha: this.senha,
        });
        const dados = response.data;
        console.log(dados);
        localStorage.setItem("token", dados.token);
        this.$router.push("/");
        localStorage.setItem("login", dados.login);
        this.$router.push({ path: "/" }).then(() => {
          this.$router.go(0);
        });
      } catch (error) {
        this.isInvalido = true;
        this.erroMensagem = error.response.data.mensagem;
      }

      this.id = "";
      this.nome = "";
    },
    cancelar() {
      this.login = "";
      this.senha = "";
    },
    logout() {
      localStorage.setItem("token", "");
      localStorage.removeItem("token", "");
      localStorage.setItem("login", "");
      localStorage.removeItem("login", "");
      this.$router.push({ path: "/" }).then(() => {
        this.$router.go(0);
      });
    },
    novo() {
      this.formVisible = !this.formVisible;
    },
    limpar(){
     
      this.formVisible = !this.formVisible;
    }
  },
};
</script>

