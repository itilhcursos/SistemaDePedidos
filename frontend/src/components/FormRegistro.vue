<template>
  <div class="container">
    <div class="row">
      <h4 class="p-1 mb-1 bg-success text-white">Cadastro</h4>
    </div>
    <hr />
    <div>
      <label class="form-label">Nome de Usuário</label>
      <input
        class="form-control"
        type="text"
        v-model="login"
        placeholder="Insira seu nome de usuário"
      />
    </div>
    <div>
      <label class="form-label">Senha</label>
      <input
        class="form-control"
        type="password"
        v-model="senha"
        placeholder="Insira a sua senha"
      />
    </div>
    <div>
      <label class="form-label">Permissões</label>
      <select v-model="role" class="form-select">
        <option value="0">Administrador</option>
        <option value="1">Usuário</option>
      </select>
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
        v-on:click.prevent="cadastrarUsers"
      >
        <i class="bi bi-clipboard2-check"></i> Cadastrar
      </button>
      <button
        class="btn btn-warning m-2"
        type="submit"
        v-on:click.prevent="cancelar"
      >
        <i class="bi bi-clipboard2-x"></i>Cancelar
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      login: "",
      senha: "",
      role: "",
      isInvalido: false,
      erroMensagem: "",
    };
  },
  methods: {
    async cadastrarUsers() {
      if (!this.login || !this.senha || !this.role) {
        this.isInvalido = true;
        this.erroMensagem = "Preencha todos os campos acima para se cadastrar.";
        return;
      }

      try {
        const response = await axios.post(
          "http://localhost:8080/auth/registro",
          this.getDados()
        );

        const dados = response.data;
        this.setDados(dados);
        this.voltarPag();
        console.log("Usuário cadastrado com sucesso!");
      } catch (error) {
        this.tratarErro(error);
      }
    },
    cancelar() {
      this.limparCampos();
      this.isInvalido = false;
      this.erroMensagem = "";
      this.voltarPag();
    },
    voltarPag() {
      this.$router.push({ path: "/" }).then(() => {
        this.$router.go(0);
      });
    },
    limparCampos() {
      this.login = "";
      this.senha = "";
      this.role = "";
    },
    getDados() {
      return {
        login: this.login,
        senha: this.senha,
        role: this.role,
      };
    },
    setDados(dados) {
      this.login = dados.login;
      this.senha = dados.senha;
      this.role = dados.role;
    },
    tratarErro(error) {
      this.isInvalido = true;
      this.erroMensagem =
        error.response?.data?.mensagem || "Erro ao registrar o usuário.";
    },
  },
};
</script>