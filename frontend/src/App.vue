<template>
  <v-app style="background-color: #fdfafa;">
    <Navbar />
    <div style="height:130px;"></div>
    <router-view class="container" />
    <Footer />
  </v-app>
</template>

<script lang="ts">
import Vue from "vue";
import Navbar from "./components/Navbar.vue";
import Footer from "./components/Footer.vue";
// test
import Chatroom from "./components/myauction/Chatroom.vue";
import WindowPortal from "./components/WindowPortal.vue";
export default Vue.extend({
  name: "App",
  components: {
    Navbar,
    Footer,
  },
  data: () => ({
  }),
  watch: {
    $route: {
      immediate: true,
      handler(to, from) {
          document.title = to.meta.title || '호랑it';
      }
    }
  },
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
@font-face {
    font-family: 'ELAND_Choice_L';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/ELAND_Choice_L.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

  *{
    font-family: 'ELAND_Choice_M';
  }

</style>