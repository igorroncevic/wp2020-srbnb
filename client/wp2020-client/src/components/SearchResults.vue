<template>
  <div>
     <search-result v-for="(apartment,i) in apartments" :apartment="apartment" :key=i />
  </div>
</template>

<script>
import SearchResult from './SearchResult.vue'
export default {
  name: 'SearchResults',
  components: {
     SearchResult
  },
  data(){
     return {
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
           }
        ]
     }
  }
}
</script>

<style scoped>


</style>
