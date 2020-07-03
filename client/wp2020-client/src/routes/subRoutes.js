import Home from "./../components/Home.vue";
import SearchResults from "./../components/SearchResults.vue";
import Settings from "./../components/Settings.vue";
import ApartmentPreview from "./../components/ApartmentPreview.vue";
import UserProfile from "./../components/UserProfile.vue";
import SearchUsers from "./../components/SearchUsers.vue";
import PreviewBookings from "./../components/PreviewBookings.vue";
import AddApartment from "./../components/AddApartment.vue";
import Amenities from "./../components/Amenities.vue";
import Vue from "vue";
/*eslint-disable*/
import toasts from "./../../public/js/toasts";

export default [
  {
    path: "",
    name: "home",
    component: Home,
    meta: {
      title: " - Vacation Rentals, Homes, Experiences & Places",
    },
  },
  {
    path: "search-results",
    name: "search-results",
    component: SearchResults,
    meta: {
      title: " - Search Results",
    },
  },
  {
    path: "/settings",
    component: Settings,
    meta: {
      title: " - Edit Your Profile",
    },
  },
  {
    path: "apartment-preview/:id",
    name: "apartment-preview",
    component: ApartmentPreview,
    meta: {
      title: " - Apartment Preview",
    },
    props: true,
  },
  {
    path: "user-profile",
    name: "user-profile",
    component: UserProfile,
    meta: {
      title: " - User Profile",
    },
  },
  {
    path: "search-users",
    name: "search-users",
    component: SearchUsers,
    meta: {
      title: " - Search Users",
    },
    beforeEnter: (to, from, next) => {
      console.log(Vue.prototype.$userType);
      if (
        Vue.prototype.$userType != "Admin" &&
        Vue.prototype.$userType != "Host"
      ) {
        Vue.toasted.show("You have no permissions to perform this action.", {
          type: "error",
          icon: "error_outline",
          iconPack: "material",
          position: "top-center",
          duration: 2500,
          singleton: true,
        });
        window.location.href = `${window.location.origin}/#/`;
        return;
      }
      next();
    },
  },
  {
    path: "preview-bookings",
    name: "preview-bookings",
    component: PreviewBookings,
    meta: {
      title: " - Preview Bookings",
    },
  },
  {
    path: "amenities",
    name: "amenities",
    component: Amenities,
    meta: {
      title: " - Manage Amenities",
    },
    beforeEnter: (to, from, next) => {
      if (Vue.prototype.$userType != "Admin") {
        Vue.toasted.show("You have no permissions to perform this action.", {
          type: "error",
          icon: "error_outline",
          iconPack: "material",
          position: "top-center",
          duration: 2500,
          singleton: true,
        });
        window.location.href = `${window.location.origin}/#/`;
        return;
      }
      next();
    },
  },
  {
    path: "add-apartment",
    name: "add-apartment",
    component: AddApartment,
    meta: {
      title: " - Add New Apartment",
    },
    beforeEnter: (to, from, next) => {
      if (Vue.prototype.$userType != "Host") {
        Vue.toasted.show("You have no permissions to perform this action.", {
          type: "error",
          icon: "error_outline",
          iconPack: "material",
          position: "top-center",
          duration: 2500,
          singleton: true,
        });
        window.location.href = `${window.location.origin}/#/`;
        return;
      }
      next();
    },
  },
];
