<template>
  <div>
    <h1>Roles Page</h1>
    <RoleList />
  </div>
</template>

<script>
import RoleList from '@/components/RoleList.vue';

export default {
  name: 'TemplatePage',
  components: {
    RoleList
  }
}
</script>
