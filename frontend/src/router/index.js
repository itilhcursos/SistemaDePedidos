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
      name: 'estado',
      component:()=>import('../views/EstadosView.vue')
    },
    {
      path: '/produtos',
      name: 'produto',
      component:()=>import('../views/ProdutoView.vue')
    },
    {
      path: '/formas-pagamento',
      name: 'formaPagamento',
      component:()=>import('../views/FormaPagamentoView.vue')
    },
    {
      path: '/municipios',
      name: 'municipio',
      component:()=>import('../views/MunicipioView.vue')
    },
    {
      path: '/clientes',
      name: 'cliente',
      component:()=>import('../views/ClienteView.vue')
    },
    {
      path: '/pedidos',
      name: 'pedido',
      component:()=>import('../views/PedidoView.vue')
    },
    {
      path: '/registro',
      name: 'registro',
      component:()=>import('../views/RegistroView.vue')
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