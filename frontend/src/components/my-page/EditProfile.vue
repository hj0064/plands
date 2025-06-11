<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/utils/api.js';
import defaultProfile from '@/assets/images/icon/profile-default.png';
import { useAuthStore } from '@/stores/authStore.js';
import ProfileImageModal from '@/components/modal/ProfileImageModal.vue';

let authStore = useAuthStore();
const router = useRouter();

const nickname = ref('');
const email = ref('');
const name = ref('');
const password = ref('');
const confirmPassword = ref('');
const passwordError = ref('');

const memberId = authStore.memberId;
const showPassword = ref(false);
const showConfirmPassword = ref(false);
const showModal = ref(false);

const profileImageUrl = ref('');
const serverBaseUrl = 'http://localhost:8081';

const displayedProfileImage = computed(() =>
  profileImageUrl.value ? serverBaseUrl + profileImageUrl.value : defaultProfile
);

const validatePassword = () => {
  const pw = password.value;
  const isLengthValid = pw.length >= 8 && pw.length <= 20;
  const hasRequiredTypes =
    [/[A-Za-z]/, /[0-9]/, /[^A-Za-z0-9]/].filter((regex) => regex.test(pw)).length >= 2;

  if (!isLengthValid) {
    passwordError.value = '비밀번호는 8자 이상 20자 이하로 입력해주세요.';
  } else if (!hasRequiredTypes) {
    passwordError.value = '영문, 숫자, 특수문자 중 2가지 이상 포함해야 합니다.';
  } else {
    passwordError.value = '';
  }
};

const canSave = computed(() => {
  return (
    nickname.value &&
    (!password.value || (password.value === confirmPassword.value && !passwordError.value))
  );
});

const getProfile = async () => {
  try {
    const { data } = await api.get(`/api/member/me/${memberId}`);
    nickname.value = data.nickname;
    email.value = data.email;
    name.value = data.name;
    profileImageUrl.value = data.profileImageUrl || '';
  } catch (error) {
    console.error('프로필 조회 실패:', error);
  }
};

const saveProfile = async () => {
  if (!nickname.value) return;

  if (password.value) {
    validatePassword();
    if (passwordError.value || password.value !== confirmPassword.value) {
      alert('비밀번호를 다시 확인해주세요.');
      return;
    }
  }

  const payload = { nickname: nickname.value };
  if (password.value) payload.password = password.value;

  try {
    await api.put(`/api/member/update/${memberId}`, payload);
    alert('저장되었습니다.');
    password.value = '';
    confirmPassword.value = '';
  } catch (error) {
    console.error('프로필 저장 실패:', error);
    alert('저장에 실패했습니다.');
  }
};

const onProfileUpdateSuccess = (newProfileUrl) => {
  authStore.setProfileImageUrl(newProfileUrl);
  getProfile();
  showModal.value = false;
};
onMounted(getProfile);
</script>

<template>
  <div id="app" class="profile-container">
    <div class="profile-card">
      <aside class="profile-sidebar">
        <div class="avatar-section">
          <img :src="displayedProfileImage" alt="User Avatar" class="avatar-image" />
          <button class="change-profile-button" @click="showModal = true">프로필 변경</button>
        </div>
        <router-link to="/member/withdraw" class="membership-leave-link">회원탈퇴</router-link>
      </aside>

      <section class="form-section">
        <div class="form-group">
          <label for="name">이름</label>
          <input type="text" id="name" v-model="name" readonly class="readonly-input" />
        </div>

        <div class="form-group">
          <label for="email">이메일</label>
          <input type="email" id="email" v-model="email" readonly class="readonly-input" />
        </div>

        <div class="form-group">
          <label for="nickname">닉네임</label>
          <input type="text" id="nickname" v-model="nickname" />
        </div>

        <div class="form-group">
          <label for="password">새로운 비밀번호</label>
          <div class="password-wrapper">
            <input
              :type="showPassword ? 'text' : 'password'"
              id="password"
              v-model="password"
              @input="validatePassword"
              class="password-input"
            />
            <span class="toggle-icon" @click="showPassword = !showPassword">
              <svg
                v-if="showPassword"
                xmlns="http://www.w3.org/2000/svg"
                class="icon"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                width="20"
                height="20"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="1.5"
                  d="M13.875 18.825A10.05 10.05 0 0112 19.5c-5.385 0-9.6-4.5-9.6-7.5a9.97 9.97 0 012.223-3.225M6.262 6.262A10.08 10.08 0 0112 4.5c5.385 0 9.6 4.5 9.6 7.5a9.96 9.96 0 01-2.07 3.075M6.262 6.262L3 3m0 0l18 18"
                />
              </svg>
              <svg
                v-else
                xmlns="http://www.w3.org/2000/svg"
                class="icon"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                width="20"
                height="20"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="1.5"
                  d="M15 12a3 3 0 11-6 0 3 3 0 016 0zm6 0c0 1.355-1.314 3.36-3.396 4.723C15.697 18.64 13.923 19.5 12 19.5c-1.923 0-3.697-.86-5.604-2.777C4.314 15.36 3 13.355 3 12s1.314-3.36 3.396-4.723C8.303 5.36 10.077 4.5 12 4.5c1.923 0 3.697.86 5.604 2.777C19.686 8.64 21 10.645 21 12z"
                />
              </svg>
            </span>
          </div>
          <div class="error-message">
            {{ password && passwordError ? passwordError : '\u00A0' }}
          </div>
        </div>

        <div class="form-group">
          <label for="confirmPassword">비밀번호 확인</label>
          <div class="password-wrapper">
            <input
              :type="showConfirmPassword ? 'text' : 'password'"
              id="confirmPassword"
              v-model="confirmPassword"
            />
            <span class="toggle-icon" @click="showConfirmPassword = !showConfirmPassword">
              <svg
                v-if="showConfirmPassword"
                xmlns="http://www.w3.org/2000/svg"
                class="icon"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="1.5"
                  d="M13.875 18.825A10.05 10.05 0 0112 19.5c-5.385 0-9.6-4.5-9.6-7.5a9.97 9.97 0 012.223-3.225M6.262 6.262A10.08 10.08 0 0112 4.5c5.385 0 9.6 4.5 9.6 7.5a9.96 9.96 0 01-2.07 3.075M6.262 6.262L3 3m0 0l18 18"
                />
              </svg>
              <svg
                v-else
                xmlns="http://www.w3.org/2000/svg"
                class="icon"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="1.5"
                  d="M15 12a3 3 0 11-6 0 3 3 0 016 0zm6 0c0 1.355-1.314 3.36-3.396 4.723C15.697 18.64 13.923 19.5 12 19.5c-1.923 0-3.697-.86-5.604-2.777C4.314 15.36 3 13.355 3 12s1.314-3.36 3.396-4.723C8.303 5.36 10.077 4.5 12 4.5c1.923 0 3.697.86 5.604 2.777C19.686 8.64 21 10.645 21 12z"
                />
              </svg>
            </span>
          </div>
          <div class="error-message">
            {{ confirmPassword && confirmPassword !== password ? '비밀번호가 일치하지 않습니다.' : '\u00A0' }}
          </div>
        </div>

        <div class="form-actions">
          <button class="save-button" :disabled="!canSave" @click="saveProfile">저장</button>
        </div>
      </section>
    </div>

    <ProfileImageModal
      v-if="showModal"
      @uploaded="onProfileUpdateSuccess"
      @close="showModal = false"
    />
  </div>
</template>
