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
  // setup() {
  //   $.ajax({
  //     url: "http://127.0.0.1:3000/user/account/token/",
  //     type: "post",
  //     data: {
  //       username: "lhy",
  //       password: "123456",
  //       confirmedPassword: "123456",
  //     },
  //     success(resp) {
  //       console.log(resp);
  //     },
  //     error(resp) {
  //       console.log(resp);
  //     }
  //   });

  //   $.ajax({
  //     url: "http://127.0.0.1:3000/user/account/info/",
  //     type: "get",
  //     headers: {
  //       Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1YzFmMDI1Njg1Y2E0MDA2YjA2YWEwNDBiMTM4YWFmOCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY2NzM5MjEzOSwiZXhwIjoxNjY4NjAxNzM5fQ.ciTXKjJkvpgk0w15MYRxll1EuH22QUnivisV0EGm9bo", //授权
  //       //Authentication  认证
  //     },
  //     success(resp) {
  //       console.log(resp);
  //     },
  //     error(resp) {
  //       console.log(resp);
  //     }
  //   })

  //   $.ajax({
  //     url: "http://127.0.0.1:3000/user/account/register/",
  //     type: "post",
  //     data: {
  //       username: "lhy2",
  //       password: "123456",
  //       confirmedPassword: "123456"
  //     },
  //     success(resp) {
  //       console.log(resp);
  //     },
  //     error(resp) {
  //       console.log(resp);
  //     }
  //   })
  // }
}
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>
