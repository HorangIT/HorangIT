<template>
  <v-app id="inspire">
    <v-app-bar app color="orange accent-3" dark height="90vh">
      <v-app-bar-nav-icon
        @click.stop="active = !active"
        class="d-flex d-md-none"
      />
      <v-toolbar-title class="pa-0 ml-2 mr-5">
        <a
          href="/"
          class="white--text d-flex align-center"
          style="text-decoration: none"
        >
          <v-img
            :src="require('../assets/img/layout/horangit_face.png')"
            max-width="4rem"
          >
          </v-img>
          <p class="mb-0 mx-2 d-none d-md-inline">호랑it</p>
        </a>
      </v-toolbar-title>
      <v-responsive max-width="150px">
        <v-text-field
          flat
          dense
          rounded
          solo-inverted
          hide-details
          prepend-inner-icon="mdi-magnify"
          label="검색"
          class="mx-2"
        >
        </v-text-field>
      </v-responsive>
      <v-btn
        class="d-none d-md-flex"
        large
        v-for="(link, idx) in links"
        :key="idx"
        text
        :href="link.href"
      >
        <h2>{{ link.name }}</h2>
      </v-btn>
      <v-spacer></v-spacer>
      <!--로그인 유무-->
      <span v-if="login" class="d-flex align-center">
        <h3 class="d-none d-md-flex">{{ nickname }}</h3>
        <v-menu transition="scroll-y-transition">
          <template v-slot:activator="{ on }">
            <v-btn v-on="on" icon class="mx-1">
              <v-badge content="2" value="2" color="red" overlap>
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
        <v-btn href="/cart" icon class="mx-1 d-none d-md-flex">
          <v-badge content="2" value="2" color="green" overlap>
            <v-icon>mdi-cart</v-icon>
          </v-badge>
        </v-btn>
        <v-btn
          outlined
          rounded
          color="white"
          class="mx-2 d-none d-md-flex"
          @click="logout"
        >
          로그아웃
        </v-btn>
      </span>
      <span v-else class="d-flex align-center">
        <v-btn
          outlined
          rounded
          color="white"
          class="mx-1 ml-2 d-none d-md-flex"
          @click="openModal(true)"
        >
          로그인
        </v-btn>
        <v-btn
          outlined
          rounded
          color="white"
          class="mx-2 d-none d-md-flex"
          @click="openModal(false)"
        >
          회원가입
        </v-btn>
      </span>
    </v-app-bar>
    <v-navigation-drawer v-model="active" absolute temporary>
      <SideNavbar @loginOrSignup="openModal(loginOrSignup)"></SideNavbar>
    </v-navigation-drawer>
    <LoginModal @close="closeModal" v-if="modal" :loginOrSignup="loginOrSignup">
    </LoginModal>
    <div style="height:90px;"></div>
    <router-view />

    <v-footer :padless="true">
      <v-card flat tile width="100%" class="secondary white--text text-center">
        <v-card-text>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-home</v-icon>
          </v-btn>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-email</v-icon>
          </v-btn>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-calendar</v-icon>
          </v-btn>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-delete</v-icon>
          </v-btn>
        </v-card-text>

        <v-card-text class="white--text pt-0">
          서비스이용약관 | 온라인경매약관 | 경매서비스운영방침 | 스토어 이용약관
          | 개인정보취급방침 | 이메일무단수집거부
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="white--text">
          {{ new Date().getFullYear() }} — <strong>호랑IT</strong>
        </v-card-text>
      </v-card>
    </v-footer>
  </v-app>
</template>
<script lang="ts">
import Vue from "vue";
import LoginModal from "../components/user/LoginModal.vue";
import SideNavbar from "../components/SideNavbar.vue";

export default Vue.extend({
  name: "Layout",

  components: {
    LoginModal,
    SideNavbar
  },

  data: () => ({
    links: [
      {
        name: "홈",
        href: "/"
      },
      {
        name: "경매",
        href: "/auction"
      },
      {
        name: "서비스 소개",
        href: "#"
      },
      {
        name: "고객센터",
        href: "/cs"
      }
    ],
    on: true,
    active: false,
    modal: false,
    loginOrSignup: true,
    nickname: ""
  }),
  computed: {
    login() {
      return this.$store.state.userModule.status.loggedIn;
    }
  },
  created() {
    const user = JSON.parse(localStorage.getItem("user") || "{}");
    this.nickname = user.object.user.nickname;
    // console.log(process.env.VUE_APP_JUSO_API_KEY);
  },
  watch: {
    login() {
      this.modal = false;
    }
  },
  methods: {
    openModal(loginOrSignup: boolean) {
      this.loginOrSignup = loginOrSignup;
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
    logout() {
      this.$store.dispatch("userModule/logout");
    }
  }
});
</script>
<style></style>
