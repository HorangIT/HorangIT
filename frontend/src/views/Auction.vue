<template>
  <v-container id="auction">
    <p class="text-center" style="font-size:3rem;">Auction</p>
    <!--로그인 O : 판매하기 // 로그인 X : 로그인-->
    <v-btn
      color="orange accent-3"
      fab
      min-width="70"
      min-height="70"
      fixed
      bottom
      right
      @click="login? dialog = true : pleaseLogin = true"
    >
      <v-icon color="black">mdi-pencil</v-icon>
    </v-btn>
    <v-dialog v-model="dialog" max-width="60vw">
      <v-card>
        <PostView @close="dialog = false"/>
      </v-card>
    </v-dialog>
    <v-dialog v-model="pleaseLogin" max-width="30vw">
      <v-card>
        <LoginForm @close="pleaseLogin = false" :message="message"/>
      </v-card>
    </v-dialog>

    <div class="row">
      <!--필터-->
      <div class="col-md-3 col-sm-3 col-xs-12">
        <Filters @filtering="getItems" :filters="filters" @reset="reset"/>
      </div>
      <!--필터링한 아이템들-->
      <div class="col-md-9 col-sm-9 col-xs-12">
        <div class="row text-center">
          <div
            class="col-md-3 col-sm-6 col-xs-12"
            :key="item.itemId"
            v-for="item in items"
          >
            <Item :item="item"></Item> 
          </div>
        </div>
        <!--페이지네이션-->
        <div class="text-center mt-12">
          <v-pagination
            v-model="page"
            :length="6"
            @input="getPage"
            color="orange"
          >
          </v-pagination>
        </div>
      </div>
    </div>
  </v-container>
</template>

<script lang="ts">
import Vue from "vue";
import Filters from "../components/auction/Filters.vue";
import Item from "../components/Item.vue";
import LoginForm from "../components/user/LoginForm.vue";
import PostView from "./PostView.vue";
import { itemApi } from "../utils/axios";
import { AxiosResponse } from "axios";

export default Vue.extend({
  name: "Auction",

  components: {
    Filters,
    PostView,
    Item,
    LoginForm
  },
  data: () => ({
    page: 1,
    filters: {
      status: false,
      category: [],
      grade: [],
      si: null,
      gu: null
    },
    dialog: false,
    pleaseLogin: false,
    message: "이 필요한 서비스입니다.🐯",
    items:[],
  }),
  computed: {
    login (): string {
      return this.$store.state.userModule.status.loggedIn;
    },
  },
  methods: {
    getPage(page: number) {
      this.$store.dispatch("auctionModule/setPage", page);
      this.getItems();
    },
    getItems(){ // page번호와 filter 데이터가 바뀔때마다 item을 요청합니다.
      // store에 저장된 page와 필터데이터로 아이템을 조회합니다.
      const page = this.$store.state.auctionModule.page;
      const filters = this.$store.state.auctionModule.filters;
      
      itemApi.getItemPage(page, filters).then((res: AxiosResponse) => {
        this.items = res.data.object;
        console.log(this.items)
      })
    },
    // reset버튼을 누르면 기본 필터값으로 적용됩니다.
    reset(data: string) {
      const tmpFilter = {status: false, category: [], grade: [], si: null, gu: null, name: data}
      this.$store.dispatch("auctionModule/setFilter", tmpFilter);
      this.filters = tmpFilter;
    },
  },
  mounted() {
    const data = this.$store.state.auctionModule.filters.name;
    this.reset(data);
    this.getItems();
    this.filters = this.$store.state.auctionModule.filters;
    this.page = this.$store.state.auctionModule.page;
  }
});
</script>

<style scoped>
#app > div.v-dialog__content.v-dialog__content--active {
  background-color: rgba(0, 0, 0, 0.767);
}
</style>
