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
    <h1 class="black">{{ msg }}</h1>
    <h3>
      Bem vindo a Fundação Speedwagon, aqui você irá encontrar mais informações,fazer compras de nossos produtos e onde atuamos.
    </h3>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.9rem;
  position: center;
  top: -10px;
  color:rgb(238, 89, 178)
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
