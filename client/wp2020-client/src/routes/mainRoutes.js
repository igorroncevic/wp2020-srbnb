import Main from "./../screens/Main.vue";
import Login from "./../screens/Login.vue";
import Signup from "./../screens/Signup.vue";
import NotFound from "./../screens/NotFound.vue";
import Subroutes from "./subRoutes";
//import UserService from "./../services/UserService";

export default [
  {
    path: "/",
    component: Main,
    children: Subroutes,
  },
  {
    path: "/login",
    name: "login",
    component: Login,
    meta: {
      title: " -  Login",
    },
    /*beforeEnter: function(to, from, next) {
      if (UserService.getToken()) {
        //this.$toasted.global.alreadyLoggedIn();  //baca error "Cannot read property toasted of undefined"
        //this.$router.push({ name: "home" });
      } else {
        next();
      }
    },*/
  },
  {
    path: "/signup",
    name: "signup",
    component: Signup,
    meta: {
      title: " - Signup",
    },
    /*beforeEnter: function(to, from, next) {
      if (UserService.getToken()) {
        //this.$toasted.global.alreadyLoggedIn();
        this.$router.push({ name: "home" });
      } else {
        next();
      }
    },*/
  },
  {
    path: "*",
    name: "notFound",
    component: NotFound,
    meta: {
      title: " - 404 Not Found!",
    },
  },
];
