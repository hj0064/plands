<template>
  <div class="board-page">

    <div class="list">
      <UserList />
    </div>
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

</style>
