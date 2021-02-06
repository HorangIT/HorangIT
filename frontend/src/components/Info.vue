<template>
  <div class="pl-4 pr-4 row">
    <div class="col-md-6 col-sm-6 col-xs-12">
      <v-card>
        <v-img
          src="../assets/img/layout/howto.png"
          class="white--text align-center"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="50vh"
        >
          <h1 class="text-center font-size">경매 참여 방법</h1>
          <div class="text-center">
            <v-btn href="#" class="white--text" outlined>Go</v-btn>
          </div>
        </v-img>
      </v-card>
    </div>
    <div class="col-md-6 col-sm-6 col-xs-12">
      <v-card>
        <v-img
          src="../assets/img/layout/faqs.png"
          class="white--text align-center"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="50vh"
        >
          <h1 class="text-center font-size">FAQs</h1>
          <div class="text-center">
            <v-btn href="/cs" class="white--text" outlined>Go</v-btn>
          </div>
        </v-img>
      </v-card>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "Info",

  data: () => ({})
});
</script>

<style></style>
