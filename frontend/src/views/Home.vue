<template>
  <div id="home">
    <v-carousel hide-delimiters cycle height="500">
      <v-carousel-item
        :src="require('../assets/img/home/main_buy.jpg')"
        class="pa-4"
        to="/auction"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>경매로 누구보다 빠르게 내 아이템을 겟!</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item
        :src="require('../assets/img/home/main_sell.jpg')"
        class="pa-4"
        to="/postview"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>집에서 놀고 있는 물건을 경매에 등록하세요!</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item
        :src="require('../assets/img/home/main_info.jpg')"
        class="pa-4"
        to="/"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>대충 우리 서비스에 대한 소개</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
    </v-carousel>
    <div class="container">
      <h2 class="my-2">오늘의 경매</h2>
      <div class="row text-center">
        <div
          class="col-md-3 col-sm-6 col-xs-12"
          :key="item.id"
          v-for="item in items"
        >
          <Item :item="item"></Item> 
        </div>
      </div>
    </div>
    <br />
    <br /><br /><br /><br />
  </div>
</template>

<script lang="ts">
import { AxiosResponse } from "axios";
import Vue from "vue";
import Item from "../components/Item.vue";
import { itemApi } from "../utils/axios";

export default Vue.extend({
  name: "Home",

  components: {
    Item
  },
  data: () => ({
    items: [],
    activeBtn: 1,
    slides: ["First", "Second", "Third", "Fourth", "Fifth"]
  }),
  mounted() {
    // 초기 페이지 값과 필터 데이터를 저장합니다.
    const InitialFilter = {status: false, category: [], grade: [], si: null, gu: null, name:""}
    sessionStorage.setItem("page", String(1));
    sessionStorage.setItem("filters", JSON.stringify(InitialFilter));
    this.$store.state.auctionModule.filters = InitialFilter;

    itemApi.getItemPage(1, InitialFilter).then((res:AxiosResponse) => {
      this.items = res.data.object
    })
  },
});
</script>

<style></style>
