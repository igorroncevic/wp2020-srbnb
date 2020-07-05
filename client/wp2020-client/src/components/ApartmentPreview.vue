<template>
  <div id="grid-container">
    <div id="header-wrapper">
      <h1>{{apartment.name}}</h1>
      <div id="city-edit">
        <p>{{apartment.location.address.street}} {{apartment.location.address.number}}, {{apartment.location.address.place}}</p>
        <div id="edit-btn">
          <svg style="width:20px;height:20px" viewBox="0 0 24 24">
            <path
              fill="#b8b8b8"
              d="M20.71,7.04C21.1,6.65 21.1,6 20.71,5.63L18.37,3.29C18,2.9 17.35,2.9 16.96,3.29L15.12,5.12L18.87,8.87M3,17.25V21H6.75L17.81,9.93L14.06,6.18L3,17.25Z"
            />
          </svg>
          <p style="float: right;">Edit</p>
        </div>
      </div>
      <div id="app-photo">Cover photo</div>
    </div>
    <div id="main-info-grid">
      <div id="left-col">
        <div id="apartment-header">
          <div id="app-type">{{apartment.type}} hosted by {{apartment.host}}</div>
          <div
            id="amenities-preview"
          >{{apartment.numberOfGuest}} guests * {{apartment.numberOfRooms}} room(s)</div>
          <div id="owner-photo"></div>
        </div>
        <div>
          <label for="amenity-item">Amenities</label>
          <div id="amenity-grid">
            <div class="single-amenity" v-for="(amenityItem, i) in amenities" :key="i">
              <svg style="width:24px;height:24px; margin-bottom: -4px;" viewBox="0 0 24 24">
                <path fill="#e8394d" d="M12,2L1,21H23M12,6L19.53,19H4.47" />
              </svg>
              {{amenityItem.name}}
            </div>
          </div>
          <p
            v-if="amenities.length == 0"
            style="color: var(--main-text-color); font-weight: 400; margin-top: 20px; font-size:18px;"
          >No amenities available.</p>
        </div>
      </div>
      <div id="right-col">
        <div id="card-container">
          <div id="price-per-night">
            <span style="font-weight: 600;">${{apartment.pricePerNight}}</span> / night
          </div>
          <div id="booking-info">
            <div class="form-item checkin" style="width: 39.7%" tabindex="1">
              <div class="stacked nest">
                <label for="checkin-dates" class="label-style">CHECK-IN</label>
                <datepicker
                  placeholder="Select check-in"
                  class="startDate"
                  v-model="startDate"
                  :value="startDate"
                  name="startDate"
                  :disabledDates="disabledStartDates"
                  :format="customFormatter"
                ></datepicker>
              </div>
            </div>
            <div class="form-item checkout" style="width: 39.2%" tabindex="1">
              <div class="stacked nest">
                <label for="checkin-dates" class="label-style">CHECK-OUT</label>
                <datepicker
                  placeholder="Select check-out"
                  class="startDate"
                  v-model="endDate"
                  :value="endDate"
                  name="endDate"
                  :disabledDates="disabledStartDates"
                  :format="customFormatter"
                ></datepicker>
              </div>
            </div>
            <div class="form-item guests" style="width: 84.2%;" tabindex="1">
              <div class="stacked nest">
                <label for="guests" class="label-style">GUESTS</label>
                <input
                  type="number"
                  min="0"
                  :max="apartment.numberOfGuest"
                  name="guests"
                  placeholder="Number of guests"
                />
              </div>
            </div>
          </div>
          <div id="message-for-the-host">
            <label for="message" style="font-size: 22px; margin-left: 26px;">Message for the host</label>
            <textarea
              v-model="reservationMessage"
              name="message"
              style="resize: none; outline: none; width; 200px; overflow: auto;"
              rows="5"
            ></textarea>
          </div>
          <div id="button">
            <Button text="Book" width="400" fontsize="24" height="50" @clicked="placeReservation" />
          </div>
        </div>
      </div>
    </div>
    <div id="reviews-title">Reviews</div>
    <div id="reviews-wrapper">
      <review-item v-for="(review, i) in reviews" :key="i" :review="review" @hideshow="hideShowReview(review)" />
      <p
        v-if="reviews.length == 0"
        style="color: var(--main-text-color); font-weight: 400; margin-top: 20px; font-size:18px;"
      >No reviews yet.</p>
    </div>
    <div id="my-review">
      <div class="my-review-title">Write a Review</div>
      <div class="review-my-rating" style="font-size: 18px; margin-left: 60px;">
        <star-rating v-model="rating"></star-rating>
      </div>
      <div class="review-my-text">
        <textarea
          v-model="text"
          name="my-review"
          style="resize: none; outline: none; width: 700px; overflow: auto; height: 150px; margin-left: -30px;"
          rows="5"
        ></textarea>
      </div>
      <div class="review-button-publish">
        <Button text="Publish" width="200" fontsize="22" height="40" @clicked="publishReview" />
      </div>
    </div>
  </div>
</template>

<script>
import Button from "./form-components/Button.vue";
import ReviewItem from "./reusable/ReviewItem.vue";
import StarRating from "./reusable/StarRating.vue";
import Datepicker from "vuejs-datepicker";
import moment from "moment";
import ApartmentsService from "./../services/ApartmentsService";
import ReservationsService from "./../services/ReservationsService";
import UserService from "./../services/UserService";
import AmenitiesService from "./../services/AmenitiesService";
import CommentsService from "./../services/CommentsService";

export default {
  components: {
    Button,
    ReviewItem,
    StarRating,
    Datepicker
  },
  name: "ApartmentPreview",
  async beforeMount() {
    // Get apartment
    this.apartment = await ApartmentsService.getApartmentById(
      this.$route.params.id
    );
    if (this.apartment == null) {
      this.$toasted.global.noApartmentWithThisId();
      this.$router.go(-1);
      return;
    }
    console.log(this.apartment);
    if (typeof this.apartment !== "object") {
      this.$toasted.global.youCantPreviewThisApartment();
      this.$router.go(-1);
      return;
    }
    //Za slucaj da ima vise prekida u datumima
    for (
      var i = 0, j = -1;
      j < this.apartment.availableDaysForRent.length;
      i += 2, j += 2
    ) {
      let oneRange = {};
      if (i == 0) {
        oneRange.from = new Date(0, 0, 0);
        oneRange.to = new Date(Date.now() - 8640000);
        this.disabledStartDates.ranges.push(oneRange);
        this.disabledEndDates.ranges.push(oneRange);
        continue;
      }
      if (j == this.apartment.availableDaysForRent.length - 1) {
        oneRange.from = this.toDate(this.apartment.availableDaysForRent[j]);
        oneRange.to = new Date(86400000000000); //skoro max datum
        this.disabledStartDates.ranges.push(oneRange);
        this.disabledEndDates.ranges.push(oneRange);
        continue;
      }
      if (
        moment() > moment(this.apartment.availableDaysForRent[i]) &&
        moment() > moment(this.apartment.availableDaysForRent[j])
      ) {
        continue;
      }
      oneRange.from = this.toDate(this.apartment.availableDaysForRent[j]);
      oneRange.to = this.toDate(this.apartment.availableDaysForRent[i]);
      this.disabledStartDates.ranges.push(oneRange);
      this.disabledEndDates.ranges.push(oneRange);
    }
    // Get amenities
    this.amenities = await AmenitiesService.getAmenitiesForApartment(
      this.$route.params.id
    );
    this.reviews = await CommentsService.getCommentsForApartment(
      this.$route.params.id
    );
    console.log(this.amenities);
    console.log(this.reviews);
  },
  data() {
    return {
      apartment: {},
      disabledStartDates: {
        ranges: []
      },
      disabledEndDates: {
        ranges: []
      },
      amenities: [],
      reviews: [],
      reservationMessage: "",
      startDate: "",
      endDate: ""
    };
  },
  methods: {
    async publishReview() {
      var review = {
        apartment: this.apartment.id,
        rating: this.rating,
        content: this.text
      };
      var success = await CommentsService.postComment(review);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1500);
        return;
      } else {
        this.$toasted.global.cantPostComment();
      }
    },
    async placeReservation() {
      var start = moment(this.startDate);
      var end = moment(this.endDate);
      var nightsStaying = end.diff(start, "days");

      if (nightsStaying <= 0) {
        this.$toasted.global.startDateAfterEndDate();
        return;
      }

      if (!UserService.getToken()) {
        this.$toasted.global.notLoggedIn();
        return;
      }

      var success = await ReservationsService.placeReservation({
        apartment: this.apartment.id,
        checkInDay: moment(start).format("DD-MM-YYYY"),
        nightsStaying: nightsStaying,
        reservationMessage: this.reservationMessage
      });
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1500);
      } else {
        this.$toasted.global.unsuccessfulReservation();
      }
    },
    customFormatter(date) {
      return moment(date).format("MMMM Do YYYY"); //DD-MM-YYYY for java friendly dates
    },
    hideShowReview(review){
       var success;
       if(review.visibleToGuests){
          success = CommentsService.hideComment(review);
       }else{
          success = CommentsService.showComment(review);
       }
       if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1000);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    toDate(date) {
      var parts = date.split("-");
      return new Date(parts[2], parts[1] - 1, parts[0]);
    }
  }
};
</script>

<style scoped>
/* For dates */
.dates {
  width: 24% !important;
}

input[data-v-8dc7cce2] {
  background: var(--background-color);
}

.vdp-datepicker[data-v-8dc7cce2] {
  width: 130px !important;
  margin-right: 10px;
}

.datepickers {
  display: inline-flex;
  width: min-content;
  flex-direction: row;
  margin-top: 2px;
  width: 50%;
}

.vdp-datepicker {
  width: 170px !important;
  color: var(--main-text-color) !important;
  cursor: pointer;
}

/* Header */
#grid-container {
  display: grid;
  grid-template-columns: repeat(19, 1fr);
  margin-bottom: 250px;
}
#header-wrapper {
  grid-column: 2/18;
  grid-row: 1;
  grid-template-rows: 10rem 5rem 50rem;
}
h1 {
  margin: 0;
  grid-row: 1;
  font-weight: 600;
  font-size: 36px;
  color: var(--main-text-color);
}
p {
  margin: 0;
  color: var(--light-text-color);
}
#city-edit {
  grid-row: 2;
  display: grid;
  grid-template-columns: 400px 150px;
  column-gap: 72.4%;
}
#edit-btn {
  position: relative;
  width: 50px;
  align-content: center;
  cursor: pointer;
}
#app-photo {
  grid-row: 3;
  height: 18rem;
  width: 100%;
  border: 1px solid var(--light-text-color);
  color: var(--light-text-color);
  text-align: center;
}

/* Main info */
#main-info-grid {
  margin-top: 20px;
  display: grid;
  grid-row: 2;
  grid-column: 2/18;
  grid-auto-columns: 50% 50%;
}
#left-col {
  grid-column: 1;
  grid-auto-rows: 30% auto;
}
#apartment-header {
  grid-row: 1;
  display: grid;
  grid-auto-columns: 80% 20%;
}
#app-type {
  grid-column: 1;
  color: var(--main-text-color);
  font-size: 32px;
  font-weight: 500;
}
#amenities-preview {
  color: var(--light-text-color);
  grid-column: 1;
  font-size: 16px;
}
#owner-photo {
  position: relative;
  top: -60%;
  grid-column: 2;
  background: #e8394d;
  border-radius: 50%;
  height: 60px;
  width: 60px;
}
label {
  font-size: 24px;
  font-weight: 500;
  color: var(--main-text-color);
}
#amenity-grid {
  display: grid;
  grid-template-columns: 30px 50px;
  column-gap: 15rem;
}
.single-amenity {
  color: var(--main-text-color);
  font-size: 18px;
  grid-column: auto;
  margin-top: 10px;
  margin-right: 0px;
  width: 300px;
}

#right-col {
  position: relative;
  grid-column: 2;
  width: 500px;
  left: 27.5%;
}
#card-container {
  display: grid;
  grid-template-rows: 5rem 10rem 12rem 2rem;
  border: 1px solid #dadada;
  border-radius: 8%;
  height: 500px;
  box-shadow: 0px 5px #dddddd;
}
#price-per-night {
  position: relative;
  top: 25%;
  left: 6%;
  grid-row: 1;
  font-size: 26px;
}
#booking-info {
  grid-row: 2;
  margin-top: 20px;
  height: 100px;
}
input[type="text"],
input[type="number"] {
  font-size: 16px;
  padding-top: 3px;
  padding-left: 0px;
  width: 150px;
  color: var(--main-text-color);
  border: 0;
}
input[type="text"]::placeholder,
input[type="number"]::placeholder {
  color: var(--light-text-color);
}

input[type="number"] {
  width: 200px;
}

.checkin {
  position: relative;
  top: -25%;
  left: 5%;
  border-radius: var(--border-radius) 0 0 0;
}
.guests {
  position: relative;
  top: -25%;
  left: 5%;
  border-radius: 0 0 var(--border-radius) var(--border-radius);
}
.checkout {
  position: relative;
  top: -25%;
  left: 5%;
  border-radius: 0 var(--border-radius) 0 0;
}
.form-item {
  display: inline-block;
  padding: 0.5rem 0 0.5rem 1.5rem;
  background: var(--background-color);
  width: 40%;
  border: 1px solid var(--light-text-color);
  height: 3rem;
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
.label-style {
  font-weight: bold;
  font-size: 12px;
  grid-row: 1;
  grid-column: 1;
}
#message-for-the-host {
  grid-row: 3;
  display: grid;
  margin-bottom: 4%;
}
textarea {
  position: relative;
  width: 435px;
  border-radius: var(--border-radius);
  border: 1px solid var(--light-text-color);
  font: inherit;
  left: 5%;
  height: 100px;
  padding: 8px;
}
textarea:focus-within {
  border: 1px solid var(--main-text-color);
}
#button {
  grid-row: 4;
  position: relative;
  left: 10%;
  bottom: 20%;
}

/* Reviews */
#reviews-wrapper {
  margin-top: 10px;
  margin-bottom: 20px;
  grid-row: 3;
  grid-column: 2/18;
  display: grid;
  grid-template-columns: 530px 530px;
  column-gap: 20rem;
}
#reviews-title {
  grid-row: 3;
  grid-column: 2;
  margin-top: 30px;
  font-size: 28px;
  font-weight: 500;
  height: 50px;
}
#my-review {
  grid-row: 5;
  grid-column: 5/18;
  position: relative;
  left: 28%;
  display: grid;
  grid-template-columns: 3rem 22rem 5rem 15rem;
  grid-template-rows: 4rem 3rem auto 3rem;
  margin-top: 25px;
  margin-bottom: 50px;
}
.my-review-title {
  grid-row: 2;
  grid-column: 1/3;
  color: var(--main-text-color);
  font-size: 24px;
  font-weight: 400;
  margin-bottom: 10px;
}
.review-my-name {
  grid-column: 2;
  grid-row: 2;
  color: var(--main-text-color);
  font-size: 22px;
  font-weight: 500;
  margin-left: 10px;
  align-self: center;
  margin-bottom: 5px;
}
.review-my-rating {
  grid-column: 4;
  grid-row: 2;
  position: relative;
  top: 6%;
}
.review-my-text {
  grid-row: 3;
  grid-column: 1/4;
  font-size: 20px;
  margin-top: 10px;
  width: 100%;
}
.review-button-publish {
  grid-row: 4;
  grid-column: 3;
  margin-left: 73px;
  margin-top: 10px;
}
</style>
