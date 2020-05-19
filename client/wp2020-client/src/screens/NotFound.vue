<template>
  <div>
    <p>404 Not Found!</p>
    <p>You are being redirected to Home page.</p>
  </div>
</template>

<script>
export default {
  name: "Signup",
  created: function() {
    setTimeout(() => {
      this.$router.push({ name: "home" });
    }, 3000);
  }
};
</script>

<style scoped>
p {
  align-content: center;
  font-size: 40;
  color: red;
}
</style>
