<template>
  <v-hover v-slot:default="{ hover }">
    <v-card
      class="mx-auto"
      :color="!isOver ? 'grey lighten-4' : 'grey darken-2'"
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
      <p v-if="isOver" class="grey darken-2 grey--text text--lighten-4">경매 종료되었습니다.</p>
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
    isOver: false,
    beforeAuction: false,
  }),
  methods: {
    goDetail(id: any) {
      this.$router.push({ name: 'Detail', params: { id: id} });
    }
  },
  created() {
    this.startDate = moment(this.item.startDate).format('YY[/]MM[/]DD HH:mm');
    // 경매 시작 전/종료 후 확인
    const now = new Date()
    if (new Date(this.item.endDate) < now) {
      this.isOver = true;
    } else if (new Date(this.item.startDate) > now) {
      this.beforeAuction = true;
    }
    if (Number(this.item.status) !== 0) {
      this.isOver = true;
    }
    // console.log(this.isOver)
    // console.log(this.beforeAuction)
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

.text-over {width:100px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; }
</style>
