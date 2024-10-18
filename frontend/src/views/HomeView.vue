<script setup>
import TelaInicial from "../components/TelaInicial.vue";
</script>

<template>
  <main class="main-container">
    <div class="content">
      <TelaInicial msg="Gestor de Vendas" msg2="Simplifique suas transações" />
      <button class="login-button">Login</button>
    </div>
  </main>
</template>

<style scoped>
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, rgba(0, 198, 255, 0.2), rgba(0, 114, 255, 0.2));
  position: relative;
  overflow: hidden;

}

.main-container::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #00c6ff, #0072ff);
  opacity: 0.1;

  z-index: 0;
  filter: blur(8px);

}


.content {
  z-index: 1;
  color: white;
  text-align: center;
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.4);

  border-radius: 12px;

  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);

  backdrop-filter: blur(5px);

}


.login-button {
  position: absolute;
  bottom: 20px;
  left: 20px;
  padding: 10px 20px;
  font-size: 1rem;
  color: white;
  background-color: rgba(0, 114, 255, 0.8);
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
  z-index: 2;
}

.login-button:hover {
  background-color: rgba(0, 114, 255, 1);
}


body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
  background: linear-gradient(135deg, #ff4e50, #f9d423);
  background-image: url('../assets/marca-dagua.png');

  background-size: cover;

  background-repeat: no-repeat;
  background-position: center center;
  opacity: 0.7;

}


@media (max-width: 768px) {
  .content {
    padding: 15px;
    font-size: 0.9rem;
  }
}
</style>
