<template>
<v-container>
  <v-list two-line>
    <h3 class="mb-5 mt-5">판매물품</h3>
    <template v-for="(item, index) in sellItems">
      <v-list-item :key="'sell' + index" class="blue lighten-5">
        <template>
          <v-list-item-content>
            <v-list-item-title v-text="item.itemTitle"></v-list-item-title>
            <v-list-item-subtitle
              class="text--primary"
              v-text="item.message"
            ></v-list-item-subtitle>
            <v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-action>
            <v-list-item-action-text v-text="item.action"></v-list-item-action-text>
          </v-list-item-action>
          <v-btn class="mr-4" v-if="item.status==0">상품페이지</v-btn>
          <v-btn class="mr-4" v-if="item.status==1">배송완료</v-btn>
          <v-btn class="mr-4" v-if="item.status==2">수금하기</v-btn>
          <v-btn>채팅</v-btn>
        </template>
      </v-list-item>
      <v-divider
        v-if="index < sellItems.length - 1"
        :key="'sellDiv' +index"
      ></v-divider>
    </template>

    <h3 class="mb-5 mt-5">구매물품</h3>
    <template  v-for="(item, index) in buyItems">
      <v-list-item :key="'buy' + index" class="orange lighten-5">
        <template>
          <v-list-item-content>
            <v-list-item-title v-text="item.itemTitle"></v-list-item-title>
            <v-list-item-subtitle
              class="text--primary"
              v-text="item.message"
            ></v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-action>
            <v-list-item-action-text v-text="item.action"></v-list-item-action-text>
          </v-list-item-action>
          <v-btn class="mr-4" v-if="item.status==1">결제</v-btn>
          <v-btn class="mr-4" v-if="item.status==2" disabled>배송중</v-btn>
          <v-btn class="mr-4" v-if="item.status==3">인수</v-btn>
          <v-btn>채팅</v-btn>
        </template>
      </v-list-item>
      <v-divider
        v-if="index < buyItems.length - 1"
        :key="'buyDiv' + index"
      ></v-divider>
    </template>

    <PayButton @showQR="showQR"/>
    <v-modal>
      
    </v-modal>
    <a :href="this.payURL">결제링크</a>

  </v-list>
  </v-container>
</template>

<script lang="ts">
import Vue from "vue";
import { myAuctionApi } from "../utils/axios";
import PayButton from "../components/myauction/PayButton.vue";

export default Vue.extend({
  name:"Myauction",
  components: {
    PayButton,
  },
  data: () => ({
    sellItemPage: 1,
    buyItemPage: 1,
    sellItems: [
      {
        itemTitle: '전자기기1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 1
      },
      {
        itemTitle: '의류1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 2
      },
    ],
    buyItems: [
      {
        itemTitle: '전자기기3',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 1
      },
      {
        itemTitle: '의류2',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 2
      },
      {
        itemTitle: '전자기기4',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 3
      },      {
        itemTitle: '전자기기3',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 1
      },
      {
        itemTitle: '의류2',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 2
      },
      {
        itemTitle: '전자기기4',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 3
      },
    ],
    payQR: "",
    is_pay: false,
  }),
  async created() {
    const uid = (this as any).$store.state.userModule.user.object.user.id;
    try {
      console.log('buyer');
      // await myAuctionApi.buyer(uid);
    } catch(error) {
      console.log(error);
    }

    try {
      console.log('seller');
      // await myAuctionApi.seller(uid);
    } catch(error) {
      console.log(error);
    }
  },
  methods: {
    showQR(data: string) {
      (this as any).payQR = data;
      (this as any).is_pay = true;
    }
  },
})
</script>