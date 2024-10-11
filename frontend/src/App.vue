<template>
  <div id="app">
    <header>
      <h1>My Plant Journal</h1>
      <!-- 여기에 헤더, 네비게이션 바 등을 추가할 수 있습니다 -->
    </header>

    <router-view></router-view> <!-- 여기서 라우트에 따라 컴포넌트가 렌더링됩니다. -->

    <footer>
      <p>© 2024 My Plant Journal</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style>
/* 여기에 스타일 추가 */
#app {
  text-align: center;
}
</style>
