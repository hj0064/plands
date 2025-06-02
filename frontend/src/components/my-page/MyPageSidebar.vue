<template>
  <aside class="sidebar">
    <nav class="menu">
      <h2 class="menu-title">마이페이지</h2>
      <ul>
        <li
          v-for="item in menuItems"
          :key="item.name"
          :class="['menu-item', { active: item.name === activeMenuItem }]"
          @click="selectMenuItem(item.name)"
        >
          {{ item.label }}
        </li>
      </ul>
    </nav>
    <div class="logout-section">
      <button class="logout-button" @click="logout">로그아웃</button>
    </div>
  </aside>
</template>

<script>
import { useAuthStore } from '@/stores/authStore.js'
import api from '@/utils/api.js'

export default {
  name: 'MyPageSidebar',
  props: {
    activeMenuItem: {
      type: String,
      default: 'Profile',
    },
  },
  data() {
    return {
      menuItems: [
        { name: 'Profile', label: '프로필' },
        { name: 'EditProfile', label: '회원 정보 수정' },
        { name: 'MyPlant', label: '내 식물' },
        { name: 'MyComplain', label: '내 문의사항' },
        { name: 'MyPost', label: '내가 작성한 게시물' },
      ]
    };
  },
  methods: {
    selectMenuItem(name) {
      this.$emit('update:active-menu-item', name);
    },
    async logout() {
      const authStore = useAuthStore();

      await api.post('/api/auth/logout', {}, {
        headers: {
          Authorization: `Bearer ${authStore.accessToken}`
        }
      });
      authStore.logout();
      this.$router.push('/');
    }
  },
};
</script>
