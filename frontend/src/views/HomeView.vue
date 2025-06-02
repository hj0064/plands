<template>
  <div class="home-container">
    <div v-if="isLoggedIn">
      <p>캘린더 component가 들어가야함...</p>
    </div>
    <div v-else>
      <Introduce />
    </div>
  </div>
</template>

<script>
import Introduce from '@/components/Introduce.vue';
import { useAuthStore } from '@/stores/authStore.js'
export default {
  name: "HomeView",
  data() {
    return {

    }
  },
  computed: {
    isLoggedIn() {
      const authStore = useAuthStore();
      return authStore.isLoggedIn;
    }
  },
  components: {
    Introduce
  },

}
</script>

<style scoped>
.home-container {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

</style>
