<template>
  <v-container>
    <p>결제가 완료되었습니다.</p>
    <p>결제한 물품 : {{ payInfo.itemName }}</p>
  </v-container>
</template>

<script lang="ts">
import Vue from "vue";
import { AxiosResponse } from "axios";
import { myAuctionApi } from "../utils/axios";

export default Vue.extend({
  name: "App",

  data: () => ({
    payInfo : {}
  })
  ,
  created () {
    // console.log(this.$route.query.pg_token);
    const token = this.$route.query.pg_token
    myAuctionApi.success(token).then((res: AxiosResponse) => {
      this.payInfo = res.data.object;
    })
  }
});
</script>
