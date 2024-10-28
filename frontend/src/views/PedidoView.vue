<script setup>
import ListarPedido from '../components/ListarPedido.vue'
</script>

<template>
  <main class="main-wrapper">
    <div class="card-container">
      <header class="header">
        <h1 class="header-title">Gerenciamento de Pedidos</h1>
      </header>
      <ListarPedido />
    </div>
  </main>
</template>

<style scoped>
/* Container principal*/
.main-wrapper {
  background-color: #121212;
  /* Fundo escuro */
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 1.5rem;
  /* Padding ao redor do cartão */
}

/* Cartão de conteúdo */
.card-container {
  background-color: #1f1f2e;
  /* Fundo escuro com contraste suave */
  border-radius: 16px;
  /* Bordas arredondadas */
  padding: 1.5rem;
  /* Padding para conteúdo */
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  /* Sombra para efeito elevado */
  max-width: 600px;
  /* Largura máxima ajustada */
  width: 100%;
  /* Largura total */
  transform: translateY(20px);
  animation: slideUp 0.6s ease-in-out forwards;
  /* Animação suave */
}

/* Título */
.header-title {
  color: #e2e2e2;
  /* Cor do texto claro */
  font-size: 1.8rem;
  /* Tamanho da fonte ajustado */
  font-weight: 700;
  /* Peso da fonte */
  text-align: center;
  /* Centraliza o texto */
  margin-bottom: 1.5rem;
  /* Margem inferior */
}

/* Animação para o efeito de subida */
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

/* Ajustes para dispositivos móveis */
@media (max-width: 768px) {
  .card-container {
    padding: 1rem;
    /* Padding ainda menor em dispositivos móveis */
  }

  .header-title {
    font-size: 1.5rem;
    /* Tamanho do título ajustado */
  }
}
</style>
