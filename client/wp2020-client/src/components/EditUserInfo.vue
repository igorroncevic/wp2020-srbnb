<template>
  <div>
    <form action method="post">
      <SimpleInput label="Full Name" v-model="name" :inputValue="name" />
      <SimpleInput label="Email" v-model="email" :inputValue="email" />

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
      <Button text="Save Changes" width="300" fontsize="20" id="button" @clicked="submitForm" />
    </form>
  </div>
</template>

<script>
import SimpleInput from "./SimpleInput.vue";
import RadioButton from "./RadioButton.vue";
import Button from "./Button.vue";

export default {
  components: {
    SimpleInput,
    RadioButton,
    Button
  },
  data() {
    return {
      name: "Petar Petrović",
      email: "pero@gmail.com",
      gender: "Male",
      selectedGender: "Male"
    };
  },
  computed: {
    isMale() {
      return this.gender === "Male" ? "checked" : "";
    }
  },
  methods: {
    setGender(value) {
      this.selectedGender = value;
    },
    submitForm(){
       console.log("Full name: " + this.name + ", email: " + this.email + ", gender: " + this.selectedGender);
    }
  }
};
</script>

<style scoped>
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
  margin-top: 8rem;
}
</style>