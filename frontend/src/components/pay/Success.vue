<template>
  <v-container>
    <v-card
      class="mx-auto my-12"
      max-width="30vw"
    >
    <v-img
      width="250"
      src="../../assets/img/layout/horangit_5.png"
      class="mx-auto my-4"
    ></v-img>

    <v-card-title>결제가 완료되었습니다🐯</v-card-title>

    <v-card-text>
      <p>구매처 : {{ payInfo.partnerID }}</p>
      <p>상품명 : {{ payInfo.itemName }}</p>
      <p>결제일시 : {{ newDate }}</p>
      <p>결제금액 : {{ payInfo.amount.total }}</p>
      <p>결제수단 : {{ payInfo.paymentMethod }}</p>
    </v-card-text>

    <v-btn
      color="orange"
      outlined
      to="/myauction"
      class="ma-5"
    >
      돌아가기
    </v-btn>
    
  </v-card>
  </v-container>
</template>

<script lang="ts">
import Vue from "vue";
import { AxiosResponse } from "axios";
import { myAuctionApi } from "../../utils/axios";

export default Vue.extend({
  name: "Success",

  data: () => ({
    payInfo : {},
    newDate: ""
  }),
  created () {
    // console.log(this.$route.query.pg_token);
    const token = this.$route.query.pg_token
    myAuctionApi.success(token).then((res: AxiosResponse) => {
      this.payInfo = res.data.object;
      const date = res.data.object.paymentDate.split(' ');
      date.reverse();
      const newdate = [date[0], date[4], date[3], date[5], date[2]];
      this.newDate = newdate.join(' ')
    })
  }
});
</script>
