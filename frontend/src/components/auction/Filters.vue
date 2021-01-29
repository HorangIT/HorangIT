<template>
  <v-card outlined>
    <DateFilter />
    <v-divider></v-divider>
    <CategoryFilter />
    <v-divider></v-divider>
    <PriceFilter />
    <v-divider></v-divider>
    <GradeFilter />
  </v-card>
</template>

<script lang="ts">
import Vue from "vue";
import DateFilter from "./DateFilter.vue";
import CategoryFilter from "./CategoryFilter.vue";
import PriceFilter from "./PriceFilter.vue";
import GradeFilter from "./GradeFilter.vue";

export default Vue.extend({
  name: "Filters",

  components: {
    DateFilter,
    CategoryFilter,
    PriceFilter,
    GradeFilter
  },

  data: () => ({})
});
</script>
<style></style>
