<template>
  <div class="wrapper">
    <header>
      <div class="logo">
        <a href="/">srbnb</a>
      </div>
      <nav class="navbar">
        <ul>
          <li>
            <a href="/login">Login</a>
          </li>
          <li>
            <a href="/signup">Signup</a>
          </li>
        </ul>
      </nav>
    </header>
    <router-view class="content" />
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
.wrapper {
  display: flex;
  flex-direction: column;
}

.content {
  flex: 1;
}

.logo a {
  float: left;
  padding-top: 10px;
  font-size: 26px;
  text-decoration: none;
  color: var(--brand-color);
}

nav {
  float: right;
}

nav ul {
  margin: 0;
  padding: 0;
  list-style: none;
}

nav li {
  display: inline-block;
  margin-left: 70px;
  padding-top: 10px;

  position: relative;
}

nav a {
  text-decoration: none;
  font-size: 16px;
  color: var(--main-text-color);
}

nav a::after {
  content: "";
  display: block;
  height: 2px;
  background-color: var(--main-text-color);

  position: absolute;
  bottom: -2px;
  left: 50%;
  right: 50%; /* 51% ako ostaje linijica */
  width: 0%;

  transition: all ease-in-out 200ms;
}

nav a:hover::after {
  width: 105%;
  left: 0;
  right: 0;
}
nav a:active {
  color: var(--main-text-color);
}
</style>
