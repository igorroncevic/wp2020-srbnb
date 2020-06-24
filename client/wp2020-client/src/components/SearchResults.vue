<template>
  <div>
    <header>
      <h2>Stays in {{location}}</h2>
      <!-- Pristupati query parametrima iz URL-a -->
      <div class="search-params">{{guests}} guests • {{startDate}} - {{endDate}}</div>
      <div class="filters">
        <!-- Rooms -->
        <div class="counter rooms">
          <p style="font-size: 18px; margin: 0px 5px 0 0;">Rooms:</p>
          <svg
            style="width:22px;height:22px; margin-right: 5px;"
            viewBox="0 0 24 24"
            @click="decrementRooms"
          >
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M7,13H17V11H7"
            />
          </svg>
          <p style="margin: -1px 5px; font-size:20px;">{{roomsCounter}}</p>
          <svg
            style="width:22px;height:22px;margin-left: 5px;"
            viewBox="0 0 24 24"
            cursor="pointer"
            @click="incrementRooms"
          >
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M13,7H11V11H7V13H11V17H13V13H17V11H13V7Z"
            />
          </svg>
        </div>
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
            <select>
              <!-- Placeholder -->
              <option value hidden>Type</option>
              <!-- normal options -->
              <option value="Whole">Whole apartment</option>
              <option value="Studio">Studio apartment</option>
              <option value="Single">Single room</option>
              <option value="Deluxe">Deluxe apartment</option>
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
      <div class="sortby">
        Sort by:
        <span class="sort-field" @click="chooseSort">
          {{this.sortParameter}}
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

export default {
  name: "SearchResults",
  components: {
    SearchResult
  },
  beforeMount() {
    this.location = this.$route.query.location;
    var startDateParts = this.$route.query.startDate.split(" ");
    var endDateParts = this.$route.query.endDate.split(" ");
    this.startDate = startDateParts[1] + " " + startDateParts[0];
    this.endDate = endDateParts[1] + " " + endDateParts[0];
  },
  data() {
    return {
      location: "",
      startDate: "",
      endDate: "",
      guests: this.$route.query.guests,
      sortParameter: "Price",
      rooms: 0,
      beds: 0,
      amenitiesExpanded: false,
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
      if (this.sortParameter == "Price") {
        this.sortParameter = "Guests";
      } else if (this.sortParameter == "Guests") {
        this.sortParameter = "Beds";
      } else if (this.sortParameter == "Beds") {
        this.sortParameter = "Price";
      }
    },
    displayRoomsSelector() {
      alert("hey");
    },
    incrementRooms() {
      this.rooms++;
    },
    decrementRooms() {
      if (this.rooms > 0) this.rooms--;
    },
    incrementBeds() {
      this.beds++;
    },
    decrementBeds() {
      if (this.beds > 0) this.beds--;
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
  width: 285px;
  margin-top: -9px;
  margin-left: 20px;
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
  right: 5rem;
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
  grid-template-areas:
    "rooms beds"
    "type amenities";
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
</style>
