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
    const memberId = query.get("memberId");
    const nickname = query.get("nickname");
    const profileImageUrl = query.get("profileImageUrl");
    const authStore = useAuthStore();

    if (accessToken && refreshToken) {
      authStore.setUser({
        accessToken,
        refreshToken,
        memberId,
        nickname,
        profileImageUrl
      })

      // URL에서 토큰 제거 (선택)
      window.history.replaceState({}, document.title, "/");
      this.$router.push("/");
    } else {
      alert("로그인에 실패했습니다.");
      this.$router.push("/login");
    }
  }
};
</script>
