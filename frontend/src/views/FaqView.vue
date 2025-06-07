<template>
  <div class="board-page">
    <div class="board-container">
      <h1 class="board-title">자주 묻는 질문</h1>
      <p class="board-subtitle">궁금하신 내용을 아래에서 찾아보세요.</p>

      <!-- 검색 -->
      <div class="search-container">
        <input
          v-model="keyword"
          @keyup.enter="onSearch"
          type="text"
          placeholder="무엇이든 찾아보세요"
          class="search-input"
        />
        <button @click="onSearch" class="search-icon-button" aria-label="search">
          <img src="@/assets/images/icon/search-icon.png" alt="search-icon" class="search-icon-img">
        </button>
      </div>

      <div class="toggle-list">
        <template v-if="displayedFaqs.length > 0">
          <div
            v-for="(faq, index) in displayedFaqs"
            :key="index"
            class="toggle-item"
          >
            <div class="toggle-question"
                 :class="{ active: activeIndex === index }"
                 @click="toggle(index)">
              Q. {{ faq.question }}
              <span class="icon">{{ activeIndex === index ? '✕' : '▾' }}</span>
            </div>
            <div v-show="activeIndex === index" class="toggle-answer">
              A. {{ faq.answer }}
            </div>
          </div>

          <div ref="observerRef" class="observer-trigger" v-if="!isLast"></div>
        </template>

        <!-- 🔍 검색 결과 없음 표시 -->
        <div v-else class="no-result-container">
          <img src="@/assets/images/icon/not_found.png" alt="no result" class="no-result-img" />
          <h2 class="no-result-title">검색 결과가 없습니다.</h2>
          <p class="no-result-desc">단어의 철자를 확인하거나 다시 검색해주세요.</p>
        </div>
      </div>

      <div class="inquiry-section">
        <p>이외의 것이 궁금하신가요?</p>
        <button class="inquiry-btn" @click="goToInquiry">1:1 문의하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import api from '@/utils/api.js'
import '@/assets/styles/board.css';
import { useRouter } from 'vue-router'

const page = ref(0)
const size = 10
const keyword = ref('')
const displayedFaqs = ref([])
const isLast = ref(false)
const activeIndex = ref(null)
const observerRef = ref(null)
const router = useRouter()

let observer

const goToInquiry = () => {
  router.push('/inquiry')
}

const fetchFaqsFromServer = async (reset = false) => {
  try {
    if (reset) {
      page.value = 0
      displayedFaqs.value = []
      isLast.value = false
    }

    const res = await api.get('/api/faq', {
      params: {
        page: page.value,
        size: size,
        keyword: keyword.value
      }
    })
    console.log(res.data);
    displayedFaqs.value.push(...res.data.content)

    if (res.data.last) {
      isLast.value = true
    } else {
      page.value++
    }
  } catch (err) {
    console.error('FAQ 불러오기 실패:', err)
  }
}

const onSearch = () => {
  fetchFaqsFromServer(true)
}

const toggle = (index) => {
  activeIndex.value = activeIndex.value === index ? null : index
}

onMounted(async () => {
  await fetchFaqsFromServer()

  observer = new IntersectionObserver(([entry]) => {
    if (entry.isIntersecting && !isLast.value) {
      fetchFaqsFromServer()
    }
  }, { threshold: 0.1 })

  await nextTick()
  if (observerRef.value) observer.observe(observerRef.value)
})

onBeforeUnmount(() => {
  if (observer && observerRef.value) observer.unobserve(observerRef.value)
})
</script>



