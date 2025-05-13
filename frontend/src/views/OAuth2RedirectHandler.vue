<template>
  <div>로그인 처리 중...</div>
</template>

<script>
import { useAuthStore } from '@/stores/authStore.js'

export default {
  name: "OAuth2RedirectHandler",
  mounted() {
    const query = new URLSearchParams(window.location.search);
    const accessToken = query.get("accessToken");
    const refreshToken = query.get("refreshToken");
    const authStore = useAuthStore();

    if (accessToken && refreshToken) {
      // localStorage 또는 cookie에 저장
      authStore.setAccessToken(accessToken);
      authStore.setRefreshToken(refreshToken);

      // URL에서 토큰 제거 (선택)
      window.history.replaceState({}, document.title, "/");

      // 홈 또는 대시보드로 이동
      this.$router.push("/");
    } else {
      alert("로그인에 실패했습니다.");
      this.$router.push("/login");
    }
  }
};
</script>
