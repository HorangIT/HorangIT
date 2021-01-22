<template>
  <transition name="modal" appear>
    <div class="modal-overlay" id="login" @click.self="$emit('close')">
      <div v-if="loginShow" class="modal-window">
        <LoginForm @goToSignup="goToSignup" />
      </div>
      <div v-else class="modal-window">
        <SignupForm @goToLogin="goToLogin" />
      </div>
    </div>
  </transition>
</template>

<style>
.modal-overlay {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  z-index: 30;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}

.modal-window {
  background: #fff;
  width: 40vw;
  height: auto;
  padding: 1vh 0;
  border-radius: 4px;
  overflow: hidden;
}

/* 오버레이 트랜지션 */
.modal-enter-active {
  transition: opacity 0.4s;
}

/* 딜레이가 적용된 모달 윈도가 제거된 후에 오버레이가 사라짐 */
.modal-leave-active {
  transition: opacity 0.6s ease 0.4s;
}

.modal-enter,
.modal-leave-to {
  opacity: 0;
}
</style>

<script lang="ts">
import Vue from "vue";
import LoginForm from "../user/LoginForm.vue";
import SignupForm from "../user/SignupForm.vue";

export default Vue.extend({
  data() {
    return {
      loginShow: true
    };
  },
  components: {
    LoginForm,
    SignupForm
  },
  methods: {
    goToLogin() {
      this.loginShow = true;
    },
    goToSignup() {
      this.loginShow = false;
    }
  }
});
</script>
