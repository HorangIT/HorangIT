<template>
  <v-app>
    <Navbar />
    <div style="height:90px;"></div>
    <v-navigation-drawer v-model="active" absolute temporary>
      <SideNavbar @loginOrSignup="openModal(loginOrSignup)"></SideNavbar>
    </v-navigation-drawer>
    <router-view class="container" />
    <Footer />
  </v-app>
</template>

<script lang="ts">
import Vue from "vue";
import Navbar from "@/components/Navbar.vue";
import SideNavbar from "@/components/SideNavbar.vue";
import Footer from "@/components/Footer.vue";

export default Vue.extend({
  name: "App",
  components: {
    Navbar,
    SideNavbar,
    Footer,
  },
  data: () => ({
    active: false,
  })
});
</script>

<style>
@font-face {
    font-family: 'CookieRunOTF-Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/CookieRunOTF-Bold00.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'ELAND_Choice_M';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/ELAND_Choice_M.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
/* 
  .v-btn:hover {
    color: orange !important;
  } */
/*   
  .v-btn::before {
    background-color: transparent !important;
  } */

  body {
    background-color: #fdfafa !important;
  }

  *{
    font-family: 'ELAND_Choice_M';
  }

</style>