<template>
  <div id="map" ref="mapRef"></div>
</template>

<script>
import mapboxgl from "mapbox-gl";
export default {
  props: {
    apartments: {
      type: Array,
      default() {
        return [];
      }
    }
  },
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
        minZoom: 12,
        maxZoom: 17,
        center: [19.8311387, 45.2506721], // Ne smije biti centra, jer odatle krece sirenje mape, a ne znamo da li je uvijek NS
        zoom: 14
      });

      const bounds = new mapboxgl.LngLatBounds();
      console.log(this.apartments);
      this.apartments.forEach(app => {
        //Create marker
        const el = document.createElement("div");
        el.className = "marker";

        //Add marker
        new mapboxgl.Marker({
          element: el,
          anchor: "bottom"
        })
          .setLngLat([app.location.longitude, app.location.latitude])
          .addTo(this.map);

        //Add popup
        new mapboxgl.Popup({
           closeOnClick: false
        })
          .setLngLat([app.location.longitude, app.location.latitude])
          .setHTML(`<p>$${app.pricePerNight}</p>`)
          .addTo(this.map);

        //Extend map bounds to include current location
        bounds.extend([app.location.longitude, app.location.latitude]);
      });

      this.map.fitBounds(bounds, {
        padding: {
          top: 150,
          bottom: 150,
          left: 100,
          right: 100
        }
      });
    }
  }
};
</script>

<style>
.mapboxgl-popup-content-wrapper {
  height: 20px;
  scale: 50%;
}
.mapboxgl-popup-content {
   font-weight: 700;
  font-size: 18px;
  padding-bottom: 1.5rem;
  padding-top: 0px;
  height: 1.9rem;
  width: 3.5rem;
  text-align: center;
}

.mapboxgl-popup-anchor-bottom {
  margin-top: -48px !important;
}
</style>