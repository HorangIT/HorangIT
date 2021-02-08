<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="600px"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        color="white"
        depressed
        v-bind="attrs"
        v-on="on"
      >
        {{ purpose === 'login' ? '로그인' : '회원가입' }}
      </v-btn>
    </template>
    <LoginForm v-if="purpose === 'login'" @close="dialog = false" @goToSignup="purpose = 'signup'" />
    <SignupForm v-else @close="dialog = false" @goToLogin="purpose = 'login'" />
  </v-dialog>
</template>


<script lang="ts">
import Vue from "vue";
import LoginForm from "../../components/user/LoginForm.vue";
import SignupForm from "../../components/user/SignupForm.vue";

export default Vue.extend({
  data() {
    return {
      dialog: false,
    };
  },
  props: [
    'purpose',
  ],
  components: {
    LoginForm,
    SignupForm,
  },
});
</script>
