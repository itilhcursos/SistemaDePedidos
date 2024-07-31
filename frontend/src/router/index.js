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
      name: 'produtos',
      component: () => import('../views/ProdutosView.vue')
    },
    {

    }
  ]
})

export default router
