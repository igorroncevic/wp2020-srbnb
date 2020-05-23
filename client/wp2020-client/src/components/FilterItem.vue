<template>
  <div class="filter-item-wrapper">
    <div class="filter-item">
      <label>{{value}}</label>
      <img
        src="https://img.icons8.com/material-outlined/18/000000/expand-arrow.png"
        style="margin-bottom: -3px; margin-left: 3px;"
      />
    </div>
  </div>
</template>

<script>
export default {
   props: ['value']
};
</script>

<style>
.filter-item-wrapper {
  width: auto;
  background: var(--background-color);
  box-shadow: 0px 3px 8px #cccccc;
  border: 1px solid var(--light-text-color);
  border-radius: var(--border-radius);
}

.filter-item-wrapper:hover {
  border: 1px solid var(--main-text-color);
}

.filter-item {
  display: inline-block;
  margin: 0 auto;
  padding: 9px 20px;
  border-radius: var(--border-radius);
}

.filter-item label {
  font-size: 14px;
}
</style>