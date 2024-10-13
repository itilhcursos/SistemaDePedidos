<template>
  <div class="container">
    <div>
      <h3>Painel do Usuário
        <span v-if="isAuthenticated"> (Usuário Logado: {{ userLogin }})</span>
      </h3>
      <hr />
    </div>

    <div class="container">
      <div class="row" style="justify-content: flex-start">
        <button class="btn btn-success" v-if="!showSignUpForm && !showLoginForm" @click="openSignupForm"
          style="width: 300px">
          <i class="bi bi-person-plus-fill"></i> Novo Cadastro
        </button>
      </div>

      <div class="row" style="justify-content: flex-start; margin-top: 10px">
        <button class="btn btn-primary" v-if="!showSignUpForm && !showLoginForm && !isAuthenticated"
          @click="openLoginForm" style="width: 300px">
          <i class="bi bi-box-arrow-in-right"></i> Entrar
        </button>
      </div>

      <div class="row" style="justify-content: flex-start; margin-top: 10px">
        <button class="btn btn-danger" v-if="!showSignUpForm && !showLoginForm && isAuthenticated"
          @click="performLogout" style="width: 300px">
          <i class="bi bi-box-arrow-right"></i> Sair
        </button>
      </div>
    </div>

    <div class="row" style="width: 60%; margin-top: 20px">
      <FormSignUp v-if="showSignUpForm" @back="hideForms" />
      <FormLogin v-if="showLoginForm" @back="hideForms" />
    </div>
  </div>
</template>

<script>
import FormLogin from './FormLogin.vue';
import FormSignUp from './FormSignUp.vue';

export default {
  components: {
    FormSignUp,
    FormLogin,
  },
  data() {
    return {
      showSignUpForm: false,
      showLoginForm: false,
    };
  },
  methods: {
    openSignupForm() {
      this.showSignUpForm = true;
      this.showLoginForm = false;
    },
    openLoginForm() {
      this.showSignUpForm = false;
      this.showLoginForm = true;
    },
    hideForms() {
      this.showSignUpForm = false;
      this.showLoginForm = false;
    },
    performLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('login');
      this.$router.push({ path: '/' }).then(() => {
        this.$router.go(0);
      });
    }
  },
  computed: {
    isAuthenticated() {
      return localStorage.getItem('token') && localStorage.getItem('login');
    },
    userLogin() {
      return localStorage.getItem('login') || 'Desconhecido';
    }
  }
};
</script>

<style scoped>
.container {
  margin-top: 30px;
}

.row {
  margin-bottom: 15px;
}
</style>