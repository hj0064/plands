<template>
  <div id="app">
    <AppHeader />

    <main class="main-container">
      <router-view />
    </main>

    <footer class="app-footer">
      <p>© 2024 My Plant Journal</p>
    </footer>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: 'App',
  components: { AppHeader },
}
</script>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 전체 높이 설정 */
  text-align: center;
}

.main-container {
  width: 100%;
  margin: 0 auto; /* 중앙 정렬 */
  padding: 40px 20px;
  box-sizing: border-box;
  min-height: 100vh;
}

.app-footer {
  text-align: center;
  border-top: 1px solid #eee;
  padding: 20px 0;
  font-size: 14px;
  color: #888;
}
</style>
