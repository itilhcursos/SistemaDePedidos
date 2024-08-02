import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/SobreView.vue')
    },
    {
      path: '/estados',
      name: 'estados',
      component:()=>import('../views/EstadosView.vue')
    },
    {
      path: '/produtos',
      nome: 'produtos',
      component:()=>import('../views/ProdutoView.vue')
    },
    {
      path: '/formas-pagamento',
      nome: 'formaPagamento',
      component:()=>import('../views/FormaPagamentoView.vue')
    }

  ]
})

export default router
