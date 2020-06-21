<template>
  <div id="grid-container">
    <div id="header-wrapper">
      <h1>Apartments Perić</h1>
      <div id="city-edit">
        <p>Novi Sad, Serbia</p>
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
          <div id="app-type">Entire apartment hosted by Petar</div>
          <div id="amenities-preview">4 guests * 1 bedroom * 2 beds * 1 bath</div>
          <div id="owner-photo"></div>
        </div>
        <div>
          <label for="amenity-item">Amenities</label>
          <div id="amenity-grid">
            <div class="single-amenity" v-for="(amenityItem, i) in amenities" :key="i">
              <svg style="width:24px;height:24px; margin-bottom: -4px;" viewBox="0 0 24 24">
                <path fill="#e8394d" d="M12,2L1,21H23M12,6L19.53,19H4.47" />
              </svg>
              {{amenityItem}}
            </div>
          </div>
        </div>
      </div>
      <div id="right-col">
        <div id="card-container">
          <div id="price-per-night">
            <span style="font-weight: 600;">$40</span> / night
          </div>
          <div id="booking-info">
            <div class="form-item checkin" style="width: 39.7%" tabindex="1">
              <div class="stacked nest">
                <label for="checkin-dates" class="label-style">CHECK-IN</label>
                <input type="text" name="checkin-dates" placeholder="Select dates" />
              </div>
            </div>
            <div class="form-item checkout" style="width: 39.2%" tabindex="1">
              <div class="stacked nest">
                <label for="checkin-dates" class="label-style">CHECK-OUT</label>
                <input type="text" name="checkin-dates" placeholder="Select dates" />
              </div>
            </div>
            <div class="form-item guests" style="width: 84.2%;" tabindex="1">
              <div class="stacked nest">
                <label for="checkin-dates" class="label-style">GUESTS</label>
                <input type="text" name="checkin-dates" placeholder="Number of guests" />
              </div>
            </div>
          </div>
          <div id="message-for-the-host">
            <label for="message" style="font-size: 22px; margin-left: 26px;">Message for the host</label>
            <textarea
              name="message"
              style="resize: none; outline: none; width; 200px; overflow: auto;"
              rows="5"
            ></textarea>
          </div>
          <div id="button">
            <Button text="Book" width="400" fontsize="24" height="50" />
          </div>
        </div>
      </div>
    </div>
    <div id="reviews-title">Reviews</div>
    <div id="reviews-wrapper">
      <review-item v-for="(review, i) in reviews" :key="i" :review="review" />
    </div>
    <div id="my-review">
      <div class="my-review-title">Write Review</div>
      <div class="review-my-photo"></div>
      <div class="review-my-name">Petar Peric</div>
      <div class="review-my-rating" style="font-size: 18px; margin-left: 110px;">
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
export default {
  components: {
    Button,
    ReviewItem,
    StarRating
  },
  name: "ApartmentPreview",
  data() {
    return {
      amenities: [
        "Private parking",
        "Microwave",
        "Stovetop",
        "Kitchen",
        "Wifi",
        "Elevator",
        "Garden",
        "Refrigerator"
      ],
      reviews: [
        {
          user: "Marko Jovanovic",
          rating: 4,
          text:
            "My stay at Apartments Peric was delightful! I loved every second of it and the owner was wonderful. Wish I could've stayed more."
        },
        {
          user: "Petar Peric",
          rating: 5,
          text:
            "My stay at Apartments Peric was delightful! I loved every second of it and the owner was wonderful. Wish I could've stayed more."
        },
        {
          user: "Ivica Ivic",
          rating: 4,
          text:
            "My stay at Apartments Peric was delightful! I loved every second of it and the owner was wonderful. Wish I could've stayed more."
        },
        {
          user: "Marko Jovanovic",
          rating: 3,
          text:
            "My stay at Apartments Peric was delightful! I loved every second of it and the owner was wonderful. Wish I could've stayed more."
        }
      ],
      rating: 0,
      text: "",
      user: "Jelena Maravic"
    };
  },
  methods: {
    publishReview() {
      this.reviews.push({
        user: this.user,
        rating: this.rating,
        text: this.text
      });
      this.text = "";
      this.rating = 0;
    }
  }
};
</script>

<style scoped>
/* Header */
#grid-container {
  display: grid;
  grid-template-columns: repeat(19, 1fr);
  margin-bottom: 150px;
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
  grid-template-columns: repeat(2, 150px);
  column-gap: 85%;
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
input[type="text"] {
  font-size: 16px;
  padding-top: 3px;
  padding-left: 0px;
  width: 150px;
  border: 0;
}
input[type="text"]::placeholder {
  color: var(--light-text-color);
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
  margin-bottom: 10%;
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
  margin-top: 70px;
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
  display: grid;
  grid-template-columns: 3rem 25rem 15rem;
  grid-template-rows: 4rem 3rem auto 3rem;
  margin-top: 50px;
}

.my-review-title {
  grid-row: 1;
  grid-column: 1/3;
  color: var(--main-text-color);
  font-size: 28px;
  font-weight: 500;
  margin-bottom: 10px;
}

.review-my-photo {
  grid-column: 0;
  grid-row: 2;
  background: var(--brand-color);
  border-radius: 50%;
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
  grid-column: 3;
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
