<template>
  <v-list two-line>
    <v-list-item-group
      v-model="selected"
      active-class="orange--text"
      multiple
    >
      <template v-for="(item, index) in items">
        <v-list-item :key="item.itemTitle">
          <template>
            <v-list-item-content>
              <v-list-item-title v-text="item.itemTitle"></v-list-item-title>
              <v-list-item-subtitle
                class="text--primary"
                v-text="item.message"
              ></v-list-item-subtitle>
              <v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-action>
              <v-list-item-action-text v-text="item.action"></v-list-item-action-text>
            </v-list-item-action>
          </template>
        </v-list-item>

        <v-divider
          v-if="index < items.length - 1"
          :key="index"
        ></v-divider>
      </template>
    </v-list-item-group>
  </v-list>
</template>

<script lang="ts">
  export default {
    data: () => ({
      selected: [0, 1, 2],
      items: [
        {
          itemTitle: '전자기기1',
          message: '등록하신 상품이 낙찰되었습니다.'
        },
        {
          itemTitle: '의류1',
          message: '축하합니다. 낙찰에 성공했습니다.'
        },
        {
          itemTitle: '전자기기2',
          message: '축하합니다. 낙찰에 성공했습니다.'
        }
      ],
    }),
  }
</script>