
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
      Navegação intuitiva e amigável, com descrições detalhadas para facilitar as decisões de compra. Oferecemos diversas opções de pagamento seguras para atender a todos os perfis de clientes. Saiba mais acessando as<a href="/">Orientações.</a>
    </p>
  </SobreItem>

  <SobreItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Ferramentas</template>

    Integre sua loja com as melhores plataformas: Shopify, WooCommerce, e Magento. Utilize gateways de pagamento como PayPal e PagSeguro, ferramentas de gestão de estoque, CRM, e soluções de marketing digital como Google Analytics e Facebook Ads.
  </SobreItem>

  <SobreItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Ecosistema</template>

    Nosso ecossistema é robusto, com foco em design, usabilidade e responsividade para uma experiência de compra perfeita. Conecte-se com diversas plataformas de e-commerce e leve seu negócio ao próximo nível. Saiba mais sobre o<a href="/">Ecosistema Sys Vendas.</a>
  </SobreItem>

  <SobreItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Comunidade</template>

    Participe da comunidade<a href="/">SysVendas,</a>troque experiências, obtenha dicas e avaliações, ou tire dúvidas com outros usuários em nossos fóruns e grupos sociais exclusivos.
  </SobreItem>

  <SobreItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Suporte</template>

    Oferecemos suporte dedicado via redes sociais, como Facebook, WhatsApp, e Instagram, além de atendimento telefônico no (00) 99191-1199. Conte com o Sys Vendas para ajudar sempre que precisar.
  </SobreItem>
</template>


<style scoped>
 body{
  color: var();
  
 } 
</style>
