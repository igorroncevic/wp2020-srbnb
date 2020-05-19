<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
:root {
   background: var(--background-color);
   color: var(--main-text-color);
   /*font-family: ;
   font-size: ;*/
}

#app {
  width: 85%;
  margin: 0 auto;
}
</style>
