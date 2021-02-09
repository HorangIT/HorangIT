<template>
  <div>
    <v-list-item>
      <v-list-item-content>
        <span v-if="login" class="d-flex">
          <v-list-item-title class="title">
            {{ nickname }}
          </v-list-item-title>
          <v-spacer></v-spacer>
          <v-btn outlined rounded color="orange" class="mr-1" @click="logout">
            로그아웃
          </v-btn>
        </span>

        <span v-else class="d-flex">
          <v-btn
            outlined
            rounded
            color="orange"
            class="mx-2"
            @click="$emit('login')"
          >
            로그인
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
            outlined
            rounded
            color="orange"
            class="mx-2"
            @click="$emit('signup')"
          >
            회원가입
          </v-btn>
        </span>
      </v-list-item-content>
    </v-list-item>
    <v-divider></v-divider>
    <v-list nav dense>
      <v-list-item-group active-class="orange--text text--accent-4">
        <v-list-item
          large
          v-for="(link, idx) in links"
          :key="idx"
          text
          :to="link.to"
        >
          <v-list-item-title>{{ link.name }}</v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </div>
</template>
<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "SideNavbar",

  computed: {
    login() {
      return this.$store.state.userModule.status.loggedIn;
    }
  },

  data: () => ({
    links: [
      {
        name: "홈",
        to: "/"
      },
      {
        name: "경매",
        to: "/auction"
      },
    ],
    nickname: ""
  }),
  methods: {
    logout() {
      this.$store.dispatch("userModule/logout");
    }
  },
  created() {
    if (localStorage.getItem("user")) {
      const user = JSON.parse(localStorage.getItem("user") || "{}");
      this.nickname = user.object.user.nickname;
    }
  }
});
</script>
<style></style>
