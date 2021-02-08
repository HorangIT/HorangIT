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

