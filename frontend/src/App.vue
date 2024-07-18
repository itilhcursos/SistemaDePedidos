<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  
    <header>
      <div class="wrapper">
          <HelloWorld msg="Sistema de compras "  />  
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/about">Sobre</RouterLink>
          <RouterLink to="/estados">Estados</RouterLink>
          
          <RouterLink to="/lista">Lista de Compras</RouterLink>
          <RouterLink to="/listaRefatorada">Lista de Compras Refatorda</RouterLink>
        </nav>
      </div>
    </header>

    <main>
      <RouterView />   
    </main>
   
  
  
</template>


<style>
  body{
    background-image: linear-gradient(to left, blue, black 90%);

  }
  

  header {
    line-height: 0;
    max-height: 95vh;
    
  }


</style>

<style scoped>




.logo {
  display: block;
  margin: 0 auto 2rem;
}

.wrapper nav {
  width: 100%;
  font-size: 12px;
  text-align:left;
  margin-top: -22rem;
  
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
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
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
    background: #23232e;
  }
}
</style> 
