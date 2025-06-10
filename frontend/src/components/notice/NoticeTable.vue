<script setup>
import { defineProps } from 'vue'
import NoticeRow from '@/components/notice/NoticeRow.vue'

const props = defineProps({
  notices: {
    type: Array,
    required: true,
    default: () => [],
  },
})
</script>

<template>
  <table class="board-table">
    <colgroup>
      <col style="width: 10%;" />
      <col style="width: 60%;" />
      <col style="width: 30%;" />
    </colgroup>
    <thead>
    <tr>
      <th>번호</th>
      <th>제목</th>
      <th>작성일</th>
    </tr>
    </thead>
    <tbody>
    <tr v-if="notices.length === 0">
      <td colspan="3" class="no-data">공지사항이 없습니다.</td>
    </tr>
    <NoticeRow
      v-for="(notice, index) in notices"
      :key="notice.noticeId"
      :notice="notice"
      :index="index"
    />
    </tbody>
  </table>
</template>
