<template>
  <div>
    <v-container>
      <v-sheet
        color="grey lighten-4"
        height="10vh"
        class="d-flex align-center justify-center"
      >
        <h1>
          경매시작까지 남은시간 / 경매종료까지 남은시간
        </h1>
      </v-sheet>
      <div class="row">
        <div class="col-md-5 col-sm-5 col-xs-12">
          <v-carousel>
            <v-carousel-item
              v-for="(img, i) in item.filePath"
              :key="i"
              :src="img"
            >
            </v-carousel-item>
          </v-carousel>
        </div>
        <div class="col-md-7 col-sm-7 col-xs-12">
          <div class="pl-6">
            <v-sheet tile class="py-3 d-flex">
              <h1 class="d-inline-flex my-1">{{ item.name }}</h1>
              <v-spacer></v-spacer>
              <div class="d-flex align-center">
                <v-avatar class="d-inline-flex">
                  <v-img src="https://cdn.vuetifyjs.com/images/lists/1.jpg">
                  </v-img>
                </v-avatar>
                <p class="ma-0 ml-2">문어복어</p>
                <v-rating
                  v-model="rating"
                  class=""
                  background-color="warning lighten-3"
                  color="warning"
                  dense
                >
                </v-rating>
              </div>
            </v-sheet>
            <v-divider></v-divider>
            <h1 class="d-inline-flex mt-1">{{ item.startPrice }}원</h1>
            <v-spacer></v-spacer>
            <p class="subtitle-1">{{ item.description }}</p>
            <div class="d-flex">
              <div style="width:50%;">
                <p class="subtitle-1">
                  시작가(응찰단위): {{ item.startPrice }}원
                </p>
                <p class="subtitle-1">즉시낙찰가: {{ item.happyPrice }}원</p>
              </div>
              <div>
                <p class="subtitle-1">판매지역: {{ item.location }}</p>
                <p class="subtitle-1">상품등급: {{ item.grade }}</p>
                <p class="subtitle-1">배송유형: {{ item.direct }}</p>
              </div>
            </div>
            <v-divider></v-divider>
            <p class="py-4 ma-0">나의 응찰 / 전체 응찰 :</p>
            <div class="d-flex">
              <v-text-field
                label="Outlined"
                single-line
                outlined
              ></v-text-field>
              <v-btn outlined tile>^</v-btn>
              <v-btn outlined tile>v</v-btn>
            </div>
            <v-btn 
              class="primary white--text block large" outlined tile dense @click="bid">
              경매 참여하기
            </v-btn>
            <v-btn class="ml-4" outlined tile @click="!active">
              찜하기
              <v-icon>{{ active ? "mdi-heart" : "mdi-heart-outline" }}</v-icon>
            </v-btn>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 col-xs-12 col-md-12">
          <v-tabs>
            <v-tab>경매 톡톡</v-tab>
            <v-tab>리뷰</v-tab>
            <v-tab-item>
              <Chat />
            </v-tab-item>
            <v-tab-item>
              <Review />
            </v-tab-item>
          </v-tabs>
        </div>
      </div>
    </v-container>
    <v-divider></v-divider>
    <v-card-text tile outlined>
      <v-card-title class="subheading">
        <strong>추천 경매</strong>
      </v-card-title>
      <ItemList></ItemList>
    </v-card-text>
    <v-divider></v-divider>
    <div class="pl-4 pr-4 row">
      <div class="col-md-6 col-sm-6 col-xs-12">
        <v-card>
          <v-img
            :src="require('../assets/img/home/slider2.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="400px"
          >
            <h1 class="text-center font-size">경매 참여 방법</h1>
            <div class="text-center">
              <v-btn href="#" class="white--text" outlined>Go</v-btn>
            </div>
          </v-img>
        </v-card>
      </div>
      <div class="col-md-6 col-sm-6 col-xs-12">
        <v-card>
          <v-img
            :src="require('../assets/img/home/slider3.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="400px"
          >
            <h1 class="text-center font-size">FAQs</h1>
            <div class="text-center">
              <v-btn href="/shop" class="white--text" outlined>Go</v-btn>
            </div>
          </v-img>
        </v-card>
      </div>
    </div>
    <br /><br /><br /><br /><br />
  </div>
</template>
<script lang="ts">
import Vue from "vue";
import ItemList from "../components/ItemList.vue";
import Chat from "../components/detail/Chat.vue";
import Review from "../components/detail/Review.vue";
import { itemApi, auctionApi } from "../utils/axios";
import { AxiosResponse } from "axios";

export default Vue.extend({
  name: "Product",

  components: {
    ItemList,
    Chat,
    Review
  },

  data: () => ({
    rating: 4.5,
    active: false,
    item: Object,
    nowPrice: Number,
    bidding: {
      userId: "",
      itemID: "임의",
      price: ""
    }
  }),
  methods: {
    getItem() {
      itemApi.getItem(1).then((res: AxiosResponse) => {
        this.item = res.data.object;
        this.nowPrice = res.data.object.startPrice;
        console.log(this.item);
      });
    },
    bid() {
      const user = JSON.parse(localStorage.getItem("user") || "{}");
      this.bidding.userId = String(user.object.user.id);
      this.bidding.price = String(this.nowPrice);
      console.log(this.bidding);
      auctionApi.bidding(this.bidding).then((res: AxiosResponse) => {
        console.log(this.bidding);
        console.log(res);
      });
    }
  },
  created() {
    this.getItem();
  }
});
</script>
<style></style>
