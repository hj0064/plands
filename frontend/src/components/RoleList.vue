<template>
  <div>
    <ul>
      <li v-for="role in roles" :key="role.roleSq">
        {{ role.roleSq }} - {{ role.roleNm }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';

export default {
  name: 'RoleList',
  setup() {
    const roles = ref([]);

    const fetchRoles = async () => {
      try {
        const response = await axios.get('api/template/getRoleList');
        roles.value = response.data;
      } catch (error) {
        console.error("There was an error fetching the roles:", error);
      }
    };

    onMounted(fetchRoles);

    return {
      roles
    };
  }
};
</script>
