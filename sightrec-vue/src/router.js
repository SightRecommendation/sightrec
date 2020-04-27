import Vue from 'vue'
import Router from 'vue-router'
import Home from './components/Home.vue'
import UserHome from './components/UserHome.vue'
import Settings from './components/user/Settings.vue'
import Main from './components/Main.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import SightDetail from './components/SightDetail.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      component: Home,
      redirect: '/main',
      meta: {
        title: '主页 - SightLens'
      },
      children: [
        {
          path: '/main',
          component: Main,
          meta: {
            title: '主页 - SightLens'
          }
        },
        {
          path: '/sight/:sightId',
          component: SightDetail,
          meta: {
            title: '景点详情 - SightLens'
          }
        }
      ]
    },
    {
      path: '/user',
      component: UserHome,
      redirect: '/user/settings',
      meta: {
        title: '个人中心 - SightLens'
      },
      children: [
        {
          path: '/user/settings',
          component: Settings,
          meta: {
            title: '基本资料 - SightLens'
          }
        },
        {
          path: '/comments',
          component: Settings,
          meta: {
            title: '我的评论 - SightLens'
          }
        }
      ]
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

  document.title = '登录 - SightLens'
  if (to.path === '/login') {
    return next()
  }
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) {
    return next('/login')
  }
  document.title = to.meta.title
  next()
})

export default router
