<template>
  <div class="login-container">
    <div class="login-main">
      <div class="login-form">
        <form @submit.prevent="signUp">
          <h2>Sign up</h2>
          <div class="input-group">
            <input type="text" placeholder="Enter Name" class="input-field" v-model="name"/>
          </div>
          <div class="input-group">
            <input type="email" placeholder="Email Address" class="input-field" v-model="email"/>
          </div>
          <div class="input-group">
            <input type="password" placeholder="Enter Password" class="input-field" v-model="password"/>
          </div>
          <button type="submit" class="btn-gr-lg">Get Started</button>
          <p class="signup-link">
            Already have an account? <router-link to="/login">Login</router-link>
          </p>
        </form>
        <div class="social-login">
          <p class="social-login text">Or continue with</p>
<!--          <a class="social-icon google" href="http://localhost:8081/oauth2/authorize/google?redirect_uri=http://localhost:8787/oauth2/redirect">-->
<!--          </a>-->
<!--          <a class="social-icon naver" href="http://localhost:8081/oauth2/authorize/naver?redirect_uri=http://localhost:8787/oauth2/redirect">-->
<!--          </a>-->
<!--          <a class="social-icon kakao" href="http://localhost:8081/oauth2/authorize/kakao?redirect_uri=http://localhost:8787/oauth2/redirect">-->
<!--          </a>-->
          <a
            v-for="provider in providers"
            :key="provider.name"
            class="social-icon"
            :class="provider.name"
            :href="getOAuthUrl(provider.name)"
          >
            {{ provider.label }}
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/assets/styles/common.css'
import '@/assets/styles/login.css'
import api from '@/utils/api.js';
import { useAuthStore } from '@/stores/authStore.js'

export default {
  name: "SignUpView",
  data() {
    return {
      name: '',
      email: '',
      password: '',
      providers: [
        { name: 'google', label: 'Google' },
        { name: 'naver', label: 'Naver' },
        { name: 'kakao', label: 'Kakao' },
      ],
    }
  },
  methods: {
    async signUp() {
      try {
        const authStore = useAuthStore();
        const response = await api.post('/api/auth/signup', {
          name: this.name,
          email: this.email,
          password: this.password,
        });
        authStore.setAccessToken(response.data.accessToken);
        authStore.setRefreshToken(response.data.refreshToken);
      } catch (error) {
        console.error('회원가입 실패:', error);
        alert('회원가입에 실패했습니다.');
      }
    },
    getOAuthUrl(provider) {
      const redirectUri = 'http://localhost:8082/oauth2/redirect';
      const backendBaseUrl = 'http://localhost:8081';
      return `${backendBaseUrl}/oauth2/authorize/${provider}?redirect_uri=${redirectUri}`;
    }
  },
};


</script>

