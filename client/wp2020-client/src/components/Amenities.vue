<template>
  <div id="wrapper">
    <div class="title-wrapper">
      <h1>Manage Amenities</h1>
    </div>
    <div id="manage-amenities-wrapper">
      <table border="0" style="border-spacing: 30px 0;">
        <tr v-for="amenity in amenities" :key="amenity.id">
          <td style="font-size:20px;margin-top:4px;">{{amenity.id}}</td>
          <td>
            <StylishInput :value="amenity.name" v-model="amenity.name" width="400" height="38" />
          </td>
          <td>
            <Button
              text="Update"
              style="margin-top:12px;margin-left: 15px;"
              width="100"
              fontsize="18"
              height="36"
              @clicked="updateAmenity(amenity)"
            />
          </td>
          <td>
            <Button
              text="X"
              style="margin-top:12px;margin-left: 15px;"
              width="50"
              fontsize="20"
              height="30"
              @clicked="deleteAmenity(amenity)"
            />
          </td>
        </tr>
      </table>
    </div>
    <div id="add-amenity">
      <p id="add-amenity-label">Add New Amenity</p>
      <StylishInput v-model="newAmenity" width="250" height="38" />
      <Button
        text="Add"
        style="margin-top:12px;margin-left: 15px;"
        width="100"
        fontsize="18"
        height="36"
        @clicked="addAmenity"
      />
    </div>
  </div>
</template>

<script>
import AmenitiesService from "./../services/AmenitiesService";
import StylishInput from "./reusable/StylishInput.vue";
import Button from "./form-components/Button.vue";

export default {
  components: {
    StylishInput,
    Button
  },
  async beforeMount() {
    this.amenities = await AmenitiesService.getAllAmenities();
    console.log(this.amenities);
  },
  data() {
    return {
      amenities: [],
      newAmenity: ""
    };
  },
  methods: {
    async deleteAmenity(amenity) {
      console.log(amenity);
      var success = await AmenitiesService.deleteAmenity(amenity);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1000);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    async addAmenity() {
      console.log(this.newAmenity);
      var success = await AmenitiesService.addAmenity({
        name: this.newAmenity
      });
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1000);
      } else {
        this.$toasted.global.unknownError();
      }
    },
    async updateAmenity(amenity) {
      var success = await AmenitiesService.updateAmenity(amenity);
      if (success) {
        this.$toasted.global.successMessage();
        setTimeout(() => {
          this.$router.go();
        }, 1000);
      } else {
        this.$toasted.global.unknownError();
      }
    }
  }
};
</script>

<style scoped>
#wrapper {
  display: grid;
  display: grid;
  grid-template-rows: 4rem 4rem 90%;
  grid-template-columns: repeat(6, 20vh);
}

/* Title */
.title-wrapper {
  position: relative;
  width: 92%;
  margin: 0 auto;
  top: 10%;
  grid-row: 1;
  grid-column: 1/3;
}

#manage-amenities-wrapper {
  grid-row: 3;
  grid-column: 3/5;
  position: relative;
  left: 8%;
}

#add-amenity {
  grid-row: 2;
  grid-column: 3/7;
  display: inline-flex;
  position: relative;
  left: 10%;
}

#add-amenity-label {
  font-size: 26px;
  font-weight: 500;
  margin-right: 15px;
  margin-top: 13px;
  color: var(--brand-color);
}
</style>