<template>
  <header class="header">
    <div class="container">
      <div class="logo-container">
        <img :src="logoIcon" alt="logoIcon" class="logo-icon" />
        <p class="logo">Plands</p>
      </div>
      <div class="menu-container">
        <div>
          <nav class="nav">
            <router-link to="/calendar" class="nav-link" :class="{ active: isActive('/calendar') }">Calendar</router-link>
            <router-link to="/plant" class="nav-link" :class="{ active: isActive('/plant') }">Plant</router-link>
            <router-link to="/community" class="nav-link" :class="{ active: isActive('/community') }">Community</router-link>
            <router-link to="/notice" class="nav-link" :class="{ active: isActive('/notice') }">Notice</router-link>
            <router-link to="/faq" class="nav-link" :class="{ active: isActive('/faq') }">Faq</router-link>
          </nav>
        </div>
        <div>
          <nav class="nav">
            <div>
              <!-- 프로필 아이콘 -->
              <img
                v-if="isLoggedIn"
                :src="displayedProfileImage"
                alt="profile"
                class="icon dropdown-button"
                @click="moveMypage"
              />
              <button v-else @click="redirectToLogin" class="login-button">Login</button>
            </div>
          </nav>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { computed } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import logoIcon from "@/assets/images/icon/logo-icon.png"
import defaultProfile from '@/assets/images/icon/profile-default.png'

// 라우터
const router = useRouter()
const route = useRoute()

// auth 상태
const authStore = useAuthStore()
const isLoggedIn = computed(() => authStore.isLoggedIn)

// 서버 베이스 URL
const serverBaseUrl = 'http://localhost:8081'

// 프로필 이미지 URL
const profileImageUrl = computed(() => authStore.profileImageUrl) // <- authStore에 있는 걸 가정
const displayedProfileImage = computed(() =>
  profileImageUrl.value ? serverBaseUrl + profileImageUrl.value : defaultProfile
)

// 현재 경로와 일치하는지 확인
const isActive = (targetRoute) => route.path === targetRoute

// 페이지 이동
const redirectToLogin = () => {
  router.push('/login')
}
const moveMypage = () => {
  router.push('/mypage')
}
</script>

<style scoped src="@/assets/styles/header.css"></style>
