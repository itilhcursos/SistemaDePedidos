<script setup>
import ListarPedido from '../components/ListarPedido.vue'
</script>

<template>
  <main class="main-container">
    <header>
      <h2 class="page-title">Lista de Pedidos</h2>
    </header>
    <section class="content">
      <ListarPedido />
    </section>
  </main>
</template>

<style scoped>
.main-container {
  background-color: #23232e;
  border: 3px solid #031320;
  border-radius: 8px;
  padding: 10px 15px;
  max-width: 90%;
  margin: 0 auto;
}

.page-title {
  color: #f0f0f0;
  font-size: 1.5em;
  text-align: center;
  margin-bottom: 15px;
}

.content {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
</style>
