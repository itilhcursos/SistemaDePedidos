<script setup>
defineProps({
  msg: {
    type: String,
    required: true
  },
  msg2: {
    type: String,
    required: true
  },
  msg3: {
    type: String,
    required: true
  }
})
</script>

<template>
  <div>
    <h1 class="cor"><b>{{ msg }}</b></h1>
    <h2><b>{{ msg2 }}</b>  </h2>
    <h3 class="itilh"><i><b>{{ msg3 }}</b></i></h3>
    <div><img src="../assets/carrinho.png" alt=""></div>
    
  </div>
</template>

<style scoped>
.cor {
  color: rgb(190, 48, 98)
}
.itilh {
  color: rgb(173, 190, 245)
}
img{
  margin: auto;
  display: block;
}

h1 {
  font-weight: 400;
  font-family: sans-serif;
  font-size: 5rem;
  position: relative;
  align-content: center;
  margin:0;
  text-align: center;
}

h2 {
  position: relative;
  font-size: 70px;
  font-family: sans-serif;
  text-align: center;
}

h3 {
  position: relative;
  font-size: 70px;
  font-family: sans-serif;
  text-align: center;
}

</style>