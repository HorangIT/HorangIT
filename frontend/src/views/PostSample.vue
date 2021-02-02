<template>
  <div>
    <v-container>
      <p class="display-3 font-weight-light	text-center pa-4">
        Best brands for fashion
      </p>
      <p class="overline text-center">By <b>John Doe</b> | January 24,2020</p>
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <v-img
            class="white--text align-end"
            height="400px"
            :src="require('../assets/img/home/slider2.jpg')"
          ></v-img>
          <p class="display-1 mb-0 pt-5">Lorem ipsum</p>
          <p class="pt-5 subtitle-1 font-weight-light">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          </p>
          <ul class="subtitle-1 font-weight-light">
            <li>
              Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
            </li>
            <li>
              Aliquam tincidunt mauris eu risus.
            </li>
          </ul>
          <p class="subtitle-1 font-weight-light pt-5">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          </p>
          <v-img
            class="white--text align-end"
            height="400px"
            :src="require('../assets/img/home/slider3.jpg')"
          ></v-img>
          <p class="display-1 mb-0 pt-5">Lorem ipsum</p>
          <p class="subtitle-1 font-weight-light pt-5">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          </p>
          <p class="subtitle-1 font-weight-light">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          </p>
          <p class="body-1	font-weight-light pt-10"><b>3 COMMENTS</b></p>
          <v-list three-line="true" avatar="true">
            <v-list-item-group v-model="item" color="primary">
              <v-list-item v-for="(item, i) in items" :key="i" inactive="true">
                <v-list-item-avatar>
                  <v-img :src="item.avatar"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title v-html="item.title"></v-list-item-title>
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
      </div>
    </v-container>
  </div>
</template>
<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "Post",

  data: () => ({
    rating: 4.5,
    item: 5,
    items: [
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
        title: "Lorem ipsum dolor?",
        subtitle: "Lorem ipsum dolor sit amet"
      },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
        title: "Lorem ipsum dolor",
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
