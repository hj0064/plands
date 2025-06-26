import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/authStore.js'

import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import SignUpView from '@/views/SignUpView.vue'
import OAuth2RedirectHandler from '@/views/OAuth2RedirectHandler.vue'
import MyPageView from '@/views/MyPageView.vue'
import FaqView from '@/views/FaqView.vue'
import NoticeListView from '@/views/NoticeListView.vue'
import NoticeDetailView from '@/views/NoticeDetailView.vue'
import AdminDashboard from '@/views/admin/AdminDashboard.vue'
import UserManagementView from '@/views/admin/UserManagementView.vue'
import NotFoundView from '@/views/NotFoundView.vue'

const publicRoutes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: { layout: 'default' }
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { layout: 'default' }
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignUpView,
    meta: { layout: 'default' }
  },
  {
    path: '/oauth2/redirect',
    name: 'oauth2Redirect',
    component: OAuth2RedirectHandler,
    meta: { layout: 'default' }
  },
  {
    path: '/mypage',
    name: 'mypage',
    component: MyPageView,
    meta: { layout: 'default' }
  },
  {
    path: '/faq',
    name: 'faq',
    component: FaqView,
    meta: { layout: 'default' }
  },
  {
    path: '/notice',
    name: 'noticeList',
    component: NoticeListView,
    meta: { layout: 'default' }
  },
  {
    path: '/notice/:id',
    name: 'noticeDetail',
    component: NoticeDetailView,
    meta: { layout: 'default' }
  },
]

const adminRoutes = {
  path: '/admin',
  meta: { layout: 'admin' },
  beforeEnter: (to, from, next) => {
    const authStore = useAuthStore()
    if (authStore && authStore.role === 'ROLE_ADMIN') {
      next()
    } else {
      next('/')
    }
  },
  children: [
    {
      path: '',
      name: 'adminDashboard',
      component: AdminDashboard,
      meta: { layout: 'admin' }
    },
    {
      path: 'users',
      name: 'userManagement',
      component: UserManagementView,
      meta: { layout: 'admin'}
    }
  ]
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...publicRoutes,
    adminRoutes,
    // 404 페이지 추가
    {
      path: '/:pathMatch(.*)*',
      name: 'notFound',
      component: NotFoundView, // 또는 직접 컴포넌트 import
      meta: { layout: 'default' }
    }
  ]
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const token = authStore.token

  const publicPaths = ['/', '/login', '/signup','/oauth2/redirect']
  const isPublic = publicPaths.some(publicPath => to.path.startsWith(publicPath))

  if (!token && !isPublic) {
    return next('/login')
  }

  next()
})

export default router
