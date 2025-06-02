<template>
  <div class="my-page-container">
    <MyPageSidebar
      :active-menu-item.sync="currentActiveMenuItem"
      @update:active-menu-item="handleSidebarMenuItemClick"
    />

    <div class="main-content-area">
      <component :is="currentComponent" />
    </div>
  </div>
</template>

<script>
import '@/assets/styles/mypage.css';
import MyPageSidebar from '@/components/my-page/MyPageSidebar.vue';
import Profile from '@/components/my-page/Profile.vue';
import EditProfile from '@/components/my-page/EditProfile.vue';
import MyPlant from '@/components/my-page/MyPlant.vue';
import MyComplain from '@/components/my-page/MyComplain.vue';
import MyPost from '@/components/my-page/MyPost.vue';

export default {
  name: 'MyPageView',
  components: {
    MyPageSidebar,
    Profile,
    EditProfile,
    MyPlant,
    MyComplain,
    MyPost,
  },
  data() {
    return {
      currentActiveMenuItem: 'Profile',
    };
  },
  computed: {
    currentComponent() {
      switch (this.currentActiveMenuItem) {
        case 'Profile':
          return 'Profile';
        case 'EditProfile':
          return 'EditProfile';
        case 'MyPlant':
          return 'MyPlant';
        case 'MyComplain':
          return 'MyComplain';
        case 'MyPost':
          return 'MyPost';
        default:
          return 'div'; // fallback component
      }
    },
  },
  methods: {
    handleSidebarMenuItemClick(name) {
      this.currentActiveMenuItem = name;
    },
  },
};
</script>
