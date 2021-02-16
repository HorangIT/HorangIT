import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";

import ProfileForm from "@/components/user/ProfileForm.vue";

import Home from "@/views/Home.vue";
import Layout from "@/views/Layout.vue";
import Auction from "@/views/Auction.vue";
import PostView from "@/views/PostView.vue";
import Detail from "@/views/Detail.vue";
import Notfound from "@/views/Notfound.vue";
import Myauction from "@/views/Myauction.vue";
import Success from "../components/pay/Success.vue";
import Fail from "../components/pay/Fail.vue";

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
      },
      {
        path: "/myauction",
        component: Myauction,
        name: "Myauction"
      },
    ]
  },
  // {
  //   path: "/404",
  //   component: Notfound
  // },
  // test page
  {
    path: "/profile",
    component: ProfileForm
  },
  {
    path: "/api/payment/success",
    component: Success,
    name: "Success"
  },
  {
    path: "/api/payment/fail",
    component: Fail,
    name: "Fail"
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
