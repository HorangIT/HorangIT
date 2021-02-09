<template>
  <v-card>
    <v-card-title>
      필터
    </v-card-title>
    <v-divider></v-divider>
    <v-card-text>
      <v-checkbox
        v-model="filters.check"
        label="현재 진행중인 경매만 보기"
        color="orange darken-3"
        class="ma-0"
      ></v-checkbox>
      <p>지역</p>
      <div class="d-flex">
        <v-select
          :items="Si"
          @input="getSi"
          class="mr-1"
          label="시/도"
        ></v-select>
        <v-select
          :items="Gu"
          @input="getGu"
          class="ml-1"
          label="시/군/구"
        ></v-select>
      </div>
      <p>시작가</p>
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
      <p class="ma-0">물품 등급</p>
      <v-container class="pa-0 d-flex" fluid>
        <v-checkbox v-model="filters.grades" label="S" value="S" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grades" label="A" value="A" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grades" label="B" value="B" class="mx-2" color="orange darken-3"></v-checkbox>
        <v-checkbox v-model="filters.grades" label="C" value="C" class="mx-2" color="orange darken-3"></v-checkbox>
      </v-container>
    </v-card-text>
    {{filters}}
  </v-card>
</template>

<script lang="ts">
import { AxiosResponse } from "axios";
import Vue from "vue";
import { itemApi } from "../../utils/axios";

export default Vue.extend({
  name: "Filters",

  data: () => ({
    filters: {
      check: false,
      minPrice: 0,
      maxPrice: 100000000,
      grades: [],
      location: new Array<String>()
    },
    Si: [],
    Gu: []
  }),
  methods: {
    getSi(data: String) {
      this.filters.location.length = 0
      this.filters.location.push(data);
      this.$emit("filtering", this.filters);
      // 구 찾기
      const districtName = data;
      itemApi.search(districtName).then((res: AxiosResponse) => {
        this.Gu = res.data.object.gungu;
      })
    },
    getGu(data: String) {
      this.filters.location.push(data);
      console.log(this.filters.location)
    }
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
    itemApi.search(null).then((res: AxiosResponse) => {
      this.Si = res.data.object.districts;
    })
  }
    
});
</script>
<style></style>
