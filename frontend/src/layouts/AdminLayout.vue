<template>
  <div class="admin-layout">
    <Sidebar />
    <div class="main-content">
      <Header />
      <main class="content-area">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import Sidebar from '@/components/admin/AdminSidebar.vue'
import Header from '@/components/admin/AdminHeader.vue'
import '@/assets/styles/admin-global.css'
</script>
