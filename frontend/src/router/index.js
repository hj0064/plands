import { createRouter, createWebHistory } from 'vue-router'
import BoardPage from '../views/BoardPage.vue'
import LoginView from '../views/LoginView.vue'
import SignUpView from "@/views/SignUpView.vue";
import OAuth2RedirectHandler from '@/views/OAuth2RedirectHandler.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'sample',
      component: BoardPage,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUpView,
    },
    {
      path: '/oauth2/redirect',
      name: 'OAuth2RedirectHandler',
      component: OAuth2RedirectHandler
    }
  ],
})

export default router
