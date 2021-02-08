<template>
  <v-card>
    <v-card-title>
      필터
    </v-card-title>
    <v-divider></v-divider>
    <v-card-text>
      <v-checkbox
        v-model="filters.check"
        label="지난 경매 보기"
        color="orange darken-3"
        class="ma-0"
      ></v-checkbox>
      <p>시작가</p>
      <div class="d-flex">
        <v-text-field
          label="최소 금액"
          v-model="filters.minPrice"
          outlined
          dense
          class="mr-2"
        >
        </v-text-field>  
        <v-text-field
          label="최대 금액"
          v-model="filters.maxPrice"
          outlined
          dense
          class="ml-2"
        >
        </v-text-field>
      </div>
      <p>현재가</p>
      <div class="d-flex">
        <v-text-field
          label="최소 금액"
          v-model="filters.minPrice"
          outlined
          dense
          class="mr-2"
        >
        </v-text-field>  
        <v-text-field
          label="최대 금액"
          v-model="filters.maxPrice"
          outlined
          dense
          class="ml-2"
        >
        </v-text-field>
      </div>
      <p class="ma-0">물품 등급</p>
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
import Vue from "vue";

export default Vue.extend({
  name: "Filters",

  data: () => ({
    filters: {
      check: false,
      minPrice: 0,
      maxPrice: 10000000000,
      grades: [],
    }
  }),
  watch: {
    filters: {
      deep: true,
      handler() {
          this.$emit("search", this.filters);
        }
      }
    }
});
</script>
<style></style>
