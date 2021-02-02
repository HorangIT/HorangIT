<template>
  <v-list >
    <v-subheader>응찰 내역</v-subheader>
    <Bidding v-for="(bidding, index) in biddings" :bidding="bidding" :key="index"></Bidding>
  </v-list>
</template>

<script lang="ts">
import Vue from 'vue';
import Bidding from '@/components/detail/Bidding.vue'

export default Vue.extend({
  name: 'BiddingLog',
  data () {
    return {
      // Test data
      biddings: [
        {
          username: '내가산다',
          price: 100000,
        },
        {
          username: '어림도없지',
          price: 95000,
        },
        {
          username: '이물건없으면내일죽는사람',
          price: 90000,
        },
      ]
    }
  },
  components: {
    Bidding,
  }
});
</script>
