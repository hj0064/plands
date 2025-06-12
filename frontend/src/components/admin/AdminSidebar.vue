<template>
  <aside :class="['sidebar', { 'sidebar-closed': !layoutStore.isSidebarOpen }]">
    <div class="sidebar-header">
      <span class="logo-text">
        {{ layoutStore.isSidebarOpen ? 'NiceAdmin' : 'N' }}
      </span>
    </div>

    <nav class="sidebar-nav">
      <ul>
        <li v-for="item in menuItems" :key="item.name">
          <router-link :to="item.path" class="nav-link">
            <svg
              class="nav-icon"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                :d="item.icon"
              />
            </svg>
            <span v-show="layoutStore.isSidebarOpen" class="nav-text">{{ item.name }}</span>
          </router-link>
        </li>
      </ul>
    </nav>
  </aside>
</template>

<script setup>
import { ref } from 'vue'
import { useLayoutStore } from '@/stores/layout'

const layoutStore = useLayoutStore()

const menuItems = ref([
  { name: 'Dashboard', path: '/admin/', icon: 'M4 6h16M4 12h16M4 18h16' },
  { name: 'Components', path: '/components', icon: 'M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M5 11v6' },
  { name: 'Forms', path: '/forms', icon: 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z' },
  { name: 'Tables', path: '/tables', icon: 'M3 10h18M3 14h18m-9-4v8' },
])
</script>

<style>
.sidebar {
  background-color: #fff;
  color: #4b5563; /* gray-700 */
  border-right: 1px solid #e5e7eb; /* gray-200 */
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
  justify-content: center;
  border-bottom: 1px solid #e5e7eb;
  font-weight: bold;
  font-size: 1.5rem;
  color: #2563eb; /* blue-600 */
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

.sidebar-nav li {
  margin: 0;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  color: #4b5563;
  text-decoration: none;
  transition: background-color 0.2s ease;
}

.nav-link:hover {
  background-color: #f3f4f6;
}

.nav-icon {
  width: 24px;
  height: 24px;
  stroke-width: 2;
}

.nav-text {
  margin-left: 12px;
  white-space: nowrap;
}
</style>
