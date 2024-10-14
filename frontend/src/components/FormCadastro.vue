<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">Cadastro</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Nome de Usuário</label>
        <input class="form-control" type="text" v-model="login" placeholder="Nome de Usuário" />
      </div>
      <div class="mb-3">
        <label class="form-label">Senha</label>
        <input class="form-control" type="password" v-model="senha" placeholder="Digite sua senha aqui!" />
      </div>
      <div class="mb-3">
        <label class="form-label">Confirmar senha</label>
        <input class="form-control" type="password" v-model="registry" placeholder="Digite sua senha novamente!" />
      </div>
      <div class="mb-3">
        <label class="form-label">Tipo de Usuário</label>
        <select v-model="role" class="form-select">
          <option :value="0">ADMIN</option>
          <option :value="1">USER</option>
        </select>
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{ erroMensagem }}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="registrar">
          <i class="bi bi-clipboard2-check"></i>
          Registrar
        </button>
        <button class="btn btn-warning m-2" type="button" v-on:click.prevent="cancelar">
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
      registry: "",
      role: "",
      isInvalido: false,
      erroMensagem: "",
    };
  },
  methods: {
    async registrar() {
      if (this.login === "" || this.senha === "" || this.role === "") {
        this.isInvalido = true;
        this.erroMensagem = "Por favor, preencha todos os campos.";
        return;
      }

      if (this.senha !== this.registry) {
        this.isInvalido = true;
        this.erroMensagem = "As senhas não coincidem.";
        return;
      }

      const roleMap = {
        0: "ADMIN",
        1: "USER"
      };

      try {
        const response = await axios.post("http://localhost:8080/auth/registro", {
          login: this.login,
          senha: this.senha,
          role: roleMap[this.role], // Mapeia o valor numérico para o texto adequado
        });

        const dados = response.data;
        localStorage.setItem('login', dados.login);
        localStorage.setItem('senha', dados.senha);
        localStorage.setItem('role', dados.role);
        this.$router.push({ path: '/' }).then(() => {
          this.$router.go(0);
        });

        this.login = "";
        this.senha = "";
        this.registry = "";
        this.role = "";
        this.isInvalido = false;

      } catch (error) {
        console.error(error);
        this.isInvalido = true;
        this.erroMensagem = "Cadastro não adicionado.";
      }
    }
  },
};
</script>