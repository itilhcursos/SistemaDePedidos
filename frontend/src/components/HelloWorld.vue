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
      Bem vindo a sua lista de compras on-line.
    </h3>
  </div>
</template>

<style scoped>
.green{
  color:aliceblue
}
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -4px;
left: 2rem;  
}

h3 {
  font-size: 1.2rem;
  color: rgb(247, 247, 247);
  position: relative;
  left: 3rem;
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
