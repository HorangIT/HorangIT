<template>
  <v-app-bar elevate-on-scroll app color="white" height="90px" id="header" class="px-10">
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
        <p id="logo-font" class="grey--text text--darken-4 mb-0 mx-2 d-none d-md-inline">호랑it</p>
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
      plain
      :ripple="false"
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
          :ripple="false"
          plain
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
  /* 현재 있는 곳 스타일 효과 */
  [aria-current] {
    color: #FF9100;
  }
  /* 메뉴에 마우스 올렸을 때 스타일 효과 */
  .v-btn:hover {
    color: #FF9100;
  }
  #header {
    border-bottom: 1px solid rgb(233, 229, 229) !important;
  }

  #logo-font {
    font-family: 'CookieRunOTF-Bold';
    font-size: 1.7em;
  }
</style>