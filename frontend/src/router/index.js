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
      path: '/municipios',
      name: 'municipios',
      component:()=>import('../views/MunicipioView.vue')
    },
    {
      path: '/produtos',
      name: 'produtos',
      component:()=>import('../views/ProdutoView.vue')
    },
    {
      path: '/formas-pagamento',
      name: 'formaPagamento',
      component:()=>import('../views/FormaPagamentoView.vue')
    },
    // {
    //   path: '/clientes',
    //   name: 'clientes',
    //   component:()=>import('../views/ClienteView.vue')
    // },
    {
      path: '/pedidos',
      name: 'pedidos',
      component:()=>import('../views/PedidoView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component:()=>import('../views/LoginView.vue')
    },
    {
      path: '/logout',
      name: 'logout',
      component:()=>import('../views/LogoutView.vue')
    }
  ]
})

export default router
