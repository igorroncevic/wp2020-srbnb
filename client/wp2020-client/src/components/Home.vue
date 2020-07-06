<template>
  <div style="margin-top: 75px;">
    <!-- Title -->
    <div class="title-wrapper">
      <h1>
        Book unique homes and
        <span id="accented">experience</span> a
        <br />city like a local.
      </h1>
    </div>

    <!-- Search -->
    <div id="searchbar-wrapper">
      <div>
        <!-- Search form -->
        <form
          ref="form"
          @submit.prevent="submitForm"
          method="GET"
          action="/search-results"
          id="searchbar"
        >
          <div class="form-item" tabindex="1">
            <div class="nest stacked">
              <label for="location" class="label">LOCATION</label>
              <input
                v-model="location"
                type="text"
                name="location"
                placeholder="Where are you going?"
              />
            </div>
          </div>
          <div class="form-item dates" tabindex="2">
            <div class="nest stacked">
              <label for="datepickerss" class="label">CHECK IN / CHECK OUT</label>
              <div class="datepickerss">
                <datepicker
                  placeholder="Select check-in"
                  class="vdp-datepickerr"
                  v-model="startDate"
                  :value="startDate"
                  name="startDate"
                  :disabledDates="disabledStartDates"
                  :format="customFormatter"
                ></datepicker>
                <datepicker
                  placeholder="Select check-out"
                  class="vdp-datepickerr"
                  v-model="endDate"
                  :value="endDate"
                  name="endDate"
                  :disabledDates="disabledEndDates"
                  :format="customFormatter"
                ></datepicker>
              </div>
            </div>
          </div>
          <div class="form-item guests" tabindex="3">
            <div class="stacked nest">
              <label for="guests" class="label">GUESTS</label>
              <input
                v-model="guests"
                min="0"
                max="10"
                type="number"
                name="guests"
                placeholder="Add guests"
              />
            </div>
          </div>
          <div class="form-item" tabindex="4">
            <div class="stacked nest">
              <label for="rooms" class="label">ROOMS</label>
              <div class="rooms">
                <input
                  v-model="minRooms"
                  min="0"
                  max="10"
                  type="number"
                  name="minRooms"
                  placeholder="Min. rooms"
                />
                <input
                  v-model="maxRooms"
                  min="0"
                  max="10"
                  type="number"
                  name="maxRooms"
                  placeholder="Max. rooms"
                />
              </div>
            </div>
          </div>
          <div class="form-item" tabindex="5">
            <div class="stacked nest">
              <label for="price" class="label">PRICE</label>
              <div class="price">
                <div class="price">
                  <input
                    v-model="minPrice"
                    min="0"
                    type="number"
                    name="minPrice"
                    placeholder="Min. price"
                  />
                  <input
                    v-model="maxPrice"
                    min="0"
                    type="number"
                    name="maxPrice"
                    placeholder="Max. price"
                  />
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
    </div>
    <div style="height: 100px"></div>
  </div>
</template>

<script>
import moment from "moment";
import Datepicker from "vuejs-datepicker";

export default {
  name: "Home",
  components: { Datepicker },
  data() {
    return {
      location: "",
      guests: "",
      startDate: "", //add 7 days
      endDate: "", //add 10 days
      minRooms: "",
      maxRooms: "",
      minPrice: "",
      maxPrice: "",
      disabledStartDates: {
        to: new Date(Date.now() - 8640000)
      },
      disabledEndDates: {
        to: new Date(Date.now() - 8640000)
      }
    };
  },
  methods: {
    customFormatter(date) {
      return moment(date).format("MMMM Do YYYY"); //DD-MM-YYYY for java friendly dates
    },
    submitForm(e) {
      e.preventDefault();
      if (moment(this.startDate).isAfter(moment(this.endDate))) {
        this.$toasted.global.startDateAfterEndDate();
      } else if (this.startDate == "" && this.endDate != "") {
        this.$toasted.global.bothCheckinCheckout();
      } else if (this.startDate != "" && this.endDate == "") {
        this.$toasted.global.bothCheckinCheckout();
      } else {
        var queryParams = {
          type: "Any_type"
        };

        if (this.startDate != "" && this.endDate != "") {
          queryParams.startDate = moment(this.startDate).format("DD-MM-YYYY");
          queryParams.endDate = moment(this.endDate).format("DD-MM-YYYY");
        }

        if (this.location != "") {
          queryParams.location = this.location;
        }

        if (this.guests != 0) {
          queryParams.guests = this.guests;
        }

        if (this.minPrice != 0) {
          queryParams.minPrice = this.minPrice;
        }
        if (this.maxPrice != 0) {
          queryParams.maxPrice = this.maxPrice;
        }
        if (this.minRooms != 0) {
          queryParams.minRooms = this.minRooms;
        }
        if (this.maxRooms != 0) {
          queryParams.maxRooms = this.maxRooms;
        }

        this.$router.push({
          name: "search-results",
          query: queryParams
        });
      }
    }
  }
};
</script>

<style scoped>
#searchbar .dates {
  width: 23% !important;
}

#searchbar input {
  background: var(--background-color);
}

#searchbar .vdp-datepickerr {
  margin-right: 10px !important;
  width: 120px !important;
  cursor: pointer;
}

#searchbar .datepickerss {
  display: inline-flex;
  width: min-content;
  flex-direction: row;
  margin-top: 1px;
  width: 50% !important;
}

/* Title */
.title-wrapper {
  position: relative;
  width: 92%;
  margin: 0 auto;
  top: -4%;
}

h1 {
  font-size: 36px;
  font-weight: 500;
  color: var(--main-text-color);
  margin: 10px 0;
  letter-spacing: 1px;
  line-height: 40px;
}

#accented {
  color: var(--brand-color);
}

/* Search bar */
#searchbar-wrapper {
  position: relative;
  width: 90%;
  height: auto;
  background: var(--background-color);
  margin-bottom: 30px;
  margin: 0 4%;
  box-shadow: 0px 4px 8px var(--dropshadow-color);
  border-radius: var(--border-radius);
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

#searchbar input[type="text"],
#searchbar input[type="number"] {
  font-size: 16px;
  padding-top: 3px;
  padding-right: 10px;
  border: 0;
}

#searchbar input[type="text"]::placeholder,
#searchbar input[type="number"]::placeholder {
  color: var(--light-text-color);
}

.form-item {
  display: inline-block;
  padding: 0.5rem 0 0.5rem 1.5rem;
  background: var(--background-color);
  width: 15%;
  border: 1px solid var(--background-color);
  border-radius: var(--border-radius);
  height: 3rem;
}

.dates {
  width: 27%;
}

.guests {
  width: 10%;
}

.guests input[type="number"] {
  margin-top: 1px;
}

.rooms {
  display: inline-flex;
  flex-direction: row;
  margin-left: -6px;
  margin-top: 1px;
}

label[for="rooms"] {
  margin-left: -4px;
}

.rooms input[name="minRooms"] {
  width: 97px;
}

.rooms input[name="maxRooms"] {
  width: 100px;
}

.price {
  display: inline-flex;
  flex-direction: row;
  margin-left: -6px;
  margin-top: 1px;
}

.price input[name="minPrice"] {
  width: 97px;
}

.price input[name="maxPrice"] {
  width: 100px;
}

label[for="price"] {
  margin-left: -9px;
}

.form-item:hover {
  border: 1px solid var(--light-text-color);
}

.form-item:focus-within {
  border: 1px solid var(--main-text-color);
}

.nest {
  max-width: 90%;
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
</style>
