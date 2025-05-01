<template>
  <div class="board-page">
    <UserList />
  </div>
</template>

<script>
import UserList from "@/components/UserList.vue";

export default {
  components: { UserList }
}
</script>

<style scoped>
.board-page {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.content {
  flex: 1;
}

.user-list {
  width: 200px;
}
</style>
