<template>
  <div>
    <v-card
      style="overflow-x: hidden; overflow-y: auto;"
      min-height="50vh"
      max-height="50vh"
      elevation="0"
      class="chatScroll"
    >
      <v-toolbar>
        <v-btn
          @click="$emit('close')"
          plain
        ><v-icon>mdi-arrow-collapse-left</v-icon></v-btn>
        1:1 채팅방
      </v-toolbar>
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
  data (): Record<string, any> {
    return {
      itemId: this.$route.params.id,
      chatInput: "",
      chatLog: [],
      stompClient: Stomp.over(new SockJS(`${process.env.VUE_APP_API_SERVER}/ws`)),
      momentTest: moment().format('YYYY년 MMMM Do HH:mm:ss'),
    };
  },
  props: [
    'chatInfo'
  ],
  created(): void {
    console.log('내 아이디는 ' + this.chatInfo.myId)
    console.log('이 아이템은 ' + this.chatInfo.itemId)
    // 채팅로그 확인
    this.getChatRoomLog();
    // 유저 로그인 확인
    if (this.user !== null) {  
      // WebSocket connect
      this.stompClient.connect({},
        // onConnect callback
        () => {
          console.log('onConnect');
          // 채팅방 구독
          this.stompClient.subscribe(`/queue/room/${this.chatInfo.itemId}`, (message: any) => {
          // this.stompClient.subscribe(`/topic/chat/?string/?number`, (message: any) => {
            console.log('im subscribe response callback');
            // chatLog의 낱개로 된 똑같은 dataset의 응답이 필요
            const subscribedMessageBody = JSON.parse(message.body)
            const subscribedMessage = {
              ...subscribedMessageBody.content,
              userId: subscribedMessageBody.sender,
              chatCreatedAt: moment(subscribedMessageBody.content.chatCreatedAt).calendar(),
              // 판매자인지 확인하는 조건 필요
              userNickname: Number(subscribedMessageBody.sender) === this.user.id ? subscribedMessageBody.content.userNickname + ' (본인)' : subscribedMessageBody.content.userNickname,
            }
            console.log(Number(subscribedMessageBody.sender), this.user.id)
            this.chatLog.push(subscribedMessage)
            console.log(JSON.parse(message.body).content);
          });
          this.stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: this.user.nickname, type: 'JOIN'}));
        },
        (onError: any) => {
          console.log(onError);
        });
    }
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
      this.getChatRoomLog();
    }    
  },
  methods: {
    // 채팅로그 확인
    getChatRoomLog (): void {
      console.log('getChatRoomLog()')
      itemApi.getChatRoomLog(this.chatInfo.itemId)
        .then((response: any) => {
          this.chatLog = response.data.object.log
          console.log(response)
          console.log(this.chatLog, '이거이거이거')
          this.chatLog.forEach((chat: any) => {
            chat.chatCreatedAt = moment(chat.chatCreatedAt).calendar()
            // 판매자 확인 조건 필요
            if (this.user !== null && Number(chat.userId) === this.user.id) {
              chat.userNickname += ' (본인)'
            }
          })
        })
    },
    // Chat send
    submit (): void {
      if (this.chatInput && this.stompClient) {
        const chatMessage = {
            sender: this.user.id,
            content: this.chatInput,
            type: 'CHAT',
        };
        this.stompClient.send(`/app/room.sendMessage/${this.chatInfo.itemId}`, {}, JSON.stringify(chatMessage));
        // this.stompClient.send(`/app/chat.sendMessage/?string/?number`, {}, JSON.stringify(chatMessage));
        this.chatInput = '';
      }
    },
  },
});
</script>
