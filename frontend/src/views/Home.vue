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
            <strong>대충 경매 참여하라는 멘트</strong>
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

    <v-card-text tile outlined>
      <v-card-title class="subheading ">
        <strong>오늘의 경매</strong>
      </v-card-title>
      <ItemList></ItemList>
    </v-card-text>
    <v-divider></v-divider>

    <v-card-text tile outlined>
      <v-card-title class="subheading ">
        <strong>추천 경매</strong>
      </v-card-title>
      <ItemList></ItemList>
    </v-card-text>
    <Info></Info>
    <br /><br /><br /><br />
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import ItemList from "../components/ItemList.vue";
import Info from "../components/Info.vue";

export default Vue.extend({
  name: "Home",

  components: {
    ItemList,
    Info
  },

  data: () => ({
    items: [
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me 2" }
    ],
    activeBtn: 1,
    colors: [
      "indigo",
      "warning",
      "pink darken-2",
      "red lighten-1",
      "deep-purple accent-4"
    ],
    slides: ["First", "Second", "Third", "Fourth", "Fifth"]
  })
});
</script>

<style></style>
