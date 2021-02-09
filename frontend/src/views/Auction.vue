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
      check: false,
      minPrice: 0,
      maxPrice: 100000000,
      category: [],
      grades: [],
      si: "",
      gu: "",
    },
    dialog: false,
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
    ]
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
        console.log(res);
      })
      
      const tmpFilter = JSON.stringify(filters)
      sessionStorage.setItem("filters", tmpFilter);
      sessionStorage.setItem("page", page.toString());
    },
    reset() {
      const tmpFilter = {check: false, minPrice: 0, maxPrice: 100000000, grades: [], si: "", gu: ""}
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
