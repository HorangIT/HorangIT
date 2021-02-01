<template>
  <div>
    <v-card-title>Price</v-card-title>
    <v-range-slider
      v-model="range"
      :max="max"
      :min="min"
      :height="10"
      class="align-center"
      dense
    ></v-range-slider>
    <v-row class="pa-2" dense>
      <v-col cols="12" sm="5">
        <v-text-field
          :value="range[0]"
          label="Min"
          outlined
          dense
          @change="$set(range, 0, $event)"
        ></v-text-field>
      </v-col>
      <v-col cols="12" sm="2">
        <p class="pt-2 text-center">TO</p>
      </v-col>
      <v-col cols="12" sm="5">
        <v-text-field
          :value="range[1]"
          label="Max"
          outlined
          dense
          @change="$set(range, 1, $event)"
        ></v-text-field>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "PriceFilter",

  data: () => ({
    range: [0, 10000],
    min: 0,
    max: 10000
  })
});
</script>
<style></style>
