<template>
  <div class="border-around">
    <div class="search-result">
      <div id="picture"></div>
      <div id="title">{{user.name}} {{user.lastname}}</div>
      <div id="features">{{user.username}}</div>
      <div id="type">
        {{user.gender}}
        <span id="user-type-span">{{user.type}}</span>
      </div>
      <div id="main-info">
        <span style="margin-bottom: 6px; font-size: 18px;" v-if="currentUserType == 'Admin'">
          <Button text="Block" width="100" height="35" fontsize="17" id="block-button" />
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import Button from "./../form-components/Button.vue";

export default {
  props: ["user", "currentUserType"],
  name: "UserSearchResult",
  components: { Button }
};
</script>

<style>
.border-around {
  height: 11rem;
  width: 30rem;
  border-radius: var(--border-radius-stronger);
  border: 1px solid var(--light-text-color);
}

.search-result {
  padding: 5px;
  display: grid;
  grid-template-columns: 10rem 18rem;
  column-gap: 1rem;
  grid-template-rows: 1rem 1.8rem 1.6rem 1.6rem 2rem 2rem;
}

#picture {
  background: orange;
  border-radius: 50%;
  width: 7rem;
  margin: 1rem 1.5rem;
  height: 7rem;
  grid-column: 1;
  grid-row: 1/5;
  /*align-self: center;*/
  text-align: center;
  background-image: url("./../../../public/img/user-photo.jpg");
  background-size: 100% 100%;
}

#title {
  grid-column: 2;
  grid-row: 2;
  align-self: center;
  color: var(--main-text-color);
  font-size: 26px;
  font-weight: 500;
}

#type {
  grid-column: 2;
  grid-row: 4;
  color: var(--light-text-color);
  align-self: center;
  font-size: 18px;
}

#user-type-span {
  margin: 51px;
}

#features {
  grid-column: 2;
  grid-row: 3;
  color: var(--light-text-color);
  font-size: 18px;
  margin-top: 3px;
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
  grid-row: 6;
  right: 50%;
  margin-top: -2px;
}

#block-button {
  position: absolute;
  grid-row: 6;
  margin-top: -2px;
  right: 5%;
}
</style>