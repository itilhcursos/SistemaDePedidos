<template>
  <div class="about">
    <h1 class="titulo">Sistema de Compras ITILH</h1>
    <p class="underline">Sistema de Compras desenvolvido no curso WEB da instituição ITILH e utilizado como desafio de design.</p>
  </div>
  
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;

  }
.underline {
  text-decoration: underline;
  margin-left: 20px;
  font-size: 1.8rem;;
  text-align: center;
  margin-top: 50px;
  font-weight: 600;
}
.titulo {
z-index: 1;
position: absolute;
margin-bottom: 250px;
color: #3F8DBF;
font-weight: 1000;
font-size: 2.4rem;
}
}
</style>
