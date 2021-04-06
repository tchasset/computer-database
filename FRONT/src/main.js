import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import store from './plugins'
import i18n from './i18n'
import { axios } from '@/api'
import SlidingPagination from 'vue-sliding-pagination'
Vue.config.productionTip = false

const token = localStorage.getItem('user-token')
if (token) {
  axios.defaults.headers.common.Authorization = token
}

new Vue({
  router,
  vuetify,
  store,
  i18n,
  SlidingPagination,
  render: h => h(App)
}).$mount('#app')
