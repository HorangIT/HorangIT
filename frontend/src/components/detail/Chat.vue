<template>
  <div>
    <v-card
      style="overflow-x: hidden; overflow-y: auto;"
      min-height="40vh"
      max-height="40vh"
      elevation="0"
      class="chatScroll"
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
            
            :key="index+'a'"
          >
            <v-list-item-avatar>
              <v-img src="../../assets/img/avatar/avatar_male.svg"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title v-text="chat.userNickname"></v-list-item-title>
              <v-list-item-subtitle v-text="chat.chatCreatedAt"></v-list-item-subtitle>
              {{ chat.chatContent }}
            </v-list-item-content>
          </v-list-item>
          <!-- 경계선 -->
          <v-divider
            :key="index+'b'"
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
import moment from 'moment';
import { itemApi } from "../../utils/axios";
moment.locale('ko');

export default Vue.extend({
  created(): void {
    // 채팅로그 확인
    this.getChatLog();
    // 유저 로그인 확인
    if (this.user !== null) {  
      // WebSocket connect
      this.stompClient.connect({},
        // onConnect callback
        () => {
          console.log('onConnect');
          // 채팅방 구독
          this.stompClient.subscribe(`/topic/chat/${this.itemId}`, (message: any) => {
            console.log('im subscribe response callback');
            // chatLog의 낱개로 된 똑같은 dataset의 응답이 필요
            const subscribedMessageBody = JSON.parse(message.body)
            const subscribedMessage = {
              ...subscribedMessageBody.content,
              userId: subscribedMessageBody.sender,
              chatCreatedAt: moment(subscribedMessageBody.content.chatCreatedAt).calendar(),
              userNickname: Number(subscribedMessageBody.sender) === this.user.id ? subscribedMessageBody.content.userNickname + ' (본인)' : subscribedMessageBody.content.userNickname,
            }
            console.log(Number(subscribedMessageBody.sender), this.user.id)
            this.chatLog.push(subscribedMessage)
            // console.log(JSON.parse(message.body).content);
          });
          // this.stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: this.user.nickname, type: 'JOIN'}));
        },
        (onError: any) => {
          alert(onError);
        });
    }
  },
  data (): Record<string, any> {
    return {
      itemId: this.$route.params.id,
      chatInput: "",
      chatLog: [],
      // stompClient: (new SockJS("http://localhost:8000/api/ws")),
      // stompClient: (new SockJS("http://i4a101.p.ssafy.io:8000/api/ws")),
      stompClient: Stomp.over(new SockJS(`${process.env.VUE_APP_API_SERVER}/ws`)),
      momentTest: moment().format('YYYY년 MMMM Do HH:mm:ss'),
    };
  },
  computed: {
    user (): any {
      if (this.$store.state.userModule.user !== null) {
        return this.$store.state.userModule.user.object.user;
      }
      return null;
    }
  },
  updated(): void {
    // Always scroll down
    const scroll: any = this.$el.querySelector(".chatScroll");
    scroll.scrollTop = scroll.scrollHeight;
  },
  watch: {
    user (): any {
      this.getChatLog();
    }    
  },
  methods: {
    // 채팅로그 확인
    getChatLog (): void {
      itemApi.getChatLog(this.itemId)
        .then((response: any) => {
          this.chatLog = response.data.object.log
          this.chatLog.forEach((chat: any) => {
            chat.chatCreatedAt = moment(chat.chatCreatedAt).calendar()
            if (this.user !== null && Number(chat.userId) === this.user.id) {
              chat.userNickname += ' (본인)'
            }
          })
        })
    },
    // Chat send
    submit (): void {
      if (!this.$store.state.userModule.user) {
        alert("로그인을 해주세요!");
        return;
      }
      if (this.chatInput && this.stompClient) {
        const chatMessage = {
            sender: this.user.id,
            content: this.chatInput,
            type: 'CHAT',
        };
        this.stompClient.send(`/app/chat.sendMessage/${this.itemId}`, {}, JSON.stringify(chatMessage));
        this.chatInput = '';
      }
    },
  },
});
</script>
