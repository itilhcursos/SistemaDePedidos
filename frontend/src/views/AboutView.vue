<script setup>
  import TheWelcome from '../components/TheWelcome.vue'
</script>
<template>
  <div class="about">

    <main>
    <h1>Compreendendo o Sistema</h1>
    <TheWelcome />
  </main>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
h1{
    color:#ffbb00;
    
}


</style>
