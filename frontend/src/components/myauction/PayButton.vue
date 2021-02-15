<template>
  <div class="ma-2">
    <v-btn
      depressed
      outlined
      color="orange"
      @click="pay"
    >
      결제하기
    </v-btn>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import { AxiosResponse } from "axios";
import { myAuctionApi } from "../../utils/axios";

export default Vue.extend({
  name: "PayButton",

  data: () => ({
  }),

  methods: {
    pay() {
      const data = {
        buyerId: 1,
        itemName: "컴퓨터",
        price: 10000
      }
      this.$emit('showQR', "https://mockup-pg-web.kakao.com/v1/68db61b490ad0808438d7cd34c555b938cd4fb9c3a5b1a644f79ebeab3939b0d/info")
      myAuctionApi.pay(data).then((res: AxiosResponse) => {
        console.log(res);
      });
    },
  }
});
</script>
<style></style>