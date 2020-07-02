<template>
  <div id="wrapper">
    <div class="logo">
      <a href="/">srbnb</a>
    </div>
    <div id="container">
      <form action method="post" style="margin-left: 8px;" @submit.prevent="login">
        <div class="title">Log In</div>
        <SimpleInput label="Username" v-model="username" width="400" />
        <SimpleInput label="Password" v-model="password" width="400" />
        <Button text="Continue" width="350" fontsize="20" id="button" height="45" />
        <div class="signup">
          Don't have an account?
          <span>
            <router-link to="/signup">Sign up here.</router-link>
          </span>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import SimpleInput from "./../components/form-components/SimpleInput.vue";
import Button from "./../components/form-components/Button.vue";
import UserService from "./../services/UserService";

export default {
  name: "Login",
  components: {
    SimpleInput,
    Button
  },
  beforeCreate() {
    if (UserService.getToken()) {
      this.$toasted.global.alreadyLoggedIn();
      this.$router.push({ name: "home" });
    }
  },
  data() {
    return {
      username: "",
      password: ""
    };
  },
  methods: {
    async login() {
      if (this.username == "" || this.password == "") {
        this.$toasted.global.emptyLoginFields();
      }

      var loginSuccess = await UserService.login({
        username: this.username,
        password: this.password
      });
      console.log("Login success - " + loginSuccess);
      if (loginSuccess) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.push({ name: "home" });
        }, 2500);
      } else {
        this.$toasted.global.loginError();
      }
    }
  }
};
</script>

<style scoped>
#wrapper {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
}

.logo a {
  float: left;
  position: relative;
  top: 13%;
  font-size: 26px;
  text-decoration: none;
  color: var(--brand-color);
}

.title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  margin-bottom: 30px;
}

#container {
  position: relative;
  margin: 30% 27%;
  padding: 40px 50px;
  width: 27rem;
  grid-row: 2;
  grid-column: 5;
  background: var(--background-color);
  margin-bottom: 30px;
  box-shadow: 0px 4px 8px var(--dropshadow-color);
  border-radius: var(--border-radius);
}

#button {
  margin-left: 2rem;
  margin-top: 1.5rem;
}

.signup {
  text-align: center;
  margin-top: 2rem;
  margin-right: 7px;
}
.signup a {
  text-decoration: none;
  color: var(--main-text-color);
  font-weight: bold;
}
</style>
