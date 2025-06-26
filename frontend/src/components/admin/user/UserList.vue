<template>
  <div class="user-list">
    <div class="table-container">
      <table class="user-table">
        <thead>
        <tr>
          <th>사용자</th>
          <th>이메일</th>
          <th>식물 활동</th>
          <th>상태</th>
          <th>가입일</th>
          <th>역할</th>
          <th>작업</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in displayUsers" :key="user.memberId" class="user-row">
          <td class="user-info">
            <div class="user-profile">
              <div class="avatar">{{ user.name.charAt(0) }}</div>
              <div class="user-details">
                <div class="user-name">{{ user.name }}</div>
                <div class="username">{{ user.nickname }}</div>
              </div>
            </div>
          </td>

          <td class="contact-info">
            <div class="contact-item">
              <svg class="contact-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                <polyline points="22,6 12,13 2,6"/>
              </svg>
              {{ user.email }}
            </div>
            <div class="provider-badge" :class="getProviderClass(user.provider)">
              {{ providerToText(user.provider) }}
            </div>
          </td>

          <td class="plant-activity">
            <div class="activity-item">
              <svg class="plant-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M7 20l4-16m2 16l4-16M6 9h14M6 15h14"/>
              </svg>
              {{ user.plantCount || 0 }}개 식물
            </div>
            <div class="activity-item">
              <svg class="diary-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                <line x1="16" y1="2" x2="16" y2="6"/>
                <line x1="8" y1="2" x2="8" y2="6"/>
                <line x1="3" y1="10" x2="21" y2="10"/>
              </svg>
              {{ user.diaryCount || 0 }}개 일기
            </div>
          </td>

          <td class="status-info">
            <div class="status-badge" :class="getStatusClass(user.status)">
              {{ statusToText(user.status) }}
            </div>
            <div class="last-login">마지막 접속: {{ user.lastLogin || 'N/A' }}</div>
          </td>

          <td class="join-date">{{ formatDate(user.createdAt) }}</td>

          <td class="">{{ roleToText(user.role) }}</td>

          <td class="actions">
            <div class="action-buttons">
              <button
                class="action-btn view-btn"
                @click="$emit('user-select', user)"
                title="상세보기"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                  <circle cx="12" cy="12" r="3"></circle>
                </svg>
              </button>
              <button
                class="action-btn edit-btn"
                @click="$emit('user-edit', user)"
                title="편집"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                  <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                </svg>
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>

      <div v-if="loading" class="loading">로딩 중...</div>
      <div v-if="error" class="error">{{ error.message || '데이터를 불러오는 중 오류가 발생했습니다.' }}</div>
    </div>
  </div>
</template>

<script setup>
import { computed, defineProps } from 'vue'
import { formatDate, statusToText, roleToText, providerToText } from '@/utils/formatUtils.js'

const props = defineProps({
  users: {
    type: Array,
    required: true
  }
})

const displayUsers = computed(() => {
  if (!props.users || !Array.isArray(props.users)) return []
  return props.users.map(user => ({
    ...user,
    // 누락된 값들 기본 제공
    plantCount: user.plantCount || 0,
    diaryCount: user.diaryCount || 0,
    experience: user.experience || 'beginner',
    status: user.status || 'inactive'
  }))
})

function getProviderClass(provider) {
  return {
    naver: 'provider-naver',
    kakao: 'provider-kakao',
    google: 'provider-google'
  }[provider] || 'provider-none'
}

function getStatusClass(status) {
  return {
    ACTIVE: 'status-active',
    BANNED: 'status-banned',
    WITHDRAW: 'status-withdraw'
  }[status] || ''
}
</script>
