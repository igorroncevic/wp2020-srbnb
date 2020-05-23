<template>
  <div class="search-result">
    <div id="picture">Picture</div>
    <div id="type">{{apartment.type}}</div>
    <div id="title">{{apartment.name}}</div>
    <div id="features">{{apartment.features}}</div>
    <div id="main-info">
      <span>
        <span style="font-weight: bold">${{apartment.price}}</span>/night
        <Button text="Visit" width="90" height="30" fontsize="14" style="margin-left:5rem;"/>
      </span>
    </div>
  </div>
</template>

<script>
import Button from "./form-components/Button.vue";

export default {
  props: ['apartment'],
  name: "SearchResult",
  components: { Button }
};
</script>

<style scoped>
.search-result {
  display: grid;
  grid-template-columns: 12rem 18rem;
  column-gap: 1rem;
  grid-template-rows: 1.2rem 2rem 1.5rem 1rem 3rem;
  width: 23rem;
  height: 11rem; /* Also bottom margin */
}

#picture {
  background: orange;
  grid-column: 1;
  grid-row: 1/6;
  /*align-self: center;*/
  text-align:center;
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
  grid-column: 2;
  grid-row: 5;
  align-self: end;
}
</style>