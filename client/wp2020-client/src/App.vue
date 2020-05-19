<template>
  <div id="app" class="row" :style="{backgroundImage: `url('${backgroundImg}')`}">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  computed: {
    backgroundImg() {
      return this.$route.path === "/"
        ? "./../public/img/home-cover.png"
        : this.$route.path === "/login" || this.$route.path === "/signup"
        ? "./../public/img/login-cover.png"
        : "";
    }
  }
};
</script>

<style>
:root {
  background: var(--background-color);
  color: var(--main-text-color);
  /*font-family: ;
   font-size: ;*/
}

*:focus {
  outline: none;
}

#app {
  margin: 0 auto;
  overflow-x: hidden;
}

.row {
  position: relative;
  max-width: 85%;
  height: 100%;
  width: 85%;
  margin: 0 auto;
}

@media all and (max-width: 86%) {
  .row {
    padding: 0 15px;
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
  }
}
</style>
