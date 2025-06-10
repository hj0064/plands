<script setup>
import { ref } from 'vue'
import { defineEmits, defineProps } from 'vue'

const emit = defineEmits(['search'])
const prop = defineProps({
  totalCount: {
    type: Number,
    default: 0
  },

})
const keyword = ref('')
const searchType = ref('TITLE_CONTENT')

const searchTypes = [
  { label: '제목 + 내용', value: 'TITLE_CONTENT' },
  { label: '제목', value: 'TITLE' },
  { label: '내용', value: 'CONTENT' },
]

const doSearch = () => {
  emit('search', { keyword: keyword.value.trim(), searchType: searchType.value })
}

// Enter 눌러도 검색 실행
const onEnter = (e) => {
  if (e.key === 'Enter') doSearch()
}
</script>

<template>
  <div class="search-wrapper">
    <div class="notice-search">
      <div class="search-type-container">
        <select v-model="searchType">
          <option v-for="type in searchTypes" :key="type.value" :value="type.value">
            {{ type.label }}
          </option>
        </select>
      </div>

      <div class="search-type-container">
        <input
          type="text"
          v-model="keyword"
          placeholder="검색어를 입력하세요"
          @keyup="onEnter"
          class="search-input"
        />
        <button @click="doSearch" class="search-icon-button">
          <img src="@/assets/images/icon/search-icon.png" alt="search" class="search-icon-img" />
        </button>
      </div>
    </div>

    <div class="search-count" v-if="prop.totalCount > 0">
      <p>total: {{ prop.totalCount }}</p>
    </div>
  </div>
</template>
