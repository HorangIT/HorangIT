<template>
  <v-sheet
    v-if="!auctionOver && !timeOver"
    height="10vh"
    class="lighten-4 d-flex align-center justify-center"
    :class="{ primary: open, warning: !open }"
  >
    <h3>{{ leftTime }}</h3>
  </v-sheet>
</template>

<script>
export default {
  data: () => ({
    open: false,
    leftTime: "",
    timeOver: false,
  }),
  props: ["start", "end", "auctionOver"],
  mounted() {
    setInterval(() => {
      const start = new Date(this.start);
      const end = new Date(this.end);
      const now = new Date();
      if (start > now) {
        this.open = false;
        this.leftTime = `경매 시작까지 ${this.getLeftTimeFromNow(start)} 남았습니다.`
      } else if (end > now) {
        this.open = true;
        this.leftTime = `경매 마감까지 ${this.getLeftTimeFromNow(end)} 남았습니다.`
      } else {
        this.timeOver = true;
      }
    }, 1000);
  },
  methods: {
    getLeftTimeFromNow(time) {
      const now = new Date();
      const diffTimeInSeconds = (time - now) / 1000;
      const leftDays = Math.floor(diffTimeInSeconds / 60 / 60 / 24);
      const leftHours = Math.floor(diffTimeInSeconds / 60 / 60 % 24);
      const leftMinutes = Math.floor(diffTimeInSeconds / 60 % 60);
      const leftSeconds = Math.floor(diffTimeInSeconds % 60);
      let leftTime = "";
      if (leftDays) {
        leftTime += `${leftDays}일 `;
      }
      if (leftHours) {
        leftTime += `${leftHours}시간 `;
      }
      if (leftMinutes) {
        leftTime += `${leftMinutes}분 `;
      }
      if (leftSeconds) {
        leftTime += `${leftSeconds}초 `;
      }
      return leftTime.trim();
    }
  }
};
</script>
