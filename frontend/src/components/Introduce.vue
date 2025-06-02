<template>
  <div class="full-screen-container">
    <img src="@/assets/images/landing-img.png" alt="Maple Energy Product" class="full-screen-image" />
    <div class="content">
      <h1 class="font-bold">Welcome Plant Diary</h1>
      <button class="signup-button" @click="goToSignUp()">
        🚀 Enjoy Plands!
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Introduce',
  methods: {
    goToSignUp() {
      this.$router.push('/signup');
    }
  }
};
</script>

<style scoped>
.full-screen-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.full-screen-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: 1;
}

.content {
  position: absolute;
  top: 60%;
  left: 30%;
  transform: translate(-50%, -50%); /* 중앙 정렬 */
  text-align: left;
  font-size: 30px;
  color: #000000;
  z-index: 10;
}

.content h2 {
  margin-bottom: 1.5rem;
}

.content p {
  margin-bottom: 1.5rem;
}

.signup-button {
  display: inline-block;
  padding: 20px 35px;
  border: none;
  border-radius: 50px;
  background-color: #000000;
  color: #00C957;
  font-size: 30px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.signup-button:hover {
  border-color: #00C957;
}
</style>
