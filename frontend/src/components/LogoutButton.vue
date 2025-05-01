<template>
  <div>
    <!-- 로그아웃 버튼 -->
    <button @click="logout" class="btn-herb-gr">logout</button>
  </div>
</template>

<script>
import axios from "axios";
import '@/assets/styles/common.css'
import { useAuthStore } from '@/stores/authStore.js'
export default {
  methods: {
    async logout() {
      try {
        const authStore = useAuthStore();
        await axios.post("api/auth/logout", {}, {
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("accessToken")}`
          }
        });
        authStore.clearToken();
        console.log("로그아웃 성공");
      } catch (error) {
        console.error("로그아웃 실패:", error);
      }
    },
  },
};
</script>
