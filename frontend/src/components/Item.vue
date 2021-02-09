<template>
  <v-hover v-slot:default="{ hover }">
    <v-card class="mx-auto" color="grey lighten-4" max-width="600">
      <v-img
        class="white--text align-end"
        :aspect-ratio="16 / 9"
        height="200px"
        :src="pro.image"
      >
        <v-card-title>{{ pro.type }} </v-card-title>
        <v-expand-transition>
          <div
            v-if="hover"
            class="d-flex transition-fast-in-fast-out white darken-2 v-card--reveal display-3 white--text"
            style="height: 100%;"
          >
            <v-btn v-if="hover" to="/detail" class="" outlined>
              경매 참가
            </v-btn>
          </div>
        </v-expand-transition>
      </v-img>
      <v-card-text class="text--primary">
        <div>
          <a href="/detail" style="text-decoration: none">
            {{ pro.name }}
          </a>
        </div>
        <div>{{ pro.category }}</div>
        <div>{{ pro.startPrice }} 원</div>
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "Item",
  props: {
    pro: Object
  },
  data: () => ({})
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
