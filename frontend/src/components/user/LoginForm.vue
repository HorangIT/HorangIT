<template>
  <!-- <v-row justify="center">
    <v-col cols="10"> -->
      <v-card ref="form">
        <v-card-title>로그인</v-card-title>
        <v-card-text>
          <v-text-field
            ref="email"
            color="orange accent-3"
            v-model="email"
            :rules="[rules.required, rules.email]"
            :error-messages="errorMessages"
            label="이메일"
            placeholder="hoit_hippo@gmail.com"
            required
          ></v-text-field>
          <v-text-field
            ref="password"
            color="orange accent-3"
            v-model="password"
            :rules="[rules.required, rules.password]"
            :error-messages="errorMessages"
            label="비밀번호"
            placeholder="******** (문자/숫자/특수문자 포함 8~15자리)"
            :append-icon="passwordShow ? 'mdi-eye': 'mdi-eye-off'"
            :type="passwordShow ? 'text' : 'password'"
            @click:append="passwordShow = !passwordShow"
            @keypress.enter="submit"
            required
          ></v-text-field>
        </v-card-text>
        <v-divider class="mt-12"></v-divider>
        <v-card-actions>
          <v-btn
            :ripple="false"
            plain
            text
            @click="goToSignup"
          >
            회원이 아니신가요?
          </v-btn>
          <v-spacer></v-spacer>
          <v-slide-x-reverse-transition>
            <v-tooltip
              v-if="formHasErrors"
              left
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  :ripple="false"
                  plain
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
            :ripple="false"
            plain
            text
            @click="close"
          >
            취소
          </v-btn>
          <v-btn
            :ripple="false"
            plain
            text
            @click="submit"
          >
            로그인
          </v-btn>
        </v-card-actions>
      </v-card>
    <!-- </v-col>
  </v-row> -->
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'Login',
  data: (): any => ({
    errorMessages: '',
    email: '',
    password: '',
    passwordShow: false,
    formHasErrors: false,
    rules: {
      // required: (v: string) => !!v || '해당 칸을 입력해주세요.',
      // email: (v: string) => /.+@.+/.test(v) || '이메일 형식에 맞게 작성해주세요.',
      // password: (v: string) => /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=]).*$/.test(v) || '비밀번호는 문자/숫자/특수문자를 포함한 8~15자리로 입력해주세요.',
    }
  }),

  computed: {
    form (): Record<string, any> {
      return {
        email: this.email,
        password: this.password,
      }
    },
  },

  watch: {
    name (): void {
      this.errorMessages = ''
    },
  },

  methods: {
    resetForm (): void {
      this.errorMessages = ''
      this.formHasErrors = false

      Object.keys(this.form).forEach(f => {
        this.$refs[f].reset()
      })
    },
    submit (): void {
      // validation
      this.formHasErrors = false
      Object.keys(this.form).forEach(f => {
        if (!this.form[f]) this.formHasErrors = true
        if (!this.$refs[f].validate(true)) this.formHasErrors = true
      })
      if (!this.formHasErrors) {
        // axios login
        this.$store.dispatch('userModule/login', this.form).catch((error: any) => {
          alert(error);
        })
      }
    },
    goToSignup (): void {
      this.$emit('goToSignup')
    },
    close (): void {
      this.$emit('close')
    }
  },
})
</script>

<style scoped>
  .v-btn:hover {
    color: #FF9100 ;
  }
</style>
