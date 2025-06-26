import { ref } from 'vue'
import api from '@/utils/api.js'

export function useMemberApi() {
  const members = ref([])
  const totalCount = ref(0)
  const stats = ref({
    totalUsers: 0,
    activeUsers: 0,
    newUsers: 0,
    totalPlants: 0
  })
  const memberDetail = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const fetchMemberList = async (params = {}) => {
    loading.value = true
    error.value = null
    try {
      const res = await api.get('/api/admin/member', { params })
      members.value = res.data.members
      totalCount.value = res.data.totalCount
    } catch (err) {
      error.value = err
    } finally {
      loading.value = false
    }
  }

  const fetchMemberStats = async () => {
    loading.value = true
    error.value = null
    try {
      const res = await api.get('/api/admin/member/stat')
      stats.value = res.data
    } catch (err) {
      error.value = err
    } finally {
      loading.value = false
    }
  }

  const fetchMemberDetail = async (memberId) => {
    loading.value = true
    error.value = null
    try {
      const res = await api.get(`/api/admin/member/${memberId}/profile`)
      return res.data
    } catch (err) {
      error.value = err
    } finally {
      loading.value = false
    }
  }

  return {
    members,
    totalCount,
    stats,
    memberDetail,
    loading,
    error,
    fetchMemberList,
    fetchMemberStats,
    fetchMemberDetail,
  }
}
