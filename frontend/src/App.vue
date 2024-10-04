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
      <RouterLink to="/produtos">Produtos</RouterLink>
      <RouterLink to="/formas-pagamento">Formas de Pagamento</RouterLink>
      <RouterLink to="/clientes">Clientes</RouterLink>
      <RouterLink to="/pedidos">Pedidos</RouterLink>
      <RouterLink to="/registro">Cadastro</RouterLink>
      <RouterLink to="/login" v-if="!isLogado">Login</RouterLink>
      <RouterLink to="/logout" v-if="isLogado">Usuário: {{ login }}</RouterLink>
     
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
    data(){
      return{
        isLogado : false,
        login : ''
      };
    },
    methods:{
      checkLogin(){
        const token = localStorage.getItem('token');
        const login = localStorage.getItem('login');
        this.isLogado = (token !== null && login !== null);
        this.login = login;
      }

    },
    mounted(){
      this.checkLogin();
    },

  };
</script>

<style scoped>

img{
  width: 160px;
  height: 130px;
  margin: auto;
  display: inline-block;
}

nav {
    text-align: left;
    font-size: 0.9rem;
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
</style> 