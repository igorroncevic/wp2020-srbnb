<template>
  <div style="margin-top: 75px;">
    <!-- Title -->
    <div class="title-wrapper">
      <h1 id="title">
        Book unique homes and
        <span id="accented">experience</span> a
        <br />city like a local.
      </h1>
    </div>

    <!-- Search -->
    <div id="searchbar-wrapper">
      <div>
        <!-- Search form -->
        <form method="GET" action="/search-results" id="searchbar">
          <div class="form-item" tabindex="1">
            <div class="nest stacked">
              <label for="location" class="label">LOCATION</label>
              <input type="text" name="location" placeholder="Where are you going?" />
            </div>
          </div>
          <div class="form-item" tabindex="2">
            <div class="nest stacked">
              <label for="location" class="label">CHECK IN / CHECK OUT</label>
              <input type="text" name="location" placeholder="Add dates" />
            </div>
          </div>
          <div class="form-item" tabindex="3">
            <div class="stacked nest">
              <label for="location" class="label">GUESTS</label>
              <input type="text" name="location" placeholder="Add guests" />
            </div>
          </div>
          <button type="submit">
            <img src="./../../public/svg/search-icon.png" />Search
          </button>
        </form>
        <!-- End of the form -->
      </div>
    </div>
    <div style="height: 100px"></div>
  </div>
</template>

<script>
export default {
  name: "Home"
};
</script>

<style scoped>
.title-wrapper {
  position: relative;
  width: 92%;
  margin: 0 auto;
}

.title {
  font-size: 48px;
  font-weight: 400;
  color: var(--main-text-color);
  margin: 60px 0;
  letter-spacing: 1px;
  line-height: 56px;
}

#accented {
  color: var(--brand-color);
}

#searchbar-wrapper {
  position: relative;
  width: 89%;
  height: auto;
  background: var(--background-color);
  margin-bottom: 30px;
  margin: 0 4%;
  box-shadow: 0px 4px 8px var(--dropshadow-color);
  border-radius: var(--border-radius);
}

#searchbar {
  position: relative;
  width: 100%;
  height: auto;
}

#searchbar button[type="submit"] {
  cursor: pointer;
  width: auto;
  font-size: 16px;
  padding: 13px 29px 12px 25px;
  color: var(--background-color);
  background-color: var(--brand-color);
  border: 0;
  border-radius: var(--border-radius);
  -webkit-border-radius: var(--border-radius);
  transition: all 0.3s ease;
  -webkit-transition: all 0.3 ease;
  -moz-transition: all 0.3 ease;
  -o-transition: all 0.3 ease;
  float: right;
  position: absolute;
  top: 20%;
  right: 1%;
  text-align: center;
  text-justify: distribute;
}

#searchbar button[type="submit"] img {
  height: 16px;
  width: auto;
  margin: 0px 5px -2px 0px;
}

#searchbar button[type="submit"]:hover {
  background: var(--brand-color-hover);
}

#searchbar input[type="text"] {
  font-size: 16px;
  padding-top: 3px;
  padding-right: 10px;
  border: 0;
}

#searchbar input[type="text"]::placeholder {
  color: var(--light-text-color);
}

.form-item {
  display: inline-block;
  padding: 0.5rem 0 0.5rem 1.5rem;
  background: var(--background-color);
  width: 27%;
  border: 1px solid var(--background-color);
  border-radius: var(--border-radius);
  height: 3rem;
}

.form-item:hover {
  border: 1px solid var(--light-text-color);
}

.form-item:focus-within {
  border: 1px solid var(--main-text-color);
}

.nest {
  max-width: 60%;
  margin-top: 6px;
}

.stacked {
  display: grid;
}

.label {
  font-weight: bold;
  font-size: 12px;
  grid-row: 1;
  grid-column: 1;
}
</style>
