import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    accessToken: localStorage.getItem('accessToken') || null,
    refreshToken: localStorage.getItem('refreshToken') || null,
    isLoggedIn: !!localStorage.getItem('accessToken'),
    memberId: localStorage.getItem('memberId') || null,
    profileImageUrl: localStorage.getItem('profileImageUrl') || null,
    nickname: localStorage.getItem('nickname') || null,
  }),
  actions: {
    setProfileImageUrl(newUrl) {
      this.profileImageUrl = newUrl
    },
    setUser(user) {
      this.isLoggedIn = true;
      this.memberId = user.memberId;
      this.profileImageUrl = user.profileImageUrl;
      this.nickname = user.nickname;
      this.accessToken = user.accessToken;
      this.refreshToken = user.refreshToken;
      localStorage.setItem('accessToken', user.accessToken);
      localStorage.setItem('refreshToken', user.refreshToken);
      localStorage.setItem('memberId', user.memberId);
      localStorage.setItem('profileImageUrl', user.profileImageUrl);
      localStorage.setItem('nickname', user.nickname);
      this.isLoggedIn = !!localStorage.getItem('accessToken');
    },
    logout() {
      this.accessToken = null;
      this.refreshToken = null;
      this.isLoggedIn = false;
      this.memberId = null;
      this.profileImageUrl = null;
      this.nickname = null;
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      localStorage.removeItem('memberId');
      localStorage.removeItem('profileImageUrl');
      localStorage.removeItem('nickname');
    },

  }
})
