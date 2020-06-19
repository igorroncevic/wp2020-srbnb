<template>
  <div class="grid-container">
      <div class="user">
        <div class="photo"></div>
        <div class="info">
          <p class="name">Petar Petrovic</p>
          <p class="role">Guest</p>
        </div>
      </div>
    <div class="wrapper">
      <div class="previous-bookings">
         <div v-for="(apartment, i) in apartments" :key="i" >
            <search-result :apartment="apartment" />
         </div>
         <div class="btn">
            <Button v-if="isAdmin" text="Block user" width="225" height="45" fontsize="20" @clicked="submitForm" id="blockUserBtn" />
            <Button v-if="isHomeowner" text="Add new apartment" width="300" height="45" fontsize="20" id="addApartmentBtn" @clicked="submitForm" />
         </div>
      </div>

      
    </div>
  </div>
</template>

<script>
import SearchResult from "./reusable/SearchResult.vue";
import Button from "./form-components/Button.vue";

export default {
   name: 'UserProfile',  
   components: {SearchResult, Button},
   data() {
      return {
         isAdmin: true,
         isHomeowner: false,
         apartments: [
        {
          name: "Apartments Perić",
          type: "Entire apartment",
          price: 40,
          features: "4 guests * 1 bedroom * 2 beds"
        },
        {
          name: "Apartments Jović",
          type: "Single room",
          price: 20,
          features: "2 guests * 1 bed"
        },
        {
          name: "Apartments Stević",
          type: "Penthouse",
          price: 349,
          features: "8 guests * 6 bed * 4 bedrooms"
        },
        {
          name: "Apartments Deluxe",
          type: "Deluxe apartment",
          price: 149,
          features: "6 guests * 4 beds * 3 bedrooms"
        }
      ]
      }
   }
}
</script>

<style>
.grid-container {
  display: grid;
  grid-template-columns: repeat(15, 1fr);
  grid-gap: 10px;
}

.wrapper {
  grid-column: 2 / 16;
  grid-row: 5;
  display: grid;
  grid-template-rows: repeat(auto-fill, 8vh);
  grid-row-gap: 5px;
}
/* User Container */
.user {
  margin-left: 10%;
  align-content: center;
  grid-row: 3/5;
  grid-column: 6/11;
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  justify-content: center;
}

.photo {
  background: orange;
  grid-column: 1;
  height: 140px;
  width: 140px;
}
.info {
  grid-column: 2/9;
  margin-left: 1rem;
}
.name {
  font-size: 2rem;
  color: var(--main-text-color);
}
.role {
  font-size: 1.2rem;
  color: var(--light-text-color);
  margin-top: -2rem;
}


/* Previous bookings */
.previous-bookings{
   margin: 4rem 0  0 6rem;
  display: grid;
  grid-gap: 20px;
  grid-template-columns: repeat(auto-fill, 41rem);
}

.btn{
   grid-row: 5;
   position: relative;
   left: 70%;
}

#blockUserBtn{

}

#addApartmentBtn{
   position: relative;
   right: 4%;
}


</style>