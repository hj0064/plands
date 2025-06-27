<template>
  <div class="modal-overlay" :class="{ active: isOpen }" @click="closeModal">
    <div class="modal" @click.stop>
      <div class="modal-header">
        <h2 class="modal-title">사용자 정보</h2>
        <button class="modal-close" @click="closeModal">×</button>
      </div>

      <div class="modal-body" v-if="user">
        <div class="modal-left">
          <div class="user-profile">
              <img
                :src="displayedProfileImage"
                :key="displayedProfileImage"
                alt="profile"
                class="profile-avatar"
              />
            <div class="profile-info">
              <h3>{{ user.name }}</h3>
              <p>{{ user.email }}</p>
              <div class="provider-badge" :class="getProviderClass(user.provider)">
                {{ providerToText(user.provider) }}
              </div>
            </div>
          </div>

          <div class="info-grid">
            <div class="info-item">
              <span class="info-label">가입일</span>
              <span class="info-value">{{ formatDate(user.createdAt) }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">최종 수정일</span>
              <span class="info-value">{{ formatDate(user.updatedAt) }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">마지막 로그인</span>
              <span class="info-value">{{ formatDate(user.lastLoginAt) }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">현재 역할</span>
              <span class="info-value">
                {{ user.role === 'admin' ? '관리자' : '사용자' }}
              </span>
            </div>
          </div>

          <div class="editable-section">
            <h3 class="section-title">관리자 설정</h3>

            <div class="form-group">
              <label class="form-label">닉네임</label>
              <input type="text" class="form-input" v-model="editForm.nickname" />
            </div>

            <div class="form-group">
              <label class="form-label">역할</label>
              <select class="form-select" v-model="editForm.role">
                <option value="ROLE_USER">사용자</option>
                <option value="ROLE_ADMIN">관리자</option>
              </select>
            </div>

            <div class="form-group">
              <label class="form-label">상태</label>
              <select class="form-select" v-model="editForm.status">
                <option value="ACTIVE">활성</option>
                <option value="BANNED">정지</option>
                <option value="WITHDRAWN">탈퇴</option>
              </select>
            </div>

            <div class="form-group reason-group">
              <label class="form-label">사유</label>
              <select class="form-select"
                      v-model="reasonSelect"
                      @change="handleReasonChange"
                      :disabled="editForm.status === 'ACTIVE'"
              >
                <option disabled value="">사유를 선택하세요</option>
                <option value="스팸 행위">스팸 행위</option>
                <option value="부적절한 언행">부적절한 언행</option>
                <option value="비활성 요청">비활성 요청</option>
                <option value="직접 입력">직접 입력</option>
              </select>
              <textarea
                class="form-input"
                v-model="editForm.reason"
                rows="3"
                placeholder="사유를 직접 입력하세요"
                :disabled="reasonSelect !== '직접 입력'"
              />
            </div>
          </div>
        </div>

        <div class="modal-right">
          <div class="log-section">
            <h3 class="section-title">
              <svg class="log-icon" width="16" height="16" fill="currentColor" viewBox="0 0 24 24">
                <path d="M16 13v-2H7V8l-5 4 5 4v-3zM20 3H4a1 1 0 00-1 1v5h2V5h14v14H5v-4H3v5a1 1 0 001 1h16a1 1 0 001-1V4a1 1 0 00-1-1z" />
              </svg>
              로그인 기록
            </h3>
            <hr class="section-divider" />
            <ul>
              <li v-for="(log, index) in user.loginHistory || sampleLoginHistory" :key="index">
                {{ formatDateTime(log) }}
              </li>
            </ul>
          </div>

          <div class="log-section">
            <h3 class="section-title">
              <svg class="log-icon" width="16" height="16" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 2a10 10 0 100 20 10 10 0 000-20zM4 12a8 8 0 0114.9-4.6L7.4 18.9A8 8 0 014 12zm8 8a7.95 7.95 0 01-4.6-1.4l11.5-11.5A8 8 0 0112 20z" />
              </svg>
              관리자 정지 기록
            </h3>
            <hr class="section-divider" />
            <ul>
              <li
                v-for="(log, index) in user.suspensionLogs || sampleSuspensionLogs"
                :key="index"
                class="suspension-log"
                :class="{ released: log.end }"
              >
                <strong>사유:</strong> {{ log.reason }}<br />
                <strong>정지 일시:</strong> {{ formatDateTime(log.start) }}<br />
                <strong>해제 일시:</strong> {{ log.end ? formatDateTime(log.end) : '해제되지 않음' }}
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-secondary" @click="closeModal">취소</button>
        <button class="btn btn-primary" @click="saveChanges">저장</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import defaultProfile from '@/assets/images/icon/profile-default.png'
import { providerToText } from '@/utils/formatUtils.js'

const serverBaseUrl = 'http://localhost:8081'

const props = defineProps({
  isOpen: Boolean,
  user: Object
})
const emit = defineEmits(['close', 'save'])

const editForm = ref({ nickname: '', role: '', status: '', reason: '' })
const reasonSelect = ref('')


const sampleLoginHistory = ['2025-06-27T08:30:00', '2025-06-26T17:45:00', '2025-06-25T09:15:00']
const sampleSuspensionLogs = [
  { reason: '부적절한 언행', start: '2025-06-20T15:30:00', end: '2025-06-27T15:30:00' },
  { reason: '스팸 행위', start: '2025-05-15T09:00:00', end: null }
]

watch(
  () => props.user,
  (newUser) => {
    if (newUser) {
      editForm.value.nickname = newUser.nickname || ''
      editForm.value.role = newUser.role || 'ROLE_USER'
      editForm.value.status = newUser.status || 'ACTIVE'
      if (['스팸 행위', '부적절한 언행', '비활성 요청'].includes(newUser.reason)) {
        reasonSelect.value = newUser.reason
        editForm.value.reason = newUser.reason
      } else if (newUser.reason) {
        reasonSelect.value = '직접 입력'
        editForm.value.reason = newUser.reason
      } else {
        reasonSelect.value = ''
        editForm.value.reason = ''
      }
    }
  },
  { immediate: true }
)

watch(
  () => props.isOpen,
  (val) => {
    document.body.style.overflow = val ? 'hidden' : 'auto'
  }
)

const displayedProfileImage = computed(() => {
  if(props.user.profileImageUrl) {
    return `${serverBaseUrl}${props.user.profileImageUrl}`;
  }
  return defaultProfile;
});

function closeModal() {
  emit('close')
}

function saveChanges() {
  emit('save', {
    memberId: props.user.memberId,
    changes: {
      nickname: editForm.value.nickname,
      role: editForm.value.role,
      status: editForm.value.status,
      reason: editForm.value.reason || reasonSelect.value
    }
  })
}

function formatDate(dateString) {
  return dateString ? new Date(dateString).toLocaleDateString('ko-KR') : 'N/A'
}

function formatDateTime(dateString) {
  return dateString
    ? new Date(dateString).toLocaleDateString('ko-KR') +
    ' ' +
    new Date(dateString).toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit' })
    : 'N/A'
}

function getProviderClass(provider) {
  return {
    naver: 'provider-naver',
    kakao: 'provider-kakao',
    google: 'provider-google'
  }[provider] || 'provider-none'
}

function handleReasonChange() {
  if (reasonSelect.value === '직접 입력') {
    editForm.value.reason = ''
  } else {
    editForm.value.reason = reasonSelect.value
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* body 스타일은 상위 앱에서 처리하길 권장 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  opacity: 0;
  visibility: hidden;
  transition: all 0.3s ease;
}

.modal-overlay.active {
  opacity: 1;
  visibility: visible;
}

.modal {
  background: white;
  border-radius: 16px;
  width: 90%;
  max-width: 1000px;
  max-height: 90vh;
  overflow: hidden;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
}

.modal-header {
  padding: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.modal-close {
  background: none;
  border: none;
  font-size: 28px;
  cursor: pointer;
  color: #666;
  padding: 4px;
  border-radius: 4px;
  transition: all 0.2s;
}

.modal-close:hover {
  background: #f5f5f5;
  color: #333;
}

.modal-body {
  display: flex;
  gap: 24px;
  padding: 24px;
  overflow-y: auto;
  flex: 1;
}

.modal-left,
.modal-right {
  flex: 1;
  overflow-y: auto;
}

.modal-left {
  border-right: 1px solid #eee;
  padding-right: 24px;
}

.modal-right {
  padding-left: 24px;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 32px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 12px;
}

.profile-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: white;
  border: #16a34a 1px solid;
  display: flex;
  align-items: center;
  justify-content: center;
}

.profile-info {
  justify-content: left;
  text-align: left;
}
.profile-info h3 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 4px;
}

.profile-info p {
  color: #666;
  font-size: 14px;
  margin-bottom: 8px;
}

.provider-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
  background: #f3e5f5;
  color: #7b1fa2;
}

.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
  margin-bottom: 32px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-label {
  font-size: 12px;
  font-weight: 600;
  color: #666;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.info-value {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

.editable-section {
  border-top: 1px solid #eee;
  padding-top: 24px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

/* select와 textarea가 항상 block으로 세로 배치되고 textarea 위에 margin 추가 */
.form-group > select.form-select,
.form-group > textarea.form-input {
  display: block;
  width: 100%;
  margin-top: 8px;
}

.form-label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.form-input,
.form-select,
textarea.form-input {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  transition: all 0.2s;
  font-family: inherit;
  resize: vertical;
}

.form-input:focus,
.form-select:focus,
textarea.form-input:focus {
  outline: none;
  border-color: #4caf50;
  box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
}

textarea.form-input:disabled {
  background-color: #f5f5f5;
  color: #999;
  cursor: not-allowed;
}

.modal-footer {
  padding: 24px;
  border-top: 1px solid #eee;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary {
  background: #4caf50;
  color: white;
}

.btn-primary:hover {
  background: #45a049;
}

.btn-secondary {
  background: #f5f5f5;
  color: #666;
}

.btn-secondary:hover {
  background: #e0e0e0;
}

/* 로그인 및 정지 기록 */
.log-section {
  margin-bottom: 32px;
}

.log-section ul {
  list-style-type: disc;
  padding-left: 20px;
  color: #444;
}

/* 반응형 */
@media (max-width: 768px) {
  .modal-body {
    flex-direction: column;
  }
  .modal-left {
    border-right: none;
    padding-right: 0;
  }
  .modal-right {
    padding-left: 0;
  }
}

.section-title {
  display: flex;
  align-items: center;
  gap: 8px;
}
.log-icon {
  color: #4caf50;
}
.section-divider {
  border: none;
  height: 1px;
  background-color: #e0e0e0;
  margin: 12px 0 16px;
}
.suspension-log {
  margin-bottom: 16px;
  padding: 16px;
  border-radius: 10px;
  border: 1px solid #f5c6cb;
  background-color: #fff0f0;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}
.suspension-log.released {
  background-color: #f0fdf4;
  border-color: #c8e6c9;
}
.reason-group .form-select {
  margin-bottom: 8px;
}
.modal-footer .btn {
  padding: 12px 24px;
  font-size: 15px;
}
.modal-footer .btn-primary {
  font-weight: 600;
}
.modal-footer .btn-secondary {
  font-weight: 500;
}
</style>
