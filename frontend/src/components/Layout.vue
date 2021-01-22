<template>
  <v-app id="inspire">
    <v-app-bar app color="orange accent-3" dark>
      <v-app-bar-nav-icon @click.stop="active = !active" />
      <v-toolbar-title>
        <a href="/" class="white--text" style="text-decoration: none">
          <v-img></v-img>&nbsp;호랑IT
        </a>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-text-field
        flat
        solo-inverted
        hide-details
        prepend-inner-icon="mdi-magnify"
        label="구매하고 싶은 물품을 검색해보세요"
        class="hidden-sm-and-down"
      />
      <v-spacer></v-spacer>
      <!--로그인 유무-->
      <span v-if="login">
        <v-btn icon>
          <v-icon>mdi-account-circle</v-icon>
        </v-btn>
        <v-menu transition="scroll-y-transition">
          <template v-slot:activator="{ on }">
            <v-btn v-on="on" icon>
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

        <v-btn v-on="on" href="/cart" icon>
          <v-badge content="2" value="2" color="green" overlap>
            <v-icon>mdi-cart</v-icon>
          </v-badge>
        </v-btn>
        <v-btn outlined rounded color="white" class="ml-2">
          로그아웃
        </v-btn>
      </span>
      <span v-else>
        <v-btn outlined rounded color="white" @click="openModal">
          로그인
        </v-btn>
      </span>
    </v-app-bar>
    <div style="height:42px;"></div>
    <v-expand-transition>
      <v-app-bar
        color="grey lighten-4"
        v-if="active"
        class="d-flex justify-center"
        flat
        style="position:sticky; top:63px; z-index:100;"
      >
        <div id="nav" class="d-flex justify-space-around">
          <v-btn text href="/">홈</v-btn>
          <v-btn text href="/#t">경매</v-btn>
          <v-btn text href="/shop">스토어</v-btn>
          <v-btn text href="#">서비스 소개</v-btn>
          <v-btn text href="/blog">고객센터</v-btn>
        </div>
      </v-app-bar>
    </v-expand-transition>

    <Login @close="closeModal" v-if="modal"> </Login>
    
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
        서비스이용약관 | 온라인경매약관 | 경매서비스운영방침 | 스토어 이용약관 | 개인정보취급방침 | 이메일무단수집거부 
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
import Login from "../components/Login.vue";

export default Vue.extend({
  name: "Layout",

  components: {
    Login,
  },

  data: () => ({
    on: true,
    active: false,
    login: false,
    modal: false
  }),

  methods: {
    openModal() {
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
  }
});
</script>
<style>
#nav {
  width: 35vw;
}
#nav > a {
  font-size: 1vw;
}
</style>
