<template>
  <div class="search-result">
    <div id="app-picture">Picture</div>
    <div id="type">{{apartment.type}}</div>
    <div id="title">{{apartment.name}}</div>
    <div
      id="features"
    >{{apartment.guests}} guests • {{apartment.beds}} beds • {{apartment.bedrooms}} bedrooms</div>
    <div id="main-info">
      <span style="margin-bottom: 6px; font-size: 18px;">
        <span style="font-weight: bold">${{apartment.pricePerNight}}</span>/night
      </span>
      <router-link
        :to="{name: 'apartment-preview', params: {apartmentId: apartment.id}}"
      >
        <Button text="Visit" width="100" height="33" fontsize="15" id="visit-button" />
      </router-link>
    </div>
  </div>
</template>

<script>
import Button from "./../form-components/Button.vue";

export default {
  props: ["apartment"],
  name: "SearchResult",
  components: { Button }
};
</script>

<style scoped>
.search-result {
  display: grid;
  grid-template-columns: 12rem 18rem;
  column-gap: 1rem;
  grid-template-rows: 1.2rem 2rem 1.5rem 1rem 3rem 0.1rem;
  width: 23rem;
  margin-bottom: 20px;
}

#app-picture {
  background: orange;
  grid-column: 1;
  grid-row: 1/6;
  /*align-self: center;*/
  text-align: center;
}

#type {
  grid-column: 2;
  grid-row: 1;
  color: var(--light-text-color);
  align-self: end;
  font-size: 14px;
}

#title {
  grid-column: 2;
  grid-row: 2;
  color: var(--main-text-color);
  font-size: 24px;
  font-weight: 500;
}

#features {
  grid-column: 2;
  grid-row: 3;
  color: var(--light-text-color);
  font-size: 14px;
}

#main-info {
  position: relative;
  grid-column: 2;
  grid-row: 5;
  align-self: end;
  display: flex;
}

#visit-button {
  position: absolute;
  right: 0%;
  bottom: 0%;
}
</style>