<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <header>
    <div class="wrapper">
      <HelloWorld msg="Sistema de compras" />
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">Sobre</RouterLink>
        <RouterLink to="/estados">Estados</RouterLink>
        <RouterLink to="/lista">Lista de Compras</RouterLink>
        <RouterLink to="/listaRefatorada">Lista de Compras Refatorda</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>

:root {
  --cor0: #c8d1f2;
  --cor1: #0B1654;
  --cor2: #33395D;
  --cor3: #8591D4;
  --cor4: #AE91D4;
  --cor5: #d65f71;
  
  --fonte-padrao: Arial, Verdana, Helvetica, sans-serif;
  --fonte-destaque: 'Bebas Neue', sans-serif;
}


header {
  background-color: #d65f71;
  padding-top: 20px;
  padding-bottom: 1px;
  border-radius: 5px;
  text-align: center;
}

nav {
  padding: 20px;
  padding-top: 5px;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  color: #0B1654;
}

nav a:hover {
  background-color: #8591D4;
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
