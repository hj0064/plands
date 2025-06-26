<template>
  <div class="page">
    <div class="header">
      <div class="title-box">
        <div class="title-icon">
          <svg width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <div class="title-text">
          <h1 class="title">사용자 관리</h1>
          <p class="subtitle">식물일기 사용자들을 관리하고 모니터링하세요</p>
        </div>
      </div>
    </div>

    <!-- 통계 -->
    <div class="stats">
      <div class="card">
        <div class="card-icon blue">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <div class="card-content">
          <p class="card-label">전체 사용자</p>
          <p class="card-value">{{ stats.totalUsers }}</p>
        </div>
      </div>

      <div class="card">
        <div class="card-icon green">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <div class="card-content">
          <p class="card-label">활성 사용자</p>
          <p class="card-value">{{ stats.activeUsers }}</p>
        </div>
      </div>

      <div class="card">
        <div class="card-icon orange">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <div class="card-content">
          <p class="card-label">신규 사용자</p>
          <p class="card-value">{{ stats.newUsers }}</p>
        </div>
      </div>

      <div class="card">
        <div class="card-icon purple">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M7 20l4-16m2 16l4-16M6 9h14M6 15h14"></path>
          </svg>
        </div>
        <div class="card-content">
          <p class="card-label">총 식물 수</p>
          <p class="card-value">{{ stats.totalPlants }}</p>
        </div>
      </div>
    </div>

    <!-- 필터 -->
    <UserFilter @search="handleSearch" />

    <!-- 사용자 수 -->
    <p class="count-text">총 {{ totalCount }}명의 사용자</p>

    <!-- 사용자 리스트 -->
    <UserList
      :users="members"
      @user-select="handleUserSelect"
      @user-edit="handleUserEdit"
    />
  </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue'
import { useMemberApi } from '@/composables/useMemberApi.js'
import UserFilter from '@/components/admin/user/UserFilter.vue'
import UserList from '@/components/admin/user/UserList.vue'

const {
  members,
  totalCount,
  stats,
  loading,
  error,
  fetchMemberStats,
  fetchMemberList
} = useMemberApi()

onMounted(() => {
  fetchMemberStats()
  fetchMemberList(filters)
})

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

function handleSearch(newFilters) {
  // UserFilter에서 emit한 필터 값을 반영
  for (const key in filters) {
    filters[key] = newFilters[key] || ''
  }
  fetchMemberList(filters)
}

const handleUserSelect = (user) => {
  alert(`${user.name} 사용자 상세보기`)
}

const handleUserEdit = (user) => {
  alert(`${user.name} 사용자 편집`)
}
</script>

<style scoped>

</style>
