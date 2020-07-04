<template>
  <div class="grid-container">
    <!-- Title -->
    <div class="title-wrapper">
      <h1>Preview reservations</h1>
    </div>
    <div id="searchbar-wrapper">
      <!-- Search form -->
      <form method="GET" id="searchbar-bookings" @submit.prevent="applyFilters">
        <div class="form-item-booking" tabindex="1">
          <div class="nest stacked">
            <label for="location" class="label-booking">USERNAME</label>
            <input
              type="text"
              name="location"
              placeholder="Who are you looking for?"
              v-model="username"
            />
          </div>
        </div>
        <div class="form-item-booking minified-booking" tabindex="2">
          <div class="dropdown-container">
            <select v-model="status">
              <option value disabled selected>Search by status</option>
              <option value="Declined">Declined</option>
              <option value="Accepted">Accepted</option>
              <option value="Canceled">Canceled</option>
              <option value="Completed">Completed</option>
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
        <button type="submit">
          <img src="./../../public/svg/search-icon.png" />Search
        </button>
      </form>
      <!-- End of the form -->
    </div>
    <table id="reservations" v-if="reservationsNonPending.length != 0">
      <tr>
        <th>Customer</th>
        <th>Check-in</th>
        <th>Check-out</th>
        <th>Message</th>
        <th>Apartment</th>
        <th>Status</th>
      </tr>
      <tr v-for="(reservation,i) in reservationsNonPending" :key="i">
        <td class="customer">{{reservation.guest}}</td>
        <td>{{reservation.checkInDay}}</td>
        <td>{{reservation.checkOutDay}}</td>
        <td>{{reservation.reservationMessage}}</td>
        <td>{{reservation.apartment}}</td>
        <td>{{reservation.totalPrice}}</td>
        <td>
          <Button
            v-if="completed(reservation) && !isUserGuest"
            text="Complete"
            width="96"
            height="34"
            fontsize="16"
            @clicked="completeReservation(reservation)"
          />
          <Button
            v-if="isUserGuest && isReservationCancelable(reservation)"
            text="Cancel"
            width="96"
            height="34"
            fontsize="16"
            @clicked="cancelReservation(reservation)"
          />
          <p v-else>{{reservation.status}}</p>
        </td>
      </tr>
    </table>
    <div
      class="no-active-reservations"
      v-if="reservationsNonPending.length == 0"
    >There are no non-pending reservations!</div>
    <div id="pending-reservations-title">
      <h2>Pending reservations</h2>
    </div>
    <table id="pending-reservations" v-if="reservationsPending.length != 0">
      <tr>
        <th>Guest</th>
        <th>Check-in</th>
        <th>Check-out</th>
        <th>Message</th>
        <th>Apartment</th>
        <th></th>
        <th></th>
      </tr>
      <tr v-for="(reservation,i) in reservationsPending" :key="i">
        <td class="customer">{{reservation.guest}}</td>
        <td>{{reservation.checkInDay}}</td>
        <td>{{reservation.checkOutDay}}</td>
        <td>{{reservation.reservationMessage}}</td>
        <td>{{reservation.apartment}}</td>
        <td>{{reservation.totalPrice}}</td>
        <td>
          <Button
            v-if="!isUserGuest"
            text="Accept"
            width="96"
            height="34"
            fontsize="16"
            @clicked="acceptReservation(reservation)"
          />
        </td>
        <td>
          <Button
            v-if="!isUserGuest"
            text="Decline"
            width="96"
            height="34"
            fontsize="16"
            @clicked="declineReservation(reservation)"
          />
          <Button
            v-if="isUserGuest && isReservationCancelable(reservation)"
            text="Cancel"
            width="96"
            height="34"
            fontsize="16"
            @clicked="cancelReservation(reservation)"
          />
        </td>
      </tr>
    </table>
    <div
      class="no-pending-reservations"
      v-if="reservationsPending.length == 0"
    >There are no pending reservations!</div>
  </div>
</template>

<script>
import Button from "./form-components/Button.vue";
import ReservationsService from "./../services/ReservationsService";
import ApartmentsService from "./../services/ApartmentsService";
import moment from "moment";
import usersService from "../services/UserService";

export default {
  components: { Button },
  async beforeMount() {
    if (this.$route.query.guest == "" && this.$route.query.guest == "") {
      this.reservations = await ReservationsService.getReservations(null);
    } else if (this.$route.query.guest == "") {
      this.reservations = await ReservationsService.getReservations({
        status: this.$route.query.status
      });
    } else if (this.$route.query.status == "") {
      this.reservations = await ReservationsService.getReservations({
        guest: this.$route.query.guest
      });
    } else {
      this.reservations = await ReservationsService.getReservations({
        status: this.$route.query.status,
        guest: this.$route.query.guest
      });
    }

    this.reservations.forEach(async reserv => {
      var app = await ApartmentsService.getApartmentById(reserv.apartment);
      if (reserv.status != "Created") {
        this.reservationsNonPending.push({
          id: reserv.id,
          guest: reserv.guest,
          checkInDay: reserv.checkInDay,
          checkOutDay: this.checkOutDay(reserv),
          reservationMessage:
            reserv.reservationMessage == ""
              ? "None"
              : reserv.reservationMessage,
          apartment: app.name,
          status: reserv.status,
          totalPrice: reserv.totalPrice
        });
      } else if (reserv.status == "Created") {
        this.reservationsPending.push({
          id: reserv.id,
          guest: reserv.guest,
          checkInDay: reserv.checkInDay,
          checkOutDay: this.checkOutDay(reserv),
          reservationMessage:
            reserv.reservationMessage == ""
              ? "None"
              : reserv.reservationMessage,
          apartment: app.name,
          status: reserv.status,
          totalPrice: reserv.totalPrice
        });
      }
    });
  },
  mounted() {
    this.$forceUpdate();
  },
  data() {
    return {
      reservations: [],
      reservationsNonPending: [],
      reservationsPending: [],
      status: "",
      selectedType: "",
      username: ""
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
  },
  methods: {
    checkOutDay(reservation) {
      var date = moment(reservation.checkInDay, "DD-MM-YYYY").add(
        reservation.nightsStaying,
        "days"
      );
      return date.format("DD-MM-YYYY");
    },
    isUserGuest() {
      return usersService.getUserType() == "Guest" ? true : false;
    },
    isReservationCancelable(reservation) {
      if (reservation.status == "Created" || reservation.status == "Accepted") {
        return true;
      } else {
        return false;
      }
    },
    completed(reservation) {
      if (
        moment(reservation.checkOutDay).isBefore(moment(), "day") &&
        reservation.status == "Accepted"
      ) {
        return true;
      }
      return false;
    },
    async acceptReservation(reservation) {
      var success = await ReservationsService.acceptReservation(reservation);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1500);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    async declineReservation(reservation) {
      var success = await ReservationsService.declineReservation(reservation);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1500);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    async completeReservation(reservation) {
      var success = await ReservationsService.completeReservation(reservation);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1500);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    async cancelReservation(reservation) {
      var success = await ReservationsService.cancelReservation(reservation);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1500);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    async applyFilters() {
      if (this.username == "" && this.status == "") {
        return;
      } else if (this.username == "") {
        this.$router.replace({
          name: "preview-bookings",
          query: {
            status: this.status
          }
        });
      } else if (this.status == "") {
        this.$router.replace({
          name: "preview-bookings",
          query: {
            guest: this.username
          }
        });
      } else {
        this.$router.replace({
          name: "preview-bookings",
          query: {
            guest: this.username,
            status: this.status
          }
        });
      }
      this.$router.go();
    }
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
  grid-column: 1/5;
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
#searchbar-bookings {
  position: relative;
  width: 80%;
  height: auto;
}
#searchbar-bookings button[type="submit"] {
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
  position: absolute;
  top: 22%;
  right: -22%;
  text-align: center;
  text-justify: distribute;
}
#searchbar-bookings button[type="submit"] img {
  height: 16px;
  width: auto;
  margin: 0px 5px -2px 0px;
}
#searchbar-bookings button[type="submit"]:hover {
  background: var(--brand-color-hover);
}
#searchbar-bookings input[type="text"] {
  font-size: 16px;
  padding-top: 3px;
  padding-right: 10px;
  border: 0;
}
#searchbar-bookings input[type="text"]::placeholder {
  color: var(--light-text-color);
}

.form-item-booking {
  display: inline-block;
  padding: 2rem 0 0.5rem 1.5rem;
  background: var(--background-color);
  width: 40%;
  border: 1px solid var(--background-color);
  border-radius: var(--border-radius);
  height: 2.3rem;
}

.form-item-booking:hover {
  border: 1px solid var(--light-text-color);
}

.form-item-booking:focus-within {
  border: 1px solid var(--main-text-color);
}

.nest {
  max-width: 60%;
  margin-top: 6px;
}

.stacked {
  display: grid;
}

.label-booking {
  font-weight: bold;
  font-size: 12px;
  grid-row: 1;
  grid-column: 1;
  margin-top: -15px;
}

.minified-booking {
  padding: 0.5rem 0 2rem 1.5rem;
}

.dropdown-container {
  width: 250px;
  position: relative;
  top: 5%;
  left: 8%;
}
select {
  width: 200px;
  height: 46px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 0;
  background-color: var(--background-color);
  border: none;
  border-bottom: 2px solid var(--medium-text-color);
  color: var(--main-text-color);
  appearance: none;
  padding: 10px;
  -webkit-appearance: none;
  -moz-appearance: none;
  transition: color 0.3s ease, background-color 0.3s ease,
    border-bottom-color 0.3s ease;
  margin-top: 0;
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
  top: 11px;
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