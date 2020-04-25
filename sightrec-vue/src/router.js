import Vue from 'vue'
import Router from 'vue-router'
import Home from './components/Home.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import SightDetail from './components/SightDetail.vue'
Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      component: Home,
      meta: {
        title: '主页 - SightLens'
      }
    },
    {
      path: '/login',
      component: Login,
      meta: {
        title: '登录 - SightLens'
      }
    },
    {
      path: '/register',
      component: Register,
      meta: {
        title: '注册 - SightLens'
      }
    },
    {
      path: '/sight',
      component: SightDetail,
      meta: {
        title: '景点详情 - SightLens'
      }
    }
  ]
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  // next() 放行 next('/login') 强制跳转
  /* 路由发生变化修改页面title */
  document.title = to.meta.title
  next()
})

export default router
