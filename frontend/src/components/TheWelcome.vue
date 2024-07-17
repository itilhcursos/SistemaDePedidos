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
    <template #heading>Sobre a criação da Fundação Speedwagon</template>
A história da Fundação Speedwagon começa em 1938 quando Robert Speedwagon viajou aos Estados Unidos em busca de oportunidades,onde não encontrou em primeira vista,depois de alguns meses perambulando em estado e estado dos Estados Unidos, ele incrivelmente encontrou petróleo no deserto de Nevada, após enriquecer repentinamente, ele fundou a poderosa Fundação Speedwagon em 1939. 
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
  <template #heading>Descubra o que a Fundação Speedwagon faz</template>

    Ciências médicas: Abrange no desenvolvimento e na produção de medicamentos globais.
   <br> Ocultismo: Criação de estudos e na contenção de eventos sobrenaturais. <br>


  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Sede da Fundação Speedwagon</template>
    Estamos situados no Estados Unidos no Estado da Califórnia.
    
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Como entra em contato</template>

    Email:Speedwagon_Fundação@gmail.com
    <br>Telefone:(001) 468.875.189 <br>
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Público de Interesse</template>
    Somos uma empresa com atividades de grande porte ao redor do mundo e transformamos a vida de milhões de pessoas. E dentro da nossa atuação, estamos sempre em contato com diferentes públicos, com interesses diversos. Por isso, buscamos entender quem são eles, suas opiniões, expectativas, percepções e necessidades.
  
  </WelcomeItem>
</template>
<style>

</style>


