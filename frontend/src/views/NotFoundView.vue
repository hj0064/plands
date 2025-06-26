<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <img src="@/assets/images/icon/not_found.png" alt="Not Found" class="not-found-image" />
      <div class="error-code">404</div>
      <div class="error-message">
        <h1>페이지를 찾을 수 없습니다</h1>
        <p>요청하신 페이지가 존재하지 않거나 이동되었을 수 있습니다.</p>
      </div>
      <div class="error-actions">
        <button @click="goHome" class="btn-primary">홈으로 돌아가기</button>
        <button @click="goBack" class="btn-secondary">이전 페이지</button>
      </div>
    </div>
    <div class="decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  name: 'NotFoundView',
  setup() {
    const router = useRouter()

    const goHome = () => {
      router.push('/')
    }

    const goBack = () => {
      if (window.history.length > 1) {
        router.go(-1)
      } else {
        router.push('/')
      }
    }

    return {
      goHome,
      goBack
    }
  }
}
</script>

<style scoped>
.not-found-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f6fdf8 0%, #d7f0df 50%, #c0e4cd 100%);
  position: relative;
  overflow: hidden;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.not-found-content {
  text-align: center;
  color: #2f2f2f;
  z-index: 2;
  position: relative;
  max-width: 600px;
  padding: 2rem;
}

.not-found-image {
  width: 120px;
  margin-bottom: 1.5rem;
}

.error-code {
  font-size: 7rem;
  font-weight: 800;
  margin-bottom: 1rem;
  color: #6db38b;
  line-height: 1;
}

.error-message h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  font-weight: 600;
  color: #2f2f2f;
}

.error-message p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0.9;
  line-height: 1.6;
}

.error-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.btn-primary,
.btn-secondary {
  padding: 12px 24px;
  border: none;
  border-radius: 50px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 150px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.btn-primary {
  background-color: #6db38b;
  color: white;
  border: none;
}

.btn-primary:hover {
  background-color: #5c9e7a;
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
}

.btn-secondary {
  background-color: white;
  color: #6db38b;
  border: 2px solid #6db38b;
}

.btn-secondary:hover {
  background-color: #f0fdf4;
  border-color: #5c9e7a;
  transform: translateY(-2px);
}

.decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 1;
}

.circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  animation: float 6s ease-in-out infinite;
}

.circle-1 {
  width: 80px;
  height: 80px;
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}

.circle-2 {
  width: 120px;
  height: 120px;
  top: 60%;
  right: 10%;
  animation-delay: 2s;
}

.circle-3 {
  width: 60px;
  height: 60px;
  bottom: 20%;
  left: 20%;
  animation-delay: 4s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px) rotate(0deg);
    opacity: 0.7;
  }
  50% {
    transform: translateY(-20px) rotate(180deg);
    opacity: 1;
  }
}

/* 반응형 */
@media (max-width: 768px) {
  .error-code {
    font-size: 6rem;
  }
  .error-message h1 {
    font-size: 2rem;
  }
  .error-message p {
    font-size: 1rem;
  }
  .error-actions {
    flex-direction: column;
    align-items: center;
  }
  .btn-primary,
  .btn-secondary {
    width: 100%;
    max-width: 250px;
  }
  .not-found-content {
    padding: 1rem;
  }
}

@media (max-width: 480px) {
  .error-code {
    font-size: 4rem;
  }
  .error-message h1 {
    font-size: 1.5rem;
  }
}
</style>
