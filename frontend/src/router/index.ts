import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Home from "@/components/Home.vue";
import Blog from '@/components/Blog.vue';
import Layout from '@/components/Layout.vue';
import Shop from '@/components/Shop.vue';
import Product from '@/components/Product.vue';
import Post from '@/components/Post.vue';
import Cart from '@/components/Cart.vue';

import PostView from '@/views/PostView.vue';

import LoginForm from '@/components/user/LoginForm.vue'
import SignupForm from '@/components/user/SignupForm.vue'

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    component: Layout,
    children:[
      {
        path:'/',
        component:Home,
        name:'Home'
      },
      {
        path:'/shop',
        component:Shop,
        name:'Shop'
      },
      {
        path:'/product',
        component:Product,
        name:'Product'
      },
      {
        path:'/blog',
        component:Blog,
        name:'Blog'
      },
      {
        path:'/post',
        component:Post,
        name:'Post'
      },
      {
        path:'/cart',
        component:Cart,
        name:'Cart'
      },
      {
        path:'/postview',
        component:PostView,
        name:'PostView'
      },
    ]
  },
  // {
  //   path: "/about",
  //   name: "About",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/About.vue")
  // }

  // test page
  {
    path:'/signup',
    component: SignupForm,
  },
  {
    path:'/login',
    component: LoginForm,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
