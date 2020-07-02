<template>
  <div id="map" ref="mapRef"></div>
</template>

<script>
import mapboxgl from "mapbox-gl";
export default {
  data() {
    return {
      accessToken: process.env.VUE_APP_MAPBOX_APIKEY
    };
  },
  mounted() {
    this.createMap();
  },
  methods: {
    createMap() {
      mapboxgl.accessToken = this.accessToken;
      this.map = new mapboxgl.Map({
        container: "map",
        style: "mapbox://styles/mapbox/streets-v11",
        minzoom: 1.3,
        scrollZoom: false,
        center: [-74.0073, 40.7124], // Manhattan
        zoom: 8,
        //interactive: false
      });

      //For map to fit the parent container
      this.map.on('load', function(){
         this.map.resize();
      });
      
      //this.map.addControl(new mapboxgl.Navigation());

      /*const bounds = new mapboxgl.LngLatBounds();
      locations.forEach(loc => {
         //Create marker
         const el = document.createElement('div');
         el.className = 'marker';

         //Add marker
         new mapboxgl.Marker({
            element: el,
            anchor: 'bottom'
         }).setLngLat(loc.coordinates)
         .addTo(this.$refs.mapRef);

         //Add popup
         new mapboxgl.Popup(
            {
               offset: 30
            }
         ).setLngLat(loc.coordinates)
         .setHTML(`<p>$${apartment.price}</p>`)
         .addTo(this.$refs.mapRef);

         //Extend map bounds to include current location
         bounds.extend(loc.coordinates);
      });

      this.map.fitBounds(bounds, {
         padding:{
            top: 150,   
            bottom: 150,
            left: 100,
            right: 100
         }
      });*/

      
    }
  }
};
</script>

<style scoped>
.marker {
  background-image: url("./../../public/img/pin.png");
  background-size: cover;
  width: 32px;
  height: 40px;
  cursor: pointer;
}
</style>