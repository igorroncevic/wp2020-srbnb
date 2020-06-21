<template>
  <div>
    <header>
      <h2>Stays in Novi Sad</h2>
      <!-- Pristupati query parametrima iz URL-a -->
      <div class="search-params">2 guests * 21 May - 25 May</div>
      <div class="filters">
        <filter-item value="Number of rooms" />
        <filter-item value="Beds" />
        <filter-item value="Type" />
        <filter-item value="Amenities" />
      </div>
    </header>
    <main>
      <div class="sortby">
        Sort by:
        <span class="sort-field" @click="chooseSort">
          Price
          <img
            src="https://img.icons8.com/material-outlined/18/8b8b8b/expand-arrow.png"
            style="margin-bottom: -3px;"
          />
        </span>
      </div>
      <div class="search-results">
        <div v-for="(apartment,i) in apartments" :key="i">
          <search-result :apartment="apartment" />
          <hr v-if="i !== apartments.length - 1" />
        </div>
      </div>
      <div class="map"></div>
    </main>
  </div>
</template>

<script>
import SearchResult from "./reusable/SearchResult.vue";
import FilterItem from "./FilterItem.vue";

export default {
  name: "SearchResults",
  components: {
    SearchResult,
    FilterItem
  },
  data() {
    return {
      apartments: [
        {
          name: "Apartments Perić",
          type: "Entire apartment",
          price: 40,
          features: "4 guests * 1 bedroom * 2 beds"
        },
        {
          name: "Apartments Jović",
          type: "Single room",
          price: 20,
          features: "2 guests * 1 bed"
        },
        {
          name: "Apartments Stević",
          type: "Penthouse",
          price: 349,
          features: "8 guests * 6 bed * 4 bedrooms"
        },
        {
          name: "Apartments Deluxe",
          type: "Deluxe apartment",
          price: 149,
          features: "6 guests * 4 beds * 3 bedrooms"
        }
      ]
    };
  },
  methods: {
    chooseSort() {
      alert("Clicked sort");
    }
  }
};
</script>

<style scoped>
main {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  column-gap: 11rem;
  position: relative; /* Fixuje mapu u mjestu?*/
}

.search-results {
  margin-top: 15px;
  margin-left: 40px;
  grid-column: 1/2;
  width: 100%;
}

.sortby {
  right: 0;
  margin-top: 15px;
  margin-left: auto;
  font-size: 14px;
  color: var(--medium-text-color);
}

.sort-field:hover {
  cursor: pointer;
}

.map {
  grid-column: 2/6;
  background: teal;
  height: 98%;
  width: 100%;
  float: right;
  position: absolute; /* Fixuje mapu u mjestu? Fixed ne radi */
}

hr {
  border: 0.5px solid #dddddd;
  width: 31rem;
  margin-left: 0;
  margin-bottom: 20px;
}

/* Header */

header {
  padding-left: 10px;
  margin-bottom: 15px;
}

h2 {
  margin-bottom: 3px;
  font-size: 30px;
}

.search-params {
  color: var(--light-text-color);
  margin-top: 0;
  font-size: 16px;
}

.filters {
  margin-top: 30px;
  width: 38%;
  display: flex;
  justify-content: space-around;
}
</style>
