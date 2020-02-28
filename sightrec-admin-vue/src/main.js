import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
import axios from 'axios'

Vue.prototype.$http = axios

Vue.config.productionTip = false
axios.defaults.baseURL = 'https://www.liulongbin.top:8888/api/private/v1/'
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
