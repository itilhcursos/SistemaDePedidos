<script setup>
import ListarEstado from '../components/ListarEstado.vue'
</script>

<template>
 <main>
  <h3>LISTAGEM DE ESTADOS</h3>
   <ListarEstado />
 </main>
</template>

<style scoped>
  main{
    position: absolute;

    top: 2rem;
    right: 15rem;
    width: 22rem;
    background-color: #23232e;
    border-color: rgb(3, 19, 32);
    border-radius: 10px;
    border-style: inset;
    border-width: 5px;
    padding: 7px;
    padding-bottom: 15px;

  }
  h3{
    width: 20rem;
  }
</style>