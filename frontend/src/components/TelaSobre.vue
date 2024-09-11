
<script setup>
import SobreItem from './SobreItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'
import CommunityIcon from './icons/IconCommunity.vue'
import SupportIcon from './icons/IconSupport.vue'
</script>

<template>
  <SobreItem class="n1">
    <template #icon>
      <DocumentationIcon />
    </template>
      <template #heading>Orientações</template>
      
    <p>
      <br>
      Navegação intuitiva e de fácil utilização, proporcionando uma experiência agradável ao usuário. 
      <br>
      Descrições detalhadas dos produtos, auxiliando os clientes na tomada de decisões de compra informadas.
      <br>
      Opções de pagamento seguras e diversificadas, atendendo às diferentes preferências dos consumidores
      <br>
      e muitas outras. acesse e veja mais <a href="/">Orientações</a>

    </p>
  </SobreItem>

  <SobreItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Ferramentas</template>

    Plataforma de e-commerce (ex: Shopify, Magento, WooCommerce)
    <br>
    Gateway de pagamento (ex: PayPal, PagSeguro)
    <br>
    Sistema de gestão de estoque
    CRM (Customer Relationship Management)
    <br>
    Ferramentas de marketing digital (ex: Google Analytics, Facebook Ads)
    e muitos outros.
  </SobreItem>

  <SobreItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Ecosistema</template>

    Plataforma de e-commerce: é a base da loja virtual, onde os produtos são exibidos,
    os pedidos são processados e os pagamentos são realizados. Existem diversas opções de 
    plataformas disponíveis, como Shopify, WooCommerce e Magento.
   
  </SobreItem>

  <SobreItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Comunidade</template>

    Acesse e interaja com a Comunidade do Sys Vendas, para interagir acesse <a href="/">SysVendas.com.br</a>. 
    Os membros desta comunidade interagem entre si, compartilham experiências, dicas, avaliações de produtos,
    feedbacks, além de tirarem dúvidas uns dos outros. A loja online pode criar um espaço específico para esta comunidade, 
    como fóruns de discussão, grupos em redes sociais, e-mails exclusivos, entre outros.
  </SobreItem>

  <SobreItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Suporte</template>

   Tenha o Suporte do Sys Vendas, pelas Redes Sociais, facebook, whatsapp, instagram e outros. <br>
   Tenha acesso tambem pelo Numero de Contato (27) 99229-6187 ou 3268-1404.
  </SobreItem>
</template>

<style scoped>
 body{
  color: var();
  
 } 
</style>
