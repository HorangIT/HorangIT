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
    <div class="container">
      <h2 class="my-2">오늘의 경매</h2>
      <div class="row text-center">
        <div
          class="col-md-3 col-sm-6 col-xs-12"
          :key="pro.id"
          v-for="pro in products"
        >
          <Item :pro="pro"></Item> 
        </div>
      </div>
    </div>
    <v-divider></v-divider>

    <Info></Info>
    <br /><br /><br /><br />
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import Info from "../components/Info.vue";
import Item from "../components/Item.vue";

export default Vue.extend({
  name: "Home",

  components: {
    Info,
    Item
  },

  data: () => ({
     products: [
      {
        id: 1,
        name: "BLACK TEE",
        type: "Jackets",
        price: "18.00",
        src: require("../assets/img/shop/1.jpg")
      },
      {
        id: 2,
        name: "WHITE TEE",
        type: "Polo",
        price: "40.00",
        src: require("../assets/img/shop/2.jpg")
      },
      {
        id: 3,
        name: "Zara limited...",
        type: "Denim",
        price: "25.00",
        src: require("../assets/img/shop/3.jpg")
      },
      {
        id: 4,
        name: "SKULL TEE",
        type: "Jackets",
        price: "30.00",
        src: require("../assets/img/shop/4.jpg")
      },
      {
        id: 5,
        name: "MANGO WINTER",
        type: "Sweaters",
        price: "50.00",
        src: require("../assets/img/shop/5.jpg")
      },
      {
        id: 6,
        name: "SHIRT",
        type: "Denim",
        price: "34.00",
        src: require("../assets/img/shop/6.jpg")
      },
      {
        id: 7,
        name: "TRUCKER JACKET",
        type: "Jackets",
        price: "38.00",
        src: require("../assets/img/shop/7.jpg")
      },
      {
        id: 8,
        name: "COATS",
        type: "Jackets",
        price: "25.00",
        src: require("../assets/img/shop/8.jpg")
      },
      {
        id: 9,
        name: "MANGO WINTER",
        type: "Sweaters",
        price: "50.00",
        src: require("../assets/img/shop/9.jpg")
      },
      {
        id: 10,
        name: "SHIRT",
        type: "Denim",
        price: "34.00",
        src: require("../assets/img/shop/10.jpg")
      },
      {
        id: 11,
        name: "TRUCKER JACKET",
        type: "Jackets",
        price: "38.00",
        src: require("../assets/img/shop/11.jpg")
      },
      {
        id: 12,
        name: "COATS",
        type: "Jackets",
        price: "25.00",
        src: require("../assets/img/shop/12.jpg")
      }
    ],
    activeBtn: 1,
    slides: ["First", "Second", "Third", "Fourth", "Fifth"]
  })
});
</script>

<style></style>
