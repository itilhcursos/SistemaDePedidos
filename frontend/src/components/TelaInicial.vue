<script setup>
defineProps({
  msg: {
    type: String,
    required: true
  },
  msg2: {
    type: String,
    required: true
  }
})
</script>

<template>
  
  <div>
    <h1 class="cor">{{ msg }}</h1>
    <h2>{{ msg2 }}</h2>
    <div><img src="../assets/carrinho.png" alt=""></div>

  </div>
</template>

<style scoped>
.cor {
  color: rgba(249, 249, 249, 0.945)
}

img {
  margin: auto;
  display: block;
}

h1 {
  font-weight: 1000;
  font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  font-size: 5rem;
  position: relative;
  align-content: center;
  margin: 0;
  text-align: center;
  text-emphasis-color: red;
}

h2 {
  position: relative;
  font-size: 120px;
  font-family: serif;
  text-align: center;
}
h3
{
  position: relative;
  font-size: small;
  font-family: Verdana, Geneva, Tahoma, sans-serif;
  text-align: left;
}
</style>