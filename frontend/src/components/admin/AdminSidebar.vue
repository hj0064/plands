<template>
  <aside :class="['admin-sidebar', { 'sidebar-closed': !layoutStore.isSidebarOpen }]">
    <div class="sidebar-header">
      <span class="logo-text">
        {{ layoutStore.isSidebarOpen ? 'Admin' : 'A' }}
      </span>
    </div>

    <!-- 유저 섹션 추가 -->
    <div class="user-section">
      <div class="user-profile">
        <svg
          class="user-icon"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
          />
        </svg>
        <span v-show="layoutStore.isSidebarOpen" class="user-text">내 정보</span>
      </div>
      <div class="site-link" @click="goToSite">
        <svg
          class="site-icon"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"
          />
        </svg>
        <span v-show="layoutStore.isSidebarOpen" class="site-text">Plands 사이트</span>
      </div>
    </div>

    <nav class="sidebar-nav">
      <ul>
        <li v-for="item in menuItems" :key="item.name" class="nav-item">
          <!-- 단일 메뉴일 때 router-link로 이동 -->
          <router-link
            v-if="!item.children"
            :to="item.path"
            class="nav-link"
          >
            <svg class="nav-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="item.icon" />
            </svg>
            <span v-show="layoutStore.isSidebarOpen" class="nav-text">{{ item.name }}</span>
          </router-link>

          <!-- 서브 메뉴가 있는 경우 클릭으로 드롭다운 -->
          <div
            v-else
            class="nav-link"
            @click="toggleDropdown(item)"
            :class="{ 'has-children': item.children }"
          >
            <svg class="nav-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="item.icon" />
            </svg>
            <span v-show="layoutStore.isSidebarOpen" class="nav-text">{{ item.name }}</span>
            <svg v-if="item.children" class="dropdown-arrow" :class="{ 'rotated': item.open }" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
            </svg>
          </div>

          <!-- 서브 메뉴 -->
          <transition name="submenu">
            <ul v-if="item.children && item.open" class="submenu">
              <li v-for="child in item.children" :key="child.name">
                <router-link :to="child.path" class="nav-link sub-link">
                  <span v-show="layoutStore.isSidebarOpen" class="nav-text">{{ child.name }}</span>
                </router-link>
              </li>
            </ul>
          </transition>
        </li>

      </ul>
    </nav>
  </aside>
</template>

<script setup>
import { ref } from 'vue'
import { useLayoutStore } from '@/stores/layout'
import { useRouter } from 'vue-router'

const layoutStore = useLayoutStore()
const router = useRouter()

const toggleDropdown = (item) => {
  if (item.children) {
    item.open = !item.open
  }
}

const goToSite = () => {
  router.push('/')
}

const menuItems = ref([
  {
    name: '대시보드',
    path: '/admin',
    icon: 'M4 6h16M4 12h16M4 18h16',
  },
  {
    name: '사용자 관리',
    path: '/admin/users',
    icon: 'M5.121 17.804A7 7 0 0112 15a7 7 0 016.879 2.804M12 12a5 5 0 100-10 5 5 0 000 10z',
  },
  {
    name: '콘텐츠 관리',
    icon: 'M3 10h18M3 14h18m-9-4v8',
    children: [
      { name: '식물 관리', path: '/admin/plants' },
      { name: '커뮤니티 관리', path: '/admin/community' },
      { name: '댓글 관리', path: '/admin/comments' },
    ],
  },
  {
    name: '고객 응대 관리',
    icon: 'M4 6h16M4 12h8m-8 6h16',
    children: [
      { name: '공지사항 관리', path: '/admin/notices' },
      { name: 'FAQ 관리', path: '/admin/faqs' },
      { name: '문의하기 관리', path: '/admin/inquiries' },
      { name: '신고 관리', path: '/admin/reports' },
    ],
  },
  {
    name: '운영 도구',
    icon: 'M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z',
    children: [
      // { name: '푸시/알림 관리', path: '/admin/notifications' },
      { name: '팝업 관리', path: '/admin/popups' },
    ],
  },
  {
    name: '통계',
    path: '/admin/statistics',
    icon: 'M11 17l-5-5m0 0l5-5m-5 5h12',
  },
  {
    name: '환경 설정',
    path: '/admin/settings',
    icon: 'M12 4v16m8-8H4',
  },
])
</script>

<style scoped>
.admin-sidebar {
  background-color: #fff;
  color: #4b5563;
  border-right: 1px solid #e5e7eb;
  transition: width 0.3s ease;
  width: 256px;
  display: flex;
  flex-direction: column;
}

.sidebar-closed {
  width: 80px;
}

.sidebar-header {
  height: 64px;
  display: flex;
  align-items: center;
  padding: 0 16px;
  border-bottom: 1px solid #e5e7eb;
  font-weight: bold;
  font-size: 1.5rem;
  color: #16a34a;
}

.logo-text {
  text-align: left;
}

/* 유저 섹션 스타일 */
.user-section {
  padding: 16px 0;
  border-bottom: 1px solid #e5e7eb;
}

.user-profile,
.site-link {
  display: flex;
  align-items: center;
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.user-profile:hover,
.site-link:hover {
  background-color: #f3f4f6;
}

.user-icon,
.site-icon {
  width: 20px;
  height: 20px;
  stroke-width: 2;
  color: #6b7280;
}

.user-text,
.site-text {
  margin-left: 12px;
  font-size: 0.875rem;
  color: #6b7280;
  white-space: nowrap;
}

.sidebar-nav {
  margin-top: 16px;
  flex-grow: 1;
}

.sidebar-nav ul {
  list-style: none;
  padding-left: 0;
  margin: 0;
}

.nav-item {
  margin: 0;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  color: #4b5563;
  text-decoration: none;
  cursor: pointer;
  transition: background-color 0.2s ease;
  position: relative;
}

.nav-link:hover {
  background-color: #f3f4f6;
}

.nav-icon {
  width: 24px;
  height: 24px;
  stroke-width: 2;
  flex-shrink: 0;
}

.nav-text {
  margin-left: 12px;
  white-space: nowrap;
  flex-grow: 1;
  text-align: left;
}

.dropdown-arrow {
  width: 16px;
  height: 16px;
  stroke-width: 2;
  margin-left: auto;
  transition: transform 0.2s ease;
}

.dropdown-arrow.rotated {
  transform: rotate(180deg);
}

.submenu {
  background-color: #f9fafb;
  overflow: hidden;
}

.submenu .nav-link {
  padding: 8px 16px 8px 52px;
  font-size: 0.875rem;
}

.sub-link:hover {
  background-color: #f3f4f6;
}

/* 서브메뉴 애니메이션 */
.submenu-enter-active,
.submenu-leave-active {
  transition: all 0.3s ease;
  max-height: 300px;
}

.submenu-enter-from,
.submenu-leave-to {
  max-height: 0;
  opacity: 0;
}

/* 사이드바가 닫혔을 때 드롭다운 화살표 숨기기 */
.sidebar-closed .dropdown-arrow {
  display: none;
}

/* 사이드바가 닫혔을 때 서브메뉴 숨기기 */
.sidebar-closed .submenu {
  display: none;
}
</style>
