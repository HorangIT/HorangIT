import Vue from "vue";
import Vuex from "vuex";

import { userModule } from "./modules/user";
import { auctionModule } from "./modules/auction";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    userModule,
    auctionModule,
  }
});
