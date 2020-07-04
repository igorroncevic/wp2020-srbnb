<template>
  <div id="search-wrapper">
    <header>
      <h2>Stays in {{location}}</h2>
      <!-- Pristupati query parametrima iz URL-a -->
      <div
        class="search-params"
      >{{guests}} guests • {{startDate}} - {{endDate}} • {{price}} • {{rooms}}</div>
      <div class="filters">
        <div id="apartment-type">
          <div class="dropdown-container">
            <select v-model="selectedType">
              <option value="Any_type">Any type</option>
              <option value="Full_Apartment">Full apartment</option>
              <option value="Room">Room</option>
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
            <label for="one" v-for="amenity in amenities" :key="amenity.id">
              <input @change="addAmenity(amenity)" type="checkbox" :value="amenity.id" />
              {{amenity.name}}
            </label>
          </div>
        </div>
        <Button
          text="Apply filters"
          width="100"
          height="33"
          fontsize="15"
          class="filter-button"
          @clicked="applyFilters"
        />
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
    </main>
    <Mapbox v-if="apartmentsLoaded" :apartments="filteredApartments" class="map-container"></Mapbox>
  </div>
</template>

<script>
import SearchResult from "./reusable/SearchResult.vue";
import Mapbox from "./Mapbox.vue";
import ApartmentsService from "./../services/ApartmentsService";
import AmenitiesService from "./../services/AmenitiesService";
import Button from "./form-components/Button.vue";
import moment from "moment";

export default {
  name: "SearchResults",
  components: {
    SearchResult,
    Mapbox,
    Button
  },
  async beforeMount() {
    if (this.$route.query.amenities) {
      this.apartments = await ApartmentsService.searchApartments({
        location: this.$route.query.location,
        guests: this.$route.query.guests,
        startDate: moment(this.$route.query.startDate).format("DD-MM-YYYY"),
        endDate: moment(this.$route.query.endDate).format("DD-MM-YYYY"),
        minRooms: this.$route.query.minRooms,
        maxRooms: this.$route.query.maxRooms,
        minPrice: this.$route.query.minPrice,
        maxPrice: this.$route.query.maxPrice,
        amenites: this.$route.query.amenities,
        type: this.$route.query.selectedType
      });
    } else {
      this.apartments = await ApartmentsService.searchApartments({
        location: this.$route.query.location,
        guests: this.$route.query.guests,
        startDate: moment(this.$route.query.startDate).format("DD-MM-YYYY"),
        endDate: moment(this.$route.query.endDate).format("DD-MM-YYYY"),
        minRooms: this.$route.query.minRooms,
        maxRooms: this.$route.query.maxRooms,
        minPrice: this.$route.query.minPrice,
        maxPrice: this.$route.query.maxPrice,
        type: this.$route.query.selectedType
      });
    }

    this.amenities = await AmenitiesService.getAllAmenities();

    this.location = this.$route.query.location;
    var startDateQuery = this.$route.query.startDate.split("-");
    var endDateQuery = this.$route.query.endDate.split("-");
    var startDateParts = moment(
      startDateQuery[1] + " " + startDateQuery[0] + "-" + startDateQuery[2]
    )
      .format("MMMM Do YYYY")
      .split(" ");
    var endDateParts = moment(
      endDateQuery[1] + " " + endDateQuery[0] + "-" + endDateQuery[2]
    )
      .format("MMMM Do YYYY")
      .split(" ");
    this.startDate = startDateParts[0] + " " + startDateParts[1];
    this.endDate = endDateParts[0] + " " + endDateParts[1];
    if (this.$route.query.minRooms == 0 && this.$route.query.maxRooms != 0) {
      this.rooms = "Up to " + this.$route.query.maxRooms + " rooms";
    } else if (
      this.$route.query.maxRooms == 0 &&
      this.$route.query.minRooms != 0
    ) {
      this.rooms = "At least " + this.$route.query.minRooms + " rooms";
    } else if (
      this.$route.query.minRooms == 0 &&
      this.$route.query.maxRooms == 0
    ) {
      this.rooms = "Any number of rooms";
    } else {
      this.rooms =
        "Rooms: " +
        this.$route.query.minRooms +
        " - " +
        this.$route.query.maxRooms;
    }

    if (this.$route.query.minPrice == 0 && this.$route.query.maxPrice != 0) {
      this.price = "Up to $" + this.$route.query.maxPrice;
    } else if (
      this.$route.query.maxPrice == 0 &&
      this.$route.query.minPrice != 0
    ) {
      this.price = "Up from $" + this.$route.query.minPrice;
    } else if (
      this.$route.query.minPrice == 0 &&
      this.$route.query.maxPrice == 0
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
    console.log(this.filteredApartments);
    this.$nextTick(() => {
      this.apartmentsLoaded = true;
    });
    this.sortApartments();
  },
  mounted() {
    this.$forceUpdate();
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
      selectedType: "Any_type",
      amenitiesExpanded: false,
      apartmentsLoaded: false,
      apartments: [],
      amenities: [],
      selectedAmenities: [],
      /*apartments: [
        {
          id: 1,
          name: "Apartments Perić",
          type: "Entire apartment",
          pricePerNight: 40,
          guests: 4,
          beds: 2,
          bedrooms: 1,
          features: "4 guests * 1 bedroom * 2 beds",
          location: {
            latitude: 45.2518875,
            longitude: 19.8336123
          }
        },
        {
          id: 2,
          name: "Apartments Jović",
          type: "Single room",
          pricePerNight: 20,
          guests: 2,
          beds: 1,
          bedrooms: 1,
          location: {
            latitude: 45.2479296,
            longitude: 19.8367477
          }
        },
        {
          id: 3,
          name: "Apartments Stević",
          type: "Penthouse",
          pricePerNight: 349,
          guests: 8,
          beds: 6,
          bedrooms: 4,
          location: {
            latitude: 45.2403965,
            longitude: 19.8415322
          }
        },
        {
          id: 4,
          name: "Apartments Deluxe",
          type: "Deluxe apartment",
          pricePerNight: 149,
          guests: 6,
          beds: 4,
          bedrooms: 3,
          location: {
            latitude: 45.2503965,
            longitude: 19.8445322
          }
        },
        {
          id: 5,
          name: "Apartments Deluxe",
          type: "Deluxe apartment",
          pricePerNight: 159,
          guests: 6,
          beds: 4,
          bedrooms: 3,
          location: {
            latitude: 45.2243965,
            longitude: 19.8445322
          }
        },
        {
          id: 6,
          name: "Apartments Deluxe",
          type: "Deluxe apartment",
          pricePerNight: 169,
          guests: 6,
          beds: 4,
          bedrooms: 3,
          location: {
            latitude: 45.2503265,
            longitude: 19.8845322
          }
        }
      ],*/
      filteredApartments: []
    };
  },
  methods: {
    chooseSort() {
      if (this.sortParameter == "Ascending") {
        this.sortParameter = "Descending";
      } else if (this.sortParameter == "Descending") {
        this.sortParameter = "Ascending";
      }
      console.log(this.sortParameter);
      this.sortApartments();
    },
    sortApartments() {
      if (this.sortParameter == "Ascending") {
        this.filteredApartments.sort(function(a, b) {
          return a.pricePerNight - b.pricePerNight;
        });
        this.apartments.sort(function(a, b) {
          return a.pricePerNight - b.pricePerNight;
        });
      } else {
        this.filteredApartments.sort(function(a, b) {
          return b.pricePerNight - a.pricePerNight;
        });
        this.apartments.sort(function(a, b) {
          return b.pricePerNight - a.pricePerNight;
        });
        console.log(this.filteredApartments);
        console.log(this.apartments);
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
    },
    applyFilters() {
      console.log(this.selectedType);
      console.log(this.selectedAmenities);
      if (
        this.selectedAmenities.length == 0 &&
        this.selectedType == "Any_type"
      ) {
        return;
      }
      var selectedAmenitiesString = "";
      this.selectedAmenities.forEach(amenity => {
        selectedAmenitiesString += amenity + ",";
      });

      selectedAmenitiesString = selectedAmenitiesString.substr(
        0,
        selectedAmenitiesString.length - 1
      );
      console.log(selectedAmenitiesString);

      if (selectedAmenitiesString != "") {
        this.$router.replace({
          name: "search-results",
          query: {
            location: this.$route.query.location,
            guests: this.$route.query.guests,
            startDate: moment(this.$route.query.startDate).format("DD-MM-YYYY"),
            endDate: moment(this.$route.query.endDate).format("DD-MM-YYYY"),
            minRooms:
              this.$route.query.minRooms < 0 ? 0 : this.$route.query.minRooms,
            maxRooms:
              this.$route.query.maxRooms < this.$route.query.minRooms
                ? this.$route.query.minRooms
                : this.$route.query.maxRooms,
            minPrice:
              this.$route.query.minPrice < 0 ? 0 : this.$route.query.minPrice,
            maxPrice:
              this.$route.query.maxPrice < this.$route.query.minPrice
                ? this.$route.query.minPrice
                : this.$route.query.maxPrice,
            amenities: selectedAmenitiesString,
            type: this.selectedType
          }
        });
      } else {
        this.$router.replace({
          name: "search-results",
          query: {
            location: this.$route.query.location,
            guests: this.$route.query.guests,
            startDate: moment(this.$route.query.startDate).format("DD-MM-YYYY"),
            endDate: moment(this.$route.query.endDate).format("DD-MM-YYYY"),
            minRooms:
              this.$route.query.minRooms < 0 ? 0 : this.$route.query.minRooms,
            maxRooms:
              this.$route.query.maxRooms < this.$route.query.minRooms
                ? this.$route.query.minRooms
                : this.$route.query.maxRooms,
            minPrice:
              this.$route.query.minPrice < 0 ? 0 : this.$route.query.minPrice,
            maxPrice:
              this.$route.query.maxPrice < this.$route.query.minPrice
                ? this.$route.query.minPrice
                : this.$route.query.maxPrice,
            type: this.selectedType
          }
        });
      }

      this.$router.go();
    },
    addAmenity(amenity) {
      if (!this.selectedAmenities.includes(amenity.id)) {
        this.selectedAmenities.push(amenity.id);
      } else {
        this.selectedAmenities = this.selectedAmenities.filter(
          id => id !== amenity.id
        );
      }
      console.log(this.selectedAmenities);
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
  grid-template-rows: 2% 98%;
  grid-template-columns: repeat(6, 20vh);
  column-gap: 2rem;
  position: relative; /* Fixuje mapu u mjestu?*/
  min-height: 69vh;
  max-height: 70vh;
  overflow: hidden;
}

.search-results {
  margin-top: 5px;
  margin-left: 20px;
  grid-column: 1/3;
  grid-row: 2;
  width: 400px; /* Sets scrollbar behind map */
  min-width: 800px;
  overflow-y: scroll;
  box-sizing: content-box;
  scrollbar-color: red;
}

.sortby {
  right: 0;
  height: 50px !important;
  font-size: 14px;
  grid-column: 3;
  margin-left: -15px;
  color: var(--medium-text-color);
}

.sort-field:hover {
  cursor: pointer;
}

.map-container {
  /*grid-column: 4/6;*/
  position: absolute; /* Fixuje mapu u mjestu? Fixed ne radi */
  left: 700px;
  bottom: 0px;
  min-height: 800px;
  max-height: 900px;
  width: 1077px;
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
  grid-template-columns: 16rem 16rem 5rem;
  grid-template-rows: 3rem 3rem;
}

.filter-button {
  grid-column: 3;
  grid-row: 1;
  position: relative;
  margin: 0;
  padding: 0;
  background-color: transparent;
  border: 0;
}

.apartment-type {
  grid-column: 1;
  grid-row: 1;
}

.amenities {
  width: 180px;
  grid-column: 2;
  grid-row: 1;
  margin-top: -2px;
  margin-left: 10px;
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

input[type="checkbox"]:checked {
  background-color: var(--brand-color) !important;
}

input[type="checkbox"]:hover {
  background-color: var(--brand-color-hover) !important;
}

.no-results {
  font-size: 20px;
  margin-top: 10px;
  margin-left: -20px;
  text-align: center;
}

input[type="number"] {
  height: 31px;
  font-size: 20px;
  width: 50px;
  margin-top: -4px;
  margin-left: 10px;
  border-top-style: hidden;
  border-left-style: hidden;
  border-right-style: hidden;
  border-color: var(--medium-text-color);
}
</style>
