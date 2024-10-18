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
/* Definindo uma paleta de cores mais suave e uma tipografia moderna */
:root {
  --primary-color: #00bfa6;
  --secondary-color: #f7f7f7;
  --accent-color: #ff6f61;
  --font-family: 'Poppins', sans-serif;
}

body {
  background-color: var(--secondary-color);
  margin: 0;
  font-family: var(--font-family);
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: var(--secondary-color);
}

.content {
  text-align: center;
  padding: 2rem;
  background: #ffffff;
  border-radius: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  animation: fadeInUp 0.8s ease-out;
}

.title {
  color: var(--primary-color);
  font-size: 4rem;
  font-weight: 700;
  letter-spacing: -1px;
  margin-bottom: 0.5rem;
  transition: color 0.3s ease;
}

.subtitle {
  color: var(--accent-color);
  font-size: 2.5rem;
  font-weight: 500;
  margin-bottom: 1.5rem;
  transition: color 0.3s ease;
}

.image-container {
  position: relative;
  width: 150px;
  height: 150px;
  margin: 0 auto;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: contain;
  border-radius: 50%;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
}

.image:hover {
  transform: scale(1.1);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
}

/* Animação de entrada suave */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Animação ao passar o mouse sobre os títulos */
.title:hover, .subtitle:hover {
  color: var(--accent-color);
}
</style>


