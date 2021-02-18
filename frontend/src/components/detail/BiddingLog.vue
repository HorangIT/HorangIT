<template>
  <!-- 응찰 내역 높이, 스크롤 설정 -->
  <v-list
    height="40vh"
    style="overflow: auto"
  >
    <Bidding v-for="(bidding, index) in biddingLog" :bidding="bidding" :key="index"></Bidding>
  </v-list>
</template>

<script lang="ts">
import Vue from 'vue';
import Bidding from '../../components/detail/Bidding.vue'

export default Vue.extend({
  name: 'BiddingLog',
  props: [
    'biddingLog',
  ],
  components: {
    Bidding,
  }
});
</script>
