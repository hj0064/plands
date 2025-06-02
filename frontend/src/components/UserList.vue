<template>
  <div class="user-list">
    <h1>📚 유저 목록</h1>
    <ul v-if="users.length">
      <li v-for="user in users" :key="user.memberId">
        <span class="name">{{ user.name }}</span>
        <span class="email">{{ user.email }}</span>
      </li>
    </ul>
    <p v-else>불러오는 중...</p>
  </div>
</template>
<script>

import api from "@/utils/api.js";

export default {
  data() {
    return {
      users: [],
    };
  },
  async created() {
    try {
      const response = await api.get('/api/sample');
      this.users = response.data;
      console.log(this.users);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  },
};
</script>
<style scoped>
.user-list {
  padding: 20px;
  border-radius: 10px;
  background: #f9f9f9;
  box-shadow: 0 2px 6px rgba(0,0,0,0.05);
}

ul {
  list-style: none;
  padding: 0;
}

li {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.name {
  font-weight: bold;
}

.email {
  margin-left: 10px;
  color: #555;
  font-size: 14px;
}
</style>


