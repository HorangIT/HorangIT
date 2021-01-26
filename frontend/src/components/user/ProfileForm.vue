<template>
  <v-form v-model="valid">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          md="6"
        >
          <v-text-field
            v-model="nickname"
            :rules="[rules.required, rules.nickname]"
            label="닉네임"
            required
          ></v-text-field>
        </v-col>
        <v-col
          cols="12"
          md="6"
        >
          <v-text-field
            v-model="email"
            :rules="[rules.required, rules.email]"
            label="이메일"
            required
          ></v-text-field>
        </v-col>
        <v-col
          cols="12"
          md="12"
        >
          <v-text-field
            v-model="address"
            :rules="[rules.required, rules.address]"
            label="주소"
            @click="jusoPopup"
            required
          ></v-text-field>
        </v-col>
        <v-btn
          text
          @click="goToMain"
        >
          메인으로 돌아가기
        </v-btn>
        <v-spacer></v-spacer>
        <v-slide-x-reverse-transition>
          <v-tooltip
            v-if="formHasErrors"
            left
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                icon
                class="my-0"
                v-bind="attrs"
                @click="resetForm"
                v-on="on"
              >
                <v-icon>mdi-refresh</v-icon>
              </v-btn>
            </template>
            <span>Refresh form</span>
          </v-tooltip>
        </v-slide-x-reverse-transition>
        <v-btn
          color="primary"
          text
          @click="submit"
        >
          수정하기
        </v-btn>
      </v-row>
    </v-container>
  </v-form>
</template>

<script lang="ts">
import Vue from 'vue';
export default Vue.extend({
  data (): Record<string, any> {
    return {
      valid: false,
      nickname: '',
      email: '',
      address: '',
      rules: {
        required: (v: string) => !!v || '해당 칸을 입력해주세요.',
        nickname: (v: string) => (v.length >= 2 && v.length <= 10) || '닉네임은 2~10자로 설정해주세요.',
        email: (v: string) => /.+@.+/.test(v) || '이메일 형식에 맞게 작성해주세요.',
        // address: 
      }
    }
  },
  methods: {
    submit () {
      console.log('submit')
    },
    jusoPopup () {
      console.log('juso popup')
      // const pop = window.open("./juso.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
      window.open("./juso.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
    },
    goToMain () {
      this.$router.push('/')
    }
  }
})
</script>