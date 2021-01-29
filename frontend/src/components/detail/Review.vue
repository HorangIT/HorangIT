<template>
  <div>
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
        subtitle: "<span class='text--primary'>Ali Connors</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nisl tincidunt eget nullam non. Tincidunt arcu non sodales neque sodales ut etiam. Lectus arcu bibendum at varius vel pharetra. Morbi tristique senectus et netus et malesuada.\n"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
        title: 'Lorem ipsum dolor <span class="grey--text text--lighten-1">4</span>',
        subtitle: "<span class='text--primary'>to Alex, Scott, Jennifer</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
        title: "Lorem ipsum dolor",
        subtitle: "<span class='text--primary'>Sandra Adams</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
        title: "Lorem ipsum dolor",
        subtitle: ""
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
        title: "Lorem ipsum dolor",
        subtitle: "<span class='text--primary'>Britta Holt</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    ]
  })
});
</script>
<style></style>
