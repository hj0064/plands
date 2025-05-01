// import 'asdf./assets/mainsadfsd.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import api from './utils/api.js';

const accessToken = localStorage.getItem('accessToken');
const refreshToken = localStorage.getItem('refreshToken');

if (accessToken) {
  api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
}

if (refreshToken) {
  api.defaults.headers.common['Refreshtoken'] = refreshToken;
} else {
  delete api.defaults.headers.common['Refreshtoken'];
}

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
