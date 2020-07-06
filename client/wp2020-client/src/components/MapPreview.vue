<template>
  <div class="wrap">
    <div id="geocoder" class="geocoder"></div>
    <div id="mapbox-preview" ref="mapRef"></div>
  </div>
</template>

<script>
import mapboxgl from "mapbox-gl";
import "mapbox-gl/dist/mapbox-gl.css";

export default {
  props: {
    latitude: {
      type: Number,
      default: 45.2671
    },
    longitude: {
      type: Number,
      default: 19.8335 //Novi Sad
    }
  },
  data() {
    return {
      accessToken: process.env.VUE_APP_MAPBOX_APIKEY
    };
  },
  created() {
    setTimeout(() => {}, 2500);
  },
  beforeMount() {
    this.$nextTick(() => {
      this.createMap();
    });
  },
  watch: {
    latitude(newVal, oldVal) {
      console.log("Old val " + oldVal + ", new val " + newVal);
    },
    longitude(newVal, oldVal) {
      console.log("Old val " + oldVal + ", new val " + newVal);
    }
  },
  methods: {
    createMap() {
      mapboxgl.accessToken = this.accessToken;
      this.map = new mapboxgl.Map({
        container: "mapbox-preview",
        style: "mapbox://styles/mapbox/streets-v11",
        minZoom: 7,
        maxZoom: 18,
        center: [this.longitude, this.latitude],
        zoom: 8
      });

      /*// Add geolocate control to the map.
      var geolocate = new mapboxgl.GeolocateControl({
        positionOptions: {
          enableHighAccuracy: true
        },
        trackUserLocation: true
      });
      this.map.addControl(geolocate);

      geolocate.on("geolocate", function(e) {
        this.longitude = e.coords.longitude;
        this.latitude = e.coords.latitude;
        var position = [this.longitude, this.latitude];
        console.log(position);
      });*/

      var marker = new mapboxgl.Marker({
        draggable: true
      })
        .setLngLat([this.longitude, this.latitude])
        .addTo(this.map);

      marker.on("dragend", () => {
        var lngLat = marker.getLngLat();
        this.$emit("markerChange", lngLat);
      });
    }
  }
};
</script>

<style>
.geocoder {
  position: absolute;
  z-index: 1;
  width: 50%;
  left: 50%;
  margin-left: -25%;
  top: 10px;
}
.mapboxgl-ctrl-geocoder {
  min-width: 100%;
}
#mapbox-preview {
  margin-top: 75px;
}
</style>