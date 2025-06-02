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
            <div class="">
              <!-- 프로필 아이콘 -->
              <img v-if="isLoggedIn" :src="profile" alt="profile" class="icon dropdown-button" @click="moveMypage" />
              <button v-else @click="redirectToLogin" class="login-button">Login</button>
            </div>
          </nav>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { useAuthStore } from '@/stores/authStore'
import logoIcon from "@/assets/images/icon/logo-icon.png"
import profile from "@/assets/images/icon/profile-default.png"

export default {
  name: "AppHeader",
  data() {
    return {
      logoIcon,
      profile
    };
  },
  computed: {
    isLoggedIn() {
      const authStore = useAuthStore();
      return authStore.isLoggedIn;
    }
  },
  methods: {
    isActive(route) {
      return this.$route.path === route;
    },
    redirectToLogin() {
      this.$router.push('/login');
    },
    moveMypage() {
      this.$router.push('/mypage');
    },
  }
};
</script>

<style scoped src="@/assets/styles/header.css"></style>
