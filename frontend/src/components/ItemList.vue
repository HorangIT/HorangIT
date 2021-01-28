<!--
ItemList 컴포넌트입니다 
상위컴포넌트에서 데이터를 넘겨주어 (20개 추천) 사용하면 됩니다.
-->
<template>
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
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "ItemList",
  data: () => ({}),
});
</script>

<style></style>
