import Home from "./../components/Home.vue";
import SearchResults from "./../components/SearchResults.vue";
import Settings from "./../components/Settings.vue";
import Apartment from "./../components/Apartment.vue";
import EditUserInfo from "./../components/EditUserInfo.vue";
import ChangePassword from "./../components/ChangePassword.vue";

export default [
  {
    path: "",
    name: "home",
    component: Home,
  },
  {
    path: "/search-results",
    name: "search-results",
    component: SearchResults,
  },
  {
    path: "/settings",
    component: Settings,
    children: [
      {
        path: "",
        component: EditUserInfo,
        name: 'editUserInfo'
      },
      {
        path: "change-password",
        component: ChangePassword,
        name: 'changePassword'
      },
    ],
  },
  {
    path: "/Apartment",
    name: "apartment",
    component: Apartment,
  },
];
