<script setup>
import  '@/assets/styles/modal.css';
import { ref } from 'vue';
import api from '@/utils/api.js'
import { useAuthStore } from '@/stores/authStore.js'

const authStore = useAuthStore();
const emit = defineEmits(['uploaded', 'close']);
const file = ref(null);
const previewUrl = ref(null);

const handleFileChange = (e) => {
  const selected = e.target.files[0];
  if (!selected || !selected.type.startsWith('image/')) {
    alert('이미지 파일만 업로드 가능합니다.');
    file.value = null;
    previewUrl.value = null;
    return;
  }
  file.value = selected;
  previewUrl.value = URL.createObjectURL(selected); // 미리보기 URL 생성
};

const upload = async () => {
  const formData = new FormData();
  formData.append('profileImage', file.value);
  formData.append('memberId', authStore.memberId);
  try {
    const res = await api.put('/api/member/me/profile-image', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    emit('uploaded', res.data.profileImageUrl);
    emit('close');
  } catch (err) {
    console.log(err);
    alert('업로드 실패');
  }
};
</script>

<template>
<div class="modal-overlay" @click.self="$emit('close')">
  <div class="modal-content">
    <h3>프로필 이미지 변경</h3>

    <label class="file-label">
      파일 선택
      <input type="file" accept="image/*" @change="handleFileChange" />
    </label>

    <div v-if="previewUrl" class="preview-container">
      <img :src="previewUrl" alt="미리보기" class="preview-image" />
    </div>
    <p v-else class="preview-placeholder">선택된 이미지가 없습니다.</p>

    <div class="modal-buttons">
      <button @click="upload" :disabled="!file">업로드</button>
      <button @click="$emit('close')">취소</button>
    </div>
  </div>
</div>
</template>
