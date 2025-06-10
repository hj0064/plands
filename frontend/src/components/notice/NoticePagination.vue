<script setup>
import { defineProps, defineEmits, computed } from 'vue'

const props = defineProps({
  page: {
    type: Number,
    required: true,
  },
  total: {
    type: Number,
    required: true,
  },
  size: {
    type: Number,
    default: 8,
  },
})

const emit = defineEmits(['changePage'])

// 페이지 개수 계산
const totalPages = computed(() => Math.ceil(props.total / props.size))

const goPage = (p) => {
  if (p !== props.page && p > 0 && p <= totalPages.value) {
    emit('changePage', p)
  }
}

const pagesToShow = computed(() => {
  const pages = []
  const maxPagesToShow = 5
  let start = Math.max(1, props.page - 2)
  let end = Math.min(totalPages.value, start + maxPagesToShow - 1)

  // 범위가 maxPagesToShow 보다 작으면 start 조정
  if (end - start + 1 < maxPagesToShow) {
    start = Math.max(1, end - maxPagesToShow + 1)
  }

  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  return pages
})
</script>

<template>
  <nav class="pagination" aria-label="pagination">
    <button
      :disabled="page === 1"
      @click="goPage(page - 1)"
      aria-label="이전 페이지"
    >
      &lt;
    </button>

    <button
      v-for="p in pagesToShow"
      :key="p"
      :class="{ active: p === page }"
      @click="goPage(p)"
      :aria-current="p === page ? 'page' : null"
    >
      {{ p }}
    </button>

    <button
      :disabled="page === totalPages"
      @click="goPage(page + 1)"
      aria-label="다음 페이지"
    >
      &gt;
    </button>
  </nav>
</template>
