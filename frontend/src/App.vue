<template>
  <component :is="layoutComponent">
    <router-view />
  </component>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'

import DefaultLayout from '@/layouts/DefaultLayout.vue'
import AdminLayout from '@/layouts/AdminLayout.vue'

const route = useRoute()

const layoutComponent = computed(() => {
  const layout = route.meta.layout
  return layout === 'admin' ? AdminLayout : DefaultLayout
})
</script>
