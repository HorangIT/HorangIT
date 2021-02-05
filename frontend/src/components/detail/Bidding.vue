<template>
  <v-list-item>
    <v-list-item-content>
      <v-list-item-title>{{ biddingSplit[0] }} ({{ biddingSplit[2] }})</v-list-item-title>
      <v-list-item-subtitle>&#8361; {{ biddingSplit[1] | comma }}</v-list-item-subtitle>
    </v-list-item-content>
  </v-list-item>    
</template>

<script lang="ts">
import Vue from 'vue';
import numberWithCommas from '../../utils/numberWithCommas'

export default Vue.extend({
  name: 'Bidding',
  props: [
    'bidding',
  ],
  computed: {
    biddingSplit () {
      return this.bidding.split(';')
    }
  },
  filters: {
    comma (val: number | string): string {
      return numberWithCommas(Number(val));
    }
  }
});
</script>
