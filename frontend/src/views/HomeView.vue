<script setup>
import TelaInicial from "../components/TelaInicial.vue";
</script>

<template>
  <main style="position: relative; min-height: 100vh;">
    <TelaInicial msg="Sales" msg2="System " />
    <h1 class="titulo-inferior">El Produções de Software</h1>
    
    <button class="btn-cadastre-se">Cadastre-se</button>
  </main>
</template>


<style>
.titulo-inferior {
  position: absolute;
  bottom: 0px;
  
  left: 0px;
 
  font-family: 'Roboto', sans-serif;
  font-size: 24px;
  color: #800020;
 
  padding: 10px;
  
}

.btn-cadastre-se {
  position: absolute;
  top: 30;
  left: 5;
  padding: 12px 24px;
  background-color: #008CBA;
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
}

.btn-cadastre-se:hover {
  background-color: #005f7a;
}
</style>
