<template>
  <div class="grid-container">
    <!-- Title -->
    <div class="title-wrapper">
      <h1>Search Users</h1>
    </div>
    <div id="searchbar-wrapper">
      <!-- Search form -->
      <form method="GET" action="/search-results" id="searchbar">
        <div class="form-item" tabindex="1">
          <div class="nest stacked">
            <label for="location" class="label">USERNAME</label>
            <input type="text" name="location" placeholder="Who are you looking for?" />
          </div>
        </div>
        <div class="form-item minified" tabindex="2">
          <div class="nest stacked">
            <label for="dates" class="label">ROLE</label>
            <input type="text" name="dates" placeholder="Search users by role" />
          </div>
        </div>
        <div class="form-item minified" tabindex="3">
          <div class="stacked nest">
            <label for="guests" class="label">GENDER</label>
            <input type="text" name="guests" placeholder="Search users by gender" />
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
      <div class="div" v-for="(user,i) in users" :key="i">
        <UserSearchResult :user="user" />
      </div>
    </div>
  </div>
</template>

<script>
import UserSearchResult from "./reusable/UserSearchResult";

export default {
  components: { UserSearchResult },
  data() {
    return {
      users: [
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
      ]
    };
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
  grid-row: 5/70; /* Hardkodiranih 70, ali radi posao */
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
  grid-column: 3/13;
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
  padding: 0.5rem 0 0.5rem 1.5rem;
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

.minified {
  width: 23%;
}
</style>