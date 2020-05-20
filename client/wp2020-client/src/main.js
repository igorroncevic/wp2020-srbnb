import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import routes from "./routes/mainRoutes";
import './../public/css/styles.css';

Vue.use(VueRouter);

const router = new VueRouter({
  routes,
  mode: "history",
});

router.beforeEach((to, from, next)=>{
   document.title = 'Srbnb';
   if(to.meta.title) document.title += to.meta.title;
   next();
})

Vue.config.productionTip = false;

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
