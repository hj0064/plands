import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import SignUpView from "@/views/SignUpView.vue";
import OAuth2RedirectHandler from '@/views/OAuth2RedirectHandler.vue';
import HomeView from '@/views/HomeView.vue'
import MyPageView from '@/views/MyPageView.vue'
import FaqView from '@/views/FaqView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
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
    },
    {
      path: '/mypage',
      name: 'MyPageView',
      component: MyPageView
    },
    {
      path: '/faq',
      name: 'FaqView',
      component: FaqView
    },
  ],
})

export default router
