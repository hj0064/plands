<template>
  <div class="my-page-info">
      <div class="user-info-section">
        <div class="avatar-placeholder">
          <img v-if="profileImageUrl" :src="displayedProfileImage"  alt="user-profile-img"/>
        </div>
        <h1 class="nickname">{{ nickname }} 님</h1>
      </div>

      <div class="stats-grid">
        <div class="stat-item">
          <p class="stat-label">등록된 식물</p>
          <p class="stat-value">{{ plantCount }}</p>
        </div>
        <div class="stat-item">
          <p class="stat-label">작성한 일기</p>
          <p class="stat-value">{{ diaryCount }}</p>
        </div>
        <div class="stat-item">
          <p class="stat-label">작성한 게시물</p>
          <p class="stat-value">{{ postCount }}</p>
        </div>
        <div class="stat-item">
          <p class="stat-label">내가 단 댓글</p>
          <p class="stat-value">{{ commentCount }}</p>
        </div>
      </div>

      <button class="inquiry-button">
        <span class="star-icon">★</span>
        문의하기
      </button>
    </div>
</template>

<script setup>
// monunt 되기 전 호출하기 위해, ref 반응형 값
import { ref, onMounted, computed } from 'vue'
import api from '@/utils/api.js';
import { useAuthStore } from '@/stores/authStore.js'
import defaultProfile from '@/assets/images/icon/profile-default.png'
const authStore = useAuthStore();

const nickname = ref('');
const profileImageUrl = ref('');
const plantCount = ref(0);
const diaryCount = ref(0);
const postCount = ref(0);
const commentCount = ref(0);

const serverBaseUrl = 'http://localhost:8081';

const displayedProfileImage = computed(() => {
  return profileImageUrl.value
    ? serverBaseUrl + profileImageUrl.value
    : defaultProfile;
});

onMounted(async () => {
  const memberId = authStore.memberId;
  const res = await api.get(`api/member/${memberId}/profile`);
  nickname.value = res.data.nickname;
  profileImageUrl.value = res.data.profileImageUrl;
  plantCount.value = res.data.plantCount;
  diaryCount.value = res.data.diaryCount;
  postCount.value = res.data.postCount;
  commentCount.value = res.data.commentCount;
});
</script>
