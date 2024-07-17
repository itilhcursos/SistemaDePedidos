<script setup>
import WelcomeItem from './WelcomeItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import CommunityIcon from './icons/IconCommunity.vue'
</script>

<template>
  <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>Orientações</template>

    Bem-vindo(a) à lista de compras... <br>
    Abaixo temos algumas tecnologias que foram utilizadas no projeto e algumas outras orientações. <br>
    Do lado esquerdo é possível navegar entre as páginas e explorar ainda mais as funcionalidades desse projeto. xD
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Ferramentas</template>

    Este projeto tem como sua principal linguagem o
    <a href="https://adoptium.net/download/" target="_blank" rel="noopener">Java</a>, utilizada no backend do sistema.
    <br>
    Os Frameworks e Bibliotecas utilizados: 
    <a href="https://spring.io/projects/spring-boot" target="_blank" rel="noopener">Spring Boot</a>, 
    <a href="https://spring.io/projects/spring-data-jpa" target="_blank" rel="noopener">Spring Data JPA</a> e 
    <a href="https://getbootstrap.com/" target="_blank" rel="noopener">Bootstrap</a>.
    <br>
    Como banco de dados utilizamos o 
    <a href="https://www.postgresql.org/download/" target="_blank" rel="noopener">PostgreSQL</a>.
    <br />
    Utilizado como ferramenta de build e gerenciamento de dependências utilizamos o 
    <a href="https://maven.apache.org/download.cgi" target="_blank" rel="noopener">Maven</a>.
    <br>
    Para realizar o controle de versão estamos utilizando o 
    <a href="https://git-scm.com/download/win" target="_blank" rel="noopener">Git</a> e o 
    <a href="https://github.com/" target="_blank" rel="noopener">GitHub</a>.
    <br>
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Comunidade</template>

    Não temos nenhuma comunidade pública atualmente, apenas o repositório do projeto no
    <a href="https://github.com/itilhcursos/SistemaDePedidos" target="_blank" rel="noopener">GitHub</a>
  </WelcomeItem>
</template>
