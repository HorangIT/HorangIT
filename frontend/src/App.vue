<template>
  <v-app style="background-color: #fdfafa;">
    <Navbar />
    <div style="height:130px;"></div>
    <v-checkbox
      label="눌러"
      v-model="open"
    ></v-checkbox>
    <router-view class="container" />
    <Footer />
    <WindowPortal v-model="open">
      <Chat />
    </WindowPortal>
  </v-app>
</template>

<script lang="ts">
import Vue from "vue";
import Navbar from "./components/Navbar.vue";
import Footer from "./components/Footer.vue";
// test
import Chat from "./components/myauction/Chat.vue";
import WindowPortal from "./components/WindowPortal.vue";
export default Vue.extend({
  name: "App",
  components: {
    Navbar,
    Footer,
    // test
    Chat,
    WindowPortal
  },
  data: () => ({
    open: false,
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