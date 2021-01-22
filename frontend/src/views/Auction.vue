<template>
  <div id="auction">
    <v-carousel hide-delimiters cycle height="200">
      <v-carousel-item
        :src="require('../assets/img/home/slider4.jpg')"
        class="pa-4"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>🔥HOT🔥</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item
        :src="require('../assets/img/home/slider2.jpg')"
        class="pa-4"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Comming Soon</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item
        :src="require('../assets/img/home/slider3.jpg')"
        class="pa-4"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>NEW</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item
        :src="require('../assets/img/home/slider1.jpg')"
        class="pa-4"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Upto 60% + Extra 10%</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
    </v-carousel>

    <v-card-text tile outlined>
      <v-card-title class="justify-center">
        <h1>Auction</h1>
      </v-card-title>
      <v-card-title class="justify-center">
        <a href="#" class="text-decoration-none black--text pr-3">
          <h4>현재 경매</h4>
        </a>
        |
        <a href="#" class="text-decoration-none black--text pl-3">
          <h4>지난 경매</h4>
        </a>
      </v-card-title>
      <v-item-group>
        <v-container>
          <v-row>
            <v-col v-for="(date, idx) in dates" :key="idx" cols="30">
              <v-item v-slot="{ active, toggle }">
                <v-card
                  :color="active ? 'orange accent-2' : ''"
                  class="d-flex align-center justify-center"
                  height="4vh"
                  v-text="date.d"
                  @click="toggle"
                >
                </v-card>
              </v-item>
            </v-col>
          </v-row>
        </v-container>
      </v-item-group>
      <router-view />
    </v-card-text>
    <v-divider></v-divider>

    <v-container>
      <div class="row text-center">
        <div class="col-md-3 col-sm-6 col-xs-12" v-for="n in 15" :key="n">
          <v-hover v-slot:default="{ hover }">
            <v-card class="mx-auto" color="grey lighten-4" max-width="600">
              <v-img
                class="white--text align-end"
                :aspect-ratio="16 / 9"
                height="200px"
                :src="require('../assets/img/home/slider1.jpg')"
              >
                <v-card-title>카테고리</v-card-title>
                <v-expand-transition>
                  <div
                    v-if="hover"
                    class="d-flex transition-fast-in-fast-out white darken-2 v-card--reveal display-3 white--text"
                    style="height: 100%;"
                  >
                    <v-btn v-if="hover" href="/product" class="" outlined>
                      VIEW
                    </v-btn>
                  </div>
                </v-expand-transition>
              </v-img>
              <v-card-text class="text--primary">
                <div>
                  <a href="/product" style="text-decoration: none">물품명</a>
                </div>
                <div><p>가격</p></div>
              </v-card-text>
            </v-card>
          </v-hover>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

declare interface Dates {
  m: number;
  d: number;
}

export default Vue.extend({
  name: "Auction",

  data: () => ({
    dates: new Array<Dates>()
  }),

  created() {
    for (let plus = 0; plus < 30; plus++) {
      const now = new Date();
      const future = new Date(now.setDate(now.getDate() + plus));
      const date = {
        m: future.getMonth() + 1,
        d: future.getDate()
      };
      this.dates.push(date);
    }
  }
});
</script>

<style></style>
