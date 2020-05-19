import Home from "./../components/Home.vue";
import SearchResults from "./../components/SearchResults.vue";
import Settings from "./../components/Settings.vue";
import Apartment from "./../components/Apartment.vue";

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
    name: "settings",
    component: Settings,
  },
  {
    path: "/Apartment",
    name: "apartment",
    component: Apartment,
  },
];
