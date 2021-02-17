<template>
  <v-container v-if="!open">
    <v-list two-line>
      <h3 class="mb-5 mt-5">판매물품</h3>
      <h5 v-if="sellItems.length == 0">판매중인 물품이 없습니다.</h5>
      <template v-for="index in sellRange((sellPage - 1)*5, 5)">
        <v-list-item :key="'sell' + index" class="blue lighten-5">
          <v-list-item-content>
            <v-list-item-title v-text="sellItems[index].itemTitle"></v-list-item-title>
            <v-list-item-subtitle
              class="text--primary"
              v-text="sellItems[index].message"
            ></v-list-item-subtitle>
          </v-list-item-content>
          <v-btn class="mr-4" v-if="sellItems[index].status==0">상품페이지</v-btn>
          <v-btn class="mr-4" v-if="sellItems[index].status==1" disabled>결제대기</v-btn>
          <v-btn class="mr-4" v-if="sellItems[index].status==2" 
          @click="deliveryCompleted(sellItems[index].sellerId, sellItems[index].itemId)">배송완료</v-btn>
          <v-btn class="mr-4" v-if="sellItems[index].status==3" disabled>배송중</v-btn>
          <v-btn class="mr-4" v-if="sellItems[index].status==4">대금확인</v-btn>
          <v-btn @click="openChat(sellItems[index].itemId, 'buyer')">채팅</v-btn>
        </v-list-item>
        <v-divider
          v-if="index < sellItems.length - 1"
          :key="'sellDiv' +index"
        ></v-divider>
      </template>
      <div class="text-center mt-5 mb-5" v-if="sellItems.length > 5">
        <v-pagination
          v-model="sellPage"
          :length="Math.ceil((sellItems.length - 1) / 5)"
          prev-icon="mdi-menu-left"
          next-icon="mdi-menu-right"
        ></v-pagination>
      </div>

      <h3 class="mb-5 mt-5">구매물품</h3>
      <h5 v-if="buyItems.length == 0">구매중인 물품이 없습니다.</h5>
      <template  v-for="index in buyRange((buyPage - 1)*5, 5)">
        <v-list-item :key="'buy' + index" class="orange lighten-5">
          <v-list-item-content>
            <v-list-item-title v-text="buyItems[index].itemTitle"></v-list-item-title>
            <v-list-item-subtitle
              class="text--primary"
              v-text="buyItems[index].message"
            ></v-list-item-subtitle>
          </v-list-item-content>
          <v-btn class="mr-4" v-if="buyItems[index].status==1" @click="pay(index, buyItems[index].itemId)">결제</v-btn>
          <v-btn class="mr-4" v-if="buyItems[index].status==2" disabled>결제완료</v-btn>
          <v-btn class="mr-4" v-if="buyItems[index].status==3" 
          @click="takeCompleted(buyItems[index].buyerId, buyItems[index].itemId)">수령확인</v-btn>
          <v-btn class="mr-4" v-if="buyItems[index].status==4" disabled>거래완료</v-btn>
          <v-btn @click="openChat(buyItems[index].itemId, 'seller')">채팅</v-btn>
        </v-list-item>
        <v-divider
          v-if="index < buyItems.length - 1"
          :key="'buyDiv' + index"
        ></v-divider>
      </template>
      <div class="text-center mt-5 mb-5" v-if="buyItems.length > 5">
        <v-pagination
          v-model="buyPage"
          :length="Math.ceil((buyItems.length - 1) / 5)"
          prev-icon="mdi-menu-left"
          next-icon="mdi-menu-right"
        ></v-pagination>
      </div>
    </v-list>
  </v-container>
  <Chatroom :chatInfo="chatInfo" @close="open = false" v-else />
</template>

<script lang="ts">
import Vue from "vue";
import { AxiosResponse } from "axios";
import { myAuctionApi } from "../utils/axios";
import Chatroom from "@/components/myauction/Chatroom.vue";

export default Vue.extend({
  name:"Myauction",
  components: {
    Chatroom,
  },
  data: () => ({
    sellPage: 1,
    buyPage: 1,
    open: false,
    chatInfo: {
      itemId: '0',
      myId: '0',
      me: '',
    },
    sellItemPage: 1,
    buyItemPage: 1,
    sellItems: [
      {
        itemId: 1,
        itemTitle: '전자기기1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 1
      },
      {
        itemId: 2,
        itemTitle: '의류1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 2
      },
      {
        itemId: 3,
        itemTitle: '전자기기1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 3
      },
      {
        itemId: 4,
        itemTitle: '의류1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 4
      },
            {
        itemId: 1,
        itemTitle: '전자기기1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 1
      },
      {
        itemId: 2,
        itemTitle: '의류1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 2
      },
      {
        itemId: 3,
        itemTitle: '전자기기1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 3
      },
      {
        itemId: 4,
        itemTitle: '의류1',
        message: '등록하신 상품이 낙찰되었습니다.',
        status: 4
      },
    ],
    buyItems: [
      {
        itemId: 5,
        itemTitle: '전자기기3',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 1
      },
      {
        itemId: 6,
        itemTitle: '의류2',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 2
      },
      {
        itemId: 7,
        itemTitle: '전자기기4',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 3
      }, 
      {
        itemId: 8,
        itemTitle: '전자기기3',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 4
      },
           {
        itemId: 5,
        itemTitle: '전자기기3',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 1
      },
      {
        itemId: 6,
        itemTitle: '의류2',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 2
      },
      {
        itemId: 7,
        itemTitle: '전자기기4',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 3
      }, 
      {
        itemId: 8,
        itemTitle: '전자기기3',
        message: '축하합니다. 낙찰에 성공했습니다.',
        status: 4
      }
    ],
  }),
  async created() {
    const uid = (this as any).$store.state.userModule.user.object.user.id;
    try {
      const { data } = await myAuctionApi.buyer(uid);
      console.log(data);
      this.buyItems = data.object;
    } catch(error) {
      console.log(error);
    }

    try {
      const { data } = await myAuctionApi.seller(uid);
      console.log(data);
      this.sellItems = data.object;
    } catch(error) {
      console.log(error);
    }
  },
  methods: {
    pay(index: number, itemId: number) {
      const data = {
        buyerId: (this.buyItems[index] as any).buyerId,
        itemId: itemId,
        name: (this.buyItems[index] as any).itemTitle,
        price: (this.buyItems[index] as any).finalPrice,
      }
      // console.log(data);
      myAuctionApi.pay(data).then((res: AxiosResponse) => {
        location.href = res.data.object.successUrl;
      });
    },
    async deliveryCompleted(userId: number, itemId: number) {
      try {
        const { data } = await myAuctionApi.sellerItem(userId, itemId);
        window.location.reload();
      } catch(error) {
        console.log(error);
      }
    },
    async takeCompleted(userId: number, itemId: number) {
      try {
        const { data } = await myAuctionApi.buyerItem(userId, itemId);
        window.location.reload();
      } catch(error) {
        console.log(error);
      }
    },
    openChat (itemId: string, me: string) {
      console.log('click openChat');
      this.open = true;
      this.chatInfo.itemId = itemId;
      this.chatInfo.myId = (this as any).$store.state.userModule.user.object.user.id;
      this.chatInfo.me = me;
    },
 
    sellRange(start: number, count: number) {
      let arr = [];
      let length = this.sellItems.length;
      
      if (length <= 0) return [];
      for (let i = 0; i < count; i++) { 
          arr[i] = start;
          start++;
          if (start >= length) break; 
      }
      return arr;
    },
    buyRange(start: number, count: number) {
      let arr = [];
      let length = this.buyItems.length;

      if (length <= 0) return [];
      for (let i = 0; i < count; i++) { 
          arr[i] = start;
          start++;
          if (start >= length) break; 
      }
      return arr;
    }
  },

})
</script>