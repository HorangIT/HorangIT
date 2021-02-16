<template>
  <v-container>
    <v-card
      class="mx-auto my-12"
      max-width="30vw"
    >
    <v-img
      width="250"
      src="../../assets/img/layout/horangit_4.png"
      class="mx-auto my-4"
    ></v-img>

    <h2 class="text-center">결제에 실패했습니다🐯</h2>

    <v-btn
      color="orange"
      outlined
      to="/myauction"
      class="d-flex my-5"
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
