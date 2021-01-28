<template>
  <div>
    Chat
    <ul class="chat-messages">
      <li v-for="(chat, index) in chatLog" :key="index">
        {{ chat }}
      </li>
    </ul>
    <form class="chat-form" @submit="submit">
      <input class="chat-input" autocomplete="off" v-model="chat" /><button>Send</button>
    </form>
  </div>
</template>

<script>
import io from 'socket.io-client'


export default {
  created () {
    const chatMessages = document.querySelector('.chat-messages');
    this.chatSocket.on('chat message to client', message => {
      this.chatLog.push(message);
    });    
  },
  data () {
    return {
      chat: '',
      chatLog: [],
      chatSocket: io('https://powerticket-socket-chat.herokuapp.com/'),
    }
  },
  methods: {
    submit (event) {
      event.preventDefault();
      if (this.chat) {
        console.log(this.chatSocket)
        this.chatSocket.emit('chat message from client', this.chat);
        this.chat = '';
      }
    }
  }
}
</script>

<style scoped>
  body { margin: 0; padding-bottom: 3rem; font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif; }

  .chat-form { background: rgba(0, 0, 0, 0.15); padding: 0.25rem; display: flex; height: 3rem; box-sizing: border-box; backdrop-filter: blur(10px); }
  .chat-input { border: none; padding: 0 1rem; flex-grow: 1; border-radius: 2rem; margin: 0.25rem; }
  .chat-input:focus { outline: none; }
  .chat-form > button { background: #333; border: none; padding: 0 1rem; margin: 0.25rem; border-radius: 3px; outline: none; color: #fff; }

  .chat-messages { list-style-type: none; margin: 0; padding: 0; }
  .chat-messages > li { padding: 0.5rem 1rem; }
  .chat-messages > li:nth-child(odd) { background: #efefef; }
</style>