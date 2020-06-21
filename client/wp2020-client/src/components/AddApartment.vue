<template>
  <div class="grid-container">
    <!-- Title -->
    <div class="title-wrapper">
      <h1 style="width:25rem; font-size: 32px;">Hi, Petar! Let's get started listing your space.</h1>
    </div>
    <div id="form">
      <StylishInput
        id="apartmentName"
        label-text="Pick a name for your space"
        :value="''"
        v-model="apartmentName"
        width="600"
        height="38"
      />
      <div id="apartment-type">
        <label for="dropdown-container">What kind of apartment do you have?</label>
        <div class="dropdown-container">
          <select required>
            <option value hidden>Select apartment type</option>
            <!-- Placeholder -->

            <!-- normal options -->
            <option value="Whole">Whole apartment</option>
            <option value="Studio">Studio apartment</option>
            <option value="Single">Single room</option>
            <option value="Deluxe">Deluxe apartment</option>
          </select>
          <div class="select-icon">
            <svg focusable="false" style="width:25px;height:25px" viewBox="0 0 24 24">
              <path fill="#e8394d" d="M7.41,8.58L12,13.17L16.59,8.58L18,10L12,16L6,10L7.41,8.58Z" />
            </svg>
          </div>
        </div>
      </div>
      <div class="counters">
        <!-- Rooms -->
        <div class="counter">
          <p style="font-size: 24px; margin: -2px 20px 0 0;">Rooms:</p>
          <svg
            style="width:28px;height:28px; margin-left: 1px;"
            viewBox="0 0 24 24"
            @click="decrementRooms"
          >
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M7,13H17V11H7"
            />
          </svg>
          <p style="margin: -1px 20px; font-size:24px;">{{roomsCounter}}</p>
          <svg
            style="width:28px;height:28px"
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
        <!-- Guests -->
        <div class="counter">
          <p style="font-size: 24px; margin: -2px 20px 0 0;">Guests:</p>
          <svg style="width:28px;height:28px" viewBox="0 0 24 24" @click="decrementGuests">
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M7,13H17V11H7"
            />
          </svg>
          <p style="margin: -1px 20px; font-size:24px;">{{guestsCounter}}</p>
          <svg
            style="width:28px;height:28px"
            viewBox="0 0 24 24"
            cursor="pointer"
            @click="incrementGuests"
          >
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M13,7H11V11H7V13H11V17H13V13H17V11H13V7Z"
            />
          </svg>
        </div>
        <!-- Beds -->
        <div class="counter">
          <p style="font-size: 24px; margin: -2px 42px 0 0;">Beds:</p>
          <svg style="width:28px;height:28px" viewBox="0 0 24 24" @click="decrementBeds">
            <path
              fill="#e8394d"
              d="M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M7,13H17V11H7"
            />
          </svg>
          <p style="margin: -1px 20px; font-size:24px;">{{bedsCounter}}</p>
          <svg
            style="width:28px;height:28px"
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
      </div>

      <div id="location">
        <StylishInput
          style="margin-right: 20px;"
          id="apartmentName"
          label-text="Street"
          :value="''"
          v-model="streetName"
          width="290"
          height="38"
        />
        <StylishInput
          id="apartmentName"
          label-text="City"
          :value="''"
          v-model="streetName"
          width="290"
          height="38"
        />
      </div>
      <div class="amenity-checkboxes">
        <label for="amenities-grid">What amenities do you offer?</label>
        <div class="amenities-grid">
          <div id="first-col">
            <Checkbox value="Essentials" :checked="false" />
            <Checkbox value="Wifi" :checked="false" />
            <Checkbox value="Shampoo" :checked="false" />
            <Checkbox value="Closet/drawers" :checked="false" />
            <Checkbox value="TV/Cable" :checked="false" />
            <Checkbox value="Heaters" :checked="false" />
            <Checkbox value="Air conditioning" :checked="false" />
            <Checkbox value="Breakfast, cofee, tea" :checked="false" />
          </div>
          <div id="second-col">
            <Checkbox value="Desk/workspace" :checked="false" />
            <Checkbox value="Fireplace" :checked="false" />
            <Checkbox value="Iron for clothes" :checked="false" />
            <Checkbox value="Hair dryer" :checked="false" />
            <Checkbox value="Pets in the house" :checked="false" />
            <Checkbox value="Private entrance" :checked="false" />
            <Checkbox value="Parking" :checked="false" />
            <Checkbox value="Elevator" :checked="false" />
          </div>
        </div>
        <label id="safety-label" for="safety-amenities-grid">Safety amenities</label>
        <div class="safety-amenities-grid">
          <div class="first-col">
            <Checkbox value="Smoke detector" :checked="false" />
            <Checkbox value="Carbon monoxide detector" :checked="false" />
            <Checkbox value="First aid kit" :checked="false" />
            <Checkbox value="Safety card" :checked="false" />
            <Checkbox value="Fire extinguisher" :checked="false" />
            <Checkbox value="Lock on bedroom door" :checked="false" />
          </div>
        </div>
      </div>
      <div class="checkin-checkout-cointainer">
        <label for="checkin-checkout">And to finish off...</label>
        <div class="checkin-checkout">
          <StylishInput
            id="apartmentName"
            label-text="Check-in"
            labelColor="#323846"
            :value="''"
            v-model="checkin"
            width="300"
            height="38"
          />
          <StylishInput
            id="apartmentName"
            label-text="Check-out"
            labelColor="#323846"
            :value="''"
            v-model="checkout"
            width="290"
            height="38"
          />
        </div>
      </div>
      <div id="price-cointainer">
        <StylishInput
          id="price-input"
          label-text="Price"
          :value="''"
          v-model="price"
          width="100"
          height="38"
        />
        <p id="per-night">$ / night</p>
      </div>
      <Button text="Publish" width="400" fontsize="20" id="button" height="50"/>
    </div>
  </div>
</template>

<script>
import Button from "./form-components/Button.vue";
import StylishInput from "./reusable/StylishInput.vue";
import Checkbox from "./form-components/Checkbox.vue";
export default {
  components: { StylishInput, Checkbox, Button },
  data() {
    return {
      apartmentName: "",
      streetName: "",
      roomCounter: 0,
      bedCounter: 0,
      guestCounter: 0,
      checkin: "",
      checkout: "",
      price: 0
    };
  },
  computed: {
    roomsCounter() {
      return this.roomCounter;
    },
    bedsCounter() {
      return this.bedCounter;
    },
    guestsCounter() {
      return this.guestCounter;
    }
  },
  methods: {
    incrementBeds() {
      this.bedCounter++;
    },
    decrementBeds() {
      if (this.bedCounter > 0) this.bedCounter--;
    },
    incrementRooms() {
      this.roomCounter++;
    },
    decrementRooms() {
      if (this.roomCounter > 0) this.roomCounter--;
    },
    incrementGuests() {
      this.guestCounter++;
    },
    decrementGuests() {
      if (this.guestCounter > 0) this.guestCounter--;
    }
  }
};
</script>

<style scoped>
#button{
   grid-row: 9;
   justify-self: center;
}

#price-input {
  grid-column: 1;
}

#per-night {
   grid-column: 2;
   font-size: 22px;
   justify-content: baseline;
   margin: 0;
   margin-top: auto;
   margin-left: 40px;
   margin-bottom: 10px;
}

#price-cointainer {
  grid-row: 7;
  display: grid;
  grid-template-columns: 5rem 15rem;
}

.checkin-checkout-cointainer {
  grid-row: 6;
}

.checkin-checkout {
  display: grid;
  margin-top: 30px;
}

.amenity-checkboxes {
  grid-row: 5;
}

#safety-label {
  color: var(--main-text-color);
}

.amenities-grid {
  margin-top: 30px;
  margin-bottom: 30px;
  display: grid;
  grid-template-columns: 12rem 13rem;
  column-gap: 10rem;
}

.safety-amenities-grid {
  margin-top: 30px;
  display: grid;
  grid-template-columns: 16rem 15rem;
  column-gap: 10rem;
}

.first-col {
  grid-column: 1;
}

#second-col {
  grid-column: 2;
}

h1 {
  color: var(--main-text-color);
}

label {
  font-size: 24px;
  font-weight: 500;
  color: var(--brand-color);
}

#form {
  margin-top: 40px;
  grid-column: 6/17;
  grid-row: 1;
  display: grid;
  grid-template-rows: repeat(15, auto-fill);
  row-gap: 1rem;
}

#apartmentName {
  grid-row: 1;
}

#apartment-type {
  grid-row: 2;
}

#location {
  grid-row: 4;
  display: grid;
}

.counters {
  grid-row: 3;
  margin-bottom: -5px;
}

svg {
  pointer-events: all;
}

.counter {
  cursor: pointer;
  position: relative;
  left: 0%;
  display: inline-flex;
  height: 60px;
  width: 100%;
  align-content: center;
}

/* Combobox */
.dropdown-container {
  width: 400px;
  margin-top: 20px;
  margin-bottom: -10px;
}
select {
  width: 300px;
  height: 50px;
  font-size: 22px;
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
  top: 13px;
  right: 7rem;
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

.title-wrapper {
  grid-row: 1;
  margin-top: -10px;
  grid-column: 6/8;
  position: relative;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(19, 1fr);
  grid-gap: 10px;
  margin-bottom: 200px;
}

#form {
  display: grid;
  grid-template-columns: repeat(19, 1fr);
  grid-gap: 50px;
  position: relative;
  top: 5%;
}
</style>