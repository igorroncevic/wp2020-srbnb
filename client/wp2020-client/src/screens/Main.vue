<template>
  <div class="wrapper">
    <header>
      <div class="logo">
        <a href="/">srbnb</a>
      </div>
      <nav>
        <ul v-if="!isLoggedIn">
          <li>
            <router-link to="/login">Login</router-link>
          </li>
          <li>
            <router-link to="/signup">Signup</router-link>
          </li>
        </ul>
        <ul v-if="isLoggedIn">
          <li v-if="isUserHost || isUserAdmin">
            <router-link to="/search-users">Search Users</router-link>
          </li>
          <li>
            <router-link to="/preview-bookings">Preview Bookings</router-link>
          </li>
          <li v-if="isUserHost && !isUserAdmin">
            <router-link to="/add-apartment">Add Apartment</router-link>
          </li>
          <li v-if="isUserAdmin">
            <router-link to="/amenities">Manage Amenities</router-link>
          </li>
          <li>
            <router-link to="/settings">Settings</router-link>
          </li>
          <li>
            <router-link to="/" @click.native="logout">Logout</router-link>
          </li>
        </ul>
      </nav>
    </header>
    <router-view class="content" />
  </div>
</template>

<script>
import UserService from "./../services/UserService";

export default {
  name: "App",
  computed: {
    isLoggedIn() {
      return UserService.getToken() === null ? false : true;
    },
    isUserHost(){
       return this.$root.$userType == 'Host' ? true : false; 
    },
    isUserAdmin(){
       return this.$root.$userType == 'Admin' ? true : false;
    }
  },
  methods: {
    logout() {
      UserService.logoutUser();
      this.$toasted.global.loggedOut();
      setTimeout(() => {
        window.location.reload();
      }, 1500);
    }
  }
};
</script>

<style>
header {
  margin-top: 5px;
}

.wrapper {
  display: flex;
  flex-direction: column;
}

.content {
  flex: 1;
}

.logo a {
  float: left;
  padding-top: 6px;
  font-size: 26px;
  text-decoration: none;
  color: var(--brand-color);
}

nav {
  float: right;
  margin-top: 3px;
}

nav ul {
  margin: 0;
  padding: 0;
  list-style: none;
}

nav li {
  display: inline-block;
  margin-left: 70px;
  padding-top: 10px;

  position: relative;
}

nav a {
  text-decoration: none;
  font-size: 16px;
  color: var(--main-text-color);
}

nav a::after {
  content: "";
  display: block;
  height: 2px;
  background-color: var(--main-text-color);

  position: absolute;
  bottom: -2px;
  left: 50%;
  right: 50%; /* 51% ako ostaje linijica */
  width: 0%;

  transition: all ease-in-out 200ms;
}

nav a:hover::after {
  width: 105%;
  left: 0;
  right: 0;
}
nav a:active {
  color: var(--main-text-color);
}
</style>
