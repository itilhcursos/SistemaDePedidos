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
      <RouterLink to="/Clientes">Clientes</RouterLink>
      <RouterLink to="/pedidos">Pedidos</RouterLink>
      <RouterLink to="/login">Login</RouterLink>
     
    </nav>
    <img src="./assets/carrinho.png" alt=""/>
  </div>
  </header>
  <main>
    <RouterView />
  </main>
</template>


<style scoped>

img{
  width: 150px;
  height: 15s0px;
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
</style> 