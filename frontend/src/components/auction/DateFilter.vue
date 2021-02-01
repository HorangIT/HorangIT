<template>
  <div>
    <v-card-title>Date</v-card-title>
    <v-date-picker
      v-model="date1"
      :events="arrayEvents"
      event-color="orange accent-3"
      full-width
    >
    </v-date-picker>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

declare interface Dates {
  m: number;
  d: number;
}

export default Vue.extend({
  name: "DateFilter",

  data: () => ({
    dates: new Array<Dates>(),
    arrayEvents: null,
    date1: new Date().toISOString().substr(0, 10),
    date2: new Date().toISOString().substr(0, 10),
  }),

  mounted() {
    // this.arrayEvents = [...Array(6)].map(() => {
    //   const day = Math.floor(Math.random() * 30)
    //   const d = new Date()
    //   d.setDate(day)
    //   return d.toISOString().substr(0, 10)
    // })
    for (let plus = 0; plus < 30; plus++) {
      const now = new Date();
      const future = new Date(now.setDate(now.getDate() + plus));
      const date = {
        m: future.getMonth() + 1,
        d: future.getDate()
      };
      this.dates.push(date);
    }
  }
});
</script>
<style></style>
