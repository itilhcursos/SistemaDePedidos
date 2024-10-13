<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">Cadastro de Usuário</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Login</label>
        <input class="form-control" type="text" v-model="login" placeholder="login" />
      </div>
      <div class="mb-3">
        <label class="form-label">Senha</label>
        <input class="form-control" type="password" v-model="senha" placeholder="senha" />
      </div>
      <div class="mb-3">
        <label class="form-label">Role</label>
        <select class="form-control" v-model="role">
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
        </select>
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ erroMensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="cadastrar">
          <i class="bi bi-clipboard2-check"></i>
          Cadastrar
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
import axios from "axios";
export default {
  data() {
    return {
      login: "",
      senha: "",
      role: "USER",
      isInvalido: false,
      erroMensagem: "",
    };
  },
  methods: {
    async cadastrar() {
      if (this.login === "" || this.senha === "") {
        return;
      }

      try {
        const response = await axios.post("http://localhost:8080/auth/registro", {
          login: this.login,
          senha: this.senha,
          role: this.role,
        });
        console.log(response.data);
        this.$router.push({ path: '/' }).then(() => { this.$router.go(0) });

      } catch (error) {
        this.isInvalido = true;
        this.erroMensagem = error.response.data.mensagem;
      }

      this.login = "";
      this.senha = "";
      this.role = "USER";
    },
    cancelar() {
      this.login = "";
      this.senha = "";
      this.role = "USER";
    },
  },
};
</script>
