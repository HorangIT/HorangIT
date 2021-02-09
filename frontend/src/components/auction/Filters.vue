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
        v-model="filters.check"
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
      <h3 class="mb-5">시작가</h3>
      <div class="d-flex">
        <v-text-field
          label="최소 금액"
          v-model="filters.minPrice"
          outlined
          dense
          class="mr-2"
          suffix="원"
          color="orange"
        >
        </v-text-field>  
        <v-text-field
          label="최대 금액"
          v-model="filters.maxPrice"
          outlined
          dense
          class="ml-2"
          suffix="원"
          color="orange"
        >
        </v-text-field>
      </div>
      <!-- <p>현재가</p>
      <div class="d-flex">
        <v-text-field
          label="최소 금액"
          v-model="filters.minPrice"
          outlined
          dense
          class="mr-2"
          suffix="원"
        >
        </v-text-field>  
        <v-text-field
          label="최대 금액"
          v-model="filters.maxPrice"
          outlined
          dense
          class="ml-2"
          suffix="원"
        >
        </v-text-field>
      </div> -->
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
        <v-checkbox v-model="filters.grades" label="S" value="S" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grades" label="A" value="A" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grades" label="B" value="B" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grades" label="C" value="C" class="mx-2" color="orange darken-3"></v-checkbox>
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
    filters: {
      deep: true,
      handler() {
        this.$emit("filtering", this.filters);
      }
    },
  },
  mounted() {
    // 시 정보 가져오기 
    itemApi.search(null).then((res: AxiosResponse) => {
      this.Si = res.data.object.districts;
    })
    itemApi.search(this.filters.si).then((res: AxiosResponse) => {
      this.Gu = res.data.object.gungu;
    })
    // 초기 필터정보 세션스토리지에 저장
    sessionStorage.setItem("filters", JSON.stringify(this.filters));
  }
});
</script>
<style></style>
