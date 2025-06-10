<script setup>
import { onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useNoticeApi } from '@/composables/useNoticeApi'

const route = useRoute()
const router = useRouter()
let noticeId = Number(route.params.id)
const notice = ref(null)
const prevNotice = ref(null)
const nextNotice = ref(null)

const { fetchNoticeDetail, fetchAdjacentNotices } = useNoticeApi()

const loadDetail = async () => {
  try {
    notice.value = await fetchNoticeDetail(noticeId)
    const { prev, next } = await fetchAdjacentNotices(noticeId)
    prevNotice.value = prev
    nextNotice.value = next
  } catch (e) {
    alert('공지사항을 불러오는 데 실패했습니다.')
    router.push('/notice')
  }
}

watch(() => route.params.id, async (newId) => {
  noticeId = Number(newId)
  await loadDetail()
})

const goBack = () => {
  router.push('/notice')
}

const copyUrl = () => {
  const url = window.location.href
  navigator.clipboard.writeText(url).then(() => {
    alert('링크가 복사되었습니다!')
  })
}

onMounted(loadDetail)
</script>

<template>
  <div class="board-detail-page">
    <div class="board-detail-container" v-if="notice">
      <header class="board-header">
        <h1 class="board-title">{{ notice.title }}</h1>
        <p class="board-date">{{ notice.createdAt }}</p>
      </header>

      <main class="board-content" v-html="notice.content" />

      <footer class="board-footer">
        <button class="action-button" @click="goBack">← 목록으로</button>
        <div class="share-group">
          <button class="action-button" @click="copyUrl">🔗공유하기</button>
        </div>
      </footer>

      <nav class="navigation-links">
        <div class="nav-item" v-if="prevNotice">
          <router-link :to="`/notice/${prevNotice.noticeId}`" class="nav-link">
            <span class="nav-label">이전글</span>
            <span class="nav-title">{{ prevNotice.title }}</span>
          </router-link>
        </div>
        <div v-else class="nav-item-placeholder"></div>
        <div class="nav-item" v-if="nextNotice">
          <router-link :to="`/notice/${nextNotice.noticeId}`" class="nav-link">
            <span class="nav-label">다음글</span>
            <span class="nav-title">{{ nextNotice.title }}</span>
          </router-link>
        </div>
      </nav>
    </div>

    <div v-else class="loading">불러오는 중...</div>
  </div>
</template>
