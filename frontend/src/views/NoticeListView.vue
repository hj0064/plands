<script setup>
import { ref, onMounted } from 'vue'
import '@/assets/styles/board.css'
import NoticeSearch from '@/components/notice/NoticeSearch.vue'
import NoticeTable from '@/components/notice/NoticeTable.vue'
import NoticePagination from '@/components/notice/NoticePagination.vue'
import { useNoticeApi } from '@/composables/useNoticeApi'

const { notices, totalCount, fetchNoticeList } = useNoticeApi()

const page = ref(1)
const size = ref(8)
const keyword = ref('')
const searchType = ref('TITLE_CONTENT')

const loadNotices = () => {
  fetchNoticeList({
    page: page.value,
    size: size.value,
    keyword: keyword.value,
    searchType: searchType.value,
  })
}

const handleSearch = ({ keyword: k, searchType: s }) => {
  keyword.value = k
  searchType.value = s
  page.value = 1
  loadNotices()
}

const setPage = (newPage) => {
  page.value = newPage
  loadNotices()
}

onMounted(() => {
  loadNotices()
})

const filteredNotices = notices
const total = totalCount
</script>

<template>
  <div class="board-page">
    <div class="board-container">
      <h1 class="board-title">공지사항</h1>
      <p class="board-subtitle">중요한 소식을 전해드립니다.</p>

      <NoticeTable :notices="filteredNotices" />
      <NoticeSearch @search="handleSearch" :totalCount="total"/>
      <NoticePagination :page="page" :total="total" @changePage="setPage" />
    </div>
  </div>
</template>
