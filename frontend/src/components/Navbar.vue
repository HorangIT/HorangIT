<template>
  <v-app-bar elevate-on-scroll app color="white" height="90px">
    <v-app-bar-nav-icon
      @click.stop="active = !active"
      class="d-flex d-md-none"
    />
    <v-toolbar-title class="pa-0 ml-2 mr-5">
      <a
        href="/"
        class="d-flex align-center"
        style="text-decoration: none"
      >
        <v-img
          :src="require('../assets/img/layout/horangit_face.png')"
          max-width="4rem"
        >
        </v-img>
        <p class="grey--text text--darken-4 mb-0 mx-2 d-none d-md-inline"><strong>호랑it</strong></p>
      </a>
    </v-toolbar-title>
    <v-btn
      color=""
      class="d-none d-md-flex"
      large
      v-for="(link, idx) in links"
      :key="idx"
      text
      :to="link.to"
    >
      <h2>{{ link.name }}</h2>
    </v-btn>
    <v-spacer></v-spacer>
    <v-responsive max-width="100%">
      <v-text-field
        flat
        dense
        rounded
        filled  
        hide-details
        prepend-inner-icon="mdi-magnify"
        placeholder="검색"
        class="mx-2"
        clearable
        color="orange lighten-1"
      >
      </v-text-field>
    </v-responsive>
    <!--로그인 유무-->
    <div class="d-none d-md-flex">
      <span v-if="login" class="d-flex align-center">
        <span class="d-none d-md-flex">{{ nickname }}님, 환영합니다.</span>
        <v-menu transition="scroll-y-transition">
          <template v-slot:activator="{ on }">
            <v-btn v-on="on" icon class="mx-1">
              <v-badge content="2" value="2" color="orange" overlap>
                <v-icon>mdi-bell</v-icon>
              </v-badge>
            </v-btn>
          </template>
          <v-list>
            <v-list-item v-for="n in 5" :key="n" link>
              <v-list-item-title v-text="'alarm ' + n"></v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
        <!-- 장바구니 삭제 -->
        <!-- <v-btn to="/cart" icon class="mx-1 d-none d-md-flex">
          <v-badge content="2" value="2" color="orange" overlap>
            <v-icon>mdi-cart</v-icon>
          </v-badge>
        </v-btn> -->
        <v-btn
          color="white"
          depressed
          class="mx-2 d-none d-md-flex"
          @click="logout"
        >
          로그아웃
        </v-btn>
      </span>
      <span v-else class="d-flex align-center">
        <AuthModal :purpose="'login'" />
        <AuthModal :purpose="'signup'" />
      </span>
    </div>
  </v-app-bar>
</template>

<script lang="ts">
import Vue from "vue";
import AuthModal from "../components/user/AuthModal.vue";

export default Vue.extend({
  name: "Layout",

  components: {
    AuthModal,
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
    on: true,
    active: false,
    // modal: false,
    // loginOrSignup: true,
    nickname: "",
    dialog: false,
  }),
  // computed: {
  //   login() {
  //     return this.$store.state.userModule.status.loggedIn;
  //   }
  // },
  created() {
    if (localStorage.getItem("user")) {
      const user = JSON.parse(localStorage.getItem("user") || "{}");
      this.nickname = user.object.user.nickname;
    }
  },
  methods: {
    logout() {
      this.$store.dispatch("userModule/logout");
    }
  }
});
</script>

<style scoped>
  [aria-current] {
    color: #FF9100;
  }
  .v-btn:hover {
    color: #FF9100;
  }
</style>