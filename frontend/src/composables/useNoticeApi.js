import { ref } from 'vue'
import api from '@/utils/api.js'

export function useNoticeApi() {
  const notices = ref([])
  const totalCount = ref(0)
  const noticeDetail = ref(null)
  const loading = ref(false)
  const error = ref(null)

  // 공지사항 목록 조회
  const fetchNoticeList = async ({ page = 1, size = 8, keyword = '', searchType = 'TITLE_CONTENT' }) => {
    loading.value = true
    error.value = null
    try {
      const res = await api.get('/api/notice', {
        params: { page, size, keyword, searchType },
      })
      notices.value = res.data.notices
      totalCount.value = res.data.totalCount
    } catch (err) {
      error.value = err
    } finally {
      loading.value = false
    }
  }

  // 공지사항 상세 조회
  const fetchNoticeDetail = async (noticeId) => {
    loading.value = true
    error.value = null
    try {
      const res = await api.get(`/api/notice/${noticeId}`)
      return res.data
    } catch (err) {
      error.value = err
    } finally {
      loading.value = false
    }
  }

  // 이전 글, 다음 글 조회
  const fetchAdjacentNotices = async (id) => {
    const res = await api.get(`/api/notice/${id}/adjacent`)
    return res.data
  }

  return {
    notices,
    totalCount,
    noticeDetail,
    loading,
    error,
    fetchNoticeList,
    fetchNoticeDetail,
    fetchAdjacentNotices
  }
}
