<script setup>
import SobreItem from './SobreItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'
import CommunityIcon from './icons/IconCommunity.vue'
import SupportIcon from './icons/IconSupport.vue'
</script>

<template>
  <div class="sobre-container">
    <SobreItem class="n1">
      <template #icon>
        <DocumentationIcon />
      </template>
      <template #heading>Orientações</template>
      <p>
        Navegação intuitiva para uma experiência de usuário excepcional.
        Oferecemos descrições detalhadas dos produtos, ajudando na sua decisão de compra.
        Explore nossas opções de pagamento seguras e diversificadas.
        Acesse <a href="/">Orientações</a> para mais informações.
      </p>
    </SobreItem>

    <SobreItem>
      <template #icon>
        <ToolingIcon />
      </template>
      <template #heading>Ferramentas</template>
      <p>
        Utilize plataformas de e-commerce como Shopify, Magento ou WooCommerce.
        Integre gateways de pagamento como PayPal e PagSeguro, além de sistemas de gestão de estoque e CRM.
        Aproveite também ferramentas de marketing digital, como Google Analytics e Facebook Ads.
      </p>
    </SobreItem>

    <SobreItem>
      <template #icon>
        <EcosystemIcon />
      </template>
      <template #heading>Ecosistema</template>
      <p>
        Sua loja virtual é suportada por plataformas robustas, onde os produtos são exibidos e os pedidos são
        processados.
        Opções populares incluem Shopify, WooCommerce e Magento.
      </p>
    </SobreItem>

    <SobreItem>
      <template #icon>
        <CommunityIcon />
      </template>
      <template #heading>Comunidade</template>
      <p>
        Junte-se à Comunidade Sys Vendas em <a href="/">SysVendas.com.br</a>.
        Interaja com outros membros, compartilhe experiências e obtenha feedback sobre produtos.
        Participe de fóruns, grupos em redes sociais e muito mais!
      </p>
    </SobreItem>

    <SobreItem>
      <template #icon>
        <SupportIcon />
      </template>
      <template #heading>Suporte</template>
      <p>
        O suporte Sys Vendas está ao seu alcance! Entre em contato via redes sociais (Facebook, WhatsApp, Instagram)
        ou ligue para (27) 99229-6187 ou (27) 3268-1404 para assistência imediata.
      </p>
    </SobreItem>
  </div>
</template>

<style scoped>
.sobre-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
}

p {
  color: #333;
  line-height: 1.6;
}


.n1 {
  border: 1px solid rgba(0, 114, 255, 0.5);
  border-radius: 12px;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 15px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .sobre-container {
    padding: 10px;
  }
}
</style>
