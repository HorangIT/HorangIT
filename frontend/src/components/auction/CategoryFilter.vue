<template>
  <div>
    <v-card-title>Category</v-card-title>
    <template>
      <v-treeview
        :items="items"
        :selected-color="'#fff'"
        activatable
        open-on-click
        dense
      >
      </v-treeview>
    </template>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "CategoryFilter",

  data: () => ({
    items: [
      {
        id: 2,
        name: "Shoes",
        children: [
          { id: 2, name: "Casuals" },
          { id: 3, name: "Formals" },
          { id: 4, name: "Sneakers" }
        ]
      },
      {
        id: 1,
        name: "Clothing",
        children: [
          { id: 5, name: "Shirts" },
          { id: 6, name: "Tops" },
          { id: 7, name: "Tunics" },
          { id: 8, name: "Bodysuit" }
        ]
      }
    ]
  })
});
</script>
<style></style>
