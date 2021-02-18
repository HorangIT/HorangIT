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
import Chatroom from "@/components/myauction/Chatroom.vue";
import Success from "../components/pay/Success.vue";
import Fail from "../components/pay/Fail.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    component: Layout,
    meta: { title: '호랑 it' },
    children: [
      {
        path: "/",
        component: Home,
        name: "Home",
        meta: { title: '호랑 it' }
      },
      {
        path: "/detail/:id",
        component: Detail,
        name: "Detail",
        meta: { title: '호랑 it - 제품 상세' }
      },
      {
        path: "/postview",
        component: PostView,
        name: "PostView",
        meta: { title: '호랑 it - 판매 등록' }
      },
      {
        path: "/auction",
        component: Auction,
        name: "Auction",
        meta: { title: '호랑 it - 경매' }
      },
      {
        path: "/myauction",
        component: Myauction,
        name: "Myauction",
        meta: { title: '호랑 it - 내 경매' }
      },
    ]
  },
  {
    path: "/404",
    component: Notfound
  },
  {
    path: "/chatroom/:id",
    component: Chatroom,
    name: "Chatroom",
    props: true,
    meta: { title: '호랑 it - 개인 채팅' }
  },
  // {
    //   path: "/404",
    //   component: Notfound
    // },
    // test page
    {
      path: "/profile",
      component: ProfileForm,
      meta: { title: '호랑 it - 개인 정보' }
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
