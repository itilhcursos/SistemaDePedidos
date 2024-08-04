<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <div class="cardNotFound">
    <figure><img src="../assets/images/erro404.png" alt="" /></figure>
    <div>
      <h2>Página não encontrada</h2>
      <p>Infelizmente não foi possível encontrar a página solicitada!</p>
    </div>
  </div>
</template>

<style scoped>
div.cardNotFound {
    border: none;
  background-color: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5em;
  min-height: 100%;
  width: 70%;
  margin: 10em auto;
  padding: 3em 0;

  & figure {
    width: 22em;

    & img {
      width: 18em;
    }
  }
  & > div h2 {
    color: var(--white3);
    font-weight: 600;
    font-size: 3em !important;

    & b {
      color: var(--yellowDebts);
    }
  }
  & > div p {
    color: var(--cleanGrey);
    font-weight: 600;
    font-size: 1.2em;
    width: 80%;
  }
}
</style>