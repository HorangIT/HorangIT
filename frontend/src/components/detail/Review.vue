<template>
  <div class="chatScroll" style="height: 30vh; overflow: auto;">
    <v-list three-line="true" avatar="true">
      <v-list-item-group v-model="item" color="primary">
        <v-list-item v-for="(item, i) in items" :key="i">
          <v-list-item-avatar>
            <v-img :src="item.avatar"></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title v-html="item.title">
            </v-list-item-title>
            <v-rating
              v-model="rating"
              class=""
              background-color="warning lighten-3"
              color="warning"
              dense
            >
            </v-rating>
            <v-list-item-subtitle v-html="item.subtitle">
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "Product",

  data: () => ({
    rating: 4.5,
    active: false,
    breadcrums: [
      {
        text: "Home",
        disabled: false,
        href: "breadcrumbs_home"
      },
      {
        text: "Clothing",
        disabled: false,
        href: "breadcrumbs_clothing"
      },
      {
        text: "T-Shirts",
        disabled: true,
        href: "breadcrumbs_shirts"
      }
    ],
    item: [],
    items: [
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
        title: "Lorem ipsum dolor?",
        subtitle: "Lorem ipsum dolor sit amet"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
        title: "Lorem ipsum dolor sit amet",
        subtitle: "Lorem ipsum dolor sit amet"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
        title: "Lorem ipsum dolor",
        subtitle: "Lorem ipsum dolor sit amet"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
        title: "Lorem ipsum dolor",
        subtitle: "Lorem ipsum dolor sit amet"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
        title: "Lorem ipsum dolor",
        subtitle: "Lorem ipsum dolor sit amet"
      }
    ]
  })
});
</script>
<style></style>
