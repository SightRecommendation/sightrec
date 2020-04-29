import Vue from "vue";
import Router from "vue-router";
import Login from "./components/Login.vue";
import Home from "./components/Home.vue";
import Welcome from "./components/Welcome.vue";
import Users from "./components/user/Users.vue";
import Sights from "./components/sight/Sights.vue";
import Comments from "./components/comment/Comments.vue";

Vue.use(Router);

const router = new Router({
  routes: [
    {path: "/", redirect: "/login"},
    {
      path: "/login",
      component: Login,
      meta: {
        title: "登录 - 景点管理系统"
      }
    },
    {
      path: "/home",
      component: Home,
      redirect: "/welcome",
      children: [
        {
          path: "/welcome",
          component: Welcome,
          meta: {
            title: "欢迎 - 景点管理系统"
          }
        },
        {
          path: "/users",
          component: Users,
          meta: {
            title: "用户管理 - 景点管理系统"
          }
        },
        {
          path: "/sights",
          component: Sights,
          meta: {
            title: "景点管理 - 景点管理系统"
          }
        },
        {
          path: "/comments",
          component: Comments,
          meta: {
            title: "评论管理 - 景点管理系统"
          }
        }
      ]
    }
  ]
});

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  // next() 放行 next('/login') 强制跳转

  document.title = "登录 - 景点管理系统";
  if (to.path === "/login") {
    return next();
  }
  // 获取token
  const tokenStr = window.sessionStorage.getItem("token");
  if (!tokenStr) {
    return next("/login");
  }
  /* 路由发生变化修改页面title */
  document.title = to.meta.title;
  next();
});

export default router;
