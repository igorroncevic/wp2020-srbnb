<template>
  <div>
    <form action method="post">
      <SimpleInput label="Full Name" inputValue="Petar Petrović"></SimpleInput>
      <SimpleInput label="Email" inputValue="pero@email.com"></SimpleInput>

      <label for="name" style="margin-top: 10px;">Gender</label>
      <div class="gender">
        <radio-button value="Male" checked="checked" /> <!-- Pass which one is checked -->
        <radio-button value="Female" style="margin-left: 10rem;"/>
      </div>
      <Button text="Save Changes" width="300" fontsize="20" id="button"/>
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

#button{
   margin-left: 6rem;
   margin-top: 8rem;
}

</style>