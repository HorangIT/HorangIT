<template>
  <v-row justify="center">
    <v-col cols="10">
      <v-card ref="form">
        <v-card-title>로그인</v-card-title>
        <v-card-text>
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
        </v-card-text>
        <v-divider class="mt-12"></v-divider>
        <v-card-actions>
          <v-btn
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
            로그인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import axios from 'axios'

const API_BASE_URL = process.env.VUE_APP_API_BASE_URL

export default {
  name: 'Login',
  data: () => ({
    errorMessages: '',
    email: '',
    password: '',
    passwordShow: false,
    formHasErrors: false,
    rules: {
      required: v => !!v || '해당 칸을 입력해주세요.',
      email: v => /.+@.+/.test(v) || '이메일 형식에 맞게 작성해주세요.',
      password: v => /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=]).*$/.test(v) || '비밀번호는 문자/숫자/특수문자를 포함한 8~15자리로 입력해주세요.',
    }
  }),

  computed: {
    form () {
      return {
        email: this.email,
        password: this.password,
      }
    },
  },

  watch: {
    name () {
      this.errorMessages = ''
    },
  },

  methods: {
    resetForm () {
      this.errorMessages = ''
      this.formHasErrors = false

      Object.keys(this.form).forEach(f => {
        this.$refs[f].reset()
      })
    },
    submit () {
      // validation
      this.formHasErrors = false
      Object.keys(this.form).forEach(f => {
        if (!this.form[f]) this.formHasErrors = true
        if (!this.$refs[f].validate(true)) this.formHasErrors = true
      })
      if (!this.formHasErrors) {
        // axios post
        const credentials = {
          email: this.email,
          password: this.password,
        }
        axios.post(`${API_BASE_URL}/account/login/`, credentials)
          .then(response => console.log(response))
          .catch(response => console.log(response))
      }
    },
    goToSignup () {
      this.$emit('goToSignup')
    }
  },
}
</script>