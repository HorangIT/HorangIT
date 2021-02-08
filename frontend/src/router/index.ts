import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";

import ProfileForm from "@/components/user/ProfileForm.vue";

import Home from "@/views/Home.vue";
import Layout from "@/views/Layout.vue";
import Auction from "@/views/Auction.vue";
import PostView from "@/views/PostView.vue";
import Detail from "@/views/Detail copy.vue";
import Notfound from "@/views/Notfound.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    component: Layout,
    children: [
      {
        path: "/",
        component: Home,
        name: "Home"
      },
      {
        path: "/detail/:id",
        component: Detail,
        name: "Detail"
      },
      {
        path: "/postview",
        component: PostView,
        name: "PostView"
      },
      {
        path: "/auction",
        component: Auction,
        name: "Auction"
      }
    ]
  },
  // test page
  {
    path: "/profile",
    component: ProfileForm
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
