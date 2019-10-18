import Vue from 'vue'
import App from './App.vue'
import './plugins/element.js'
import router from './router'


Vue.config.productionTip = false
import axios from 'axios'
Vue.prototype.$http=axios.create({
  // baseURL:'http://localhost:8080'
})
// 引入mockjs
require('./mock/mock.js')
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
