<template>
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
        {{ new Date().getFullYear() }} — <strong class="orange--text text--accent-3">호랑IT</strong>
      </v-card-text>
    </v-card>
    </v-footer>
</template>
