<script setup>
import ListarEstado from "../components/ListarEstado.vue";
</script>

<template>
  <main class="main-wrapper">
    <div class="card-container">
      <header class="header">
        <h1 class="header-title">Gerenciamento de Estados</h1>
      </header>
      <ListarEstado />
    </div>
  </main>
</template>

<style scoped>
/* Container principal para centralização e estilo da página */
.main-wrapper {
  background-color: #121212;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 1.5rem;
}

/* Card de conteúdo centralizado */
.card-container {
  background-color: #1f1f2e;
  border-radius: 16px;
  padding: 2rem 2.5rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  max-width: 800px;
  width: 100%;
  transform: translateY(20px);
  animation: slideUp 0.6s ease-in-out forwards;
}

/* Estilo do título do card */
.header-title {
  color: #e2e2e2;
  font-size: 2rem;
  font-weight: 700;
  text-align: center;
  margin-bottom: 1.5rem;
}

/* Animação de transição do card */
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

/* Estilos responsivos para dispositivos menores */
@media (max-width: 768px) {
  .card-container {
    padding: 1.5rem;
  }

  .header-title {
    font-size: 1.6rem;
  }
}
</style>
