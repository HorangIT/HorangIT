<template>
  <div>
    <v-container>
      <TimeBar :start="item.startDate" :end="item.endDate"></TimeBar>
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
                <p class="ma-0 mx-2">{{ item.nickname }}</p>
              </div>
            </v-sheet>
            <v-divider></v-divider>

            <v-row>
              <v-col>
                <h2 class="my-3 d-inline-flex mr-10">시작가</h2>
                <h2 class="d-inline-flex">\ {{ item.startPrice | comma }}</h2>
                <br />
                <h2 class="d-inline-flex mr-10">현재가</h2>
                <h2 class="d-inline-flex">\ {{ nowPrice | comma }}</h2>
              </v-col>

              <v-col>
            <div class="mt-4">
              <p class="subtitles">지역: {{ item.location }}</p>
              <p class="subtitles">등급: {{ item.grade }}</p>
              <p class="subtitles">배송: {{ item.direct }}</p>
            </div>
              </v-col>
            </v-row>
            

            <p class="my-3">{{ item.description }}</p>

            <v-divider></v-divider>
            <p class="py-4 ma-0">나의 응찰 / 전체 응찰 :</p>
            <div>
              <v-btn
                class="primary white--text"
                outlined
                tile
                width="50%"
                height="12vh"
                @click="bid"
              >
                <h2 v-if="isOver">Flex!!!!!!!!!!!!</h2>
                <h2 v-else>
                  {{ nowPrice | comma }}원<br />응찰하기<br />
                  <small>다음 응찰가는 {{ nextPrice | comma }}원입니다.</small>
                </h2>
              </v-btn>
              <v-btn
                class="orange white--text"
                outlined
                tile
                width="50%"
                height="12vh"
                @click="dialog = true"
              >
                <h2 v-if="isOver">Flex!!!!!!!!!!!!</h2>
                <h2 v-else>{{ item.happyPrice | comma }}원<br />FLEX</h2>
              </v-btn>
              <v-dialog v-model="dialog" max-width="290">
                <v-card>
                  <h1 class="pt-4 text-center">ARE YOU SURE?</h1>
                  <v-img src="../assets/img/layout/horangit_6.png"></v-img>
                  <v-card-actions>
                    <v-btn color="primary" @click="dialog = false">
                      한 번 더 생각해볼게요
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn color="warning" @click="flex"> FLEX!!! </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
            <v-divider></v-divider>
            <p class="py-4 ma-0">응찰내역</p>
            <BiddingLog :biddingLog="biddingLog"></BiddingLog>
          </div>
        </div>
      </div>
    </v-container>
    <v-divider></v-divider>
    <v-divider></v-divider>
    <Info />
    <br /><br /><br /><br /><br />
  </div>
</template>
<script lang="ts">
import Vue from "vue";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import Info from "../components/Info.vue";
import Chat from "../components/detail/Chat.vue";
import Review from "../components/detail/Review.vue";
import BiddingLog from "../components/detail/BiddingLog.vue";
import TimeBar from "../components/detail/TimeBar.vue";
import { itemApi, auctionApi } from "../utils/axios";
import { AxiosResponse } from "axios";
import numberWithCommas from "../utils/numberWithCommas";

export default Vue.extend({
  name: "Product",
  components: {
    Chat,
    Review,
    BiddingLog,
    TimeBar,
    Info,
  },

  data: () => ({
    item: {},
    itemId: 0,
    nowPrice: 0,
    nextPrice: 0,
    happyPrice: 0,
    biddingLog: [],
    isOver: false,
    dialog: false,
    stompClient: Stomp.over(new SockJS("http://localhost:8000/api/ws")),
  }),
  filters: {
    comma(val: number | string) {
      return numberWithCommas(Number(val));
    },
  },
  watch: {
    nowPrice() {
      if (this.nowPrice === this.happyPrice) {
        this.isOver = true;
      }
    },
    item() {
      console.log(this.item)
    }
  },
  methods: {
    getItem(id: number) {
      itemApi.getItem(id).then((res: AxiosResponse) => {
        this.item = res.data.object;
        this.itemId = res.data.object.itemId;
        this.nowPrice = res.data.object.nowPrice;
        this.nextPrice = res.data.object.nextPrice;
        this.happyPrice = res.data.object.happyPrice;
      });
    },
    connect () {
      this.stompClient.connect(
        {},
        // socket 연결 성공
        () => {
          console.log('응찰 소켓 연결 성공');
          // server 옥션 메세지 전송 endpoint 구독하기
          this.stompClient.subscribe("#", res => {
            console.log('응찰 구독 성공!', res);
          });
        },
        // socket 연결 실패
        err => {
          console.log(err);
        })
    },
    bid() {
      if (localStorage.getItem("user") && this.stompClient) {
        const user = JSON.parse(localStorage.getItem("user") || "{}");
        const bidInfo = {
          userId: String(user.object.user.id),
          itemId: String(this.itemId),
          nowPrice: String(this.nowPrice),
        };
        // socket으로 전송
        this.stompClient.send("#",{}, JSON.stringify(bidInfo));
        // 기존 api로 전송
        auctionApi.bidding(bidInfo).then((res: AxiosResponse) => {
          console.log(res.data.object);
          this.nowPrice = res.data.object.nowPrice;
          this.nextPrice = res.data.object.nextPrice;
          this.log();
        });
      } else {
        alert("로그인 해주세요!");
      }
    },
    flex() {
      if (localStorage.getItem("user")) {
        const user = JSON.parse(localStorage.getItem("user") || "{}");
        const flexInfo = {
          userId: String(user.object.user.id),
          itemId: String(this.itemId),
          nowPrice: String(this.nowPrice),
        };
        auctionApi.flex(flexInfo).then((res: AxiosResponse) => {
          console.log(res.data);
          this.nowPrice = this.happyPrice;
          this.isOver = true;
          this.log();
        });
      } else {
        alert("로그인 해주세요!");
      }
      this.dialog = false;
    },
    log() {
      // 응찰 내역 불러오기
      auctionApi
        .log(Number(this.$route.params.id))
        .then((res: AxiosResponse) => {
          this.biddingLog = res.data.object.log.reverse();
          // console.log('this.biddingLog')
          // console.log(this.biddingLog)
        })
        .catch(() => {
          this.biddingLog = [];
        });
      },
    },
  created() {
    // id번 detail정보 가져오기
    const id = Number(this.$route.params.id);
    this.getItem(id);
    // socket 연결
    this.connect();
    // 응찰 내역
    this.log();
  },
});
</script>
<style></style>
