<template>
  <div class="login-container">
    <div class="login-main">
      <div class="login-form">
        <form @submit.prevent="login">
        <h2>Login</h2>
          <div class="input-group">
            <input type="email" placeholder="Email Address" class="input-field" v-model="email"/>
          </div>
          <div class="input-group">
            <input type="password" placeholder="Enter Password" class="input-field" v-model="password"/>
          </div>
        <button type="submit" class="btn-gr-lg">Login</button>
        <p class="signup-link">
          Don't have an account? <router-link to="/signup">Sign Up</router-link>
        </p>
        </form>
        <div class="social-login">
          <p class="social-login text">Or continue with</p>
          <a class="social-icon google" href="http://localhost:8081/oauth2/authorize/google?redirect_uri=http://localhost:8787/oauth2/redirect">
          </a>
          <a class="social-icon naver" href="http://localhost:8081/oauth2/authorize/naver?redirect_uri=http://localhost:8787/oauth2/redirect">
          </a>
          <a class="social-icon kakao" href="http://localhost:8081/oauth2/authorize/kakao?redirect_uri=http://localhost:8787/oauth2/redirect">
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import '@/assets/styles/common.css'
import '@/assets/styles/login.css'
import api from "@/utils/api.js";
import { useAuthStore } from '@/stores/authStore.js';

export default {
  name: 'LoginPage',
  data() {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    async login() {
      try {
        const authStore = useAuthStore();
        const response = await api.post('/api/auth/login', {
          email: this.email,
          password: this.password
        });

        // ✅ authStore를 통해 토큰 저장 및 로그인 상태 설정
        authStore.setAccessToken(response.data.accessToken);
        authStore.setRefreshToken(response.data.refreshToken);
        console.log("access token!!: " + response.data.accessToken);
        this.$router.push('/');  // TODO: 구현 완료 후 캘린더 화면으로 이동할 수 있도록
      } catch (error) {
        console.error('로그인 실패:', error);
        alert('이메일 또는 비밀번호가 올바르지 않습니다.');
      }
    }
  }
};
</script>
