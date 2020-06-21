<template>
  <div class="review-item">
    <div class="review-user-photo"></div>
    <div class="review-user-name">{{review.user}}</div>
    <div class="review-rating" style="font-size: 18px;">
      {{review.rating}}/5
      <svg
        style="width:24px;height:24px;margin-bottom:-4px;"
        viewBox="0 0 24 24"
      >
        <path
          fill="var(--brand-color)"
          d="M12,17.27L18.18,21L16.54,13.97L22,9.24L14.81,8.62L12,2L9.19,8.62L2,9.24L7.45,13.97L5.82,21L12,17.27Z"
        />
      </svg>
    </div>
    <div class="review-text">{{review.text}}</div>
  </div>
</template>

<script>
export default {
  props: ["review"]
};
</script>

<style>
.review-item {
  margin-top: 30px;
  display: grid;
  grid-template-columns: 3rem 25rem 5rem;
  grid-template-rows: 3rem auto;
}

.review-user-photo {
  grid-column: 0;
  background: var(--brand-color);
  border-radius: 50%;
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
  top: 6%;
}

.review-text {
  grid-row: 2;
  grid-column: 1/4;
  font-size: 20px;
  margin-top: 10px;
}
</style>