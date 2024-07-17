<script setup>
import TheWelcome from '../components/TheWelcome.vue'
</script>

<template>
  <main>
    <h3>Documentação Adicional</h3>
    <TheWelcome />
  </main>
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

h3 {
  background-color: #8591D4;
  margin-top: 10px;
  margin-bottom: 20px;
  border-radius: 5px;
  text-shadow: 2px 2px 0px rgba(0, 0, 0, 0.267);
  text-align: left;
  padding: 10px;
  color: #0B1654;
}

</style>
