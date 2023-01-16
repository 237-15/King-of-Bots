<template>
  <div>
    <NavBar />
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
// import $ from 'jquery'

export default {
  components: {
    NavBar
  },
}
</script>

<style>
body {
  background-image: url("https://pica.zhimg.com/v2-e4e60a926de9293e9ec38b1eabb593b2_r.jpg?source=1940ef5c");
  background-size: cover;
}
</style>
