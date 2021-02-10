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
        <Filters @filtering="filtering" :filters="filters" @reset="reset"/>
      </div>
      <div class="col-md-9 col-sm-9 col-xs-12">
        <div class="row text-center">
          <div
            class="col-md-3 col-sm-6 col-xs-12"
            :key="pro.id"
            v-for="pro in products"
          >
            <Item :pro="pro"></Item> 
          </div>
        </div>
        <div class="text-center mt-12">
          <v-pagination
            v-model="page"
            :length="6"
            @input="getItemPage"
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
    products:[],
  }),
  methods: {
    getItemPage(page: number) {
      this.page = page;
      this.getItems(this.page, this.filters);
    },
    filtering(filters: any) {
      this.filters = filters;
      this.page = 1;
      this.getItems(this.page, this.filters);
    },
    getItems(page: number, filters: any){
      console.log(this.page, this.filters);
      
      itemApi.getItemPage(page, filters).then((res: AxiosResponse) => {
        this.products = res.data.object;
        console.log(res);
      })
      
      const tmpFilter = JSON.stringify(filters)
      sessionStorage.setItem("filters", tmpFilter);
      sessionStorage.setItem("page", page.toString());
    },
    reset() {
      const tmpFilter = {status: false, category: [], grade: [], si: null, gu: null}
      this.filters = tmpFilter;
    }
  },
  created() {
    this.getItems(
      parseInt(this.$store.state.auctionModule.page), 
      JSON.parse(this.$store.state.auctionModule.filters)
    )
    this.filters = JSON.parse(this.$store.state.auctionModule.filters);
  }
});
</script>

<style>
.auction {
  width: 60%;
}
</style>
