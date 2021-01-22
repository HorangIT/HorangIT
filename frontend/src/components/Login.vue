<template>
  <transition name="modal" appear>
    <div class="user modal modal-overlay" id="login" @click.self="$emit('close')">
      <div class="modal-window">
          <h1>원표님 짱짱</h1>      
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
    width: 20vw;
    height: 40vh;
    border-radius: 4px;
    overflow: hidden;
    padding: 30px;
  }

  /* 오버레이 트랜지션 */
  .modal-enter-active {
    transition: opacity 0.4s;
  }

  /* 딜레이가 적용된 모달 윈도가 제거된 후에 오버레이가 사라짐 */
  .modal-leave-active {
    transition: opacity 0.6s ease 0.4s;
  }

  .modal-enter, .modal-leave-to {
    opacity: 0;
  }
</style>

<script lang="ts">
import Vue from "vue";

export default Vue.extend ({
  
});
</script>
