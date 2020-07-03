<template>
  <div class="grid-container">
    <!-- Title -->
    <div class="title-wrapper">
      <h1>Preview reservations</h1>
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
            <label for="dates" class="label">DATES</label>
            <input type="text" name="dates" placeholder="Search by reservation dates" />
          </div>
        </div>
        <div class="form-item minified" tabindex="3">
          <div class="stacked nest">
            <label for="guests" class="label">APARTMENT</label>
            <input type="text" name="guests" placeholder="Search by apartment name" />
          </div>
        </div>
        <button type="submit">
          <img src="./../../public/svg/search-icon.png" />Search
        </button>
      </form>
      <!-- End of the form -->
    </div>
    <table id="reservations" v-if="reservations.length != 0">
      <tr>
        <th>Customer</th>
        <th>Check-in</th>
        <th>Check-out</th>
        <th>Message</th>
        <th>Apartment</th>
        <th>Guests</th>
        <th>Status</th>
      </tr>
      <tr v-for="(reservation,i) in reservations" :key="i">
        <td class="customer">{{reservation.customer}}</td>
        <td>{{reservation.checkin}}</td>
        <td>{{reservation.checkout}}</td>
        <td>{{reservation.message}}</td>
        <td>{{reservation.apartment}}</td>
        <td>{{reservation.guests}}</td>
        <td>{{reservation.status}}</td>
      </tr>
    </table>
    <div
      class="no-active-reservations"
      v-if="reservations.length == 0"
    >There are no active reservations!</div>
    <div id="pending-reservations-title">
      <h2>Pending reservations</h2>
    </div>
    <table id="pending-reservations" v-if="reservations.length != 0">
      <tr>
        <th>Customer</th>
        <th>Check-in</th>
        <th>Check-out</th>
        <th>Message</th>
        <th>Apartment</th>
        <th>Guests</th>
        <th></th>
        <th></th>
      </tr>
      <tr v-for="(reservation,i) in reservations" :key="i">
        <td class="customer">{{reservation.customer}}</td>
        <td>{{reservation.checkin}}</td>
        <td>{{reservation.checkout}}</td>
        <td>{{reservation.message}}</td>
        <td>{{reservation.apartment}}</td>
        <td>{{reservation.guests}}</td>
        <td>
          <Button text="Accept" width="96" height="34" fontsize="16" />
        </td>
        <td>
          <Button text="Decline" width="96" height="34" fontsize="16" />
        </td>
      </tr>
    </table>
    <div
      class="no-pending-reservations"
      v-if="reservations.length == 0"
    >There are no pending reservations!</div>
  </div>
</template>

<script>
import Button from "./form-components/Button.vue";
import ReservationsService from "./../services/ReservationsService";

export default {
  components: { Button },
  async beforeMount() {
    this.reservations = await ReservationsService.getReservations();
  },
  mounted() {
    this.$forceUpdate();
  },
  data() {
    return {
       reservations: [],
      /*reservations: [
        {
          customer: "Jovan Jovanovic",
          checkin: "25/5/2020",
          checkout: "30/5/2020",
          message: "Can I check it out before I enter?",
          apartment: "Apartment Peric",
          guests: 4,
          status: "Active"
        },
        {
          customer: "Jovan Jovanovic",
          checkin: "25/5/2020",
          checkout: "30/5/2020",
          message: "Can I check it out before I enter?",
          apartment: "Apartment Peric",
          guests: 4,
          status: "Active"
        },
        {
          customer: "Jovan Jovanovic",
          checkin: "25/5/2020",
          checkout: "30/5/2020",
          message: "Can I check it out before I enter?",
          apartment: "Apartment Peric",
          guests: 4,
          status: "Active"
        },
        {
          customer: "Jovan Jovanovic",
          checkin: "25/5/2020",
          checkout: "30/5/2020",
          message: "Can I check it out before I enter?",
          apartment: "Apartment Peric",
          guests: 4,
          status: "Active"
        },
        {
          customer: "Jovan Jovanovic",
          checkin: "25/5/2020",
          checkout: "30/5/2020",
          message: "Can I check it out before I enter?",
          apartment: "Apartment Peric",
          guests: 4,
          status: "Active"
        }
      ]*/
    };
  }
};
</script>

<style>
#reservations {
  grid-row: 3/9;
  grid-column: 2/14;
  margin-top: 5px;
  border-collapse: separate;
  border-spacing: 0em 2.5em;
}

th {
  color: var(--brand-color);
  font-size: 20px;
}

td {
  text-align: center;
  font-size: 20px;
}

.customer {
  font-weight: 600;
}

#pending-reservations-title {
  grid-row: 10;
  grid-column: 1/5;
  font-size: 18px;
}

#pending-reservations {
  grid-row: 11;
  grid-column: 2/15;
  margin-top: -25px;
  border-collapse: separate;
  border-spacing: 0em 2.5em;
}

.no-active-reservations {
  font-size: 22px;
  font-weight: 500;
  grid-row: 6;
  grid-column: 6/10;
}

.no-pending-reservations {
  font-size: 22px;
  font-weight: 500;
  grid-row: 12;
  grid-column: 6/10;
}

/* Search bar */
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
  margin-bottom: 100px;
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