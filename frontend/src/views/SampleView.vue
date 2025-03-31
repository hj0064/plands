<template>
  <div>
    <h1>📚 유저 목록</h1>
    <ul v-if="users.length">
      <li v-for="user in users" :key="user.userSq">
        {{ user.userName }} - {{ user.userEmail }}
      </li>
    </ul>
    <p v-else>불러오는 중...</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
    };
  },
  async created() {
    try {
      const response = await axios.get('http://localhost:8081/api/sample');
      this.users = response.data;
      console.log(this.users);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  },
};
</script>
