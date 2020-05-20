<template>
  <div class="">
     <form action method="post">
      <SimpleInput label="Current Password" v-model="currentPassword" :inputValue="''" />
      <SimpleInput label="New Password" v-model="newPassword" :inputValue="''" />
      <SimpleInput label="Confirm" v-model="confirmPassword" :inputValue="''" />
      <Button text="Save Changes" width="300" fontsize="20" id="button" @clicked="submitForm" />
    </form>
  </div>
</template>

<script>
import SimpleInput from "./form-components/SimpleInput.vue";
import Button from "./form-components/Button.vue";

export default {
  components: {
    SimpleInput,
    Button
  },
  data() {
    return {
      currentPassword: "",
      newPassword: "",
      confirmPassword: ""
    };
  },
  methods: {
    submitForm(){
       console.log("Current Password: " + this.currentPassword + ", new password: " + this.newPassword + ", confirm: " + this.confirmPassword);
    }
  }
};
</script>

<style scoped>
#button {
  margin-left: 6rem;
  margin-top: 6rem;
}

</style>