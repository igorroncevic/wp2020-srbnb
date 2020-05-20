<template>
  <div id="app" :style="{background: 'url(' + backgroundImage +')'}">
    <router-view class="row"/>
  </div>
</template>

<script>
import homeBg from "./../public/img/home-cover.png";
import loginSignupBg from "./../public/img/login-cover.png";

export default {
  name: "App",
  computed: {
    backgroundImage() {
      console.log();
      return this.$route.name === "home"
        ? homeBg
        : this.$route.name === "login" || this.$route.name === "signup"
        ? loginSignupBg
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
  height: 100vh;
}

.row {
  position: relative;
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
