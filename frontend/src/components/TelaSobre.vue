
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
      navegação intuitiva e fácil de usar <br>
      <br>
      descrições detalhadas para ajudar os clientes a tomar uma decisão de compra.
      <br>
      pagamento seguras e variadas para atender às preferências dos clientes.
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
  <br>
    Design e usabilidade do site: uma boa experiência do usuário é essencial para atrair e reter clientes.
    O design do site, a navegação fácil e a responsividade são aspectos importantes a serem considerados.
    acesse e saiba mais sobre o ecossistema do Sys Vendas.  
  </SobreItem>

  <SobreItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Comunidade</template>

    Acesse e interaja com a Comunidade do <a href="/">Sys Vendas</a> 
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
   Tenha acesso tambem pelo Numero de Contato (xx) xxxxxxxxxxx.
  </SobreItem>
</template>

<style scoped>
 body{
  color: var();
  
 } 
</style>
