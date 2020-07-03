import Main from "./../screens/Main.vue";
import Login from "./../screens/Login.vue";
import Signup from "./../screens/Signup.vue";
import NotFound from "./../screens/NotFound.vue";
import Subroutes from "./subRoutes";

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
    }
  },
  {
    path: "/signup",
    name: "signup",
    component: Signup,
    meta: {
      title: " - Signup",
    }
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
