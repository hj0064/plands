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
import "@/assets/styles/global.css";
export default {
  name: 'App',
  components: { AppHeader },
}
</script>

