<template>
  <div id="home">
    <v-carousel hide-delimiters cycle height="500">
      <v-carousel-item :src="require('../assets/img/home/slider4.jpg')" class="pa-4">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>🔥HOT🔥</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item :src="require('../assets/img/home/slider2.jpg')" class="pa-4">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Comming Soon</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item :src="require('../assets/img/home/slider3.jpg')" class="pa-4">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>NEW</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item :src="require('../assets/img/home/slider1.jpg')" class="pa-4">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Upto 60% + Extra 10%</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
    </v-carousel>

    <v-card-text tile outlined>
      <v-card-title class="subheading ">
        <strong>오늘의 경매</strong>
      </v-card-title>
      <v-slide-group class="pa-4" show-arrows>
        <v-slide-item v-for="n in 20" :key="n" v-slot="{ toggle }">
          <v-hover v-slot:default="{ hover }">
            <v-card
              color="grey lighten-5"
              :elevation="hover ? 10 : 0"
              class="ma-4"
              height="40vh"
              width="20vw"
              @click="toggle"
            >
              <v-img
                class="white--text align-center"
                height="60%"
                :src="require('../assets/img/home/deal1.jpg')"
              >
                <h1 class="text-center font-size">Lorem & Ipsum</h1>
              </v-img>
              <v-card-text class="text--primary text-left">
                <h2>고양이 저금통</h2>
                <br />
                <div>경매시간 21.02.28</div>
                <div>경매가 10,000 ~ 30,000</div>
              </v-card-text>
            </v-card>
          </v-hover>
        </v-slide-item>
      </v-slide-group>
    </v-card-text>
    <v-divider></v-divider>

    <v-card-text tile outlined>
      <v-card-title class="subheading ">
        <strong>추천 경매</strong>
      </v-card-title>
      <v-slide-group class="pa-4" show-arrows>
        <v-slide-item v-for="n in 20" :key="n" v-slot="{ toggle }">
          <v-hover v-slot:default="{ hover }">
            <v-card
              color="grey lighten-5"
              :elevation="hover ? 10 : 0"
              class="ma-4"
              height="40vh"
              width="20vw"
              @click="toggle"
            >
              <v-img
                class="white--text align-center"
                height="60%"
                :src="require('../assets/img/home/deal2.jpg')"
              >
                <h1 class="text-center font-size">Lorem & Ipsum</h1>
              </v-img>

              <v-card-text class="text--primary text-left">
                <h2>내가 그린 기린 그림</h2>
                <br />
                <div>경매시간 21.02.28</div>
                <div>경매가 100,000 ~ 500,000</div>
              </v-card-text>
            </v-card>
          </v-hover>
        </v-slide-item>
      </v-slide-group>
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
            <h1 class="text-center font-size">New Arrivals</h1>
            <div class="text-center">
              <v-btn href="/shop" class="white--text" outlined>SHOP NOW</v-btn>
            </div>
          </v-img>
        </v-card>
      </div>
    </div>
    <div class="pl-4 pr-4 row">
      <div class="col-md-4 col-sm-4 col-xs-12">
        <v-card outlined>
          <v-img
            :src="require('../assets/img/home/deal2.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="300px"
          >
            <h1 class="text-center font-size">Lorem & Ipsum</h1>
            <div class="text-center mt-2">
              <v-btn class="white--text caption" href="/shop" text>
                SHOP NOW
                <v-icon class="white--text caption">mdi-arrow-right</v-icon>
              </v-btn>
            </div>
          </v-img>
        </v-card>
      </div>
      <div class="col-md-4 col-sm-4 col-xs-12">
        <v-card outlined>
          <v-img
            :src="require('../assets/img/home/deal3.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="300px"
          >
            <h1 class="text-center font-size">Lorem & Ipsum</h1>
            <div class="text-center mt-2">
              <v-btn class="white--text caption" href="/shop" text>
                SHOP NOW
                <v-icon class="white--text caption">mdi-arrow-right</v-icon>
              </v-btn>
            </div>
          </v-img>
        </v-card>
      </div>
      <div class="col-md-4 col-sm-4 col-xs-12">
        <v-card outlined>
          <v-img
            :src="require('../assets/img/home/deal4.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="300px"
          >
            <h1 class="text-center font-size">Lorem & Ipsum</h1>
            <div class="text-center mt-2">
              <v-btn class="white--text caption" href="/shop" text>
                SHOP NOW
                <v-icon class="white--text caption">mdi-arrow-right</v-icon>
              </v-btn>
            </div>
          </v-img>
        </v-card>
      </div>
    </div>

    <br /><br /><br /><br />
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "Home",

  data: () => ({
    items: [
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me 2" }
    ],
    activeBtn: 1,
    colors: [
      "indigo",
      "warning",
      "pink darken-2",
      "red lighten-1",
      "deep-purple accent-4"
    ],
    slides: ["First", "Second", "Third", "Fourth", "Fifth"]
  })
});
</script>

<style></style>
