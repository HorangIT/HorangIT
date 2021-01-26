<template>
  <div>
    <v-container>
      <div class="row">
        <div class="col-md-5 col-sm-5 col-xs-12">
          <v-carousel>
            <v-carousel-item :src="require('../assets/img/home/slider4.jpg')">
            </v-carousel-item>
            <v-carousel-item :src="require('../assets/img/home/slider2.jpg')">
            </v-carousel-item>
            <v-carousel-item :src="require('../assets/img/home/slider3.jpg')">
            </v-carousel-item>
          </v-carousel>
        </div>
        <div class="col-md-7 col-sm-7 col-xs-12">
          <v-breadcrumbs class="pt-0" :items="breadcrums"></v-breadcrumbs>
          <div class="pl-6">
            <div class="d-flex justify-space-between align-center">
              <h1 class="d-inline-flex mb-0">경매물품 이름</h1>
              <h3 class="d-inline-flex mb-0">
                경매시작까지 남은시간 / 경매종료까지 남은시간
              </h3>
            </div>
            <v-divider></v-divider>
            <v-sheet tile class="py-3 d-flex">
              <h1 class="d-inline-flex">100,000원</h1>
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
            <p class="subtitle-1">상세설명이 들어갈 자리입니다. 주저리주저리...</p>
            <div class="d-flex">
              <div style="width:50%;">
                <p class="subtitle-1">시작가(응찰단위):</p>
                <p class="subtitle-1">즉시낙찰가:</p>
              </div>
              <div>
                <p class="subtitle-1">판매지역:</p>
                <p class="subtitle-1">상품등급:</p>
                <p class="subtitle-1">배송유형:</p>
              </div>
            </div>
            <v-divider></v-divider>
            <p class="py-4 ma-0">나의 응찰 / 전체 응찰 :</p>
            <v-btn class="primary white--text block large" outlined tile dense>
              경매 참여하기
            </v-btn>
            <v-btn class="ml-4" outlined tile>
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
              <p class="pt-10 subtitle-1 font-weight-thin">
                경매 톡 컴포넌트 들어갈 자리입니다.
              </p>
            </v-tab-item>
            <v-tab-item>
              <v-list three-line="true" avatar="true">
                <v-list-item-group v-model="item" color="primary">
                  <v-list-item
                    v-for="(item, i) in items"
                    :key="i"
                    inactive="true"
                  >
                    <v-list-item-avatar>
                      <v-img :src="item.avatar"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title v-html="item.title">
                      </v-list-item-title>
                      <v-rating
                        v-model="rating"
                        class=""
                        background-color="warning lighten-3"
                        color="warning"
                        dense
                      >
                      </v-rating>
                      <v-list-item-subtitle v-html="item.subtitle">
                      </v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
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

export default Vue.extend({
  name: "Product",

  components: {
    ItemList
  },

  data: () => ({
    rating: 4.5,
    breadcrums: [
      {
        text: "Home",
        disabled: false,
        href: "breadcrumbs_home"
      },
      {
        text: "Clothing",
        disabled: false,
        href: "breadcrumbs_clothing"
      },
      {
        text: "T-Shirts",
        disabled: true,
        href: "breadcrumbs_shirts"
      }
    ],
    item: 5,
    items: [
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
        title: "Lorem ipsum dolor?",
        subtitle: "<span class='text--primary'>Ali Connors</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nisl tincidunt eget nullam non. Tincidunt arcu non sodales neque sodales ut etiam. Lectus arcu bibendum at varius vel pharetra. Morbi tristique senectus et netus et malesuada.\n"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
        title: 'Lorem ipsum dolor <span class="grey--text text--lighten-1">4</span>',
        subtitle: "<span class='text--primary'>to Alex, Scott, Jennifer</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
        title: "Lorem ipsum dolor",
        subtitle: "<span class='text--primary'>Sandra Adams</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
        title: "Lorem ipsum dolor",
        subtitle: ""
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
        title: "Lorem ipsum dolor",
        subtitle: "<span class='text--primary'>Britta Holt</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    ]
  })
});
</script>
<style></style>
