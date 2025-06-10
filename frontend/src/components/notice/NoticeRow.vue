<script setup>
import { defineProps } from 'vue'

const props = defineProps({
  notice: {
    type: Object,
    required: true,
  },
  index: {
    type: Number,
    required: true,
  }
})
</script>

<template>
  <tr>
    <td>{{ index + 1 }}</td>
    <td>
      <router-link :to="`/notice/${notice.noticeId}`">{{ notice.title }}</router-link>
    </td>
    <td>{{ notice.createdAt }}</td>
  </tr>
</template>
