<script setup>
import ListarMunicipio from '../components/ListarMunicipio.vue'
</script>

<template>
  <main class="main-wrapper">
    <div class="card-container">
      <header class="header">
        <h1 class="header-title">Gerenciamento de Municípios</h1>
      </header>
      <ListarMunicipio />
    </div>
  </main>
</template>

<style scoped>
.main-wrapper {
  background: linear-gradient(135deg, #1b2735, #415a77, #6c91c2);
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 1.5rem;
}


.card-container {
  background-color: rgba(31, 31, 46, 0.95);

  border-radius: 10px;

  padding: 1.5rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);

  max-width: 92%;

  width: 100%;

  transform: translateY(20px);
  animation: slideUp 0.6s ease-in-out forwards;
}


.header-title {
  color: #f0f4f8;

  font-size: 2rem;

  font-weight: 700;

  text-align: center;
  margin-bottom: 1.5rem;
}


@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}


@media (max-width: 768px) {
  .card-container {
    padding: 1.5rem;

  }

  .header-title {
    font-size: 1.6rem;

  }
}
</style>
