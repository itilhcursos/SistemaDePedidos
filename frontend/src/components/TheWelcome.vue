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
    <template #heading>Orientações</template>

    Bem-vindo a lista de compras, abaixo temos umas opções de melhor entendimento do nosso Software!
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Ferramentas</template>
    Obtenha ferramentas e bibliotecas oficiais para seu projeto, clique aqui 
        <a href="https://vitejs.dev/guide/features.html" target="_blank" rel="noopener">Vite</a>. 
    Se precisar de mais recursos, sugerimos que você faça uma visita ao
        <a href="https://www.cypress.io/" target="_blank" rel="noopener">Cypress</a>
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Ecossistemas</template>

    Segue nossa listas de Ecossistemas integrado de sistemas:
  <ol>
  <li>Sistema Compras</li>
  <li>Sistemas NF-e</li>
  <li>Sistema de Almoxarifado</li>
  </ol>
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Comunidade</template>

    Segue a nossa Comunidades dos nossos Clientes e com o nosso forúm do sistema.
    <a href="https://web.telegram.org/k/" target="_blank" rel="noopener">@Telegram</a>
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Suporte Técnico</template>

    Precisando de Suporte do nosso sistema, favor entrar em contato clicando aqui
    <a href="https://web.whatsapp.com/" target="_blank" rel="noopener">@whatsapp</a>
  
  </WelcomeItem>
</template>
