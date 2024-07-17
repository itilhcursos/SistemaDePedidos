<script setup>
import WelcomeItem from './WelcomeItem.vue'
</script>

<template>
  <WelcomeItem>
    <template #heading>Orientações</template>
    Aqui vão estar todas as orientações que forem precisas para orientar o usuário na hora de fazer seus pedidos.
  </WelcomeItem>
  <WelcomeItem>
    <template #heading>Ferramentas</template>
    Esse projeto é construído com
    <a href="https://vitejs.dev/guide/features.html" target="_blank" rel="noopener">Vite</a>. E a configuração recomendada para <strong>IDE</strong> seria
    <a href="https://code.visualstudio.com/" target="_blank" rel="noopener">VSCode</a> +
    <a href="https://github.com/johnsoncodehk/volar" target="_blank" rel="noopener">Volar</a>. Mais instruções estão disponíveis no arquivo <strong>README.md</strong>.
  </WelcomeItem>
  <WelcomeItem>
    <template #heading>Ecossistema</template>
    Obtenha ferramentas e bibliotecas oficiais para o seu projeto como:
    <a href="https://pinia.vuejs.org/" target="_blank" rel="noopener">Pinia</a>,
    <a href="https://router.vuejs.org/" target="_blank" rel="noopener">Vue Router</a>,
    <a href="https://test-utils.vuejs.org/" target="_blank" rel="noopener">Vue Test Utils</a> e
    <a href="https://github.com/vuejs/devtools" target="_blank" rel="noopener">Vue Dev Tools</a>.
  </WelcomeItem>
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

a {
  color: #8591D4;
}

a:hover {
  background-color: #0B1654;
}

strong {
  color: #d65f71;
}

</style>