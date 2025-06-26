<template>
  <div class="filter-box">
    <div class="filter-header">
      <div class="filter-icon">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <polygon points="22,3 2,3 10,12.46 10,19 14,21 14,12.46 22,3" />
        </svg>
      </div>
      <h3>사용자 필터</h3>
    </div>

    <div class="filter-grid">
      <div class="filter-item">
        <label>이름/닉네임</label>
        <input v-model="filters.username" type="text" placeholder="이름 입력" />
      </div>
      <div class="filter-item">
        <label>이메일</label>
        <input v-model="filters.email" type="email" placeholder="이메일 입력" />
      </div>
      <div class="filter-item">
        <label>권한</label>
        <select v-model="filters.role">
          <option value="">전체</option>
          <option value="ROLE_USER">사용자</option>
          <option value="ROLE_ADMIN">관리자</option>
        </select>
      </div>
      <div class="filter-item">
        <label>상태</label>
        <select v-model="filters.status">
          <option value="">전체</option>
          <option value="active">활성</option>
          <option value="banned">정지</option>
          <option value="withdrawn">탈퇴</option>
        </select>
      </div>
      <div class="filter-item">
        <label>가입 경로</label>
        <select v-model="filters.provider">
          <option value="">전체</option>
          <option value="email">이메일</option>
          <option value="google">구글</option>
          <option value="kakao">카카오</option>
          <option value="naver">네이버</option>
        </select>
      </div>
      <div class="filter-item">
        <label>정렬</label>
        <select v-model="filters.sort">
          <option value="">기본</option>
          <option value="username_asc">이름순 ↑</option>
          <option value="username_desc">이름순 ↓</option>
          <option value="createdAt_desc">가입일순 ↓</option>
          <option value="createdAt_asc">가입일순 ↑</option>
        </select>
      </div>
      <div class="filter-item date-range">
        <label>가입 기간</label>
        <div class="date-range-inputs">
          <input v-model="filters.startDate" type="date" />
          <span>~</span>
          <input v-model="filters.endDate" type="date" />
        </div>
      </div>
    </div>

    <div class="filter-actions">
      <button class="btn-reset" @click="onReset">필터 초기화</button>
      <button class="btn-search" @click="onSearch">검색</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted, defineEmits } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const emit = defineEmits(['search'])

const route = useRoute()
const router = useRouter()

const filters = reactive({
  username: '',
  email: '',
  role: '',
  status: '',
  provider: '',
  sort: '',
  startDate: '',
  endDate: ''
})

function applyQueryToFilters(query) {
  for (const key in filters) {
    if (query[key]) filters[key] = query[key]
  }
}

function updateQueryFromFilters() {
  const query = {}
  for (const key in filters) {
    if (filters[key]) query[key] = filters[key]
  }
  router.replace({ query })
}

function onSearch() {
  updateQueryFromFilters()
  emit('search', { ...filters })
}

function onReset() {
  for (const key in filters) filters[key] = ''
  router.replace({ query: {} })
  emit('search', { ...filters })
}

onMounted(() => {
  applyQueryToFilters(route.query)
  emit('search', { ...filters })
})
</script>
