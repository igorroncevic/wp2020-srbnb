import Home from "./../components/Home.vue";
import SearchResults from "./../components/SearchResults.vue";
import Settings from "./../components/Settings.vue";
import ApartmentPreview from "./../components/ApartmentPreview.vue";
import EditUserInfo from "./../components/EditUserInfo.vue";
import ChangePassword from "./../components/ChangePassword.vue";
import UserProfile from "./../components/UserProfile.vue";
import SearchUsers from "./../components/SearchUsers.vue";
import PreviewBookings from "./../components/PreviewBookings.vue";

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
    children: [
      {
        path: "",
        component: EditUserInfo,
        name: "editUserInfo",
        meta: {
          title: " - Edit Your Profile",
        },
      },
      {
        path: "change-password",
        component: ChangePassword,
        name: "changePassword",
        meta: {
          title: " - Change Your Password",
        },
      },
    ],
  },
  {
    path: "apartment",
    name: "apartment",
    component: ApartmentPreview,
    meta: {
      title: " - Apartment Preview",
    },
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
  },
  {
   path: "preview-bookings",
   name: "preview-bookings",
   component: PreviewBookings,
   meta: {
     title: " - Preview Bookings",
   },
 },
];
