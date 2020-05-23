<template>
  <button type="submit" :style="{width: width + 'px', height: height + 'px', fontSize: fontsize + 'px'}" @click="$emit('clicked')">
    {{text}}
  </button>
</template>

<script>
export default {
   props: ['text', 'width', 'height', 'fontsize'],
   methods: {
      clicked(){
         this.$emit('clicked');
      }
   }
};
</script>

<style scoped>
button[type="submit"] {
  cursor: pointer;
  width: auto;
  font-size: 16px;
  padding-right: 2.5%;
  text-align: center;

  color: var(--background-color);
  background-color: var(--brand-color);

  border: 0;
  border-radius: var(--border-radius);
  -webkit-border-radius: var(--border-radius);
  transition: all 0.3s ease;
  -webkit-transition: all 0.3 ease;
  -moz-transition: all 0.3 ease;
  -o-transition: all 0.3 ease;
}

button[type="submit"]:hover {
  background: var(--brand-color-hover);
}
</style>