<script setup>
import { RouterLink, RouterView } from "vue-router";

</script>

<template>
  <header>
    <div>
    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/about">Sobre</RouterLink>
      <RouterLink to="/estados">Estados</RouterLink>
      <RouterLink to="/municipios">Municípios</RouterLink>
      <RouterLink to="/formas-pagamento">Formas de Pagamento</RouterLink>
      <RouterLink to="/clientes">Clientes</RouterLink>
      <RouterLink to="/produtos">Produtos</RouterLink>
      <RouterLink to="/pedidos">Pedidos</RouterLink>
      <!-- <RouterLink to="/itens-pedido">Itens de Pedidos</RouterLink> -->
      <RouterLink to="/login" v-if="!isLogado" class="login-link">Login</RouterLink>
      <RouterLink to="/logout" v-if="isLogado" class="user-link">Usuário: {{ login }}</RouterLink>

     
    </nav>
    <img src="./assets/carrinho.png" alt=""/>
  </div>
  </header>
  <main>
    <RouterView />
  </main>
</template>

<script>
export default {
  data() {
    return {
      isLogado: false,
      login: ''
    };
  },
  methods: {
    checkLogin() {
      const token = localStorage.getItem('token')
      const login = localStorage.getItem('login')
      this.isLogado = (token !== null && login !== null);
      this.login = login;
      // alert(this.isLogado);
    }
  },
  mounted() {
    this.checkLogin();
  },
};
</script>


<style scoped>

img{
  width: 150px;
  height: 150px;
  margin: auto;
  display: inline-block;

}

nav {
    text-align: left;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
    background: #23232e;
    border-radius: 11px;
    display: inline-block;
    width: 80%;
  }

.logo {
  display: block;
  margin: 0 auto 2rem;
}

.wrapper nav {
  width: 100%;
  font-size: 12px;
  text-align: left;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-text);
  color: rgb(255, 255, 255);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {


  .logo {
    margin: 0 2rem 0 0;
  }
}

.login-link {
  color:#ffae00; 
  font-weight: bold; 
}

.login-link:hover {
  color: #ffae00; 
}

.user-link {
  color: #198754; 
  font-weight: bold; 
}

.user-link:hover {
  color: #198754; 
}
</style> 