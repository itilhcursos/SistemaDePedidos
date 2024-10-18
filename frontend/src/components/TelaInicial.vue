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
  <div class="container">
    <div class="content">
      <h1 class="title">{{ msg }}</h1>
      <h2 class="subtitle">{{ msg2 }}</h2>
      <div class="image-container">
        <img src="../assets/carrinho.png" alt="Carrinho de compras" class="image">
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Paleta de cores com tons suaves e elegante */
:root {
  --primary-color: #00796b;
  --secondary-color: #fafafa;
  --accent-color: #d32f2f;
  --font-family: 'Inter', sans-serif;
  --transition-duration: 0.4s;
}

body {
  margin: 0;
  font-family: var(--font-family);
  background-color: var(--secondary-color);
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.content {
  text-align: center;
  padding: 2rem;
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  animation: slideIn 0.8s ease;
  max-width: 400px;
  width: 100%;
}

.title {
  color: var(--primary-color);
  font-size: 3.5rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  transition: color var(--transition-duration), transform var(--transition-duration);
}

.subtitle {
  color: var(--accent-color);
  font-size: 2rem;
  font-weight: 400;
  margin-bottom: 2rem;
  transition: color var(--transition-duration), transform var(--transition-duration);
}

.image-container {
  position: relative;
  width: 120px;
  height: 120px;
  margin: 0 auto;
  border: 4px solid var(--primary-color);
  border-radius: 50%;
  overflow: hidden;
  transition: border var(--transition-duration), transform var(--transition-duration);
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-duration), box-shadow var(--transition-duration);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

/* Hover effects suaves */
.title:hover, .subtitle:hover {
  color: var(--accent-color);
  transform: scale(1.05);
}

.image-container:hover {
  transform: scale(1.1);
  border-color: var(--accent-color);
}

.image-container:hover .image {
  transform: scale(1.1);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

/* Animação de entrada */
@keyframes slideIn {
  from {
    transform: translateY(50px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
