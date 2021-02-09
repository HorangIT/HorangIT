<template>
  <div>
    <v-card
      style="overflow-x: hidden; overflow-y: auto;"
      min-height="25vh"
      max-height="25vh"
      elevation="0"
    >
      <v-list
        two-line
      >
        <template v-for="(chat, index) in chatLog">
          <!-- 날짜선 -->
          <!-- <v-subheader
            :key="index"
          ></v-subheader> -->
          <v-list-item
            :key="index"
          >
            <v-list-item-avatar>
              <v-img src="../../assets/img/avatar/avatar_male.svg"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title v-text="chat.nickname"></v-list-item-title>
              <v-list-item-subtitle v-text="momentTest"></v-list-item-subtitle>
              {{ chat.content }}
            </v-list-item-content>
          </v-list-item>
          <!-- 경계선 -->
          <v-divider
            :key="index"
            inset
          ></v-divider>
        </template>
      </v-list>
    </v-card>
    <v-text-field class="d-flex-column justify-content-end" v-model="chatInput" placeholder="" @keydown.enter="submit">
      <v-icon slot="prepend">
        mdi-greater-than
      </v-icon>
      <v-icon slot="append" @click="submit">
        mdi-keyboard-return
      </v-icon>
    </v-text-field>
  </div>
</template>

<script lang="ts">
// import io from " lang="tssocket.io-client";
import Vue from 'vue';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import moment from 'moment';;
moment.locale('ko');

export default Vue.extend({
  created(): void {
    console.log(this.stompClient)
    this.stompClient.connect({},
      // onConnect callback
      () => {
        console.log('onConnect');
        this.stompClient.subscribe('/topic/public', (response: any) => {
          console.log('im subscribe response callback');
          console.log(response);
        });
        this.stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: this.username, type: 'JOIN'}));

      },
      (onError: any) => {
        console.log('onError');
        console.log(onError);
      });
  },
  data (): Record<string, any> {
    return {
      // userId: user.id,
      username:'testuser',
      chatInput: "",
      chatLog: [],
      // chatSocket: io("https://powerticket-socket-chat.herokuapp.com/"),
      stompClient: Stomp.over(new SockJS("http://localhost:8000/api/ws")),
      momentTest: moment().format('YYYY년 MMMM Do HH:mm:ss'),
    };
  },
  updated(): void {
    // Always scroll down
    // const scroll: any = this.$el.querySelector(".chatScroll");
    // scroll.scrollTop = scroll.scrollHeight;
  },
  computed: {
    user(): Record<string, any> {
      const user = JSON.parse(localStorage.getItem("user") || "{}");
      console.log("user");
      console.log(user);
      if (user) {
        return user.object.user;
      }
      return {
        id: "anonymous",
        nickname: "익명"
      };
    }
  },
  methods: {
    submit (): void {
      if (this.chatInput && this.stompClient) {
        const chatMessage = {
            sender: this.username,
            content: this.chatInput,
            type: 'CHAT'
        };
        this.stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        this.chatInput = '';
      }
    },
  },
});
</script>

<style scoped>
  .overflow-only-y {
    overflow-y: auto;
    overflow-x: hidden;
  }
</style>