<template>
  <v-card>
    <div class="container d-flex">
      <h2>필터</h2>
      <v-spacer></v-spacer>
      <v-btn icon color="orange" @click="reset"><v-icon>mdi-cached</v-icon></v-btn>
    </div>
    <v-divider></v-divider>
    <v-card-text>
      <v-checkbox
        v-model="filters.status"
        label="현재 진행중인 경매만 보기"
        color="orange darken-3"
        class="ma-0"
      ></v-checkbox>
      <h3 class="mb-5">지역</h3>
      <div class="d-flex">
        <v-select
          :items="Si"
          v-model="filters.si"
          @input="getGu"
          class="mr-1 pa-0"
          label="시/도"
          color="grey"
          item-color="orange"
        >
        </v-select>
        <v-select
          :items="Gu"
          v-model="filters.gu"
          class="ml-1 pa-0"
          label="시/군/구"
          color="grey"
          item-color="orange"
        >
        </v-select>
      </div>
      <h3 class="mb-3">카테고리</h3>
      <v-select
        v-model="filters.category"
        :items="Category"
        attach
        chips
        label="Category"
        multiple
        color="grey"
        item-color="orange"
      >
      </v-select> 
      <h3 class="mt-2">물품 등급</h3>
      <v-container class="pa-0 d-flex" fluid>
        <v-checkbox v-model="filters.grade" label="S" value="S" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grade" label="A" value="A" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grade" label="B" value="B" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grade" label="C" value="C" class="mx-2" color="orange darken-3"></v-checkbox>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import { AxiosResponse } from "axios";
import Vue from "vue";
import { itemApi } from "../../utils/axios";

export default Vue.extend({
  name: "Filters",

  props: {
    filters: Object
  },
  data: () => ({
    Si: [],
    Gu: [],
    Category: ["전자기기","의류","미용","잡화","도서"]
  }),
  methods: {
    getGu(data: String) {
      // 구 찾기
      itemApi.search(this.filters.si).then((res: AxiosResponse) => {
        this.Gu = res.data.object.gungu;
      })
    },
    reset() {
      this.$emit("reset");
    },
  },
  watch: {
    //필터의 값이 바뀌면 바뀐값을 store에 저장하고 필터링액션을 상위컴포넌트에 전달합니다.
    filters: {
      deep: true,
      handler() {
        this.$store.dispatch("auctionModule/setFilter", this.filters);
        this.$store.dispatch("auctionModule/setPage", 1);
        this.$emit("filtering");
      }
    },
  },
  mounted() {
    // 시 정보 가져오기 
    itemApi.search(null).then((res: AxiosResponse) => {
      this.Si = res.data.object.districts;
    })
    // 구 정보 가져오기
    itemApi.search(this.filters.si).then((res: AxiosResponse) => {
      this.Gu = res.data.object.gungu;
    })
  }
});
</script>
<style></style>
