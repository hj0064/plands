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
            <router-link
              v-if="authStore.role==='ROLE_ADMIN'"
              to="/admin"
              class="nav-link"
              :class="{active: isActive('/admin')}"
            >Admin</router-link>
          </nav>
        </div>
        <div>
          <nav class="nav">
            <div>
              <img
                v-if="isLoggedIn"
                :src="displayedProfileImage"
                :key="displayedProfileImage"
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

const router = useRouter()
const route = useRoute()

const authStore = useAuthStore()
const isLoggedIn = computed(() => authStore.isLoggedIn)

const serverBaseUrl = 'http://localhost:8081'

const profileImageUrl = computed(() => authStore.profileImageUrl)
const displayedProfileImage = computed(() => {
  if (profileImageUrl.value) {
    return `${serverBaseUrl}${profileImageUrl.value}?t=${new Date().getTime()}`;
  }
  return defaultProfile;
});

const isActive = (targetRoute) => route.path === targetRoute

const redirectToLogin = () => {
  router.push('/login')
}
const moveMypage = () => {
  router.push('/mypage')
}
</script>

<style scoped src="@/assets/styles/header.css"></style>
