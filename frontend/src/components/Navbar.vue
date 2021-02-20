<template>
  <div>
    <v-navigation-drawer v-model="active" fixed temporary>
      <SideNavbar @login="dialogLogin = true" @signup="dialogSignup = true"></SideNavbar>
    </v-navigation-drawer>
    <v-app-bar elevate-on-scroll app color="white" height="130px" id="header">
      <!-- nav bar -->
      <div class="container">
        <!--로그인 유무-->
        <v-row justify="end" class="py-2 d-md-flex d-none">
          <span v-if="login" class="d-flex align-center">
            <span class="d-none d-flex" id="welcome">{{ nickname }} 님, 환영합니다.</span>
              <v-btn icon class="ml-2" to="/myauction">
                <i class="fas fa-gavel"></i>
              </v-btn>
            <v-btn
              :ripple="false"
              plain
              depressed
              class="mr-2 d-none d-flex"
              @click="logout"
              id="logout-btn"
            >
              로그아웃
            </v-btn>
          </span>
          <span v-else class="d-flex align-center">
            <!-- Login dialog -->
            <v-dialog
              v-model="dialogLogin"
              persistent
              max-width="600px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  color=""
                  depressed
                  v-bind="attrs"
                  v-on="on"
                  :ripple="false"
                  plain
                >
                  로그인
                </v-btn>
              </template>
              <LoginForm @close="dialogLogin = false" @goToSignup="dialogLogin = false; dialogSignup = true;"/>
            </v-dialog>
            <!-- Signup dialog -->
            <v-dialog
              v-model="dialogSignup"
              persistent
              max-width="600px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  color=""
                  depressed
                  v-bind="attrs"
                  v-on="on"
                  :ripple="false"
                  plain
                >
                  회원가입
                </v-btn>
              </template>
              <SignupForm @close="dialogSignup = false" @goToLogin="dialogLogin = true; dialogSignup = false;" />
            </v-dialog>
          </span>
        </v-row>
        <!-- end of login -->

        <v-divider class="orange d-md-flex d-none"></v-divider>
        <v-row align="center">
      <!-- hamburger icon -->
      <v-app-bar-nav-icon
        @click.stop="active = !active"
        class="d-flex d-md-none"
      />
          <!-- logo -->
          <v-col cols="4">
            <v-toolbar-title class="ml-3 mt-3">
              <a
                href="/"
                class="d-inline-flex align-center"
                style="text-decoration: none"
              >
                <v-img
                  :src="require('../assets/img/layout/horangit_face.png')"
                  max-width="4rem"
                />
                <p
                  id="logo-font"
                  class="grey--text text--darken-4 mb-0 mx-2 d-sm-inline d-none"
                >
                  호랑it
                </p>
              </a>
            </v-toolbar-title>
          </v-col>

          <!-- search bar -->
          <v-col cols="md-4">
            <v-responsive>
              <v-text-field
                flat
                dense
                filled
                hide-details
                prepend-inner-icon="mdi-magnify"
                placeholder="검색"
                clearable
                color="orange lighten-1"
                v-model="searchData"
                @change="search"
              >
              </v-text-field>
            </v-responsive>
          </v-col>

          <!-- links to pages -->
          <v-col cols="2" class="d-none d-md-flex flex-row-reverse">
            <v-btn
              color=""
              class=""
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
          </v-col>

          <!-- 판매하기 -->
          <v-col cols="2" class="d-none d-md-flex">
            <v-btn
              @click="login ? dialog = true : pleaseLogin = true"
              text
              plain
              :ripple="false"
            >
              <h2>판매하기</h2>
            </v-btn>
            <v-dialog v-model="dialog" max-width="60vw">
              <v-card>
                <PostView @close="dialog = false" />
              </v-card>
            </v-dialog>
            <v-dialog v-model="pleaseLogin" max-width="30vw">
              <v-card>
                <LoginForm @close="pleaseLogin = false" :message="message"/>
              </v-card>
            </v-dialog>
          </v-col>
        </v-row>
      </div>
    </v-app-bar>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import PostView from "../views/PostView.vue";
import LoginForm from "../components/user/LoginForm.vue";
import SignupForm from "../components/user/SignupForm.vue";
import SideNavbar from "../components/SideNavbar.vue";

export default Vue.extend({
  name: "Navbar",

  components: {
    PostView,
    LoginForm,
    SignupForm,
    SideNavbar,
  },
  data: () => ({
    links: [
      {
        name: "경매",
        to: "/auction"
      },
    ],
    // Sidebar active
    active: false,
    nickname: "",
    dialog: false,
    dialogLogin: false,
    pleaseLogin: false,
    dialogSignup: false,
    message: "이 필요한 서비스입니다.🐯",
    searchData: "",
    
    alarmCount: 5,
  }),
  computed: {
    login (): string {
      return this.$store.state.userModule.status.loggedIn;
    },
  },
  watch: {
    login () {
      if (this.login) {
        this.getNickname();
        this.dialogLogin = false;
      }
    }
  },
  mounted (): void {
    if (this.login) {
      this.getNickname();
    };
  },
  methods: {
    logout (): void {
      this.$store.dispatch("userModule/logout");
      this.$router.push({ path: '/' });
    },
    getNickname (): void {
      const user = JSON.parse(localStorage.getItem("user") || "{}");
      this.nickname = user.object.user.nickname;
    },
    search () {
      this.$store.dispatch("auctionModule/search", this.searchData);
      this.searchData = "";
      if (this.$router.currentRoute.name !== "Auction") {
        this.$router.push({ name: "Auction" });
      };
    }
  }
});
</script>

<style scoped>
/* 현재 있는 곳 스타일 효과 */
[aria-current] {
  color: #ff9100;
}
/* 메뉴에 마우스 올렸을 때 스타일 효과 */
.v-btn:hover {
  color: #ff9100;
}
#header {
  border-bottom: 1px solid rgb(233, 231, 229) !important;
}

#logo-font {
  font-family: "CookieRunOTF-Bold";
  font-size: 1.7em;
}

#login-bar {
  border-bottom: 1px solid rgb(233, 229, 229) !important;
}

#app > div.v-dialog__content.v-dialog__content--active {
  background-color: rgba(0, 0, 0, 0.767);
}

#welcome, #logout-btn {
  font-family: 'ELAND_Choice_L';
  font-size: 15px;
}

</style>