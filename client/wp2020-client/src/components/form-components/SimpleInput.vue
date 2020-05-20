<template>
  <div id="simpleInput">
    <label for="input">{{label}}</label>
    <input type="text" name="input" :value=inputValue @input="$emit('input', $event.target.value)"/>
  </div>
</template>

<script>
export default {
  props: ["label", "inputValue", "submitted"],

};
</script>

<style scoped>
#simpleInput{
   margin-bottom: 1rem;
}

label {
  font-weight: bold;
  font-size: 1.3rem;
  grid-row: 1;
  grid-column: 1;
}

form input[type="text"] {
  height: 1.8rem;
  padding: 3px;
  width: 31rem;
  margin-top:5px;
  color: var(--main-text-color);
  font-size: 1.3rem;
  border: 1px solid var(--light-text-color);


}
</style>