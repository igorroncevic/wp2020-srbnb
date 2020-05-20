<template>
  <div class="grid-container">
    <div class="menu">
      <router-link to="/settings" class="navigation">Edit Your Profile</router-link>
      <router-link to="/settings/change-password" class="navigation">Change Password</router-link>
    </div>
    <div class="wrapper">
      <div class="title">{{ title }}</div>
      <div class="user">
        <div class="photo"></div>
        <div class="info">
          <p class="name">Petar Petrovic</p>
          <p class="role">Guest</p>
        </div>
      </div>
      <div class="form">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Settings",
  computed: {
    title() {
      return this.$route.name === "editUserInfo"
        ? "Edit Your Profile"
        : "Change Your Password";
    }
  }
};
</script>

<style scoped>
.grid-container {
  margin-top: 25px;
  display: grid;
  grid-template-columns: repeat(15, 1fr);
  grid-gap: 10px;
}

/* Navigation */
.menu {
  grid-column: 1 / 4;
  background: red;
}

.wrapper {
  grid-column: 6 / 11;
  display: grid;
  grid-template-rows: repeat(10, 8vh);
  grid-row-gap: 10px;
}
/* Title */
.title {
  grid-row: 1/2;
  font-size: 36px;
  font-weight: 600;
}

/* User Container */
.user {
  grid-row: 2/4;
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  justify-content: center;
}
.photo {
  background: orange;
  grid-column: 1;
  height: 140px;
  width: 140px;
}
.info {
  grid-column: 2/9;
  margin-left: 1rem;
}
.name {
  font-size: 2rem;
  color: var(--main-text-color);
}
.role {
  font-size: 1.2rem;
  color: var(--light-text-color);
  margin-top: -2rem;
}

/* Form */
.form {
  grid-row: 4/10;
}

/* Submit Button */
</style>
