<template>
  <AppHeader />
  <main class="main-container">
    <router-view />
  </main>
  <footer class="app-footer">
    <p>© 2024 My Plant Journal</p>
  </footer>
</template>
<script>
import AppHeader from '@/components/AppHeader.vue'
import "@/assets/styles/global.css";
import { defineComponent } from 'vue'

export default defineComponent({
  components: { AppHeader }
})

</script>
