<template>
  <div id="wrapper">
    <div class="logo">
      <a href="/">srbnb</a>
    </div>
    <div id="container">
      <form action method="post" style="margin-left: 8px;">
        <div class="title">Sign Up</div>
        <SimpleInput label="Username" :value="''" v-model="username" width="550" />
        <SimpleInput label="Full Name" :value="''" v-model="fullname" width="550" />
        <SimpleInput label="Email" :value="''" v-model="email" width="550" />
        <label for="name">Gender</label>
        <div class="gender" style="margin-bottom: 7px;">
          <radio-button value="Male" @change="setGender" />
          <!-- Pass which one is checked -->
          <radio-button value="Female" style="margin-left: 2rem;" @change="setGender" />
        </div>
        <SimpleInput label="Password" :value="''" v-model="password" width="550" />
        <SimpleInput label="Confirm Password" :value="''" v-model="confirmPassword" width="550" />
        <Button text="Continue" width="400" fontsize="20" id="button" @clicked="login" height="50"/>
        <div class="signup">
          Already have an account?
          <span>
            <router-link to="/login">Log in here.</router-link>
          </span>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import SimpleInput from "./../components/form-components/SimpleInput.vue";
import Button from "./../components/form-components/Button.vue";
import RadioButton from "./../components/form-components/RadioButton.vue";

export default {
  name: "Signup",
  components: {
    SimpleInput,
    Button,
    RadioButton
  },
  data() {
    return {
      username: "",
      fullname: "",
      email: "",
      confirmPassword: "",
      password: "",
      gender: "Male",
      selectedGender: ""
    };
  },
  methods: {
    setGender(value) {
      this.selectedGender = value;
    },
    login() {
      console.log(
        "Username: " + this.username + ", Password: " + this.password
      );
    }
  }
};
</script>

<style scoped>
#wrapper {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
}

.gender {
  margin-top: 1rem;
}

label {
  font-weight: bold;
  font-size: 1.4rem;
  grid-row: 1;
  grid-column: 1;
}

.group {
  display: inline-block;
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
  margin: 5% 17%;
  padding: 40px 50px;
  width: 38rem;
  grid-row: 2;
  grid-column: 5;
  background: var(--background-color);
  margin-bottom: 30px;
  box-shadow: 0px 4px 8px var(--dropshadow-color);
  border-radius: var(--border-radius);
}

#button {
  position: relative;
  margin-top: 20px;
  left: 16%;
}

.signup {
  text-align: center;
  margin-top: 2rem;
  margin-right: 23px;
}
.signup a {
  text-decoration: none;
  color: var(--main-text-color);
  font-weight: bold;
}
</style>
