<template>
  <div>
    <label for="input" :style="{color: labelColor}">{{labelText}}</label>
    <input type="text" class="input" :name="name" :style="{width: width + 'px', height: height+'px'}" :value="value" @input="$emit('input', $event.target.value)" required />
  </div>
</template>

<script>
export default {
   props: ["labelText", "value", "name", "width", "height", "labelColor"],

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