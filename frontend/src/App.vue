<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <header>
    
    <div class="wrapper">
      <HelloWorld msg="FUNDAÇÃO SPEEDWAGON" />
      <nav>
        <RouterLink to="/">Principal</RouterLink>
        <RouterLink to="/about">Sobre a Fundação Speedwagon</RouterLink>
        <RouterLink to="/estados">Estados Atuantes</RouterLink>
        <p></p>
        <p></p>
        <RouterLink to="/lista">Lista de Compras medicamentos</RouterLink>
        <RouterLink to="/listaRefatorada">Lista de Compras Ocultismo</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  line-height: 0.9;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 10px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: rgb(211, 113, 113);
}

nav a.router-link-exact-active:hover {
  background-color: rgb(0, 0, 0);
}

nav a {
  display: center;
  padding: 0 1rem;
  border-left: 3px solid var(--color-border);
  color:rgb(253, 253, 253)
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: inline-flex;
    place-items: center;
    color:  rgb(236, 15, 137) ;
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: inline-flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: center;
    margin-left: -3rem;
    font-size: 1.1rem;

    padding: 1.1rem 0;
    margin-top: 2rem;
    
  }
  
}
</style>
