<template>
  <div>
    <v-container>
      <TimeBar :start="item.startDate" :end="item.endDate" :auctionOver="isOver"></TimeBar>
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
              <v-tab-item>
                <Chat />
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
            <p v-if="!seller" class="py-4 ma-0">나의 응찰 / 전체 응찰 :</p>
            <!-- <div v-if="seller"></div> -->
            <span v-if="!seller"> <!-- 판매자가 아니고 -->
              <div v-if="isOver"> <!-- 경매가 끝났으면 -->
                <v-btn
                  class="blue-grey"
                  outlined
                  tile
                  width="100%"
                  height="12vh"
                  disabled
                >
                  <h2 class="blue-grey--text text--lighten-5">
                    마감되었습니다.
                  </h2>
                </v-btn>              
              </div>
              <div v-else> <!-- 경매가 진행중이면 -->
                <v-btn
                  class="primary white--text"
                  outlined
                  tile
                  width="50%"
                  height="12vh"
                  @click="bid"
                >
                  <h2>
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
                  <h2>{{ item.happyPrice | comma }}원<br />FLEX</h2>
                </v-btn>
                <v-dialog v-model="dialog" max-width="290"> <!-- flex 확인 모달 -->
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
            </span>

            <span v-else> <!-- 판매자고 -->
              <div v-if="isOver"> <!-- 경매가 끝났으면 -->
                <v-btn
                  class="blue-grey"
                  outlined
                  tile
                  width="100%"
                  height="12vh"
                  disabled
                >
                  <h2 class="blue-grey--text text--lighten-5">
                    마감되었습니다.
                  </h2>
                </v-btn>
              </div>
              <div v-else> <!-- 경매가 진행중이면 -->
                <v-btn
                  color="orange lighten-3"
                  outlined
                  tile
                  width="100%"
                  height="12vh"
                  disabled
                >
                  <h2 class="blue-grey--text text--lighten-5">
                    경매가 진행중입니다.
                  </h2>
                </v-btn>
              </div>
            </span>
            <v-divider></v-divider>
            <p class="py-4 ma-0">응찰내역</p>
            <BiddingLog :biddingLog="biddingLog"></BiddingLog>
          </div>
        </div>
      </div>
    </v-container>
    <br /><br /><br /><br /><br />
  </div>
</template>
<script lang="ts">
import Vue from "vue";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import Chat from "../components/detail/Chat.vue";
import BiddingLog from "../components/detail/BiddingLog.vue";
import TimeBar from "../components/detail/TimeBar.vue";
import { itemApi, auctionApi } from "../utils/axios";
import { AxiosResponse } from "axios";
import numberWithCommas from "../utils/numberWithCommas";

export default Vue.extend({
  name: "Product",
  components: {
    Chat,
    BiddingLog,
    TimeBar,
  },

  data: () => ({
    seller: false,
    item: {},
    itemId: -1,
    nowPrice: -1,
    nextPrice: -1,
    happyPrice: -1,
    sellerId: -1,
    biddingLog: new Array<String>(),
    dialog: false,
    stompClient: Stomp.over(new SockJS(`${process.env.VUE_APP_API_SERVER}/ws`)),
  }),
  filters: {
    comma(val: number | string) {
      return numberWithCommas(Number(val));
    },
  },
  computed: {
    user (): any {
      if (this.$store.state.userModule.user !== null) {
        return this.$store.state.userModule.user.object.user;
      }
      return null;
    },
    isOver (): boolean {
      if (this.biddingLog.length === 0) {
        return false;
      }
      return this.happyPrice === Number(this.biddingLog[0].split(';')[1])
    }
  },
  watch: {
    // biddingLog() {
    //   console.log('watched!')
    //   if (this.happyPrice === Number(this.biddingLog[0].split(';')[1])) {
    //     this.isOver = true;
    //   }
    // }
  },
  methods: {
    getItem(id: number) {
      itemApi.getItem(id).then((res: AxiosResponse) => {
        this.item = res.data.object;
        this.itemId = res.data.object.itemId;
        this.happyPrice = res.data.object.happyPrice;
        this.nextPrice = res.data.object.nextPrice;
        this.nowPrice = res.data.object.nowPrice;
        if (Number(this.$store.state.userModule.user.object.user.id) === Number(res.data.object.sellerId)) {
          this.seller = true;
          console.log(this.seller)
        }
      })
      // 404 처리
      .catch(() => this.$router.replace({ path: '/404' }));
    },
    connect () {
      // socket 연결
      this.stompClient.connect(
        {},
        () => {
          // server 옥션 메세지 전송 endpoint 구독하기
          this.stompClient.subscribe(`/topic/auction/${this.itemId}`, res => {
            // nowPrice, nextPrice 업데이트
            console.log('auction subscribed!!!')
            const info = JSON.parse(res.body).content
            // 경매 종료 확인 (경매가 종료되면 type이 AUCTION으로 온다)
            if (JSON.parse(res.body).type === 'AUCTION') {
              this.isOver = true;
            }
            // log 업데이트
            this.biddingLog.unshift(info.log);
            this.nowPrice = info.nowPrice;
            this.nextPrice = info.nextPrice;
            // console.log(info)
            if (info.test !== undefined) {
              this.log()
            }
          });
        },
        // socket 연결 실패
        err => {
          console.log(err);
        })
    },
    bid() {
      // 마감 확인
      if (!this.isOver) {
        if (localStorage.getItem("user") && this.stompClient) {
          const bidInfo = {
            sender: this.user.id,
            content: this.itemId,
            type: "AUCTION"
          };
          // socket으로 전송
          this.stompClient.send(`/app/auction.sendMessage/${this.itemId}`, {}, JSON.stringify(bidInfo));
        } else {
          alert("로그인 해주세요!");
        }
      } else {
        alert("마감되었습니다.")
      }
    },
    flex() {
      // 마감 확인
      if (!this.isOver) {
        if (localStorage.getItem("user") && this.stompClient) {
          const flexInfo = {
            sender: this.user.id,
            content: this.itemId,
            type: "FLEX"
          };
          this.stompClient.send(`/app/auction.sendMessage/${this.itemId}`, {}, JSON.stringify(flexInfo));
        } else {
          alert("로그인 해주세요!");
        }
        this.dialog = false;
      } else {
        alert("마감되었습니다.")
      }
    },
    log() {
      // 응찰 내역 불러오기
      auctionApi.log(Number(this.$route.params.id)).then((res: AxiosResponse) => {
          this.biddingLog = res.data.object.log.reverse();
          // console.log(this.biddingLog);
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
    // 응찰 내역
    this.log();
    this.connect();
  },
});
</script>
<style></style>
