<template>
  <div class="review-item">
    <div class="review-user-photo"></div>
    <div class="review-user-name">{{review.author}}</div>
    <div class="review-rating" style="font-size: 18px;display: inline-block;">
      {{review.rating}}/5
      <svg
        style="width:24px;height:24px;margin-bottom:-4px;display: inline-block;"
        viewBox="0 0 24 24"
      >
        <path
          fill="var(--brand-color)"
          d="M12,17.27L18.18,21L16.54,13.97L22,9.24L14.81,8.62L12,2L9.19,8.62L2,9.24L7.45,13.97L5.82,21L12,17.27Z"
        />
      </svg>
    </div>
    <div id="hide-show-btn" @click="$emit('hideshow')" v-if="isUserHost">
      <svg
        style="width:20px;height:20px;display:inline-block;margin-bottom: -2px;"
        viewBox="0 0 24 24"
      >
        <path
          fill="#b8b8b8"
          d="M20.71,7.04C21.1,6.65 21.1,6 20.71,5.63L18.37,3.29C18,2.9 17.35,2.9 16.96,3.29L15.12,5.12L18.87,8.87M3,17.25V21H6.75L17.81,9.93L14.06,6.18L3,17.25Z"
        />
      </svg>
      <p v-if="review.visibleToGuests" style="display:inline-block;">Hide</p>
      <p v-if="!review.visibleToGuests" style="display:inline-block;">Show</p>
    </div>
    <div class="review-text">{{review.content}}</div>
  </div>
</template>

<script>
import UserService from "./../../services/UserService";
export default {
  props: {
    review: Object
  },
  computed: {
    isUserHost() {
      return UserService.getUserType() == "Host" ? true : false;
    }
  }
};
</script>

<style>
#hide-show-btn {
  position: relative;
  align-content: center;
  cursor: pointer;
  color: #b8b8b8;
  font-size: 16px;
  grid-row: 1;
  grid-column: 4;
  display: inline-block;
}

.review-item {
  margin-top: 30px;
  display: grid;
  grid-template-columns: 3rem 20rem 6rem 4rem;
  grid-template-rows: 3rem auto;
}

.review-user-photo {
  grid-column: 0;
  background: var(--brand-color);
  border-radius: 50%;
  background-image: url("./../../../public/img/user-photo.jpg");
  background-size: 100% 100%;
}

.review-user-name {
  grid-column: 2;
  color: var(--main-text-color);
  font-size: 22px;
  font-weight: 500;
  margin-left: 10px;
  align-self: center;
  margin-bottom: 5px;
}

.review-rating {
  grid-column: 3;
  position: relative;
  top: 15%;
}

.review-text {
  grid-row: 2;
  grid-column: 1/4;
  font-size: 20px;
  margin-top: 10px;
}
</style>