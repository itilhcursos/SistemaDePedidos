<script setup>
defineProps({
  msg: {
    type: String,
    required: true
  }
})
</script>

<template>
<div class="greetings">
  <header>
    <h1 class="green">{{ msg }}</h1>
    <h2>
      Bem vindo(a) a sua lista de compras online. Este é um projeto de lista de compras online com <a href="https://vitejs.dev/" target="_blank" rel="noopener">Vite</a> e com <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue3</a>.
    </h2>
  </header>
</div>
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

* {
  padding: 0px;
  margin: 0px;
}

header {
  background-color: #d65f71;
  padding-top: 30px;
  padding-bottom: 20px;
  min-height: 80px;
  text-align: center;
}

 h1 {
  margin-bottom: 20px;
  text-shadow: 2px 2px 0px rgba(0, 0, 0, 0.267);
  font-family: var(--fonte-destaque);
  font-weight: normal;
  font-size: 3em;
  color: #0B1654;
}

h2 {
  text-shadow: 2px 2px 0px rgba(0, 0, 0, 0.267);
  font-size: 15pt;
  color: #0B1654;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

</style>
