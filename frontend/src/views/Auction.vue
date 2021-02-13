<template>
  <v-container id="auction">
    <p class="text-center" style="font-size:3rem;">Auction</p>
    <v-btn
      color="orange accent-3"
      fab
      min-width="70"
      min-height="70"
      fixed
      bottom
      right
      @click="dialog = true"
    >
      <v-icon>mdi-pencil</v-icon>
    </v-btn>
    <v-dialog v-model="dialog" max-width="60vw">
      <v-card>
        <PostView @close="dialog = false"/>
      </v-card>
    </v-dialog>
    <div class="row">
      <div class="col-md-3 col-sm-3 col-xs-12">
        <Filters @filtering="getItems" :filters="filters" @reset="reset"/>
      </div>
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
import PostView from "./PostView.vue";
import { itemApi } from "../utils/axios";
import { AxiosResponse } from "axios";

export default Vue.extend({
  name: "Auction",

  components: {
    Filters,
    PostView,
    Item
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
    items:[],
  }),
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
      })
    },
    // reset버튼을 누르면 기본 필터값으로 적용됩니다.
    reset(data: string) {
      const tmpFilter = {status: false, category: [], grade: [], si: null, gu: null, name: data}
      this.$store.dispatch("auctionModule/setFilter", tmpFilter);
      this.filters = tmpFilter;
    }
  },
  mounted() {
    const data = this.$store.state.auctionModule.filters.name;
    console.log(data);
    this.reset(data);
    this.getItems();
    this.filters = this.$store.state.auctionModule.filters;
    this.page = this.$store.state.auctionModule.page;
  }
});
</script>

<style>
.auction {
  width: 60%;
}
</style>
