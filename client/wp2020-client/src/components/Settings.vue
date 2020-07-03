<template>
  <div class="grid-container-settings">
    <div class="menu"></div>
    <div class="wrapper">
      <div class="title">{{ title }}</div>
      <div class="user">
        <div class="photo-user"></div>
        <div class="info">
          <p class="name">{{name}} {{surname}}</p>
          <p class="role">{{type}}</p>
        </div>
      </div>
      <div class="form">
        <div>
          <form action method="post">
            <div id="names">
              <SimpleInput
                label="First Name"
                id="firstname"
                v-model="name"
                :inputValue="name"
                width="220"
              />
              <SimpleInput
                label="Last Name"
                id="lastname"
                v-model="surname"
                :inputValue="surname"
                width="243"
              />
            </div>
            <SimpleInput label="New Password" v-model="password" :inputValue="password" />
            <SimpleInput
              label="Confirm new Password"
              v-model="passwordConfirm"
              :inputValue="passwordConfirm"
            />

            <label for="name" style="margin-top: 10px;">Gender</label>
            <div class="gender">
              <radio-button v-if="isMale" value="Male" :checked="isMale" @change="setGender" />
              <!-- Pass which one is checked -->
              <radio-button
                value="Female"
                :checked="!isMale"
                style="margin-left: 10rem;"
                @change="setGender"
              />
            </div>
            <Button
              text="Save Changes"
              width="300"
              height="45"
              fontsize="20"
              id="button"
              @clicked="submitForm"
            />
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SimpleInput from "./form-components/SimpleInput.vue";
import RadioButton from "./form-components/RadioButton.vue";
import Button from "./form-components/Button.vue";
import UserService from "./../services/UserService";

export default {
  name: "Settings",
  components: {
    SimpleInput,
    RadioButton,
    Button
  },
  async beforeMount() {
    var userData = await UserService.getMyInfo();
    this.name = userData.name;
    this.surname = userData.lastname;
    this.gender = userData.gender;
    this.type = userData.type;
    this.setGender(this.gender);
  },
  data() {
    return {
      name: "",
      surname: "",
      gender: "",
      selectedGender: "",
      password: "",
      passwordConfirm: "",
      type: ""
    };
  },
  computed: {
    isMale() {
      return this.gender === "Male" ? "checked" : "";
    },
    title() {
      return "Edit Your Profile";
    }
  },
  methods: {
    setGender(value) {
      this.selectedGender = value;
    },
    async submitForm() {
      if (this.name == "" || this.email == "" || this.surname == "") {
        this.$toasted.global.emptyFields();
        return;
      } else if (this.password != this.passwordConfirm) {
        this.$toasted.global.badConfirmPassword();
        return;
      }
      var data = {
        name: this.name,
        lastname: this.surname,
        gender: this.selectedGender,
        password: this.password
      };

      var success = await UserService.updateUserData(data);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 2000);
      } else {
        this.$toasted.global.unknownError();
        setTimeout(() => {
          this.$router.go();
        }, 2000);
      }
    }
  }
};
</script>

<style scoped>
.grid-container-settings {
  margin-top: 5px;
  display: grid;
  grid-template-columns: repeat(15, 1fr);
  grid-gap: 10px;
}

/* Navigation */
.menu {
  grid-column: 1 / 4;
}

.wrapper {
  grid-row: 1;
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
  grid-column: 1;
  margin-left: 0;
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  justify-content: center;
}
.photo-user {
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

#names {
  display: flex;
  margin-bottom: 15px;
}

#firstname {
  flex: 0 0 40%;
  width: 50%;
}

#lastname {
  margin-left: 5%;
  flex: 0 0 50%;
  width: 50%;
}

label {
  font-weight: bold;
  font-size: 1.5rem;
  grid-row: 1;
  grid-column: 1;
}

.gender {
  margin-top: 1rem;
}

#button {
  margin-left: 6rem;
  margin-top: 4rem;
}
</style>
