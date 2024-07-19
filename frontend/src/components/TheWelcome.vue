<script setup>
import WelcomeItem from './WelcomeItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'
import CommunityIcon from './icons/IconCommunity.vue'
import SupportIcon from './icons/IconSupport.vue'
</script>

<template>
  <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>Orientações para Compras</template>
    Bem-vindo ao Sistema de Pedidos...
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Ferramentas</template>

    Este sistema permite o cadastro de um novo produto na
    <a href="/listaRefatorada" target="_blank" rel="noopener">Lista de Compra</a>. 
    Podemos contar com o cadastro da 
    <a href="/estados" target="_blank" rel="noopener">Lista de Estados</a> para 
    inclusão e exclusão de novos registros na base de dados.
    <br />

  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Ecossistema</template>

   O sistema de Pedidos conta com diversas tecnologias embarcadas no projeto:
    <a href="https://getbootstrap.com/" target="_blank" rel="noopener">Bootstrap</a>,
    <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue.js</a>,
    <a href="https://www.javascript.com/" target="_blank" rel="noopener">JavaScript</a> e
    <a href="https://www.oracle.com/br/java/" target="_blank" rel="noopener">Java</a>. Além
    de diversas outras aplicações e tecnologias agregadas ao desenvolvimento do sistema.

  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Comunidade</template>

    Ficou alguma dúvida? Em breve estaremos disponibilizando nossos canais de
    comunicação para falar com os desenvolvedores responsáveis pelo projeto.
    <!-- <a href="https://chat.vuejs.org" target="_blank" rel="noopener">Vue Land</a>, our official
    Discord server, or
    <a href="https://stackoverflow.com/questions/tagged/vue.js" target="_blank" rel="noopener"
      >StackOverflow</a
    >. You should also subscribe to
    <a href="https://news.vuejs.org" target="_blank" rel="noopener">our mailing list</a> and follow
    the official
    <a href="https://twitter.com/vuejs" target="_blank" rel="noopener">@vuejs</a>
    twitter account for latest news in the Vue world. -->
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Suporte</template>

   Este é um projeto para aprendizado e estudo, o Sistema de Pedidos conta com o apoio dos participantes do
   curso e seu instrutor.
    <a href="https://vuejs.org/sponsor/" target="_blank" rel="noopener"></a>
  </WelcomeItem>
</template>
