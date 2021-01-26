<template>
  <v-row justify="center">
    <v-col cols="10">
      <v-card ref="form">
        <v-card-title>회원가입</v-card-title>
        <v-card-text>
          <v-text-field
            ref="nickname"
            v-model="nickname"
            :rules="[rules.required, rules.nickname]"
            :error-messages="errorMessages"
            label="닉네임"
            placeholder="호잇하마"
            required
          ></v-text-field>
          <v-text-field
            ref="email"
            v-model="email"
            :rules="[rules.required, rules.email]"
            :error-messages="errorMessages"
            label="이메일"
            placeholder="hoit_hippo@gmail.com"
            required
          ></v-text-field>
          <v-text-field
            ref="password"
            v-model="password"
            :rules="[rules.required, rules.password]"
            :error-messages="errorMessages"
            label="비밀번호"
            placeholder="******** (문자/숫자/특수문자 포함 8~15자리)"
            :append-icon="passwordShow ? 'mdi-eye': 'mdi-eye-off'"
            :type="passwordShow ? 'text' : 'password'"
            @click:append="passwordShow = !passwordShow"
            required
          ></v-text-field>
          <v-text-field
            ref="passwordConfirm"
            v-model="passwordConfirm"
            :rules="[rules.required, rules.password, passwordConfirmRule]"
            :error-messages="errorMessages"
            label="비밀번호"
            placeholder="******** (문자/숫자/특수문자 포함 8~15자리)"
            :append-icon="passwordConfirmShow ? 'mdi-eye': 'mdi-eye-off'"
            :type="passwordConfirmShow ? 'text' : 'password'"
            @click:append="passwordConfirmShow = !passwordConfirmShow"
            @keypress.enter="submit"
            required
          ></v-text-field>
        </v-card-text>
        <v-divider class="mt-12"></v-divider>
        <v-card-actions>
          <v-btn
            text
            @click="goToLogin"
          >
            로그인으로 돌아가기
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
            회원가입
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  data: (): any => ({
    errorMessages: '',
    nickname: '',
    email: '',
    password: '',
    passwordConfirm: '',
    passwordShow: false,
    passwordConfirmShow: false,
    formHasErrors: false,
    rules: {
      // required: (v: string) => !!v || '해당 칸을 입력해주세요.',
      // nickname: (v: string) => (v.length >= 2 && v.length <= 10) || '닉네임은 2~10자로 설정해주세요.',
      // email: (v: string) => /.+@.+/.test(v) || '이메일 형식에 맞게 작성해주세요.',
      // password: (v: string) => /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=]).*$/.test(v) || '비밀번호는 문자/숫자/특수문자를 포함한 8~15자리로 입력해주세요.',
    }
  }),

  computed: {
    form (): Record<string, any> {
      return {
        nickname: this.nickname,
        email: this.email,
        password: this.password,
        passwordConfirm: this.passwordConfirm,
      }
    },
    passwordConfirmRule () {
      return this.password === this.passwordConfirm || '비밀번호를 다시 확인해주세요.'
    }
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
      this.formHasErrors = false;
      Object.keys(this.form).forEach(f => {
        if (!this.form[f]) this.formHasErrors = true;
        if (!this.$refs[f].validate(true)) this.formHasErrors = true;
      });
      if (!this.formHasErrors) {
        // axios post
        const user = {
          email: this.email,
          nickname: this.nickname,
          password: this.password,
        }
        console.log('signup methods')
        console.log(user)
        this.$store.dispatch('userModule/signup', user).then(() => {
          alert('회원가입이 완료되었습니다.');
          this.goToLogin();
        })
      }
    },
    goToLogin (): void {
      this.$emit('goToLogin')
    }
  },
})
</script>