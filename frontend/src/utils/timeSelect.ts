// 미완성

const TimeSelect = Vue.component("TimeSelect", {
  name: "TimeSelect",
  props: {
    alwaysShow: {
      type: Array,
      default: () => [0, 15, 30, 45]
    },
    color: {
      type: String,
      default: null
    },
    label: {
      type: String,
      default: "Time"
    },
    value: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      expandedMinutes: null,
      minMenu: false
    };
  },
  computed: {
    hour24():number {
      if (!(this.$props as any).value) return 12;
      return Number((this.$props as any).value.split(":")[0]);
    },
    hour: {
      get():number {
        return (this as any).hour24 % 12 === 0 ? 12 : (this as any).hour24 % 12;
      },
      set(value) {
        if (value) {
          const hr = `${((value as any) === 12 ? 0 : (value as any)) +
            ((this as any).isAm ? 0 : 12)}`.padStart(2, "0");
          (this as any).$emit("input", `${hr}:${(this as any).minute}`);
        }
      }
    },
    expandedStart() {
      return (this as any).expandedMinutes
        ? parseInt((this as any).expandedMinutes.split("-")[1])
        : -1;
    },
    expandedEnd() {
      return (this as any).expandedMinutes
        ? parseInt((this as any).expandedMinutes.split("-")[2])
        : -1;
    },
    minuteList() {
      return Array.from(Array(60).keys())
        .map(i => {
          if (
            (this.alwaysShow as any).includes(i) ||
            (i >= this.expandedStart && i <= this.expandedEnd)
          ) {
            return `${i}`.padStart(2, "0");
          } else if ((this.alwaysShow as any).includes(i - 1)) {
            const end = this.alwaysShow.find(a => (a as number) > i) || 59;
            return `ellipsis-${i}-${end}`;
          } else {
            return false;
          }
        })
        .filter(i => !!i);
    },
    minute: {
      get() {
        if (!(this as any).$props.value) return "00";
        return this.$props.value.split(":")[1].replace(/\D+/g, "");
      },
      set(value) {
        if (value) {
          (this as any).$emit(
            "input",
            `${("" + (this as any).hour24).padStart(2, "0")}:${value}`
          );
        }
      }
    },
    isAm: {
      get() {
        return (this as any).hour24 < 12;
      },
      set(toAm) {
        if (toAm === undefined || toAm === (this as any).isAm) return; // avoid if not changing
        const hr = `${(this as any).hour24 + (toAm ? -12 : 12)}`.padStart(2, "0");
        (this as any).$emit("input", `${hr}:${(this as any).minute}`);
      }
    }
  },
  methods: {
    onMinuteClick(min: any) {
      const isEllipsis = min.includes("-");
      (this as any).expandedMinutes = isEllipsis ? min : null;
      if (!isEllipsis) {
        (this as any).minMenu = false;
        (this as any).minute = min;
      }
    },
    getMinuteClass(min: any): any {
      return (this as any).alwaysShow.includes(parseInt(min)) ? "font-weight-bold" : "";
    }
  },
  template: `
  <v-input hide-details class="time-select" :color="color">
    <template v-if="label" v-slot:label>
      <div class="caption">{{ label }}</div>
    </template>
    <v-menu offset-y>
      <template v-slot:activator="{ on }">
        <v-btn outlined class="time-hours" :color="color" v-on="on">
          <span>{{ value ? hour : 'H' }}</span>
          <v-icon right>mdi-menu-down</v-icon>
        </v-btn>
      </template>
      <v-list dense>
        <v-list-item
          v-for="hr in 12"
          :key="hr"
          class="pr-0 pl-2"
          @click="hour = hr === 1 ? 12 : hr - 1"
          >{{ hr === 1 ? 12 : hr - 1 }}
        </v-list-item>
      </v-list>
    </v-menu>
    <div class="">:</div>
    <v-menu
      v-model="minMenu"
      offset-y
      max-height="80vh"
      :close-on-content-click="false"
    >
      <template v-slot:activator="{ on: menu }">
        <v-btn outlined class="time-minutes" :color="color" v-on="menu">
          <span>{{ value ? minute : 'M' }}</span>
          <v-icon right>mdi-menu-down</v-icon>
        </v-btn>
      </template>
      <v-list dense>
        <v-list-item
          v-for="min in minuteList"
          :key="min"
          class="pr-0 pl-2"
          :class="getMinuteClass(min)"
          @click="onMinuteClick(min)"
        >
          <v-icon v-if="min.includes('-')">mdi-dots-horizontal</v-icon>
          <span v-else>{{ min }}</span>
        </v-list-item>
      </v-list>
    </v-menu>
    <v-btn-toggle v-model="isAm" class="toggle-am-pm" :color="color">
      <v-btn :value="true" class="btn-am">am</v-btn>
      <v-btn :value="false" class="btn-pm">pm</v-btn>
    </v-btn-toggle>
  </v-input>
  `
});