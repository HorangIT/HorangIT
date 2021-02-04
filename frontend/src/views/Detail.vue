<template>
  <div>
    <v-container>
      <TimeBar
        :start="item.startDate"
        :end="item.endDate"
      ></TimeBar>
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
            <h2 class="d-inline-flex mt-1 mr-5">
              시작가 : {{ item.startPrice }}원
            </h2>
            <h2 class="d-inline-flex mt-1" color="primary">
              현재가 : {{ nowPrice }}원
            </h2>
            <p class="my-3">{{ item.description }}</p>
            <div class="mt-2">
              <p class="subtitle-1">지역: {{ item.location }}</p>
              <p class="subtitle-1">등급: {{ item.grade }}</p>
              <p class="subtitle-1">배송: {{ item.direct }}</p>
            </div>
            <v-divider></v-divider>
            <p class="py-4 ma-0">나의 응찰 / 전체 응찰 :</p>
            <div>
              <v-btn
                class="primary white--text"
                outlined
                tile
                width="50%"
                height="10vh"
                @click="bid"
              >
                <h2>'nextPrice'<br />응찰하기</h2>
              </v-btn>
              <v-btn
                class="orange white--text"
                outlined
                tile
                width="50%"
                height="10vh"
                @click="flex"
              >
                <h2>{{ item.happyPrice }}원<br />FLEX</h2>
              </v-btn>
            </div>
            <v-divider></v-divider>
            <BiddingLog></BiddingLog>
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
import BiddingLog from "../components/detail/BiddingLog.vue";
import TimeBar from "../components/detail/TimeBar.vue";
import { itemApi, auctionApi } from "../utils/axios";
import { AxiosResponse } from "axios";

export default Vue.extend({
  name: "Product",

  components: {
    ItemList,
    Chat,
    Review,
    BiddingLog,
    TimeBar,
  },

  data: () => ({
    rating: 4.5,
    active: false,
    item: Object,
    itemId: 0,
    nowPrice: 0
  }),
  methods: {
    getItem(id: number) {
      itemApi.getItem(id).then((res: AxiosResponse) => {
        this.item = res.data.object;
        this.itemId = res.data.object.itemId;
        this.nowPrice = res.data.object.nowPrice;
        console.log(this.item);
      });
    },
    bid() {
      const user = JSON.parse(localStorage.getItem("user") || "{}");
      const bidInfo = {
        userId: String(user.object.user.id),
        itemId: String(this.itemId),
        nowPrice: String(this.nowPrice)
      };
      console.log(bidInfo);
      auctionApi.bidding(bidInfo).then((res: AxiosResponse) => {
        console.log(res.data.data);
        this.nowPrice = res.data.object.nowPrice;
      });
    },
    flex() {
      const user = JSON.parse(localStorage.getItem("user") || "{}");
      const flexInfo = {
        userId: String(user.object.user.id),
        itemId: String(this.itemId)
      };
      auctionApi.flex(flexInfo).then((res: AxiosResponse) => {
        console.log(res);
      });
    }
  },
  created() {
    const id = Number(this.$route.params.id);
    this.getItem(id);
  }
});
</script>
<style></style>
