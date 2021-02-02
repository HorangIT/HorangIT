<template>
  <div>
    <div class="chatScroll" style="height: 30vh; overflow: auto;">
      Chat
      <!-- 자신의 메시지는 오른쪽 정렬, user 정보 확인 필요 -->
      <!-- 가로 길이 길어졌을 때, 글씨 깨지는 현상 고쳐야 함 -->
      <div v-for="(chat, index) in chatLog" :key="index" :class="{ 'text-right ': chat.userId === userId }">
        <v-chip dark style="height: auto; white-space: normal;" class="pa-4 mb-2" :class="{ 'primary': chat.userId === userId }">
          <strong>{{ chat.nickname }}</strong>
          {{ chat.content }}
          <sub class="ml-2">
            <!-- 서버에서 받아온 created_at으로 변경 -->
            {{ new Date() }}
          </sub>
        </v-chip>
      </div>
    </div>
    <v-text-field v-model="chatInput" placeholder="" @keydown.enter="submit">
      <v-icon
        slot="prepend"
      >
        mdi-greater-than
      </v-icon>
      <v-icon
        slot="append"
        @click="submit"
      >
        mdi-keyboard-return
      </v-icon>
    </v-text-field>
  </div>
</template>

<script>
import io from 'socket.io-client'
// 로그인 안했을 때, 예외 처리 필요
const user = JSON.parse(localStorage.getItem('user')).object.user

export default {
  created () {
    this.chatSocket.on('chat message to client', message => {
      this.chatLog.push(message);
    });    
  },
  data () {
    return {
      userId: user.id,
      chatInput: '',
      chatLog: [],
      chatSocket: io('https://powerticket-socket-chat.herokuapp.com/'),
    }
  },
  updated () {
    // Always scroll down
    const scroll = this.$el.querySelector('.chatScroll')
    scroll.scrollTop = scroll.scrollHeight;
  },
  methods: {
    // 채팅 submit 서버 socket에 emit
    submit (event) {
      event.preventDefault();
      if (this.chatInput) {
        console.log(this.chatSocket);
        const chatObj = {
          userId: user.id,
          nickname: user.nickname,
          content: this.chatInput,
        }
        this.chatSocket.emit('chat message from client', chatObj);
        this.chatInput = '';
      }
    }
  }
}
</script>
