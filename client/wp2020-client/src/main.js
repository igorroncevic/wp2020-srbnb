import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import "./../public/css/styles.css";
import "./../public/js/toasts.js";
import routes from "./routes/mainRoutes";
import UserService from "./services/UserService";

Vue.use(VueRouter);

// Problem: https://github.com/vuejs/vue-router/issues/2125
// Solution (kind of): https://stackoverflow.com/questions/53683522/vue-router-handle-urls-incorrectly-in-hash-mode
if (window.location.search) {
  window.location.href = `${window.location.origin}/#${window.location.pathname}${window.location.search}`;
}

const router = new VueRouter({
  mode: "hash",
  routes,
});

router.beforeEach((to, from, next) => {
  document.title = "Srbnb";
  if (to.meta.title) document.title += to.meta.title;
  if (UserService.getToken() === null) {
    console.log("nema tokena");
  } else {
    console.log("ulogovan");
  }

  next();
});

Vue.config.productionTip = false;

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
