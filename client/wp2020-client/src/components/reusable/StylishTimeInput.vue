<template>
  <div>
    <label for="input" :style="{color: labelColor}">{{labelText}}</label>
    <input required min="6:00:00" step="1800" max="23:30:00" type="time" class="input" name="input" :style="{width: width + 'px', height: height+'px'}" :value=inputValue @input="$emit('input', $event.target.value)" />
  </div>
</template>

<script>
export default {
   props: ["labelText", "inputValue", "width", "height", "labelColor"],

};
</script>

<style scoped>
label{
   font-size: 24px;
   font-weight: 500;
   color: var(--brand-color);
}

.input{
   margin-top: 8px;
   height: 2.3rem;
   font-size: 24px;
   width: 35rem;
   border-top-style: hidden;
   border-left-style: hidden;
   border-right-style: hidden;
   border-color: var(--medium-text-color);
}
</style>