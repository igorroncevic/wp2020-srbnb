<template>
  <div class="grid-container">
    <!-- Title -->
    <div class="title-wrapper">
      <h1>Search Users</h1>
    </div>
    <div id="searchbar-wrapper">
      <!-- Search form -->
      <form method="GET" action="/search-results" id="searchbar" @submit.prevent="searchUsers">
        <div class="form-item" tabindex="1">
          <div class="nest stacked">
            <label for="location" class="label">USERNAME</label>
            <input
              type="text"
              name="location"
              placeholder="Who are you looking for?"
              v-model="username"
            />
          </div>
        </div>
        <div class="form-item minified-booking" tabindex="2">
          <div class="nest stacked">
            <div class="dropdown-container">
              <select v-model="type">
                <option value="" disabled>Search by type</option>
                <option value="Guest">Guest</option>
                <option value="Host" v-if="userType == 'Admin'">Host</option>
                <option value="Admin" v-if="userType == 'Admin'">Admin</option>
              </select>
              <div class="select-icon">
                <svg focusable="false" style="width:14px;height:14px" viewBox="0 0 18 18">
                  <path
                    fill="#e8394d"
                    d="M7.41,8.58L12,13.17L16.59,8.58L18,10L12,16L6,10L7.41,8.58Z"
                  />
                </svg>
              </div>
            </div>
          </div>
        </div>
        <div class="form-item minified-booking" tabindex="3">
          <div class="stacked nest">
            <div class="dropdown-container">
              <select v-model="gender">
                <option value="" disabled>Search by gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </select>
              <div class="select-icon">
                <svg focusable="false" style="width:14px;height:14px" viewBox="0 0 18 18">
                  <path
                    fill="#e8394d"
                    d="M7.41,8.58L12,13.17L16.59,8.58L18,10L12,16L6,10L7.41,8.58Z"
                  />
                </svg>
              </div>
            </div>
          </div>
        </div>
        <button type="submit">
          <img src="./../../public/svg/search-icon.png" />Search
        </button>
      </form>
      <!-- End of the form -->
    </div>
    <!-- Search results -->
    <div class="search-results-wrapper">
      <div v-for="user in users" :key="user.username">
        <UserSearchResult :user="user" :currentUserType="userType" />
      </div>
    </div>
  </div>
</template>

<script>
import UserSearchResult from "./reusable/UserSearchResult";
import usersService from "../services/UserService";

export default {
  components: { UserSearchResult },
  async beforeMount() {
    var queryParams = {};
    if (this.$route.query.username) {
      this.username = this.$route.query.username;
      queryParams.username = this.$route.query.username;
    }
    if (this.$route.query.gender) {
      this.gender = this.$route.query.gender;
      queryParams.gender = this.$route.query.gender;
    }
    if (this.$route.query.type) {
      this.type = this.$route.query.type;
      queryParams.type = this.$route.query.type;
    }

    this.users = await usersService.searchUsers(queryParams);
    this.userType = usersService.getUserType();

    this.$nextTick(() => {
      this.$forceUpdate();
    });
  },
  data() {
    return {
      users: [],
      userType: "",
      username: "",
      gender: "",
      type: ""
      /*users: [
        {
          name: "Jovan Jovanovic",
          gender: "Male",
          type: "Homeowner",
          apartments: 4
        },
        {
          name: "Jovan Jovanovic",
          gender: "Male",
          type: "Homeowner",
          apartments: 4
        },
        {
          name: "Jovan Jovanovic",
          gender: "Male",
          type: "Homeowner",
          apartments: 4
        },
        {
          name: "Jovan Jovanovic",
          gender: "Male",
          type: "Homeowner",
          apartments: 4
        },
        {
          name: "Jovan Jovanovic",
          gender: "Male",
          type: "Homeowner",
          apartments: 4
        },
        {
          name: "Jovan Jovanovic",
          gender: "Male",
          type: "Homeowner",
          apartments: 4
        },
        {
          name: "Jovan Jovanovic",
          gender: "Male",
          type: "Homeowner",
          apartments: 4
        }
      ]*/
    };
  },
  methods: {
    searchUsers() {
      var queryParams = {};
      if (this.username != "") {
        queryParams.username = this.username;
      }
      if (this.gender != "") {
        queryParams.gender = this.gender;
      }
      if (this.type != "") {
        queryParams.type = this.type;
      }
      console.log(queryParams);
      this.$router.replace({
        name: "search-users",
        query: queryParams
      });
      this.$router.go();
    }
  }
};
</script>

<style>
.title-wrapper {
  grid-row: 0;
  margin-top: -10px;
  grid-column: 1/4;
  position: relative;
}
h1 {
  margin: 5px 0 30px 0;
  font-size: 34px;
  font-weight: 500;
  color: var(--main-text-color);
  letter-spacing: 1px;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(15, 1fr);
  grid-gap: 10px;
}
.search-results-wrapper {
  grid-column: 1 / 16;
  grid-row: 4/70; /* Hardkodiranih 70, ali radi posao */
  margin-top: 10px;
  display: grid;
  grid-template-rows: repeat(auto-fill, 23vh);
  grid-template-columns: repeat(auto-fill, 57vh);
  grid-row-gap: 5px;
}
#searchbar-wrapper {
  height: auto;
  background: var(--background-color);
  box-shadow: 0px 4px 8px var(--dropshadow-color);
  border-radius: var(--border-radius);
  grid-row: 2;
  grid-column: 3/14 !important;
}
#searchbar {
  position: relative;
  width: 100%;
  height: auto;
}
#searchbar button[type="submit"] {
  cursor: pointer;
  width: auto;
  font-size: 16px;
  padding: 13px 29px 12px 25px;
  color: var(--background-color);
  background-color: var(--brand-color);
  border: 0;
  border-radius: var(--border-radius);
  -webkit-border-radius: var(--border-radius);
  transition: all 0.3s ease;
  -webkit-transition: all 0.3 ease;
  -moz-transition: all 0.3 ease;
  -o-transition: all 0.3 ease;
  float: right;
  position: absolute;
  top: 20%;
  right: 1%;
  text-align: center;
  text-justify: distribute;
}
#searchbar button[type="submit"] img {
  height: 16px;
  width: auto;
  margin: 0px 5px -2px 0px;
}
#searchbar button[type="submit"]:hover {
  background: var(--brand-color-hover);
}
#searchbar input[type="text"] {
  font-size: 16px;
  padding-top: 3px;
  padding-right: 10px;
  border: 0;
}
#searchbar input[type="text"]::placeholder {
  color: var(--light-text-color);
}

.form-item {
  display: inline-block;
  padding: 0.5rem 0 0.6rem 1rem;
  background: var(--background-color);
  width: 30%;
  border: 1px solid var(--background-color);
  border-radius: var(--border-radius);
  height: 3rem;
}

.form-item:hover {
  border: 1px solid var(--light-text-color);
}

.form-item:focus-within {
  border: 1px solid var(--main-text-color);
}

.nest {
  max-width: 60%;
  margin-top: 6px;
}

.stacked {
  display: grid;
}

.label {
  font-weight: bold;
  font-size: 12px;
  grid-row: 1;
  grid-column: 1;
}

.minified-booking {
  width: 23%;
  padding: 0.5rem 0 0.5rem 1rem;
}

.dropdown-container {
  width: 250px;
  position: relative;
  left: 10%;
  top: 20%;
}
select {
  width: 200px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 0;
  background-color: var(--background-color);
  border: none;
  border-bottom: 2px solid var(--medium-text-color);
  color: var(--main-text-color);
  appearance: none;
  padding: 0px 10px 10px 10px;
  -webkit-appearance: none;
  -moz-appearance: none;
  transition: color 0.3s ease, background-color 0.3s ease,
    border-bottom-color 0.3s ease;
}
select * {
  color: var(--main-text-color);
}

/* For IE <= 11 */
select::-ms-expand {
  display: none;
}
.select-icon {
  position: relative;
  right: 2.8rem;
  pointer-events: none;
  transition: background-color 0.3s ease, border-color 0.3s ease;
  width: 30px;
  float: right;
}
.select-icon svg.icon {
  transition: fill 0.3s ease;
  fill: var(--brand-color);
}
select:hover,
select:focus {
  color: var(--brand-color);
  background-color: white;
  border-bottom-color: var(--medium-text-color);
}
select:hover ~ .select-icon,
select:focus ~ .select-icon {
  background-color: white;
  border-color: var(--medium-text-color);
}
select:hover ~ .select-icon svg.icon,
select:focus ~ .select-icon svg.icon {
  fill: var(--brand-hover-color);
}
</style>