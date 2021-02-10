<template>
  <v-hover v-slot:default="{ hover }">
    <v-card
      class="mx-auto"
      color="grey lighten-4"
      max-width="600"
      :elevation="hover ? 12 : 2"
      :class="{ 'on-hover': hover }"
      @click="goDetail(item.itemId)"
      >
      <v-img
        :aspect-ratio="16 / 9"
        height="200px"
        :src="item.image"
      >
      </v-img>
      <div>
        <h2 class="my-1">{{ item.name }}</h2>
        <p class="text-left mx-2 mb-0">경매: {{ startDate }}</p>
        <p class="text-left mx-2 mb-0">등급: {{ item.grade }}</p>
        <p class="text-left mx-2 mb-0">카테고리: {{ item.category }}</p>
      </div>
    </v-card>
  </v-hover>
</template>

<script lang="ts">
import Vue from "vue";
import moment from "moment";

export default Vue.extend({
  name: "Item",
  props: {
    item: Object
  },
  data: () => ({
    startDate: "",
  }),
  methods: {
    goDetail(id: any) {
      this.$router.push({ name: 'Detail', params: { id: id} });
    }
  },
  created() {
    this.startDate = moment(this.item.startDate).format('YY[/]MM[/]DD HH:mm');
  }
});
</script>

<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .5;
  position: absolute;
  width: 100%;
}
</style>
