import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import AdminView from "@/views/user/AdminView.vue";
import LayoutView from "@/views/LayoutView.vue";
import RegisterView from "@/views/RegisterView.vue";
import NoticeView from "@/views/NoticeView.vue";
import AuditView from "@/views/user/AuditView.vue";
import UserView from "@/views/person/UserView.vue";
import InfoView from "@/views/person/InfoView.vue";
import AddPostView from "@/views/Post/AddPostView.vue";
import MyPostView from "@/views/person/MyPostView.vue";
import AllPostView from "@/views/Post/AllPostView.vue";
import BlogView from "@/views/Post/PostView.vue";
import MyFavoriteView from "@/views/person/MyFavoriteView.vue";
import MyFanView from "@/views/person/MyFanView.vue";
import MyFollowView from "@/views/person/MyFollowView.vue";
import postCategoryView from "@/views/Post/PostCategoryView.vue";

Vue.use(VueRouter)
// 每一个路由都对应一个组件,将组件渲染到 router-view 中
const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/',
    name: 'layout',
    component: LayoutView,
    children: [
      {
        path: '/admin',
        name: 'admin',
        component: AdminView
      },
      {
        path: '/',
        name: 'home',
        component: HomeView
      },
      {
        path: '/notice',
        name: 'notice',
        component: NoticeView
      },
      {
        path: '/post/category',
        name: 'postCategory',
        component: postCategoryView,
      },
      {
        path: '/audit',
        name: 'audit',
        component: AuditView
      },
      {
        path: '/addpost',
        name: 'addpost',
        component: AddPostView
      },
      {
        path: '/allpost',
        name: 'allpost',
        component: AllPostView,
      },
      {
        path: '/view/:id',
        name: 'view',
        component: BlogView,
      },
      {
        path: '/user/:id',
        name: 'user',
        component: UserView,
        children:[
          {
            path:'/user/info/:id',
            name: 'info',
            component: InfoView,
          },
          {
            path: '/user/post/:id',
            name: 'post',
            component: MyPostView
          },
          {
            path: '/user/favorite/:id',
            name: 'favorite',
            component: MyFavoriteView
          },
          {
            path: '/user/myFan/:id',
            name: 'myFan',
            component: MyFanView
          },
          {
            path: '/user/myFollow/:id',
            name: 'myFollow',
            component: MyFollowView
          }
        ]
      }
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
// 路由守卫,防止别人不登陆直接通过路由进入首页
router.beforeEach((to, from, next) => {
  if(to.path === '/login' || to.path === '/register'){
    next();
  }
  const user = localStorage.getItem("user");
  if(!user && to.path !== '/login' && to.path !== "/register") {
    return next('/login');
  }
  next();
})
export default router
