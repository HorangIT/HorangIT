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

<script>
// import io from "socket.io-client";
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import moment from 'moment';
moment.locale('ko')

export default {
  created() {
    console.log(this.socket)
    console.log(this.stompClient)
    // this.stompClient.connect();
      // onConnect => {
      //   console.log('onConnect');
      //   // console.log(onConnect);
      // },
      // onError => {
      //   console.log('onError');
      //   // console.log(onError);
      // });

    // this.chatSocket.on("chat message to client", message => {
    //   this.chatLog.push(message);
    // });
  },
  data() {
    return {
      // userId: user.id,
      chatInput: "",
      chatLog: [],
      // chatSocket: io("https://powerticket-socket-chat.herokuapp.com/"),
      socket: new SockJS("http://localhost:8000/api/ws/"),
      stompClient: Stomp.over(new SockJS("http://localhost:8000/api/ws/")),
      momentTest: moment().format('YYYY년 MMMM Do HH:mm:ss'),
    };
  },
  updated() {
    // Always scroll down
    const scroll = this.$el.querySelector(".chatScroll");
    scroll.scrollTop = scroll.scrollHeight;
  },
  computed: {
    user() {
      const user = JSON.parse(localStorage.getItem("user"));
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
    // 채팅 submit 서버 socket에 emit
    submit(event) {
      event.preventDefault();
      if (this.chatInput) {
        console.log(this.chatSocket);
        const chatObj = {
          userId: this.user.id,
          nickname: this.user.nickname,
          content: this.chatInput
        };
        this.chatSocket.emit("chat message from client", chatObj);
        this.chatInput = "";
      }
    }
  }
};
</script>

<style scoped>
  .overflow-only-y {
    overflow-y: auto;
    overflow-x: hidden;
  }
</style>