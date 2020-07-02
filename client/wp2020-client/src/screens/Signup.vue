<template>
  <div id="wrapper">
    <div class="logo">
      <a href="/">srbnb</a>
    </div>
    <div id="container">
      <form action method="post" style="margin-left: 18px;" @submit.prevent="signup">
        <div class="title">Sign Up</div>
        <SimpleInput label="Username" :value="''" v-model="username" width="550" />
        <div class="name-container">
          <SimpleInput
            id="firstname"
            label="First name"
            :value="''"
            v-model="firstname"
            width="230"
          />
          <SimpleInput id="lastname" label="Last name" :value="''" v-model="lastname" width="287" />
        </div>
        <label for="name">Gender</label>
        <div class="gender" style="margin-bottom: 7px;">
          <radio-button value="Male" @change="setGender" :checked="true" />
          <!-- Pass which one is checked -->
          <radio-button value="Female" style="margin-left: 2rem;" @change="setGender" />
        </div>
        <SimpleInput label="Password" :value="''" v-model="password" width="550" />
        <SimpleInput label="Confirm Password" :value="''" v-model="confirmPassword" width="550" />
        <Button text="Continue" width="400" fontsize="20" id="button" height="50" />
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
import UserService from "./../services/UserService";

export default {
  name: "Signup",
  components: {
    SimpleInput,
    Button,
    RadioButton
  },
  beforeCreate() {
    if (UserService.getToken()) {
      this.$toasted.global.alreadyLoggedIn();
      this.$router.push({ name: "home" });
    }
  },
  beforeMount() {
    this.setGender("Male");
  },
  data() {
    return {
      username: "",
      firstname: "",
      lastname: "",
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
    async signup() {
      if (
        this.username == "" ||
        this.firstname == "" ||
        this.lastname == "" ||
        this.password == "" ||
        this.confirmPassword == "" ||
        this.selectedGender == ""
      ) {
        this.$toasted.global.emptyFields();
      } else if (this.confirmPassword != this.password) {
        this.$toasted.global.badConfirmPassword();
      } else {
        var signupSuccess = await UserService.signup({
          username: this.username,
          name: this.firstname,
          lastname: this.lastname,
          password: this.password,
          gender: this.selectedGender
        });

        if (signupSuccess) {
          this.$toasted.global.successMessage();
          setTimeout(() => {
            this.$router.push({ name: "login" });
          }, 1000);
        } else {
          this.$toasted.global.signupError();
        }
      }
    }
  }
};
</script>

<style scoped>
.name-container {
  width: 80%;
  display: flex;
  margin-bottom: 15px;
}

#firstname {
  flex: 0 0 40%;
}

#lastname {
  margin-left: 5%;
  flex: 0 0 50%;
}

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
  margin-right: 30px;
}

#container {
  position: relative;
  margin: 8% 17%;
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
