<template>
  <header class="header">
    <div class="header-left">
      <button @click="layoutStore.toggleSidebar" class="menu-button">
        <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"/>
        </svg>
      </button>
      <div class="search-wrap">
        <input type="text" class="search-input" placeholder="Search...">
        <span class="search-icon">
          <svg class="icon-small" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"/>
          </svg>
        </span>
      </div>
    </div>

    <div class="header-right">
      <div class="notifications">
        <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"/>
        </svg>
        <span class="notification-badge">3</span>
      </div>
      <div class="user-info">
        <img
          v-if="isLoggedIn"
          :src="displayedProfileImage"
          :key="displayedProfileImage"
          alt="profile"
          class="user-avatar"
          @click="moveMypage"
        />
        <span class="user-name">{{ authStore.nickname }}</span>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useLayoutStore } from '@/stores/layout'
import { useAuthStore } from '@/stores/authStore.js'
import { useRouter } from 'vue-router'
import { computed } from 'vue'
import defaultProfile from '@/assets/images/icon/profile-default.png'

const layoutStore = useLayoutStore()
const authStore = useAuthStore()
const router = useRouter()

const serverBaseUrl = 'http://localhost:8081'

const isLoggedIn = computed(() => authStore.isLoggedIn)
const profileImageUrl = computed(() => authStore.profileImageUrl)
const displayedProfileImage = computed(() => {
  if (profileImageUrl.value) {
    return `${serverBaseUrl}${profileImageUrl.value}?t=${new Date().getTime()}`;
  }
  return defaultProfile;
});

</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  padding: 0 24px;
  background-color: white;
  border-bottom: 1px solid #e5e7eb;
}

.header-left {
  display: flex;
  align-items: center;
}

.menu-button {
  padding: 8px;
  border-radius: 6px;
  background: transparent;
  cursor: pointer;
}

.menu-button:hover {
  background-color: #f3f4f6;
}

.icon {
  width: 24px;
  height: 24px;
  color: #4b5563;
}

.search-wrap {
  position: relative;
  margin-left: 16px;
}

.search-input {
  padding: 8px 16px 8px 36px;
  border: 1px solid #d1d5db;
  border-radius: 6px;
}

.search-icon {
  position: absolute;
  top: 50%;
  left: 8px;
  transform: translateY(-50%);
  color: #9ca3af;
}

.icon-small {
  width: 20px;
  height: 20px;
}

.header-right {
  display: flex;
  align-items: center;
}

.notifications {
  position: relative;
  margin-right: 16px;
  cursor: pointer;
}

.notification-badge {
  position: absolute;
  top: 0;
  right: 0;
  transform: translate(50%, -50%);
  background-color: #dc2626;
  color: #fef2f2;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 9999px;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  margin-left: 8px;
  font-weight: 600;
  color: #374151;
}
</style>
