<template>
  <div>
    <header>
      <h2>Stays in {{location}}</h2>
      <!-- Pristupati query parametrima iz URL-a -->
      <div
        class="search-params"
      >{{guests}} guests • {{startDate}} - {{endDate}} • {{price}} • {{rooms}}</div>
      <div class="filters">
        <!-- Beds -->
        <div class="counter beds">
          <p style="font-size: 18px; margin: 0px 5px 0 0;">Beds:</p>
          <svg
            style="width:22px;height:22px; margin-right: 5px;"
            viewBox="0 0 24 24"
            @click="decrementBeds"
          >
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M7,13H17V11H7"
            />
          </svg>
          <p style="margin: -1px 5px; font-size:20px;">{{bedsCounter}}</p>
          <svg
            style="width:22px;height:22px;margin-left: 5px;"
            viewBox="0 0 24 24"
            cursor="pointer"
            @click="incrementBeds"
          >
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M13,7H11V11H7V13H11V17H13V13H17V11H13V7Z"
            />
          </svg>
        </div>
        <div id="apartment-type">
          <div class="dropdown-container">
            <select @change="handleTypeChange" v-model="selectedType">
              <!-- Placeholder -->
              <option value hidden>Any type</option>
              <!-- normal options -->
              <option value="Any type">Any type</option>
              <option value="Entire apartment">Entire apartment</option>
              <option value="Penthouse">Penthouse</option>
              <option value="Single room">Single room</option>
              <option value="Deluxe apartment">Deluxe apartment</option>
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
        <div class="amenities">
          <div class="selectBox" @click="showCheckboxes">
            <select>
              <option>Amenities</option>
            </select>
            <div class="overSelect">
              <div class="select-icon amenities-select-icon">
                <svg focusable="false" style="width:14px;height:14px" viewBox="0 0 18 18">
                  <path
                    fill="#e8394d"
                    d="M7.41,8.58L12,13.17L16.59,8.58L18,10L12,16L6,10L7.41,8.58Z"
                  />
                </svg>
              </div>
            </div>
          </div>
          <div id="checkboxes">
            <label for="one">
              <input type="checkbox" value="Hair dryer" id="one" /> Hair dryer
            </label>
            <label for="two">
              <input type="checkbox" id="two" /> Fridge
            </label>
            <label for="three">
              <input type="checkbox" id="three" /> Wi-Fi
            </label>
            <label for="three">
              <input type="checkbox" id="three" /> TV
            </label>
          </div>
        </div>
      </div>
    </header>
    <main>
      <div class="sortby" v-if="filteredApartments.length != 0">
        Sort by Price
        <span class="sort-field" @click="chooseSort" v>
          {{this.sortParameter}}
          <svg style="width:14px;height:14px;" viewBox="0 0 18 18">
            <path fill="var(--medium-text-color)" d="M3,13H15V11H3M3,6V8H21V6M3,18H9V16H3V18Z" />
          </svg>
        </span>
      </div>
      <div class="search-results">
        <div v-for="(apartment,i) in filteredApartments" :key="i">
          <search-result :apartment="apartment" />
          <hr v-if="i !== filteredApartments.length - 1" />
        </div>
        <div
          class="no-results"
          v-if="filteredApartments.length == 0"
        >No apartments matching search criteria.</div>
      </div>
      <div class="map"></div>
    </main>
  </div>
</template>

<script>
import SearchResult from "./reusable/SearchResult.vue";

export default {
  name: "SearchResults",
  components: {
    SearchResult
  },
  beforeMount() {
    this.location = this.$route.query.location;
    var startDateParts = this.$route.query.startDate.split(" ");
    var endDateParts = this.$route.query.endDate.split(" ");
    this.startDate = startDateParts[0] + " " + startDateParts[1];
    this.endDate = endDateParts[0] + " " + endDateParts[1];
    if (
      (this.$route.query.minRooms == "" || this.$route.query.minRooms == 0) &&
      this.$route.query.maxRooms != "" &&
      this.$route.query.maxRooms != 0
    ) {
      this.rooms = "Up to " + this.$route.query.maxRooms + " rooms";
    } else if (
      (this.$route.query.maxRooms == "" || this.$route.query.maxRooms == 0) &&
      this.$route.query.minRooms != "" &&
      this.$route.query.minRooms != 0
    ) {
      this.rooms = "At least " + this.$route.query.minRooms + " rooms";
    } else if (
      (this.$route.query.minRooms == "" || this.$route.query.minRooms == 0) &&
      (this.$route.query.maxRooms == "" || this.$route.query.maxRooms == 0)
    ) {
      this.rooms = "Any number of rooms";
    } else {
      this.rooms =
        "Rooms: " +
        this.$route.query.minRooms +
        " - " +
        this.$route.query.maxRooms;
    }

    if (
      (this.$route.query.minPrice == "" || this.$route.query.minPrice == 0) &&
      this.$route.query.maxPrice != "" &&
      this.$route.query.maxPrice != 0
    ) {
      this.price = "Up to $" + this.$route.query.maxPrice;
    } else if (
      (this.$route.query.maxPrice == "" || this.$route.query.maxPrice == 0) &&
      this.$route.query.minPrice != "" &&
      this.$route.query.minPrice != 0
    ) {
      this.price = "Up from $" + this.$route.query.minPrice;
    } else if (
      (this.$route.query.minPrice == "" || this.$route.query.minPrice == 0) &&
      (this.$route.query.maxPrice == "" || this.$route.query.maxPrice == 0)
    ) {
      this.price = "Any price";
    } else {
      this.price =
        "Price: $" +
        this.$route.query.minPrice +
        " - $" +
        this.$route.query.maxPrice;
    }
    this.filteredApartments = [...this.apartments];
    this.sortApartments();
  },
  data() {
    return {
      location: "",
      startDate: "",
      endDate: "",
      guests: this.$route.query.guests,
      sortParameter: "Ascending",
      rooms: "",
      price: "",
      beds: 0,
      selectedType: "Any type",
      amenitiesExpanded: false,
      apartments: [
        {
          id: 1,
          name: "Apartments Perić",
          type: "Entire apartment",
          price: 40,
          guests: 4,
          beds: 2,
          bedrooms: 1,
          features: "4 guests * 1 bedroom * 2 beds"
        },
        {
          id: 2,
          name: "Apartments Jović",
          type: "Single room",
          price: 20,
          guests: 2,
          beds: 1,
          bedrooms: 1
        },
        {
          id: 3,
          name: "Apartments Stević",
          type: "Penthouse",
          price: 349,
          guests: 8,
          beds: 6,
          bedrooms: 4
        },
        {
          id: 4,
          name: "Apartments Deluxe",
          type: "Deluxe apartment",
          price: 149,
          guests: 6,
          beds: 4,
          bedrooms: 3
        }
      ],
      filteredApartments: []
    };
  },
  computed: {
    roomsCounter() {
      return this.rooms;
    },
    bedsCounter() {
      return this.beds;
    }
  },
  methods: {
    chooseSort() {
      if (this.sortParameter == "Ascending") {
        this.sortParameter = "Descending";
      } else if (this.sortParameter == "Descending") {
        this.sortParameter = "Ascending";
      }
      this.sortApartments();
    },
    sortApartments() {
      if (this.sortParameter == "Ascending") {
        this.filteredApartments.sort(function(a, b) {
          return a.price - b.price;
        });
        this.apartments.sort(function(a, b) {
          return a.price - b.price;
        });
      } else {
        this.filteredApartments.sort(function(a, b) {
          return b.price - a.price;
        });
        this.apartments.sort(function(a, b) {
          return b.price - a.price;
        });
      }
    },
    handleTypeChange() {
      if (this.selectedType == "Penthouse") {
        this.filteredApartments = this.apartments.filter(
          app => app.type == "Penthouse" && app.beds >= this.bedsCounter
        );
      } else if (this.selectedType == "Deluxe apartment") {
        this.filteredApartments = this.apartments.filter(
          app => app.type == "Deluxe apartment" && app.beds >= this.bedsCounter
        );
      } else if (this.selectedType == "Single room") {
        this.filteredApartments = this.apartments.filter(
          app => app.type == "Single room" && app.beds >= this.bedsCounter
        );
      } else if (this.selectedType == "Entire apartment") {
        this.filteredApartments = this.apartments.filter(
          app => app.type == "Entire apartment" && app.beds >= this.bedsCounter
        );
      } else if (this.selectedType == "Any type") {
        this.filteredApartments = this.apartments.filter(
          app => app.beds >= this.bedsCounter
        );
      }
    },
    filterByBeds() {
      if (this.selectedType != "Any type") {
        this.filteredApartments = this.apartments.filter(
          app => app.beds >= this.bedsCounter && app.type == this.selectedType
        );
      } else {
        this.filteredApartments = this.apartments.filter(
          app => app.beds >= this.bedsCounter
        );
      }
    },
    incrementBeds() {
      this.beds++;
      this.filterByBeds();
    },
    decrementBeds() {
      if (this.beds > 0) {
        this.beds--;
        this.filterByBeds();
      }
    },
    showCheckboxes() {
      var checkboxes = document.getElementById("checkboxes");
      if (!this.amenitiesExpanded) {
        checkboxes.style.display = "block";
        this.amenitiesExpanded = true;
      } else {
        checkboxes.style.display = "none";
        this.amenitiesExpanded = false;
      }
    }
  }
};
</script>

<style scoped>
/* Combobox */
.dropdown-container {
  width: 250px;
  margin-top: -9px;
  margin-left: 20px;
  margin-right: -30px;
}
select {
  width: 200px;
  height: 41px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 0;
  background-color: white;
  border: none;
  border-bottom: 2px solid var(--medium-text-color);
  color: var(--main-text-color);
  appearance: none;
  padding: 10px;
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
  top: 8px;
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

/* Counters */
svg {
  pointer-events: fill;
}
.counter {
  cursor: pointer;
  position: relative;
  left: 0%;
  display: inline-flex;
  height: 60px;
  width: 90%;
  align-content: center;
}

main {
  display: grid;
  grid-template-rows: 50px auto;
  grid-template-columns: repeat(5, 1fr);
  column-gap: 11rem;
  position: relative; /* Fixuje mapu u mjestu?*/
  min-height: 75vh;
}

.search-results {
  margin-top: 15px;
  margin-left: 40px;
  grid-column: 1/2;
  grid-row: 2;
  width: 100%;
  min-width: 500px;
}

.sortby {
  right: 0;
  height: 50px !important;
  margin-top: 15px;
  margin-left: auto;
  font-size: 14px;
  grid-row: 1;
  color: var(--medium-text-color);
}

.sort-field:hover {
  cursor: pointer;
}

.map {
  grid-column: 2/6;
  background: teal;
  width: 110%;
  float: right;
  position: absolute; /* Fixuje mapu u mjestu? Fixed ne radi */
  min-height: 99%;
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
}

h2 {
  margin-top: 0;
  margin-bottom: 3px;
  font-size: 30px;
}

.search-params {
  color: var(--light-text-color);
  margin-top: 0;
  font-size: 16px;
  width: 43%;
  line-height: 25px;
  word-wrap: break-word;
}

.filters {
  margin-top: 30px;
  width: 38%;
  display: grid;
  grid-template-areas: "beds type amenities";
}

.rooms {
  margin-left: 22px;
  grid-area: "rooms";
}

.beds {
  grid-area: "beds";
  position: relative;
  left: 6%;
}

.apartment-type {
  grid-area: "type";
}

.amenities {
  grid-area: "amenities";
  width: 180px;
}

.amenities-select-icon {
  left: 3%;
}

/* Amenities multiselect */
.selectBox {
  position: relative;
  bottom: 15%;
  cursor: pointer;
}

.selectBox select {
  width: 100%;
  font-weight: bold;
}

.overSelect {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}

#checkboxes {
  display: none;
  position: absolute;
  width: 224px;
  padding: 5px;
  z-index: 100;
  border: 1px #dadada solid;
  background: var(--background-color);
}

#checkboxes label {
  display: block;
  margin: 5px 0;
}

#checkboxes label:hover {
  background-color: #dddddd;
}

[type="checkbox"]:checked {
  background-color: var(--brand-color) !important;
}

[type="checkbox"]:hover {
  background-color: var(--brand-color-hover) !important;
}

.no-results {
  font-size: 20px;
  margin-top: 20px;
  text-align: center;
}
</style>
