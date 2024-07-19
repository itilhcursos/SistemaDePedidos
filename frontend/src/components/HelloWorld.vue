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
    <h1 class="green">{{ msg }}</h1>
    <h3>
      BEM VINDO À SUA LISTA DE COMPRAS ONLINE
    </h3>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 1000;
  font-size: 3rem;
  position: relative;
  top: -10px;
  margin-bottom: 10px;
}

h3 {
  font-size: 1.8rem;
  margin-bottom: 5px;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: center;
  }
}
</style>
