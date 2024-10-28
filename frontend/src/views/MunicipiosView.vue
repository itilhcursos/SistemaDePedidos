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
/* Fundo com gradiente azulado */
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
  /* Fundo escuro com transparência */
  border-radius: 10px;
  /* Bordas arredondadas */
  padding: 1.5rem;
  /* Espaçamento interno */
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  /* Sombra para efeito elevado */
  max-width: 92%;
  /* Para manter a largura dentro do limite */
  width: 100%;
  /* Largura total */
  transform: translateY(20px);
  animation: slideUp 0.6s ease-in-out forwards;
}

/* Título personalizado */
.header-title {
  color: #f0f4f8;
  /* Cor de texto clara */
  font-size: 2rem;
  /* Tamanho do título */
  font-weight: 700;
  /* Peso da fonte */
  text-align: center;
  margin-bottom: 1.5rem;
}

/* Animação para efeito de subida */
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

/* Responsividade para dispositivos móveis */
@media (max-width: 768px) {
  .card-container {
    padding: 1.5rem;
    /* Mantém padding adequado */
  }

  .header-title {
    font-size: 1.6rem;
    /* Ajusta tamanho do título para dispositivos móveis */
  }
}
</style>
