<template>
  <div>
    <v-card-title class="pb-0">Grade</v-card-title>
    <v-container class="pt-0" fluid>
      <v-checkbox label="S" hide-details dense></v-checkbox>
      <v-checkbox label="A" hide-details dense></v-checkbox>
      <v-checkbox label="B" hide-details dense></v-checkbox>
      <v-checkbox label="C" hide-details dense></v-checkbox>
    </v-container>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "GradeFilter",

  data: () => ({})
});
</script>
<style></style>
