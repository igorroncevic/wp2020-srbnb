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
        <form ref="form" @submit="submitForm" method="GET" action="/search-results" id="searchbar">
          <div class="form-item" tabindex="1">
            <div class="nest stacked">
              <label for="location" class="label">LOCATION</label>
              <input type="text" name="location" placeholder="Where are you going?" />
            </div>
          </div>
          <div class="form-item" tabindex="2">
            <div class="nest stacked">
              <label for="dates" class="label">CHECK IN / CHECK OUT</label>
              <div class="datepickers">
                <datepicker
                  placeholder="Select check-in date"
                  input-class="input-style"
                  class="startDate"
                  v-model="startDate"
                  :value="startDate"
                  name="startDate"
                  :format="customFormatter"
                ></datepicker>
                <datepicker
                  placeholder="Select check-out date"
                  input-class="input-style"
                  class="endDate"
                  v-model="endDate"
                  :value="endDate"
                  name="endDate"
                  :format="customFormatter"
                ></datepicker>
              </div>
            </div>
          </div>
          <div class="form-item" tabindex="3">
            <div class="stacked nest">
              <label for="guests" class="label">GUESTS</label>
              <input type="number" name="guests" placeholder="Add guests" />
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
      startDate: "", //add 7 days
      endDate: "", //add 10 days
      dateFormat: "D MMM"
    };
  },
  methods: {
    customFormatter(date) {
      return moment(date).format("MMMM Do YYYY");  //DD-MM-YYYY for java friendly dates
    },
    submitForm(e) {
      e.preventDefault();
      if (moment(this.startDate).isAfter(moment(this.endDate))) {
        alert("Start date cannot be before end date!");
      } else if(this.startDate == '' || this.endDate == ''){
         alert("Please select dates!")
      } else {
        this.$refs.form.submit();
      }
    }
  }
};
</script>

<style scoped>
/* Styles for dropdown */
.datepickers {
  display: inline-flex;
  width: min-content;
  flex-direction: row;
  margin-top: 2px;
}

.vdp-datepicker {
  width: 180px !important;
  color: var(--main-text-color) !important;
  cursor: pointer;
}

.input-style {
  color: var(--main-text-color);
  background: red !important;
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
  width: 89%;
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
  width: 27%;
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
</style>
