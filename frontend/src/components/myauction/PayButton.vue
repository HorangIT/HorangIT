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
import { myAuctionapi } from "../../utils/axios";

export default Vue.extend({
  name: "PayButton",

  data: () => ({
  }),

  methods: {
    pay() {
      console.log("데이터전송!")
      this.$emit('url', "https://mockup-pg-web.kakao.com/v1/022a663167e0bfa6cb8338a2c66217250152299654b13b1cfc7b28b44bd23db7/info");
      const data = ""
      // myAuctionapi.pay(data).then((res: AxiosResponse) => {
      //   console.log(res);
      // });
    },
  }
});
</script>
<style></style>