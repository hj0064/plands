import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    accessToken: localStorage.getItem('accessToken') || null,
    refreshToken: localStorage.getItem('refreshToken') || null,
    isLoggedIn: !!localStorage.getItem('accessToken')
  }),
  actions: {
    setAccessToken(token) {
      this.accessToken = token;
      this.isLoggedIn = true;
      localStorage.setItem('accessToken', token);
    },
    setRefreshToken(token) {
      this.refreshToken = token;
      localStorage.setItem('refreshToken', token);
    },
    clearToken() {
      this.accessToken = null;
      this.refreshToken = null;
      this.isLoggedIn = false;
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      this.$router.push("/login");
    },
    checkLogin() {
      this.isLoggedIn = !!localStorage.getItem('accessToken');
    }
  }
})
