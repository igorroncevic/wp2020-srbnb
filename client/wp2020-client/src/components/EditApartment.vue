<template>
  <div class="grid-container">
    <!-- Title -->
    <div class="title-wrapper">
      <h1 style="width:25rem; font-size: 32px;">Let's get started listing your space!</h1>
    </div>
    <div id="form">
      <StylishInput
        id="apartmentName"
        label-text="Pick a name for your space"
        :value="apartmentName"
        v-model="apartmentName"
        width="600"
        height="38"
      />
      <div id="apartment-type">
        <label class="label" for="dropdown-container">What kind of apartment do you have?</label>
        <div class="dropdown-container">
          <select required v-model="apartmentType">
            <option value disabled hidden>Select apartment type</option>
            <option
              value="Full_Apartment"
              :selected="apartmentType == 'Full_Apartment'"
            >Full apartment</option>
            <option value="Room" :selected="apartmentType == 'Room'">Room</option>
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
      </div>

      <div id="location">
        <StylishInput
          style="margin-right: 20px;"
          id="apartmentName"
          label-text="Street"
          :value="streetName"
          v-model="streetName"
          width="260"
          height="38"
        />
        <StylishInput
          id="apartmentName"
          label-text="Number"
          name="streetNo"
          :value="streetNo"
          v-model="streetNo"
          width="120"
          height="28"
        />
        <StylishInput
          id="apartmentName"
          label-text="City"
          :value="place"
          v-model="place"
          width="270"
          height="38"
        />
        <StylishNumberInput
          id="apartmentName"
          label-text="Zip"
          name="zipcode"
          :value="zipCode"
          v-model="zipCode"
          width="120"
          height="28"
        />
      </div>
      <MapPreview
        v-if="mapReady"
        id="mapbox-preview"
        :latitude="selectedLatitude"
        :longitude="selectedLongitude"
        @markerChange="getLngLat"
        style="margin-top:0;"
      />
      <div class="amenity-checkboxes">
        <label class="label" for="amenity-grid">What amenities do you offer?</label>
        <div id="amenity-grid">
          <div class="single-amenity" v-for="(amenityItem, i) in amenities" :key="i">
            <Checkbox
              :value="amenityItem.name"
              :checked="selectedAmenities.includes(amenityItem.id)"
              @change="editAmenities($event.target.value)"
            />
          </div>
        </div>
      </div>
      <div id="available-dates">
        <div class="nest stacked">
          <label
            for="datepickerss"
            class="label"
            style="margin-bottom:10px;"
          >Add available check-in / check-out dates</label>
          <div class="datepickerss">
            <datepicker
              placeholder="Select check-in"
              class="vdp-datepickerr"
              v-model="startDate"
              :value="startDate"
              name="startDate"
              :disabledDates="disabledDates"
              :format="customFormatter"
            ></datepicker>
            <datepicker
              placeholder="Select check-out"
              class="vdp-datepickerr"
              v-model="endDate"
              :value="endDate"
              name="endDate"
              :disabledDates="disabledDates"
              :format="customFormatter"
            ></datepicker>
            <Button text="Add" width="300" fontsize="16" height="36" @clicked="addAvailableDates" />
          </div>
        </div>
        <table style="grid-row:2; grid-column:1/3;border-spacing:3rem 10px;margin-left: -40px;">
          <tr style="margin-top:5px;" v-for="(startDate, i) in availableStartDates" :key="i">
            <td>{{startDate}}</td>
            <td>{{availableEndDates[i]}}</td>
            <td>
              <Button
                text="X"
                width="40"
                fontsize="16"
                height="40"
                @clicked="removeDate(availableEndDates[i])"
              />
            </td>
          </tr>
        </table>
      </div>
      <div class="checkin-checkout-cointainer">
        <label class="label" for="checkin-checkout">And to finish off...</label>
        <div class="checkin-checkout">
          <StylishTimeInput
            id="apartmentName"
            label-text="Check-in"
            labelColor="#323846"
            :value="checkin"
            v-model="checkin"
            width="310"
            height="38"
          />
          <StylishTimeInput
            id="apartmentName"
            label-text="Check-out"
            labelColor="#323846"
            :value="checkout"
            v-model="checkout"
            width="310"
            height="38"
          />
        </div>
      </div>
      <div id="price-cointainer">
        <StylishNumberInput
          id="price-input"
          label-text="Price"
          name="price"
          v-model="price"
          :value="price"
          width="100"
          height="28"
        />
        <p id="per-night">$ / night</p>
      </div>
      <Button
        text="Publish"
        width="400"
        fontsize="20"
        id="button"
        height="50"
        @clicked="updateApartment"
      />
    </div>
  </div>
</template>

<script>
import Button from "./form-components/Button.vue";
import StylishInput from "./reusable/StylishInput.vue";
import StylishNumberInput from "./reusable/StylishNumberInput.vue";
import StylishTimeInput from "./reusable/StylishTimeInput.vue";
import Checkbox from "./form-components/Checkbox.vue";
import AmenitiesService from "./../services/AmenitiesService";
import MapPreview from "./MapPreview";
import moment from "moment";
import Datepicker from "vuejs-datepicker";
import ApartmentsService from "../services/ApartmentsService";

export default {
  components: {
    StylishInput,
    StylishNumberInput,
    StylishTimeInput,
    Checkbox,
    Button,
    MapPreview,
    Datepicker
  },
  async beforeMount() {
    this.apartment = await ApartmentsService.getApartmentById(
      this.$route.params.id
    );
    if (this.apartment == null) {
      this.$toasted.global.noApartmentWithThisId();
      this.$router.go({ name: "home" });
      return;
    }

    this.apartmentName = this.apartment.name;
    this.apartmentType = this.apartment.type;
    this.streetName = this.apartment.location.address.street;
    this.streetNo = this.apartment.location.address.number;
    this.place = this.apartment.location.address.place;
    this.zipCode = this.apartment.location.address.zipCode;
    this.numberOfRooms = this.apartment.numberOfRooms;
    this.numberOfGuest = this.apartment.numberOfGuest;
    this.checkin = this.apartment.checkInTime + ":00";
    this.checkout = this.apartment.checkOutTime + ":00";
    this.price = this.apartment.pricePerNight;
    this.selectedLatitude = this.apartment.location.latitude;
    this.selectedLongitude = this.apartment.location.longitude;
    this.roomSelected = this.apartmentType == "Room" ? true : false;

    this.selectedAmenities = [...this.apartment.amenities];
    for (let i = 0; i < this.apartment.availableDaysForRent.length; i++) {
      if (i % 2 == 0)
        this.availableStartDates.push(this.apartment.availableDaysForRent[i]);
      if (i % 2 == 1)
        this.availableEndDates.push(this.apartment.availableDaysForRent[i]);
    }

    this.amenities = await AmenitiesService.getAllAmenities();

    console.log(this.apartment);
    this.$nextTick(() => {
      this.mapReady = true;
    });
  },
  data() {
    return {
      apartment: null,
      apartmentName: "",
      apartmentType: "",
      roomSelected: false,
      streetName: "",
      streetNo: "",
      place: "",
      zipCode: "",
      numberOfRooms: 0,
      numberOfGuest: 0,
      checkin: "",
      checkout: "",
      price: 0,
      amenities: [],
      selectedAmenities: [],
      selectedLatitude: "",
      selectedLongitude: "",
      startDate: "",
      endDate: "",
      availableStartDates: [],
      availableEndDates: [],
      mapReady: false,
      disabledDates: {
        to: new Date(Date.now() - 8640000)
      }
    };
  },
  computed: {
    roomsCounter() {
      return this.numberOfRooms;
    },
    guestsCounter() {
      return this.numberOfGuest;
    }
  },
  methods: {
    async updateApartment() {
      var daysForRent = [];
      for (var i = 0; i < this.availableStartDates.length; i++) {
        daysForRent.push(this.availableStartDates[i]);
        daysForRent.push(this.availableEndDates[i]);
      }

      if (daysForRent.length == 0) {
        this.$toasted.global.pleaseSelectAvailableDates();
        return;
      }

      var updatedApartment = {
        id: this.$route.params.id,
        name: this.apartmentName,
        type: this.apartmentType,
        numberOfRooms: this.numberOfRooms,
        numberOfGuest: this.numberOfGuest,
        location: {
          latitude: this.selectedLatitude,
          longitude: this.selectedLongitude,
          address: {
            street: this.streetName,
            number: this.streetNo,
            place: this.place,
            zipCode: this.zipCode
          }
        },
        host: this.apartment.host,
        status: this.apartment.status,
        daysForRent: daysForRent,
        pricePerNight: this.price,
        checkInTime: this.checkin.split(":")[0],
        checkOutTime: this.checkout.split(":")[0],
        amenities: this.selectedAmenities
      };
      console.log(updatedApartment);
      var success = await ApartmentsService.updateApartment(updatedApartment);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go({ name: "home" });
        }, 1000);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    customFormatter(date) {
      return moment(date).format("DD-MM-YYYY"); //DD-MM-YYYY for java friendly dates
    },
    incrementRooms() {
      this.numberOfRooms++;
    },
    decrementRooms() {
      if (this.numberOfRooms > 0) this.numberOfRooms--;
    },
    incrementGuests() {
      this.numberOfGuest++;
    },
    decrementGuests() {
      if (this.numberOfGuest > 0) this.numberOfGuest--;
    },
    editAmenities(value) {
      const element = this.amenities.find(amenity => amenity.name == value);
      if (this.selectedAmenities.includes(element.id)) {
        this.selectedAmenities = this.selectedAmenities.filter(
          amenity => amenity != element.id
        );
      } else {
        this.selectedAmenities.push(element.id);
      }
      console.log(this.selectedAmenities);
    },
    getLngLat(lngLat) {
      var parts = lngLat.toString().split(",");
      this.selectedLongitude = parts[0].substr(7);
      this.selectedLatitude = parts[1].substr(1, parts[1].length - 2);
      console.log(parts);
      console.log(this.selectedLongitude);
      console.log(this.selectedLatitude);
    },
    addAvailableDates() {
      if (
        this.availableStartDates.includes(
          moment(this.startDate).format("DD-MM-YYYY")
        ) ||
        this.availableEndDates.includes(
          moment(this.endDate).format("DD-MM-YYYY")
        )
      ) {
        this.$toasted.global.alreadySelectedDates();
        return;
      } else if (moment(this.startDate).isAfter(moment(this.endDate))) {
        this.$toasted.global.startDateAfterEndDate();
        return;
      }

      for (var i = 0; i < this.availableStartDates.length; i++) {
        var start = moment(this.availableStartDates[i], "DD-MM-YYYY");
        var end = moment(this.availableEndDates[i], "DD-MM-YYYY");

        if (
          moment(this.startDate, "DD-MM-YYYY").isAfter(start) &&
          moment(this.startDate, "DD-MM-YYYY").isBefore(end) &&
          moment(this.endDate, "DD-MM-YYYY").isAfter(end)
        ) {
          return;
        } else if (
          moment(this.startDate, "DD-MM-YYYY").isBefore(start) &&
          moment(this.endDate, "DD-MM-YYYY").isBefore(end) &&
          moment(this.endDate, "DD-MM-YYYY").isAfter(start)
        ) {
          return;
        } else if (
          moment(this.startDate, "DD-MM-YYYY").isBefore(start) &&
          moment(this.endDate, "DD-MM-YYYY").isAfter(end)
        ) {
          return;
        } else if (
          moment(this.startDate, "DD-MM-YYYY").isAfter(start) &&
          moment(this.endDate, "DD-MM-YYYY").isBefore(end)
        ) {
          return;
        }
      }

      this.availableStartDates.push(
        moment(this.startDate).format("DD-MM-YYYY")
      );
      this.availableEndDates.push(moment(this.endDate).format("DD-MM-YYYY"));
      console.log(this.availableStartDates);
      console.log(this.availableEndDates);
    },
    removeDate(endDate) {
      console.log(endDate);
      var index = this.availableEndDates.indexOf(endDate);
      console.log(index);
      if (this.availableStartDates.length == 1) {
        this.availableStartDates = [];
        this.availableEndDates = [];
        return;
      } else if (index == -1) {
        return;
      }
      this.availableEndDates.splice(index, 1);
      this.availableStartDates.splice(index, 1);
      console.log(this.availableStartDates);
      console.log(this.availableEndDates);
    }
  }
};
</script>

<style scoped>
#button {
  grid-row: 10;
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
  grid-row: 9;
  display: grid;
  grid-template-columns: 5rem 15rem;
}

.checkin-checkout-cointainer {
  grid-row: 8;
}

#available-dates {
  grid-row: 7;
  display: grid;
  grid-template-columns: repeat(3, auto-fit);
  grid-template-rows: 3rem auto;
  row-gap: 2rem;
}

#available-dates .vdp-datepickerr {
  margin-right: 15px !important;
  width: 180px !important;
  height: 50px !important;
  cursor: pointer !important;
  font-size: 16px !important;
}

#available-dates .datepickerss {
  display: inline-flex;
  width: min-content;
  flex-direction: row;
  margin-top: 1px;
  width: 50% !important;
}

.checkin-checkout {
  display: grid;
  margin-top: 30px;
}

#addDate {
  width: 150px !important;
  grid-column: 3;
  grid-row: 1;
}

#removeDateButton {
  grid-column: 3;
  grid-row: 2;
}

.amenity-checkboxes {
  grid-row: 6;
}

#safety-label {
  color: var(--main-text-color);
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
  left: 0%;
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

#amenity-grid {
  display: grid;
  grid-template-columns: 30px 50px;
  column-gap: 15rem;
  margin-top: 15px;
}
.single-amenity {
  color: var(--main-text-color);
  font-size: 18px;
  grid-column: auto;
  margin-top: 12px;
  margin-right: 0px;
  width: 300px;
}

#mapbox-preview {
  height: 700px;
  grid-row: 5;
  margin-top: 0 !important;
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
  font-size: 22px;
  color: var(--brand-color);
  grid-row: 1;
  grid-column: 1;
}

.title-wrapper {
  grid-row: 1;
  margin-top: -10px;
  grid-column: 6/8;
  position: relative;
}
</style>