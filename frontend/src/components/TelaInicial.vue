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
    <h2 >{{ msg2 }}</h2>
    <div><img src="../assets/carrinho.png" alt=""></div>
    
  </div>
</template>

<style scoped>
.cor {
  color: rgb(255, 174, 0)
}
img{
  margin: auto;
  display: block;
}

h1 {
  font-weight: 400;
  font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  font-size: 5rem;
  position: relative;
  align-content: center;
  margin:0;
  text-align: center;
}

h2 {
  position: relative;
  font-size: 70px;
  font-family: serif;
  text-align: center;
}


</style>