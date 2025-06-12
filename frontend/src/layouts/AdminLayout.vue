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
</script>

<style>
.admin-layout {
  display: flex;
  height: 100vh;
  background-color: #f3f4f6; /* 연한 회색 배경 */
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.content-area {
  flex: 1;
  overflow-x: hidden;
  overflow-y: auto;
  background-color: #f3f4f6;
  padding: 24px;
}
</style>
